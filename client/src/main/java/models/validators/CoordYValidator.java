package models.validators;

/**
 * Implementation of validator for Coordinates.y field.
 *
 * @since 1.0
 * @author Nikita
 */
public class CoordYValidator implements Validator<Double> {
    /**
     * Checks if value cannot be null and should be greater than -725
     *
     * @see models.Coordinates
     * @param value y to validate
     * @return true/false -- matches the restrictions
     */
    @Override
    public boolean validate(Double value) {
        return value != null && value > -725 && value <= Double.MAX_VALUE;
    }
}
