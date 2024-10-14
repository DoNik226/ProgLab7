package models.validators;
/**
 * Implementation of validator for averagePoint field. (LabWork)
 *
 * @since 1.0
 * @author Nikita
 */
public class AveragePointValidator implements Validator<Long> {
    /**
     * Checks if value greater than 0.
     *
     * @see models.Discipline
     * @param value averagePoint to validate
     * @return true/false -- matches the restrictions
     */
    @Override
    public boolean validate(Long value) {
        return (value > 0);
    }
}

