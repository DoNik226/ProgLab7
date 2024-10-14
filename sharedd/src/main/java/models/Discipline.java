package models;

import java.io.Serializable;
import java.util.Objects;
public class Discipline implements Serializable {
    private String name;
    private long practiceHours;
    private Long selfStudyHours;
    private Integer labsCount;
    /**
     *  Restrictions: Field cannot be null. String must not be empty.
     * @return Value of field name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return Value of field practiceHours
     */
    public long getPracticeHours() {
        return practiceHours;
    }

    /**
     * Restrictions: Field cannot be null.
     * @return Value of field selfStudyHours
     */
    public Long getSelfStudyHours() {
        return selfStudyHours;
    }

    /**
     * Restrictions: Field cannot be null.
     * @return Value of field labsCount
     */
    public Integer getLabsCount() {
        return labsCount;
    }

    /**
     * Restrictions: Field cannot be null. String must not be empty.
     * @param name Value of field name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param practiceHours Value of field practiceHours
     */
    public void setPracticeHours(long practiceHours) {
        this.practiceHours = practiceHours;
    }

    /**
     * Restrictions: Field cannot be null.
     * @param labsCount Value of field labsCount
     */
    public void setLabsCount(Integer labsCount) {
        this.labsCount = labsCount;
    }

    /**
     * Restrictions: Field cannot be null.
     * @param selfStudyHours Value of field selfStudyHours
     */
    public void setSelfStudyHours(Long selfStudyHours) {
        this.selfStudyHours = selfStudyHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discipline discipline = (Discipline) o;
        return Long.compare(discipline.practiceHours, practiceHours) == 0 && name.equals(discipline.name) && selfStudyHours.equals(discipline.selfStudyHours) && labsCount.equals(discipline.labsCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, practiceHours, selfStudyHours, labsCount);
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "name='" + name + '\'' +
                ", practiceHours=" + practiceHours +
                ", selfStudyHours=" + selfStudyHours +
                ", labsCount=" + labsCount +
                '}';
    }
}