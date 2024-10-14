package mains;

import commandManager.serverCommands.ServerCommandManager;
import databaseLogic.databaseElementLogic.DBCollectionLoader;
import exceptions.UnknownCommandException;
import models.LabWork;
import models.handlers.CollectionHandler;
import models.handlers.LabWorksHandler;
import requestLogic.RequestReader;
import requestLogic.StatusRequest;
import requestLogic.requestWorkers.RequestWorkerManager;
import requestLogic.requests.ServerRequest;
import requests.BaseRequest;
import serverLogic.DatagramServerConnectionFactory;
import serverLogic.ServerConnections;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SuppressWarnings("InfiniteLoopStatement")
public class MainS {
    public static final int PORT = 50456;
    private static final Executor readRqMtExecutor = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        CollectionHandler<HashSet<LabWork>, LabWork> handler = LabWorksHandler.getInstance();

        System.out.println("This is a server!");

        // server command manager
        Thread readServerCommands = new Thread(() -> {
            System.out.println("Started server command reader");
            ServerCommandManager manager = new ServerCommandManager();
            Scanner cmdScanner = new Scanner(System.in);
            while (cmdScanner.hasNext()) {
                String line = cmdScanner.nextLine();
                if (line.isEmpty()) continue;
                try {
                    manager.executeCommand(line.split(" "));
                } catch (UnknownCommandException ex) {
                    System.out.println("Unknown command!");
                }
            }
        });

        readServerCommands.start();

        // load collection
        HashSet<LabWork> loadedCollection = new HashSet<>();
        try (DBCollectionLoader<HashSet<LabWork>> loader = new DBCollectionLoader<>(loadedCollection)) {
            loader.loadFromDB();
            handler.setCollection(loadedCollection);
        } catch (SQLException | IOException e) {
            System.out.println("Something went wrong during collection load: " + e);
        }
        System.out.println("Loaded " + handler.getCollection().size() + " elements total.");
        System.out.println();

        // commands
        System.out.println("Welcome to CLI server! Now you are operating with collection of type " + handler.getCollection().getClass().getName() + ", filled with elements of type " + handler.getFirstOrNew().getClass().getName());
        System.out.println("Now server is listening a requests.");

        // connection
        ServerConnections connection = new DatagramServerConnectionFactory().initializeServer(PORT);
        while (true) {
            try {
                StatusRequest rq = connection.listenAndGetData();
                if (rq.getCode() < 0) {
                    System.out.println("Status code: " + rq.getCode());
                    continue;
                }
                readRqMtExecutor.execute(() -> {
                    try {
                        RequestReader rqReader = new RequestReader(rq.getInputStream());
                        BaseRequest baseRequest = rqReader.readObject();
                        var request = new ServerRequest(baseRequest, rq.getCallerBack(), connection);
                        RequestWorkerManager worker = new RequestWorkerManager();
                        worker.workWithRequest(request);
                    } catch (IOException e) {
                        System.out.println("Something went wrong during I/O " + e);
                    } catch (ClassNotFoundException e) {
                        System.out.println("Class not Found " + e);
                    }
                });
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}