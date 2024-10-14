package models.handlers.userMode;

import exceptions.BuildObjectException;
import exceptions.StreamInterruptedException;
import main.Utilities;
import models.Coordinates;
import models.handlers.ModuleHandler;
import models.validators.CoordXValidator;
import models.validators.CoordYValidator;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Implementation of ModuleHandler for Coordinates Model.
 *
 * @since 1.0
 * @author Nikita
 */
public class CoordinatesCLIHandler implements ModuleHandler<Coordinates> {

    /**
     * Method for create fully validated objects by CLI.
     *
     * @return Built object
     */
    @Override
    public Coordinates buildObject() throws BuildObjectException {
        try {
            System.out.println("Generating object...");
            Coordinates result = new Coordinates();
            System.out.println("Welcome to master of Coordinates object creation!");
            System.out.println("Follow the instructions to setup your object.");
            System.out.println();

            Scanner scanner = new Scanner(System.in);

            while (true) {
                try {
                    CoordXValidator xValidator = new CoordXValidator();
                    System.out.println("Enter the value of x (Type: int (default value: 0))");
                    int value = 0;
                    if (Utilities.hasNextLineOrThrow(scanner)) {
                        String line = scanner.nextLine();
                        if (!line.isEmpty())
                            value = Integer.parseInt(line);
                    }
                    if (xValidator.validate(value))
                        result.setX(value);
                    else {
                        System.out.println("Value violates restrictions for field! Try again.");
                        System.out.println("Restrictions: IEEE 751 Integer value. Cannot be greater than 178.");
                        continue;
                    }
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Wrong input! Try again.");
                    System.out.println("You should enter a positive real number, matches with IEEE 754 Integer value standard (not so big/small).");
                    continue;
                }
                break;
            }

            while (true) {
                try {
                    CoordYValidator yValidator = new CoordYValidator();
                    System.out.println("Enter the value of y (Type: double)");
                    Double value = null;
                    if (Utilities.hasNextLineOrThrow(scanner)) {
                        String line = scanner.nextLine();
                        if (!line.isEmpty())
                            value = Double.valueOf(line);
                    }
                    if (yValidator.validate(value))
                        result.setY(value);
                    else {
                        System.out.println("Value violates restrictions for field! Try again.");
                        System.out.println("Restrictions: IEEE 754 double number. Not null and should be greater than -725.");
                        continue;
                    }
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Wrong input! Try again.");
                    System.out.println("You should enter a real number, matches with IEEE 754 double value standard (not so big/small).");
                    continue;
                }
                break;
            }

            System.out.println("Object setup completed! Sending result...");

            return result;
        } catch (StreamInterruptedException e) {
            throw new BuildObjectException("Во время конструирования объекта произошла ошибка: " + e.getMessage());
        }
    }
}
