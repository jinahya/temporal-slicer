package com.github.jinahya.time.temporal;

import java.time.temporal.Temporal;

import static java.util.Objects.requireNonNull;

/**
 * A class for representing temporal slice.
 *
 * @author onacit_at_wemakeprice.com on 29/08/2018
 */
public final class TemporalSlice<T extends Temporal> {

    // -----------------------------------------------------------------------------------------------------------------
    public static <T extends Temporal> TemporalSlice<T> of(final T start, final T end) {
        return new TemporalSlice<>(start, end);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private TemporalSlice(final T start, final T end) {
        super();
        this.start = requireNonNull(start, "start is null");
        this.end = requireNonNull(end, "end is null");
    }


    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return super.toString() + "{"
                + "start=" + start
                + ",end=" + end
                + "}";
    }


    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns start (inclusive) value of this temporal slice.
     *
     * @return start (inclusive) value of this temporal slice
     */
    public T getStart() {
        return start;
    }

    /**
     * Returns end (exclusive) value of this temporal slice.
     *
     * @return end (exclusive) value of this temporal slice.
     */
    public T getEnd() {
        return end;
    }

    // -----------------------------------------------------------------------------------------------------------------
    private final T start;

    private final T end;
}
