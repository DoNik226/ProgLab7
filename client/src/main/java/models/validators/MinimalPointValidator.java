package models.validators;
/**
 * Implementation of validator for minimalPoint field. (LabWork)
 *
 * @since 1.0
 * @author Nikita
 */
public class MinimalPointValidator implements Validator<Integer> {
    /**
     * Checks if value > 0 and not null.
     *
     * @see models.LabWork
     * @param value minimalPoint to validate
     * @return true/false -- matches the restrictions
     */
    @Override
    public boolean validate(Integer value) {
        return (value > 0 && value!=null);
    }
}