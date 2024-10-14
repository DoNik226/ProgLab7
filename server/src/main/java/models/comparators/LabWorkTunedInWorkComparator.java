package models.comparators;

import models.LabWork;

import java.util.Comparator;

/**
 * Compare two LabWorks by tunedInWork. Used in commands, such as remove_greater, remove_lower, add_if max
 *
 * @author Nikita
 * @since 1.0
 */
public class LabWorkTunedInWorkComparator implements Comparator<LabWork> {
    @Override
    public int compare(LabWork o1, LabWork o2) {
        return (int) (o1.getTunedInWorks() - o2.getTunedInWorks());
    }
}