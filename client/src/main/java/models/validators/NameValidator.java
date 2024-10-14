package models.validators;

/**
 * Implementation of validator for name field. (LabWork)
 *
 * @since 1.0
 * @author Nikita
 */
public class NameValidator implements Validator<String> {

    /**
     * Checks if value not null and not blank.
     *
     * @see models.LabWork
     * @param value name to validate
     * @return true/false -- matches the restrictions
     */
    @Override
    public boolean validate(String value) {
        return value != null && !value.isEmpty() && !value.isBlank();
    }
}
