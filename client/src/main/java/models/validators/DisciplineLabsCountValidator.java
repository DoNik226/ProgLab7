package models.validators;
/**
 * Implementation of validator for labsCount field. (Discipline)
 *
 * @since 1.0
 * @author Nikita
 */
public class DisciplineLabsCountValidator implements Validator<Integer> {
    /**
     * Checks if value null or >0.
     *
     * @see models.Discipline
     * @param value labsCount to validate
     * @return true/false -- matches the restrictions
     */
    @Override
    public boolean validate(Integer value) {
        return (value == null || value > 0);
    }
}