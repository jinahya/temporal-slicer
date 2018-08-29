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
            throw new IllegalArgumentException("...");
        }
        if (start.equals(end)) {
        }
        T s, e;
        s = discriminator.apply(start);
        e = incrementer.apply(s);
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
}
