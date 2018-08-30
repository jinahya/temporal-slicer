package com.github.jinahya.time.temporal;

import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.logging.Logger.getLogger;

public class TemporalSlicer {


    private static final Logger logger = getLogger(lookup().lookupClass().getName());


    // -----------------------------------------------------------------------------------------------------------------

    public static <T extends Temporal & Comparable<? super T>> BiPredicate<? super T, ? super T> natural() {
        return (t1, t2) -> t1.compareTo(t2) <= 0;
    }

    // -----------------------------------------------------------------------------------------------------------------


    public static <T extends Temporal> void slice(
            final T start, final T end,
            final BiPredicate<T, T> comparator,
            final UnaryOperator<T> discriminator,
            final UnaryOperator<T> incrementer,
            final BiConsumer<T, T> consumer) {
        T s, e;
        s = discriminator.apply(start);
        System.out.println("first s: " + s);
        e = incrementer.apply(s);
        if (comparator.test(s, start)) {
            s = start;
            while (comparator.test(e, s)) {
                e = incrementer.apply(e);
            }
        }
        while (true) {
            if (comparator.test(end, e)) {
                consumer.accept(s, end);
                break;
            }
            consumer.accept(s, e);
            s = e;
            e = incrementer.apply(e);
        }
    }

    public static <T extends Temporal & Comparable<? super T>> List<TemporalSlice<T>> slice(
            final T start, final T end, final UnaryOperator<T> discriminator, final UnaryOperator<T> incrementer) {
        logger.info(() -> format(">>>>>: %1$s %2$s %3$d %4$d", start, end, start.get(ChronoField.NANO_OF_SECOND), end.get(ChronoField.NANO_OF_SECOND)));
        if (start == null) {
            throw new NullPointerException("start is null");
        }
        if (end == null) {
            throw new NullPointerException("end is null");
        }
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException("start...");
        }
        T s = discriminator.apply(start);
        if (s.compareTo(start) > 0) {
            throw new RuntimeException("discriminator yielded a future temporal(" + s + ") for start(" + start + ")");
        }
        T e = incrementer.apply(s);
        if (e.compareTo(s) < 0) {
            throw new RuntimeException("incrementer yielded a past temporal(" + e + ") for " + s);
        }
        if (s.compareTo(start) < 0) {
            while (e.compareTo(s) < 0) {
                e = incrementer.apply(e);
            }
            s = start;
        }
        final List<TemporalSlice<T>> list = new ArrayList<>();
        while (true) {
            if (end.compareTo(e) < 0) {
                list.add(TemporalSlice.of(s, end));
                break;
            }
            list.add(TemporalSlice.of(s, e));
            s = e;
            e = incrementer.apply(e);
        }
        return list;
    }

    public static <T extends Temporal & Comparable<? super T>> void slice1(
            final T start, final T end, final UnaryOperator<T> discriminator, final UnaryOperator<T> incrementer) {
        logger.info(() -> format(">>>>>: %1$s %2$s %3$d %4$d", start, end, start.get(ChronoField.NANO_OF_SECOND), end.get(ChronoField.NANO_OF_SECOND)));
        if (start == null) {
            throw new NullPointerException("start is null");
        }
        if (end == null) {
            throw new NullPointerException("end is null");
        }
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException("start...");
        }
        T s = discriminator.apply(start);
        if (s.compareTo(start) > 0) {
            throw new RuntimeException("discriminator yielded a future temporal(" + s + ") for start(" + start + ")");
        }
        T e = incrementer.apply(s);
        if (e.compareTo(s) < 0) {
            throw new RuntimeException("incrementer yielded a past temporal(" + e + ") for " + s);
        }
        if (s.compareTo(start) < 0) {
            while (e.compareTo(s) < 0) {
                e = incrementer.apply(e);
            }
            s = start;
        }
        final List<TemporalSlice<T>> list = new ArrayList<>();
        while (true) {
            if (end.compareTo(e) < 0) {
                list.add(TemporalSlice.of(s, end));
                break;
            }
            list.add(TemporalSlice.of(s, e));
            s = e;
            e = incrementer.apply(e);
        }
    }

    public static <T extends Temporal> void slice2(
            final T start, final T end,
            final BiPredicate<? super T, ? super T> temporalComparator,
            final UnaryOperator<T> startIdentifier, final UnaryOperator<T> endIncrementer) {
        if (start == null) {
            throw new NullPointerException("start is null");
        }
        if (end == null) {
            throw new NullPointerException("end is null");
        }
        T s = startIdentifier.apply(start);
        T e = endIncrementer.apply(s);
        if (temporalComparator.test(s, start)) {
            while (temporalComparator.test(e, s)) {
                e = endIncrementer.apply(e);
            }
            s = start;
        }
        while (true) {
            if (temporalComparator.test(end, e)) {
                final T s1 = s;
                System.out.printf("%1$S ~ %2$s\n", s1, end);
                break;
            }
            final T s1 = s;
            final T e1 = e;
            System.out.printf("%1$S ~ %2$s\n", s1, e1);
            s = e;
            e = endIncrementer.apply(e);
        }
    }
}
