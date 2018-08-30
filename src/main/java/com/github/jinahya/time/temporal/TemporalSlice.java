package com.github.jinahya.time.temporal;

import java.time.temporal.Temporal;

/**
 * A class for representing temporal slice.
 *
 * @author onacit_at_wemakeprice.com on 29/08/2018
 */
public final class TemporalSlice<T extends Temporal & Comparable<? super T>> {

    // -----------------------------------------------------------------------------------------------------------------
    public static <T extends Temporal & Comparable<? super T>> TemporalSlice<T> of(final T startInclusive,
                                                                                   final T endExclusive) {
        return new TemporalSlice<>(startInclusive, endExclusive);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private TemporalSlice(final T startInclusive, final T endExclusive) {
        super();
        if (startInclusive == null) {
            throw new NullPointerException("startInclusive is null");
        }
        if (endExclusive == null) {
            throw new NullPointerException("endExclusive is null");
        }
        if (startInclusive.compareTo(endExclusive) >= 0) {
            throw new IllegalArgumentException(
                    "startInclusive(" + startInclusive + ") >= endExclusive(" + endExclusive + ")");
        }
        this.startInclusive = startInclusive;
        this.endExclusive = endExclusive;
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return super.toString() + "{"
                + "startInclusive=" + startInclusive
                + ",endExclusive=" + endExclusive
                + "}";
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns {@code startInclusive} attribute this temporal slice.
     *
     * @return The {@code startInclusive} attribute of this temporal slice
     */
    public T getStartInclusive() {
        return startInclusive;
    }

    /**
     * Returns {@code endExclusive} attribute value of this temporal slice.
     *
     * @return {@code endExclusive} attribute value of this temporal slice.
     */
    public T getEndExclusive() {
        return endExclusive;
    }

    // -----------------------------------------------------------------------------------------------------------------
    private final T startInclusive;

    private final T endExclusive;
}
