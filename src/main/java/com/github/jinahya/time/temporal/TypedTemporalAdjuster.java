package com.github.jinahya.time.temporal;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

/**
 * @author onacit_at_wemakeprice.com on 30/08/2018
 */
public interface TypedTemporalAdjuster<T extends Temporal & Comparable<? super T>> { //extends TemporalAdjuster {

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends Temporal & Comparable<? super T>> TypedTemporalAdjuster<T> of(
            final Class<T> temporalClass, final TemporalAdjuster temporalAdjuster) {
        if (temporalClass == null) {
            throw new NullPointerException("temporalClass is null");
        }
        if (temporalAdjuster == null) {
            throw new NullPointerException("temporalAdjuster is null");
        }
        return temporal -> temporalClass.cast(temporalAdjuster.adjustInto(temporalClass.cast(temporal)));
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Adjusts the specified temporal object.
     *
     * @param temporal the temporal object to adjust.
     * @return an object of the same observable type with the adjustment made.
     * @see TemporalAdjuster#adjustInto(Temporal)
     */
    T adjustInto(T temporal);
}
