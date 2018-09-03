package com.github.jinahya.time.temporal;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.github.jinahya.time.temporal.TemporalSlicer.sliceAdding;
import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.longThat;
import static org.mockito.ArgumentMatchers.notNull;
import static org.slf4j.LoggerFactory.getLogger;

public class TemporalSlicerTest {

    // -----------------------------------------------------------------------------------------------------------------
    private static final Logger logger = getLogger(lookup().lookupClass());

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    public void testSliceAddingAssertNullPointerExceptionThrownWhenStartInclusiveIsNull() {
        assertThrows(NullPointerException.class,
                     () -> sliceAdding(null, notNull(), notNull(), notNull(), notNull(), notNull()));
    }

    @Test
    public void testSliceAddingAssertNullPointerExceptionThrownWhenEndExclusiveIsNull() {
        assertThrows(NullPointerException.class,
                     () -> sliceAdding(notNull(), null, notNull(), notNull(), notNull(), notNull()));
    }

    @Test
    public void testSliceAddingAssertIllegalArgumentExceptionThrownWhenStartInclusiveIsAfterThanEndExclusive() {
        final LocalDate today = LocalDate.now();
        final LocalDate yesterday = today.plus(-1L, ChronoUnit.DAYS);
        assertThrows(IllegalArgumentException.class,
                     () -> sliceAdding(today, yesterday, notNull(), longThat(v -> v > 0L), notNull(), notNull()));
    }
}
