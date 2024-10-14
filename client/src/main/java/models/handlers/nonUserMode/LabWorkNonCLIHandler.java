package models.handlers.nonUserMode;

import exceptions.BuildObjectException;
import models.Coordinates;
import models.Difficulty;
import models.Discipline;
import models.LabWork;
import models.handlers.ModuleHandler;
import models.validators.LabWorkValidator;
import models.validators.Validator;

import java.time.Instant;
import java.util.Date;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Implementation of LabWork ModelHandler for non-User Mode.
 *
 * @since 1.1
 * @author Nikita
 */
public class LabWorkNonCLIHandler implements ModuleHandler<LabWork> {
    final Scanner scanner;

    /**
     * Constructor for setup handler's scanner.
     *
     * @param scanner Command scanner for reading argument
     */
    public LabWorkNonCLIHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public LabWork buildObject() throws BuildObjectException {
        System.out.println("Generating object...");
        LabWork result = new LabWork();
        int valuesToRead = 11;
        int coordsIndex = 1;
        int locIndex = 7;
        ArrayList<String> values = new ArrayList<>(valuesToRead);

        for (int i = 0; i < valuesToRead && scanner.hasNextLine(); i++)
        {
            String line = scanner.nextLine();
            if (!line.isEmpty())
                values.add(line);
            else
            {
                values.add(null);

                if (i == coordsIndex)
                {
                    valuesToRead -= 1;
                }

                if (i == locIndex)
                {
                    valuesToRead -= 3;
                }

            }
        }

        try {
            result.setName(values.get(0));
            System.out.println("Name: " + result.getName());
            if (values.get(coordsIndex) != null) {
                System.out.println("Generating coordinates...");
                Coordinates coordinates = new Coordinates();
                coordinates.setX(Integer.parseInt(values.get(coordsIndex)));
                System.out.println("Coords X: " + coordinates.getX());
                coordinates.setY(Double.valueOf(values.get(coordsIndex + 1)));
                System.out.println("Coords Y: " + coordinates.getY());
                result.setCoordinates(coordinates);
            }
            System.out.println("Coords: " + result.getCoordinates());
            result.setMinimalPoint(Integer.valueOf(values.get(3)));
            System.out.println("MinimalPoint: " + result.getMinimalPoint());
            result.setTunedInWorks(Long.parseLong(values.get(4)));
            System.out.println("TunedInWorks: " + result.getTunedInWorks());
            result.setAveragePoint(Long.parseLong(values.get(5)));
            System.out.println("AveragePoint: " + result.getAveragePoint());
            result.setDifficulty(Difficulty.valueOf((values.get(6))));
            System.out.println("Difficulty: " + result.getDifficulty());
            result.setDiscipline(generateDiscipline(locIndex, values));
            System.out.println("Discipline: " + result.getDiscipline());
            result.setCreationDate(Date.from(Instant.now()));
            System.out.println("Generated at: " + result.getCreationDate());
            System.out.println("Object generated! Validating result...");

            Validator<LabWork> validator = new LabWorkValidator();
            if (!validator.validate(result))
            {
                System.out.println("Object's invalid, skipping...");
                throw new BuildObjectException("Созданный элемент нарушает ограничения и не может быть добавлен в коллекцию!");
            }
            System.out.println("Validate successful! Sending result...");
            System.out.println(result);

            return result;

        } catch (NumberFormatException | NullPointerException e)
        {
            System.out.println("Объект будет пропущен. Устраните ошибку в скрипте и повторите попытку.");
            throw new BuildObjectException("Предоставленные данные для построения объекта неверны. Воспользуйтесь ручным вводом или исправьте команду в скрипте.");
        }
    }

    private Discipline generateDiscipline(int locIndex, ArrayList<String> values) {
        Discipline obj = null;
        if (values.get(locIndex) != null)
        {
            System.out.println("Generating discipline...");
            obj = new Discipline();
            obj.setName(values.get(locIndex));
            System.out.println("Name: " + obj.getName());
            obj.setPracticeHours(Long.valueOf(values.get(locIndex + 1)));
            System.out.println("PracticeHours: " + obj.getPracticeHours());
            obj.setSelfStudyHours(Long.valueOf(values.get(locIndex + 2)));
            System.out.println("SelfStudyHours: " + obj.getSelfStudyHours());
            obj.setLabsCount(Integer.valueOf(values.get(locIndex + 3)));
            System.out.println("LabsCount:" + obj.getLabsCount());
        }
        return obj;
    }
}
