package com.github.jinahya.time.temporal;

import java.time.temporal.Temporal;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.UnaryOperator;

public class TemporalSlicer {

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
}
