package com.tanpopo.demo.commons.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Test;

/**
 * TimeUtil单元测试
 *
 * @author tanpopo
 * @since 2023/5/29
 */

public class TimeUtilTest {

    @Test
    public void toLocalDateTime() {
        Assert.assertEquals(LocalDateTime.of(2021, 1, 2, 3, 4),
                TimeUtil.toLocalDateTime("202101020304", TimeUtil.FORMATTER_MINUTE));
        Assert.assertEquals(LocalDateTime.of(2021, 1, 2, 3, 4, 5),
                TimeUtil.toLocalDateTime("20210102030405", TimeUtil.FORMATTER_SECOND));
        Assert.assertEquals(LocalDateTime.of(2021, 1, 2, 3, 4, 5),
                TimeUtil.toLocalDateTime("2021-01-02 03:04:05", TimeUtil.FORMATTER_FULL_SECOND));
        Assert.assertEquals(LocalDateTime.of(2021, 1, 2, 3, 4, 5),
                TimeUtil.toLocalDateTime("2021-01-02T03:04:05",
                        DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    @Test
    public void testToLocalDateTime() {
        Assert.assertEquals(LocalDateTime.of(2021, 1, 2, 3, 4),
                TimeUtil.toLocalDateTime("202101020304"));
        Assert.assertEquals(LocalDateTime.of(2021, 1, 2, 3, 4, 5),
                TimeUtil.toLocalDateTime("20210102030405"));
        Assert.assertEquals(LocalDateTime.of(2021, 1, 2, 3, 4, 5),
                TimeUtil.toLocalDateTime("2021-01-02 03:04:05"));
    }


    @Test
    public void toTimeString() {
        Assert.assertEquals("20210102",
                TimeUtil.toTimeString(1609527845000L, TimeUtil.FORMATTER_DAY));
        Assert.assertEquals("2021010203",
                TimeUtil.toTimeString(1609527845000L, TimeUtil.FORMATTER_HOUR));
        Assert.assertEquals("2021-01-02 03:04:05",
                TimeUtil.toTimeString(1609527845000L, TimeUtil.FORMATTER_FULL_SECOND));
    }

    @Test
    public void testToTimeString() {
        Assert.assertEquals("202101020304",
                TimeUtil.toTimeString(LocalDateTime.of(2021, 1, 2, 3, 4, 5),
                        TimeUtil.FORMATTER_MINUTE));
        Assert.assertEquals("20210102030405",
                TimeUtil.toTimeString(LocalDateTime.of(2021, 1, 2, 3, 4, 5),
                        TimeUtil.FORMATTER_SECOND));
        Assert.assertEquals("2021-01-02 03:04:05",
                TimeUtil.toTimeString(LocalDateTime.of(2021, 1, 2, 3, 4, 5),
                        TimeUtil.FORMATTER_FULL_SECOND));
    }

    @Test
    public void isWeekend() {
        Assert.assertFalse(TimeUtil.isWeekend(LocalDateTime.of(2021, 1, 1, 3, 4, 5)));
        Assert.assertTrue(TimeUtil.isWeekend(LocalDateTime.of(2021, 1, 2, 3, 4, 5)));
        Assert.assertTrue(TimeUtil.isWeekend(LocalDateTime.of(2021, 1, 3, 3, 4, 5)));
        Assert.assertFalse(TimeUtil.isWeekend(LocalDateTime.of(2021, 1, 4, 3, 4, 5)));
        Assert.assertFalse(TimeUtil.isWeekend(LocalDateTime.of(2021, 1, 5, 3, 4, 5)));
        Assert.assertFalse(TimeUtil.isWeekend(LocalDateTime.of(2021, 1, 6, 3, 4, 5)));
        Assert.assertFalse(TimeUtil.isWeekend(LocalDateTime.of(2021, 1, 7, 3, 4, 5)));
    }

    @Test
    public void toTimestamp() {
        Assert.assertEquals(1609441445000L,
                TimeUtil.toTimestamp(LocalDateTime.of(2021, 1, 1, 3, 4, 5)));
        Assert.assertEquals(1609527845000L,
                TimeUtil.toTimestamp(LocalDateTime.of(2021, 1, 2, 3, 4, 5)));
    }

    @Test
    public void testToTimestamp() {
        Assert.assertEquals(1609441445000L,
                TimeUtil.toTimestamp("20210101030405", TimeUtil.FORMATTER_SECOND, 0));
        Assert.assertEquals(1609527845000L,
                TimeUtil.toTimestamp("2021-01-02 03:04:05", TimeUtil.FORMATTER_FULL_SECOND, 0));
        Assert.assertEquals(0L,
                TimeUtil.toTimestamp("2021-01-02 03:04:", TimeUtil.FORMATTER_FULL_SECOND, 0));
    }

    @Test
    public void testToTimestamp1() {
        Assert.assertEquals(1609441445000L,
                TimeUtil.toTimestamp("20210101030405", 0L));
        Assert.assertEquals(1609527845000L,
                TimeUtil.toTimestamp("2021-01-02 03:04:05", 0L));
        Assert.assertEquals(0L,
                TimeUtil.toTimestamp(null, 0L));
        Assert.assertEquals(0L,
                TimeUtil.toTimestamp("2021-01-02-03-04-05", 0L));
    }

    @Test
    public void delta() {
        Assert.assertEquals(TimeUtil.delta("2021-07-01 12:01:21", 5), "2021-07-01 12:01:26");
        Assert.assertEquals(TimeUtil.delta("2021-07-01 12:01:21", 60), "2021-07-01 12:02:21");
        Assert.assertEquals(TimeUtil.delta("2021-07-01 12:01:21", 3600), "2021-07-01 13:01:21");
    }

    @Test
    public void delta1() {
        Assert.assertEquals(TimeUtil.delta("2021-07-01 12:01:21",
                TimeUtil.FORMATTER_SECOND, 5L), "20210701120126");
        Assert.assertEquals(TimeUtil.delta("2021-07-01 12:01:21",
                TimeUtil.FORMATTER_MINUTE, 60L), "202107011202");
        Assert.assertEquals(TimeUtil.delta("2021-07-01 12:01:21",
                TimeUtil.FORMATTER_HOUR, 3600L), "2021070113");
        Assert.assertEquals(TimeUtil.delta("2021-07-01 12:01:21",
                TimeUtil.FORMATTER_FULL_SECOND, 5L), "2021-07-01 12:01:26");
    }
}