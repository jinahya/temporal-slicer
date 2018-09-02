package com.github.jinahya.time.temporal;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalUnit;
import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.logging.Logger.getLogger;

public class TemporalSlicer {

    // -----------------------------------------------------------------------------------------------------------------
    private static final Logger logger = getLogger(lookup().lookupClass().getName());

    // -----------------------------------------------------------------------------------------------------------------
    private static void slice(final Temporal startInclusive, final Temporal endExclusive,
                              final Comparator<? super Temporal> temporalComparator,
                              final TemporalAdjuster startAdjuster, final TemporalAdjuster endAdjuster,
                              final BiConsumer<Temporal, Temporal> sliceConsumer) {
        if (startInclusive == null) {
            throw new NullPointerException("start is null");
        }
        if (endExclusive == null) {
            throw new NullPointerException("end is null");
        }
        if (temporalComparator == null) {
            throw new NullPointerException("temporalComparator is null");
        }
        if (startAdjuster == null) {
            throw new NullPointerException("startAdjuster is null");
        }
        if (endAdjuster == null) {
            throw new NullPointerException("endAdjuster is null");
        }
        if (sliceConsumer == null) {
            throw new NullPointerException("sliceConsumer is null");
        }
        Temporal s = startInclusive.with(startAdjuster);
        Temporal e = s.with(endAdjuster);
        if (temporalComparator.compare(s, startInclusive) < 0) {
            s = startInclusive;
        }
        while (temporalComparator.compare(e, s) < 0) {
            e = e.with(endAdjuster);
        }
        while (temporalComparator.compare(endExclusive, e) > 0) {
            sliceConsumer.accept(s, e);
            s = e;
            e = e.with(endAdjuster);
        }
        sliceConsumer.accept(s, endExclusive);
    }

    @SuppressWarnings({"unchecked"})
    private static <T extends Temporal & Comparable<? super T>> void slice(
            final T startInclusive, final T endExclusive, final UnaryOperator<T> startAdjuster,
            final UnaryOperator<T> endAdjuster, final BiConsumer<? super T, ? super T> sliceConsumer) {
        if (startInclusive == null) {
            throw new NullPointerException("start is null");
        }
        if (endExclusive == null) {
            throw new NullPointerException("end is null");
        }
        if (startAdjuster == null) {
            throw new NullPointerException("startAdjuster is null");
        }
        if (endAdjuster == null) {
            throw new NullPointerException("endAdjuster is null");
        }
        if (sliceConsumer == null) {
            throw new NullPointerException("sliceConsumer is null");
        }
        final Comparator<? super T> temporalComparator
                = startInclusive.compareTo(endExclusive) < 0 ? Comparator.naturalOrder() : Comparator.reverseOrder();
        slice(startInclusive,
              endExclusive,
              (t1, t2) -> temporalComparator.compare((T) t1, (T) t2),
              t -> startAdjuster.apply((T) t),
              t -> endAdjuster.apply((T) t),
              (t1, t2) -> sliceConsumer.accept((T) t1, (T) t2)
        );
    }

    public static <T extends Temporal & Comparable<? super T>> void sliceAdding(
            final T startInclusive, final T endExclusive, final UnaryOperator<T> startAdjuster,
            final long temporalAmount, final TemporalUnit temporalUnit,
            final BiConsumer<? super T, ? super T> sliceConsumer) {
        if (startInclusive == null) {
            throw new NullPointerException("start is null");
        }
        if (endExclusive == null) {
            throw new NullPointerException("end is null");
        }
        if (startInclusive.compareTo(endExclusive) > 0) {
            throw new IllegalArgumentException(
                    "startInclusive(" + startInclusive + ") is after than endExclusive(" + endExclusive + ")");
        }
        if (temporalAmount < 0L) {
            throw new IllegalArgumentException("temporalAmount(" + temporalAmount + ") < 0L");
        }
        slice(startInclusive, endExclusive, startAdjuster, t -> (T) t.plus(temporalAmount, temporalUnit),
              sliceConsumer);
    }

    public static <T extends Temporal & Comparable<? super T>> void sliceSubtracting(
            final T startInclusive, final T endExclusive, final UnaryOperator<T> startAdjuster,
            final long temporalAmount, final TemporalUnit temporalUnit,
            final BiConsumer<? super T, ? super T> sliceConsumer) {
        if (startInclusive == null) {
            throw new NullPointerException("start is null");
        }
        if (endExclusive == null) {
            throw new NullPointerException("end is null");
        }
        if (startInclusive.compareTo(endExclusive) < 0) {
            throw new IllegalArgumentException(
                    "startInclusive(" + startInclusive + ") is before than endExclusive(" + endExclusive + ")");
        }
        if (temporalAmount < 0L) {
            throw new IllegalArgumentException("temporalAmount(" + temporalAmount + ") < 0L");
        }
        slice(startInclusive, endExclusive, startAdjuster, t -> (T) t.minus(temporalAmount, temporalUnit),
              sliceConsumer);
    }
}
