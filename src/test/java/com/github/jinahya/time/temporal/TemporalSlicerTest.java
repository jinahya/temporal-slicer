package com.github.jinahya.time.temporal;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class TemporalSlicerTest {

    // -----------------------------------------------------------------------------------------------------------------
    private static final Logger logger = getLogger(lookup().lookupClass());

    // -----------------------------------------------------------------------------------------------------------------
//    @Test
//    @Disabled
//    public void testLocalDateForYearByMonth() {
//        final LocalDate start = LocalDate.now();
//        final LocalDate end = start.plus(1L, ChronoUnit.YEARS);
//        System.out.printf("%1$s , %2$s\n", start, end);
//        TemporalSlicer.slice(
//                start,
//                end,
//                (t1, t2) -> {
//                    //System.out.printf("comparing %1$s <> %2$s\n", t1, t2);
//                    boolean r = t1.equals(t2) || t1.isBefore(t2);
//                    //System.out.println(r);
//                    return r;
//                },
//                t -> t.with(TemporalAdjusters.firstDayOfYear()),
//                t -> t.plus(1, ChronoUnit.MONTHS),
//                (s, e) -> System.out.printf("%1$s ~ %2$s\n", s, e)
//                            );
//    }
//
//    @Test
//    @Disabled
//    public void testLocalDateTimeForYearByMonth() {
//        //final LocalDateTime start = LocalDate.now().atStartOfDay();
//        final LocalDateTime start = LocalDateTime.now();
//        final LocalDateTime end = start.plus(1L, ChronoUnit.YEARS);
//        System.out.printf("%1$s , %2$s\n", start, end);
//        TemporalSlicer.slice(
//                start,
//                end,
//                (t1, t2) -> {
//                    //System.out.printf("comparing %1$s <> %2$s\n", t1, t2);
//                    boolean r = t1.equals(t2) || t1.isBefore(t2);
//                    //System.out.println(r);
//                    return r;
//                },
//                t -> LocalDate.now().atStartOfDay(),
//                t -> t.plus(1, ChronoUnit.MONTHS),
//                (s, e) -> System.out.printf("%1$s ~ %2$s\n", s, e)
//                            );
//    }
//
////    @Test
////    @Disabled
////    public void testLocalDateTimeFromNowForYearByMonth() {
////        final LocalDateTime start = LocalDateTime.now();
////        final LocalDateTime end = start.plus(1L, ChronoUnit.YEARS);
////        logger.debug("duration: {}", Duration.between(start, end));
////        final List<TemporalSlice<LocalDateTime>> slices = TemporalSlicer.slice(
////                start,
////                end,
////                t -> t.with(ChronoField.DAY_OF_MONTH, 1L)
////                        .with(ChronoField.HOUR_OF_DAY, 0L)
////                        .with(ChronoField.MINUTE_OF_HOUR, 0L)
////                        .with(ChronoField.SECOND_OF_MINUTE, 0L)
////                        .with(ChronoField.MILLI_OF_SECOND, 0L)
////                        .with(ChronoField.MICRO_OF_SECOND, 0L)
////                        .with(ChronoField.NANO_OF_SECOND, 0L)
////                ,
////                t -> t.plus(1L, ChronoUnit.MONTHS),
////                UnaryOperator.identity());
////        for (final TemporalSlice<LocalDateTime> slice : slices) {
////            logger.debug("slice: {} / {} / {}", slice, slice.getStartInclusive().get(ChronoField.NANO_OF_SECOND), slice.getStartInclusive().get(ChronoField.NANO_OF_SECOND));
////        }
////    }
//
//    @Test
//    @Disabled
//    public void testLocalDateTimeFromNowForYearByMonthIncrement() {
//        logger.debug("inc...");
//        final LocalDateTime start = LocalDateTime.now();
//        final LocalDateTime end = start.plus(1L, ChronoUnit.YEARS);
//        TemporalSlicer.slice2(
//                start,
//                end,
//                (t1, t2) -> t1.compareTo(t2),
////                TemporalSlicer.<LocalDateTime>natural(),
//                t -> t.with(ChronoField.DAY_OF_MONTH, 1L)
//                        .with(ChronoField.HOUR_OF_DAY, 0L)
//                        .with(ChronoField.MINUTE_OF_HOUR, 0L)
//                        .with(ChronoField.SECOND_OF_MINUTE, 0L)
//                        .with(ChronoField.MILLI_OF_SECOND, 0L)
//                        .with(ChronoField.MICRO_OF_SECOND, 0L)
//                        .with(ChronoField.NANO_OF_SECOND, 0L)
//                ,
//                t -> t.plus(1L, ChronoUnit.MONTHS));
//    }
//
//    @Test
//    @Disabled
//    public void testLocalDateTimeFromNowForYearByMonthDecrement() {
//        logger.debug("dec...");
//        final LocalDateTime start = LocalDateTime.now();
//        final LocalDateTime end = start.minus(1L, ChronoUnit.YEARS);
//        TemporalSlicer.slice2(
//                start,
//                end,
//                (t1, t2) -> t2.compareTo(t1),
////                TemporalSlicer.<LocalDateTime>natural().negate(),
//                t -> t.with(ChronoField.DAY_OF_MONTH, 1L)
//                        .with(ChronoField.HOUR_OF_DAY, 0L)
//                        .with(ChronoField.MINUTE_OF_HOUR, 0L)
//                        .with(ChronoField.SECOND_OF_MINUTE, 0L)
//                        .with(ChronoField.MILLI_OF_SECOND, 0L)
//                        .with(ChronoField.MICRO_OF_SECOND, 0L)
//                        .with(ChronoField.NANO_OF_SECOND, 0L)
//                        .plus(1L, ChronoUnit.MONTHS)
//                ,
//                t -> t.minus(1L, ChronoUnit.MONTHS));
//    }
//
//    @Test
//    @Disabled
//    public void testLocalDateTimeFromNowToNow() {
//        final LocalDateTime start = LocalDateTime.now();
//        final LocalDateTime end = start;
//        TemporalSlicer.slice2(
//                start,
//                end,
//                (t1, t2) -> t2.compareTo(t1),
////                TemporalSlicer.<LocalDateTime>natural().negate(),
//                t -> t.with(ChronoField.DAY_OF_MONTH, 1L)
//                        .with(ChronoField.HOUR_OF_DAY, 0L)
//                        .with(ChronoField.MINUTE_OF_HOUR, 0L)
//                        .with(ChronoField.SECOND_OF_MINUTE, 0L)
//                        .with(ChronoField.MILLI_OF_SECOND, 0L)
//                        .with(ChronoField.MICRO_OF_SECOND, 0L)
//                        .with(ChronoField.NANO_OF_SECOND, 0L)
//                        .plus(1L, ChronoUnit.MONTHS)
//                ,
//                t -> t.minus(1L, ChronoUnit.MONTHS));
//    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    public void testSlice3LocalDateTimeFromNowForYearByMonthIncrement() {
        logger.debug("not to a year after");
        final LocalDateTime start = LocalDateTime.now();
        final LocalDateTime end = start.plus(1L, ChronoUnit.YEARS);
        TemporalSlicer.slice3(
                start,
                end,
                Comparator.comparing(t2 -> ((LocalDateTime) t2)),
                t -> t.with(ChronoField.DAY_OF_MONTH, 1L)
                        .with(ChronoField.HOUR_OF_DAY, 0L)
                        .with(ChronoField.MINUTE_OF_HOUR, 0L)
                        .with(ChronoField.SECOND_OF_MINUTE, 0L)
                        .with(ChronoField.MILLI_OF_SECOND, 0L)
                        .with(ChronoField.MICRO_OF_SECOND, 0L)
                        .with(ChronoField.NANO_OF_SECOND, 0L)
                ,
                t -> t.plus(1L, ChronoUnit.MONTHS));
    }

    @Test
    //@Disabled
    public void testSlice3LocalDateTimeFromNowForYearByMonthDecrement() {
        logger.debug("now to a year ago");
        final LocalDateTime start = LocalDateTime.now();
        final LocalDateTime end = start.minus(1L, ChronoUnit.YEARS);
        TemporalSlicer.slice3(
                start,
                end,
                Comparator.comparing(t2 -> ((LocalDateTime) t2)).reversed(),
//                (t1, t2) -> ((LocalDateTime) t2).compareTo((LocalDateTime) t1),
//                TemporalSlicer.<LocalDateTime>natural().negate(),
                t -> t.with(ChronoField.DAY_OF_MONTH, 1L)
                        .with(ChronoField.HOUR_OF_DAY, 0L)
                        .with(ChronoField.MINUTE_OF_HOUR, 0L)
                        .with(ChronoField.SECOND_OF_MINUTE, 0L)
                        .with(ChronoField.MILLI_OF_SECOND, 0L)
                        .with(ChronoField.MICRO_OF_SECOND, 0L)
                        .with(ChronoField.NANO_OF_SECOND, 0L)
                        .plus(1L, ChronoUnit.MONTHS)
                ,
                t -> t.minus(1L, ChronoUnit.MONTHS));
    }

    @Test
    //@Disabled
    public void testSlice3LocalDateTimeFromNowToNow() {
        logger.debug("now-to-now");
        final LocalDateTime start = LocalDateTime.now();
        final LocalDateTime end = start;
        TemporalSlicer.slice3(
                start,
                end,
                Comparator.comparing(t2 -> ((LocalDateTime) t2)).reversed(),
//                TemporalSlicer.<LocalDateTime>natural().negate(),
                t -> t,
                t -> t);
    }
}
