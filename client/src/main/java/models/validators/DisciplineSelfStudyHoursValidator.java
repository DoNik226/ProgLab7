package models.validators;
/**
 * Implementation of validator for selfStudyHours field. (Discipline)
 *
 * @since 1.0
 * @author Nikita
 */
public class DisciplineSelfStudyHoursValidator implements Validator<Long> {
    /**
     * Checks if value not null.
     *
     * @see models.Discipline
     * @param value selfStudyHours to validate
     * @return true/false -- matches the restrictions
     */
    @Override
    public boolean validate(Long value) {
        return (value == null || value > 0 || value<=0);
    }
}
