package models.comparators;

import models.LabWork;

import java.util.Comparator;
import java.util.Objects;

/**
 * Compare two LabWorks by ID (default used)
 *
 * @since 1.0
 * @author Nikita
 */
public class LabWorkComparator implements Comparator<LabWork> {
    @Override
    public int compare(LabWork o1, LabWork o2) {
        return Long.compare(o1.getId(), o2.getId());
    }
}

