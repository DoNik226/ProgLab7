package models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class LabWork implements Comparable<LabWork>, Serializable {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private java.util.Date creationDate;
    private Integer minimalPoint;
    private long tunedInWorks;
    private long averagePoint;
    private Difficulty difficulty;
    private static Discipline discipline;
    /**
     * Restrictions: Field cannot be null. The value of this field should be unique, greater than 0 and automatically generated.
     * @return Value of field id
     */
    public Long getId()
    {
        return id;
    }

    /**
     * Restrictions: Field cannot be null. String must not be empty.
     * @return Value of field name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Restrictions: Field cannot be null.
     * @return Value of field coordinates
     */
    public Coordinates getCoordinates()
    {
        return coordinates;
    }

    /**
     * Restrictions: Field cannot be null and the value of this field should be automatically generated.
     * @return Value of field creationDate
     */
    public java.util.Date getCreationDate()
    {
        return creationDate;
    }

    /**
     * Restrictions: Field cannot be null. The value of this field should be greater than 0.
     * @return Value of field minimalPoint
     */
    public Integer getMinimalPoint()
    {
        return minimalPoint;
    }

    /**
     *
     * @return Value of field tunedInWorks
     */
    public long getTunedInWorks()
    {
        return tunedInWorks;
    }

    /**
     * Restrictions: The value of this field should be greater than 0.
     * @return Value of field averagePoint
     */
    public long getAveragePoint()
    {
        return averagePoint;
    }

    /**
     * Restrictions: Field cannot be null.
     * @return Value of field difficulty
     */
    public Difficulty getDifficulty()
    {
        return difficulty;
    }

    /**
     * Restrictions: Field can be null.
     * @return Value of field discipline
     */
    public static Discipline getDiscipline()
    {
        return discipline;
    }

    /**
     * Restrictions: Field cannot be null. The value of this field should be unique, greater than 0 and automatically generated.
     * @param id Value of field id
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * Restrictions: Field cannot be null. String must not be empty.
     * @param name Value of field name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Restrictions: Field cannot be null.
     * @param coordinates Value of field coordinates
     */
    public void setCoordinates(Coordinates coordinates)
    {
        this.coordinates = coordinates;
    }

    /**
     * Restrictions: Field cannot be null and the value of this field should be automatically generated.
     * @param creationDate Value of field creationDate
     */
    public void setCreationDate(Date creationDate) {this.creationDate = creationDate;}

    /**
     * Restrictions: Field cannot be null. The value of this field should be greater than 0.
     * @param minimalPoint Value of field minimalPoint
     */
    public void setMinimalPoint(Integer minimalPoint)
    {
        this.minimalPoint = minimalPoint;
    }

    /**
     *
     * @param tunedInWorks Value of field tunedInWorks
     */
    public void setTunedInWorks(long tunedInWorks)
    {
        this.tunedInWorks = tunedInWorks;
    }

    /**
     * Restrictions: The value of this field should be greater than 0.
     * @param averagePoint Value of field averagePoint
     */
    public void setAveragePoint(long averagePoint)
    {
        this.averagePoint = averagePoint;
    }

    /**
     * Restrictions: Field cannot be null.
     * @param difficulty Value of field difficulty
     */
    public void setDifficulty(Difficulty difficulty)
    {
        this.difficulty = difficulty;
    }

    /**
     * Restrictions: Field can be null.
     * @param discipline Value of field discipline
     */
    public void setDiscipline(Discipline discipline)
    {
        this.discipline = discipline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabWork labWork = (LabWork) o;
        return id.equals(labWork.id) && name.equals(labWork.name) && coordinates.equals(labWork.coordinates) && minimalPoint.equals(labWork.minimalPoint) && Long.compare(this.averagePoint, averagePoint) == 0 && Long.compare(this.tunedInWorks, tunedInWorks) == 0 && Objects.equals(discipline, labWork.discipline) && Objects.equals(difficulty, labWork.difficulty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, minimalPoint, tunedInWorks, averagePoint, difficulty, discipline);
    }

    @Override
    public String toString() {
        return "LabWork{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", minimalPoint=" + minimalPoint +
                ", tunedInWorks=" + tunedInWorks +
                ", averagePoint=" + averagePoint +
                ", difficulty=" + difficulty +
                ", discipline=" + discipline +
                '}';
    }

    @Override
    public int compareTo(LabWork o) {
        return 0;
    }
}