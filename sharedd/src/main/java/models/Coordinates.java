package models;

import java.io.Serializable;
import java.util.Objects;


public class Coordinates implements Serializable {
    private int x;
    private double y;

    /**
     * Restrictions: The value of this field cannot be greater than 178.
     * @return Value of field x
     */
    public int getX() {
        return x;
    }

    /**
     * Restrictions: Field cannot be null and the value of this field should be greater than -725.
     * @return Value of field y
     */
    public double getY() {
        return y;
    }

    /**
     * Restrictions: The value of this field cannot be greater than 178.
     * @param x Value of field x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Restrictions: Field cannot be null and the value of this field should be greater than -725.
     * @param y Value of field y
     */
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Integer.compare(that.x, x) == 0 && Double.compare(that.y, y)==0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
