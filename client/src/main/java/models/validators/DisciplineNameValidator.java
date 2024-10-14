package models.validators;
/**
 * Implementation of validator for name field. (Discipline)
 *
 * @since 1.0
 * @author Nikita
 */
public class DisciplineNameValidator implements Validator<String> {
    /**
     * Checks if value not empty.
     *
     * @see models.Discipline
     * @param value name to validate
     * @return true/false -- matches the restrictions
     */
    @Override
    public boolean validate(String value) {
        if (value == null) return false;
        return !(value.isEmpty() || value.isBlank());
    }
}