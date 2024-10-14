package models.validators;

/**
 * Implementation of validator for Coordinates.x field.
 *
 * @since 1.0
 * @author Nikita
 */
public class CoordXValidator implements Validator<Integer> {
    /**
     * Checks if value cannot be greater than 178
     *
     * @see models.Coordinates
     * @param value x to validate
     * @return true/false -- matches the restrictions
     */
    @Override
    public boolean validate(Integer value) {
        return value <= 178 && value >= Integer.MIN_VALUE;
    }
}
