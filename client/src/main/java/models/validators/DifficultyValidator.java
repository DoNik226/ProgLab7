package models.validators;

import models.Difficulty;
/**
 * Implementation of validator for difficulty field. (LabWork)
 *
 * @since 1.0
 * @author Nikita
 */
public class DifficultyValidator implements Validator<Difficulty> {
    /**
     * Checks if value not null.
     *
     * @see models.LabWork
     * @param value difficulty to validate
     * @return true/false -- matches the restrictions
     */
    @Override
    public boolean validate(Difficulty value) {
        return !(value == null);
    }
}