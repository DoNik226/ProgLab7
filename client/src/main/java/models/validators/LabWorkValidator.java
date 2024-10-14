package models.validators;

import models.Coordinates;
import models.Difficulty;
import models.LabWork;

import java.util.Optional;

/**
 * Implementation of validator for all fields. (LabWork)
 *  *
 *  * @since 1.1
 *  * @author Nikita
 */
public class LabWorkValidator implements Validator<LabWork> {
    @Override
    public boolean validate(LabWork labWork) {
        Validator<Long> idValidate = (id) -> id != null && id > 0;

        return idValidate.validate(labWork.getId())
                && new NameValidator().validate(labWork.getName())
                && new AveragePointValidator().validate(labWork.getAveragePoint())
                && new CoordXValidator().validate(Optional.of(labWork).map(LabWork::getCoordinates).map(Coordinates::getX).orElse(0))
                && new CoordYValidator().validate(Optional.of(labWork).map(LabWork::getCoordinates).map(Coordinates::getY).orElse(null))
                && new DifficultyValidator().validate(labWork.getDifficulty())
                && new DisciplineLabsCountValidator().validate(labWork.getDiscipline().getLabsCount())
                && new DisciplineNameValidator().validate(labWork.getDiscipline().getName())
                && new DisciplineSelfStudyHoursValidator().validate(labWork.getDiscipline().getSelfStudyHours())
                && new MinimalPointValidator().validate(labWork.getMinimalPoint());
    }
}
