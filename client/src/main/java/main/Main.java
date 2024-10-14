package main;

import authorization.authCredentials.AuthenticationData;
import authorization.authCredentials.RegistrationData;
import commandManager.CommandDescriptionHolder;
import commandManager.CommandExecutor;
import commandManager.CommandLoaderUtility;
import commandManager.CommandMode;
import exceptions.CommandsNotLoadedException;
import requestLogic.requestSenders.AuthorizationRequestSender;
import requestLogic.requestSenders.RegistrationRequestSender;
import responses.AuthorizeResponse;
import serverLogic.*;

import javax.swing.*;
import java.io.Console;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Program entry point class. Contains main() method.
 * This program is managing collection of type <code>HashSet&#8249;LabWork></code>
 *
 * @author Nikita
 * @since 1.0
 */
public class Main {
    public static final int PORT = 50456;

    /**
     * Program entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        // server connecting
        try {
            //ServerConnection connection = new UdpServerConnectionFactory().openConnection(InetAddress.getByName(HOST_ADDRESS), PORT);
            ServerConnection connection = new UdpConnectionBlockDecorator(
                    (UdpServerConnection) new UdpServerConnectionFactory().openConnection(
                            InetAddress.getLocalHost(), PORT), true);
            ServerConnectionHandler.setServerConnection(connection);
            connection.openConnection();

            // authorisation
            AuthorizeResponse response;
            do {
                Console console = System.console();
                String username = null;
                String answer = "Our programming skills are beautiful. Tu tu tututu tututu already gone already gone...";
                if (args.length == 0 || !args[0].equals("-login")) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Do you want to register new account? Enter 'y': ");
                    answer = scanner.nextLine();
                    System.out.println("Hint! If you want to skip this question in future, use this command before run application: ");
                    System.out.println("java -jar client.jar -login [username]");
                }
                if (args.length == 2 && args[0].equals("-login")) username = args[1];
                else username = console.readLine(Objects.equals(answer, "y") ? "Pick a login: " : "Username: ");
                char[] password = console.readPassword(Objects.equals(answer, "y") ? "Pick a password: " : "Password: ");
                if (answer.equals("y")) {
                    String name = console.readLine("What's your name? ");
                    RegistrationData data = new RegistrationData(name, username, password);
                    response = new RegistrationRequestSender().sendRegisterRequest(data);
                } else {
                    AuthenticationData data = new AuthenticationData(username, password);
                    response = new AuthorizationRequestSender().sendLoginRequest(data);
                }
            } while (response == null);
            System.out.println("Authorization successful!");
            System.out.println("Authorized as: " + response.getAuthorizedAs().name() + " (login: " + response.getAuthorizedAs().login() + ")");
            System.out.println("Last login: " + response.getAuthorizedAs().lastLogin());

            // request commands
            boolean commandsNotLoaded = true;
            int waitingCount = 4000;
            while (commandsNotLoaded) {
                try {
                    CommandLoaderUtility.initializeCommands();
                    CommandExecutor executor = new CommandExecutor(CommandDescriptionHolder.getInstance().getCommands(),
                            System.in, CommandMode.CLI_UserMode);
                    commandsNotLoaded = false;

                    // start executing
                    System.out.println("Welcome to CLI! We've established connection to a server.");
                    System.out.println("Now you can enter the commands. Use help for reference.");
                    executor.startExecuting();
                } catch (CommandsNotLoadedException e) {
                    System.out.println("We couldn't get commands from server last time, so now we'll try to do this again...");

                    AtomicInteger secondsRemained = new AtomicInteger(waitingCount / 1000 - 1);

                    javax.swing.Timer timer = new Timer(1000, (x) -> System.out.println("Re-attempt in " +
                            secondsRemained.getAndDecrement() + " seconds."));

                    timer.start();

                    CountDownLatch latch = new CountDownLatch(1);

                    int finalWaitingCount = waitingCount;
                    Runnable wait = () -> {
                        try {
                            Thread.sleep(finalWaitingCount);
                            latch.countDown();
                        } catch (InterruptedException ex) {
                            System.out.println("Continuing...");
                        }
                    };

                    Thread tWait = new Thread(wait);

                    tWait.start();

                    try {
                        latch.await();
                        timer.stop();
                        tWait.interrupt();
                    } catch (InterruptedException ex) {
                        System.out.println("Interrupted");
                    }

                    waitingCount += 2000;
                }
            }
        } catch (UnknownHostException ex) {
            System.out.println("Can't find host.");
        } catch (IOException ex) {
            System.out.println("Something went wrong during IO operations.");
        }
    }
}