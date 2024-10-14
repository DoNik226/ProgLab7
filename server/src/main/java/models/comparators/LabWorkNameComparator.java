package models.comparators;


import models.LabWork;

import java.util.Comparator;

/**
 * Compare two LabWorks by Name (default used)
 *
 * @since 1.0
 * @author Nikita
 */
public class LabWorkNameComparator implements Comparator<LabWork> {
    @Override
    public int compare(LabWork o1, LabWork o2) {
        return CharSequence.compare(o1.getName(), o2.getName());
    }
}