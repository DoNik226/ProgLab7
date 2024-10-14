package models.handlers.userMode;

import exceptions.BuildObjectException;
import exceptions.StreamInterruptedException;
import main.Utilities;
import models.Difficulty;
import models.LabWork;
import models.handlers.*;
import models.validators.*;

import java.time.Instant;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Implementation of ModuleHandler for LabWork Model.
 *
 * @since 1.0
 * @author Nikita
 */
public class LabWorkCLIHandler implements ModuleHandler<LabWork> {
    /**
     * Method for create fully validated objects by CLI (userMode).
     *
     * @return Built object
     */
    @Override
    public LabWork buildObject() throws BuildObjectException {
        try {
            System.out.println("Generating object...");
            LabWork result = new LabWork();
            System.out.println("Welcome to master of LabWork object creation!");
            System.out.println("Follow the instructions to setup your object.");
            System.out.println();

            Scanner scanner = new Scanner(System.in);

            //result.setId(LabWorkHandlers.generateID());
            // name
            Validator<String> nameValidator = new NameValidator();
            String name = null;
            do {
                System.out.println("Enter the value of name (Type: String)");
                if (Utilities.hasNextLineOrThrow(scanner)) {
                    String line = scanner.nextLine();
                    if (!line.isEmpty())
                        name = line;
                }
                if (!nameValidator.validate(name)) {
                    System.out.println("Value violates restrictions for field! Try again.");
                    System.out.println("Restrictions: Should be not null and not empty.");
                }
            } while (!nameValidator.validate(name));
            result.setName(name);

            // coords
            System.out.println("Starting coordinates field setup... (Type: Coordinates)");
            CoordinatesCLIHandler coordinatesCLIHandler = new CoordinatesCLIHandler();
            result.setCoordinates(coordinatesCLIHandler.buildObject());



            // minimalPoint
            while (true) {
                try {
                    MinimalPointValidator validator = new MinimalPointValidator();
                    System.out.println("Enter the value of minimalPoint (Type: Integer (default value: 1))");
                    Integer value = 1;
                    if (Utilities.hasNextLineOrThrow(scanner)) {
                        String line = scanner.nextLine();
                        if (!line.isEmpty() && line!=null)
                            value = Integer.parseInt(line);
                    }
                    if (!validator.validate(value)) {
                        System.out.println("Value violates restrictions for field! Try again.");
                        System.out.println("Restrictions: Should be not null and number should be grater than 0.");
                        continue;
                    }
                    result.setMinimalPoint(value);
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Wrong input! Try again.");
                    System.out.println("You should enter a number in range [-2147483648; 2147483647], it shouldn't be decimal.");
                    continue;
                }
                break;
            }

            // tunedInWork
            while (true) {
                try {
                    System.out.println("Enter the value of tunedInWork (Type: long (default value: 0))");
                    long value = 0;
                    if (Utilities.hasNextLineOrThrow(scanner)) {
                        String line = scanner.nextLine();
                        if (!line.isEmpty())
                            value = Long.parseLong(line);
                    }
                    result.setTunedInWorks(value);
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Wrong input! Try again.");
                    System.out.println("You should enter a number in range [-9223372036854775808; 9223372036854775807], it shouldn't be decimal.");
                    continue;
                }
                break;
            }
            // averagePoint
            while (true) {
                try {
                    AveragePointValidator validator = new AveragePointValidator();
                    System.out.println("Enter the value of averagePoint (Type: long (default value: 1))");
                    long value = 1;
                    if (Utilities.hasNextLineOrThrow(scanner)) {
                        String line = scanner.nextLine();
                        if (!line.isEmpty())
                            value = Long.parseLong(line);
                    }
                    if (!validator.validate(value)) {
                        System.out.println("Value violates restrictions for field! Try again.");
                        System.out.println("Restrictions: Number should be grater than 0.");
                        continue;
                    }
                    result.setAveragePoint(value);
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Wrong input! Try again.");
                    System.out.println("You should enter a number in range [-9223372036854775808; 9223372036854775807], it shouldn't be decimal.");
                    continue;
                }
                break;
            }

            // difficulty
            while (true) {
                try {
                    DifficultyValidator validator = new DifficultyValidator();
                    System.out.println("Enter the value of difficulty (Type: Difficulty)");
                    System.out.println("Доступные типы Difficulty: " +Difficulty.NORMAL + ", " + Difficulty.INSANE + ", " + Difficulty.TERRIBLE);
                    Difficulty value = null;
                    if (Utilities.hasNextLineOrThrow(scanner)) {
                        String line = scanner.nextLine();
                        if (!line.isEmpty())
                            value = Difficulty.valueOf(line);
                    }
                    if (!validator.validate(value)) {
                        System.out.println("Value violates restrictions for field! Try again.");
                        System.out.println("Restrictions: the field value must be of the type difficulty.");
                        continue;
                    }
                    result.setDifficulty(value);
                } catch (InputMismatchException | IllegalArgumentException e) {
                    System.out.println("Wrong input! Try again.");
                    System.out.println("You should enter one of the suggested values (NORMAL, INSANE, TERRIBLE).");
                    continue;
                }
                break;
            }


            // discipline
            System.out.println("Starting discipline field setup... (Type: Discipline)");
            DisciplineCLIHandler disciplineCLIHandler = new DisciplineCLIHandler();
            result.setDiscipline(disciplineCLIHandler.buildObject());

            // creationDate
            Date creationDate = Date.from(Instant.now());
            System.out.println("Object created at " + creationDate);
            result.setCreationDate(creationDate);

            System.out.println("Object setup completed! Sending result...");

            return result;

        } catch (StreamInterruptedException e) {
            throw new BuildObjectException("Во время конструирования объекта произошла ошибка: " + e.getMessage());
        }
    }
}