package com.github.jinahya.time.temporal;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class TemporalSlicerTest {

    // -----------------------------------------------------------------------------------------------------------------
    private static final Logger logger = getLogger(lookup().lookupClass());

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    @Disabled
    public void testLocalDateForYearByMonth() {
        final LocalDate start = LocalDate.now();
        final LocalDate end = start.plus(1L, ChronoUnit.YEARS);
        System.out.printf("%1$s , %2$s\n", start, end);
        TemporalSlicer.slice(
                start,
                end,
                (t1, t2) -> {
                    //System.out.printf("comparing %1$s <> %2$s\n", t1, t2);
                    boolean r = t1.equals(t2) || t1.isBefore(t2);
                    //System.out.println(r);
                    return r;
                },
                t -> t.with(TemporalAdjusters.firstDayOfYear()),
                t -> t.plus(1, ChronoUnit.MONTHS),
                (s, e) -> System.out.printf("%1$s ~ %2$s\n", s, e)
                            );
    }

    @Test
    @Disabled
    public void testLocalDateTimeForYearByMonth() {
        //final LocalDateTime start = LocalDate.now().atStartOfDay();
        final LocalDateTime start = LocalDateTime.now();
        final LocalDateTime end = start.plus(1L, ChronoUnit.YEARS);
        System.out.printf("%1$s , %2$s\n", start, end);
        TemporalSlicer.slice(
                start,
                end,
                (t1, t2) -> {
                    //System.out.printf("comparing %1$s <> %2$s\n", t1, t2);
                    boolean r = t1.equals(t2) || t1.isBefore(t2);
                    //System.out.println(r);
                    return r;
                },
                t -> LocalDate.now().atStartOfDay(),
                t -> t.plus(1, ChronoUnit.MONTHS),
                (s, e) -> System.out.printf("%1$s ~ %2$s\n", s, e)
                            );
    }

    @Test
    public void testLocalDateTimeFromNowForYearByMonth() {
        final LocalDateTime start = LocalDateTime.now();
        final LocalDateTime end = start.plus(1L, ChronoUnit.YEARS);
        final List<TemporalSlice<LocalDateTime>> list = TemporalSlicer.slice(
                start,
                end,
                t -> {
                    return t.with(ChronoField.DAY_OF_MONTH, 1L).with(ChronoField.HOUR_OF_DAY, 0L)
                            .with(ChronoField.MINUTE_OF_HOUR, 0L).with(ChronoField.SECOND_OF_MINUTE, 0L)
                            .with(ChronoField.MILLI_OF_SECOND, 0L)
                            .with(ChronoField.MICRO_OF_SECOND, 0L)
                            .with(ChronoField.NANO_OF_SECOND, 0L);
                },
                t -> {
                    return t.plus(1L, ChronoUnit.MONTHS);
                });
        for (final TemporalSlice<LocalDateTime> slice : list) {
            logger.debug("slice: {} / {} / {}", slice, slice.getStart().get(ChronoField.NANO_OF_SECOND), slice.getStart().get(ChronoField.NANO_OF_SECOND));
        }
    }
}
