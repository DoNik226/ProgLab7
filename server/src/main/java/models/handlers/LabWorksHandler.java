package models.handlers;

import models.LabWork;
import models.comparators.LabWorkComparator;
import models.comparators.LabWorkNameComparator;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * Current implementation of CollectionsHandler for HashSet of LabWork.
 *
 * @author Nikita
 * @since 1.0
 */
public class LabWorksHandler implements CollectionHandler<HashSet<LabWork>, LabWork> {

    private static LabWorksHandler singletoneMoment;
    private final Date initDate;
    private HashSet<LabWork> labWorks;
    private final Lock readLock;
    private final Lock writeLock;

    private LabWorksHandler() {
        labWorks = new HashSet<>();
        initDate = Date.from(Instant.now());
        ReadWriteLock rwl = new ReentrantReadWriteLock();
        readLock = rwl.readLock();
        writeLock = rwl.writeLock();
    }

    /**
     * Singletone moment.
     *
     * @return Single instance of handler.
     */
    public static LabWorksHandler getInstance() {
        if (singletoneMoment == null)
            singletoneMoment = new LabWorksHandler();
        return singletoneMoment;
    }

    /**
     * Returns actual collection reference.
     *
     * @return Current collection
     */
    @Override
    public HashSet<LabWork> getCollection() {
        readLock.lock();
        var result = labWorks;
        readLock.unlock();
        return result;
    }

    /**
     * Overrides current collection by provided value.
     *
     * @param labWorks Collection
     */
    @Override
    public void setCollection(HashSet<LabWork> labWorks) {
        writeLock.lock();
        this.labWorks = labWorks;
        sort();
        writeLock.unlock();
    }

    /**
     * Adds element to collection.
     *
     * @param e Element to add
     */
    @Override
    public void addElementToCollection(LabWork e) {
        writeLock.lock();
        labWorks.add(e);
        sort();
        writeLock.unlock();
    }


    /**
     * Sorts elements by Name Field in LabWork.
     */
    @Override
    public void sort() {
        writeLock.lock();
        LinkedHashSet<LabWork> sorted = new LinkedHashSet<>();

        for (Iterator<LabWork> it = labWorks.stream().sorted(new LabWorkNameComparator()).iterator(); it.hasNext(); ) {
            LabWork sortedItem = it.next();

            sorted.add(sortedItem);
        }

        this.labWorks = sorted;
        writeLock.unlock();
    }
    @Override
    public HashSet<LabWork> getSorted() {
        readLock.lock();
        var result = labWorks.stream().sorted(new LabWorkNameComparator()).collect(Collectors.toCollection(LinkedHashSet::new));
        readLock.unlock();
        return result;
    }


    /**
     * Returns first element of collection.
     *
     * @return First element of collection. If collection is empty, returns new object.
     */
    @Override
    public LabWork getFirstOrNew() {
        LabWork result;
        readLock.lock();
        if (labWorks.iterator().hasNext())
            result = labWorks.iterator().next();
        else
            result = new LabWork();
        readLock.unlock();
        return result;
    }

    @Override
    public Date getInitDate() {
        return initDate;
    }

    /**
     * Returns last element of collection.
     *
     * @return Last element of collection of null if collection is empty
     */
    @Override
    public LabWork getLastElement() {
        LabWork result = null;
        readLock.lock();
        for (LabWork labWork : labWorks) {
            result = labWork;
        }
        readLock.unlock();
        return result;
    }

    /**
     * Gets max element by given comparator
     *
     * @param comparator Comparator to compare.
     * @return Max element or null if collection is empty
     */
    @Override
    public LabWork getMax(Comparator<LabWork> comparator) {
        readLock.lock();
        var result = getCollection().stream().max(comparator).orElse(null);
        readLock.unlock();
        return result;
    }
}
