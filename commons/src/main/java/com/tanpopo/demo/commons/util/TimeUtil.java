package com.tanpopo.demo.commons.util;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * 时间日期处理工具
 *
 * @author tanpopo
 * @since 2023/5/29
 */

public class TimeUtil {

    public static final ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");
    public static final ZoneOffset ZONE_OFFSET = ZoneOffset.of("+8");

    public static final DateTimeFormatter FORMATTER_DAY = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter FORMATTER_HOUR = DateTimeFormatter
            .ofPattern("yyyyMMddHH");
    public static final DateTimeFormatter FORMATTER_MINUTE = DateTimeFormatter
            .ofPattern("yyyyMMddHHmm");
    public static final DateTimeFormatter FORMATTER_SECOND = DateTimeFormatter
            .ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter FORMATTER_FULL_DAY = DateTimeFormatter
            .ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter FORMATTER_FULL_SECOND = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取字符串格式，支持格式:
     * <ul><li>yyyyMMdd</li>
     * <li>yyyyMMddHH</li>
     * <li>yyyyMMddHHmm</li>
     * <li>yyyyMMddHHmmss</li>
     * <li>yyyy-MM-dd</li>
     * <li>yyyy-MM-dd HH:mm:ss</li>
     * </ul>
     *
     * @param timeString 要转换的时间字符串
     * @return DateTimeFormatter时间格式
     */
    public static DateTimeFormatter getDateTimeFormatter(String timeString) {
        int strLen = timeString.length(); // tmp
        switch (strLen) {
            case 8:
                return FORMATTER_DAY;
            case 10:
                if (timeString.contains("-")) {
                    return FORMATTER_FULL_DAY;
                } else {
                    return FORMATTER_HOUR;
                }
            case 12:
                return FORMATTER_MINUTE;
            case 14:
                return FORMATTER_SECOND;
            default:
                return FORMATTER_FULL_SECOND;
        }
    }

    /**
     * 将时间字符串根据指定的格式转换为LocalDateTime
     *
     * @param timeString 时间字符串
     * @param dateTimeFormatter 类中只提供几个常用的，如需自己构建，可以调用 DateTimeFormatter.ofPattern()
     *         时间格式，常用格式：
     *         <ul><li>年 - yyyy</li>
     *         <li>月 - MM</li>
     *         <li>日 - dd</li>
     *         <li>时 - HH</li>
     *         <li>分 - mm</li>
     *         <li>秒 - ss</li>
     *         <li>毫秒 - SSS</li></ul>
     * @return LocalDateTime类型的时间
     */
    public static LocalDateTime toLocalDateTime(String timeString,
            DateTimeFormatter dateTimeFormatter) {
        return LocalDateTime.parse(timeString, dateTimeFormatter);
    }

    /**
     * 将字符串转换为LocalDateTime，支持的格式:
     * <ul><li>yyyyMMdd</li>
     * <li>yyyyMMddHH</li>
     * <li>yyyyMMddHHmm</li>
     * <li>yyyyMMddHHmmss</li>
     * <li>yyyy-MM-dd</li>
     * <li>yyyy-MM-dd HH:mm:ss</li>
     * </ul>
     *
     * @param timeString 要转换的时间字符串
     * @return LocalDateTime类型的时间
     */
    public static LocalDateTime toLocalDateTime(String timeString) {
        DateTimeFormatter dateTimeFormatter = getDateTimeFormatter(timeString);

        if (dateTimeFormatter.equals(FORMATTER_DAY) || dateTimeFormatter
                .equals(FORMATTER_FULL_DAY)) {
            return LocalDate.parse(timeString, dateTimeFormatter).atStartOfDay();
        } else {
            return LocalDateTime.parse(timeString, dateTimeFormatter);
        }
    }

    /**
     * 时间戳转换为LocalDateTime
     *
     * @param timestamp 毫秒级时间戳
     */
    public static LocalDateTime toLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZONE_ID);
    }


    /**
     * LocalDateTime转换为时间戳(毫秒), +8时区
     *
     * @param localDateTime LocalDateTime
     * @return 毫秒级时间戳
     */
    public static long toTimestamp(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZONE_OFFSET).toEpochMilli();
    }

    /**
     * 将时间字符串转换为时间戳(毫秒)，+8时区
     *
     * @param timeString 要转换的时间字符串
     * @param dateTimeFormatter 类中只提供几个常用的，如需自己构建，可以调用 DateTimeFormatter.ofPattern()
     *         时间格式，常用格式：<ul>
     *         <li>年 - yyyy</li>
     *         <li>月 - MM</li>
     *         <li>日 - dd</li>
     *         <li>时 - HH</li>
     *         <li>分 - mm</li>
     *         <li>秒 - ss</li>
     *         <li>毫秒 - SSS</li>
     *         </ul>
     * @return
     */
    public static long toTimestamp(String timeString, DateTimeFormatter dateTimeFormatter,
            long defaultValue) {
        if (timeString != null && timeString.length() != 0) {
            try {
                return toLocalDateTime(timeString, dateTimeFormatter).toInstant(ZONE_OFFSET)
                        .toEpochMilli();
            } catch (Exception ignored) {

            }
        }
        return defaultValue;
    }

    /**
     * 将时间字符串转换为时间戳, +8时区
     *
     * @param timeString 时间字符串，支持的格式有：
     *         <ul><li>yyyyMMdd</li>
     *         <li>yyyyMMddHH</li>
     *         <li>yyyyMMddHHmm</li>
     *         <li>yyyyMMddHHmmss</li>
     *         <li>yyyy-MM-dd</li>
     *         <li>yyyy-MM-dd HH:mm:ss</li></ul>
     * @param defaultValue 默认值，默认为0L
     * @return 毫秒级时间戳
     */
    public static long toTimestamp(String timeString, long defaultValue) {
        if (timeString != null && timeString.length() != 0) {
            try {
                return toLocalDateTime(timeString).toInstant(ZONE_OFFSET).toEpochMilli();
            } catch (Exception e) {
                // do nothing
            }
        }
        return defaultValue;
    }

    public static long toTimestamp(String timeString) {
        return toTimestamp(timeString, 0L);
    }

    /**
     * 将毫秒级时间戳转为格式化时间字符串
     *
     * @param timestamp 时间戳，毫秒
     * @param dateTimeFormatter 要输出的时间格式,类中只提供几个常用的， 如需自己构建，可以调用
     *         DateTimeFormatter.ofPattern()
     * @return 指定格式的字符串时间
     */
    public static String toTimeString(long timestamp, DateTimeFormatter dateTimeFormatter) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return dateTimeFormatter.format(LocalDateTime.ofInstant(instant, ZONE_ID));
    }

    /**
     * 将LocalDateTime转换为格式化时间字符串
     *
     * @param localDateTime LocalDateTime
     * @param dateTimeFormatter 要输出的时间格式,类中只提供几个常用的， 如需自己构建，可以调用
     *         DateTimeFormatter.ofPattern()
     * @return 指定格式的字符串时间
     */
    public static String toTimeString(LocalDateTime localDateTime,
            DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter.format(localDateTime);
    }

    /**
     * 获取当前时间字符串
     *
     * @param dateTimeFormatter 要输出的时间格式,类中只提供几个常用的， 如需自己构建，可以调用
     *         DateTimeFormatter.ofPattern()
     * @return 指定格式的当前时间字符串
     */
    public static String getCurrentTimeString(DateTimeFormatter dateTimeFormatter) {
        assert dateTimeFormatter != null;
        // Instant.now().toEpochMilli 等同于 System.currentTimeMillis()
        return dateTimeFormatter.format(LocalDateTime.now());
    }

    /**
     * 判断给定的日期是否为周末
     *
     * @param localDateTime 其他格式如string或long等可以通过类中提供的方法转为LocalDateTime
     * @return 是周末返回true，否则返回false
     */
    public static boolean isWeekend(LocalDateTime localDateTime) {
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        return dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY);
    }

    /**
     * 求与基准时间字符串间隔一定偏移量的时间字符串
     *
     * @param timeString 基准时间字符串，支持格式：<ul>
     *         <li>yyyyMMdd</li>
     *         <li>yyyyMMddHH</li>
     *         <li>yyyyMMddHHmm</li>
     *         <li>yyyyMMddHHmmss</li>
     *         <li>yyyy-MM-dd</li>
     *         <li>yyyy-MM-dd HH:mm:ss</li>
     *         </ul>
     * @param offset 偏移量(毫秒)，有符号int值
     * @return 基准时间偏移指定量后的时间
     */
    public static String delta(String timeString, int offset) {
        DateTimeFormatter dateTimeFormatter = getDateTimeFormatter(timeString);
        LocalDateTime newTime = toLocalDateTime(timeString).plusSeconds(offset);
        return toTimeString(newTime, dateTimeFormatter);
    }

    /**
     * 求与基准时间字符串间隔一定偏移量的时间字符串
     *
     * @param timeString 基准时间字符串
     * @param format 基准时间的格式，常用格式：yyyyMMdd、yyyyMMddHH、yyyyMMddHHmm、yyyyMMddHHmmss
     * @param offset 偏移量，单位：毫秒
     * @return 基准时间偏移deltaMills后的时间
     */
    public static String delta(String timeString, DateTimeFormatter format, Long offset) {
        LocalDateTime newTime = toLocalDateTime(timeString).plusSeconds(offset);
        return toTimeString(newTime, format);
    }

    public static void main(String[] args) {
        String res = TimeUtil.delta("2021070400", -1000 * 86400);
        System.out.println(res);
        System.out.println(TimeUtil.toTimestamp("2019-06-23 15:28:24"));
    }

}
