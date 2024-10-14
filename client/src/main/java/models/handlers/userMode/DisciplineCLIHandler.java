package models.handlers.userMode;
import exceptions.BuildObjectException;
import exceptions.StreamInterruptedException;
import main.Utilities;
import models.Discipline;
import models.handlers.ModuleHandler;
import models.validators.*;

import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Implementation of ModuleHandler for Discipline Model.
 *
 * @since 1.0
 * @author Nikita
 */
public class DisciplineCLIHandler implements ModuleHandler<Discipline> {

    /**
     * Method for create fully validated objects by CLI.
     *
     * @return Built object
     */
    @Override
    public Discipline buildObject() throws BuildObjectException {
        try {
            System.out.println("Generating object...");
            Discipline result = new Discipline();
            System.out.println("Welcome to master of Discipline object creation!");
            System.out.println("Follow the instructions to setup your object.");
            System.out.println();

            Scanner scanner = new Scanner(System.in);

            // name
            Validator<String> disciplineNameValidator = new DisciplineNameValidator();
            String name = null;
            do {
                System.out.println("Enter the value of name (Type: String)");
                if (Utilities.hasNextLineOrThrow(scanner)) {
                    String line = scanner.nextLine();
                    if (!line.isEmpty())
                        name = line;
                }
                if (!disciplineNameValidator.validate(name)) {
                    System.out.println("Value violates restrictions for field! Try again.");
                    System.out.println("Restrictions: Should be not null and not empty.");
                }
            } while (!disciplineNameValidator.validate(name));
            result.setName(name);

            // practiceHours
            while (true) {
                try {
                    System.out.println("Enter the value of practiceHours (Type: long (default value: 0))");
                    long value = 0;
                    if (Utilities.hasNextLineOrThrow(scanner)) {
                        String line = scanner.nextLine();
                        if (!line.isEmpty())
                            value = Long.parseLong(line);
                    }
                    result.setPracticeHours(value);
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Wrong input! Try again.");
                    System.out.println("You should enter a number in range [-9223372036854775808; 9223372036854775807], it shouldn't be decimal.");
                    continue;
                }
                break;
            }

            // selfStudyHours
            while (true) {
                try {
                    DisciplineSelfStudyHoursValidator validator = new DisciplineSelfStudyHoursValidator();
                    System.out.println("Enter the value of selfStudyHours (Type: Long (default value: null))");
                    Long value = null;
                    if (Utilities.hasNextLineOrThrow(scanner)) {
                        String line = scanner.nextLine();
                        if (!line.isEmpty() && line!=null)
                            value = Long.parseLong(line);
                    }
                    if (!validator.validate(value)) {
                        System.out.println("Value violates restrictions for field! Try again.");
                        System.out.println("Restrictions: Can be null.");
                        continue;
                    }
                    result.setSelfStudyHours(value);
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Wrong input! Try again.");
                    System.out.println("You should enter a number in range [-9223372036854775808; 9223372036854775807], it shouldn't be decimal.");
                    continue;
                }
                break;
            }

            // labsCount
            while (true) {
                try {
                    DisciplineLabsCountValidator validator = new DisciplineLabsCountValidator();
                    System.out.println("Enter the value of labsCount (Type: Integer (default value: null))");
                    Integer value = null;
                    if (Utilities.hasNextLineOrThrow(scanner)) {
                        String line = scanner.nextLine();
                        if (!line.isEmpty() && line!=null)
                            value = Integer.parseInt(line);
                    }
                    if (!validator.validate(value)) {
                        System.out.println("Value violates restrictions for field! Try again.");
                        System.out.println("Restrictions: Can be null.");
                        continue;
                    }
                    result.setLabsCount(value);
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Wrong input! Try again.");
                    System.out.println("You should enter a number in range [-2147483648; 2147483647], it shouldn't be decimal.");
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
