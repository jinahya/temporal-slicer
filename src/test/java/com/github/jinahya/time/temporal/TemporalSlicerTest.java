package com.github.jinahya.time.temporal;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class TemporalSlicerTest {

    @Test
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
}
