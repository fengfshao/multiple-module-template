package com.tanpopo.demo.commons.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.lang3.StringUtils;

/**
 * 字符串常用处理
 *
 * @author tanpopo
 * @since 2023/5/29
 */
public class StringUtil {

    /**
     * 判断字符串是否为空或长度为0，以及值为null
     *
     * @param str 目标字符串
     * @return 为空返回true，否则返回false
     */
    public static boolean isNullOrEmpty(String str) {
        return StringUtils.isEmpty(str) || str.equalsIgnoreCase("null");
    }

    /**
     * 将字符串转换为对应的Boolean值，空值、长度为0或转换失败，均返回defaultValue
     *
     * @param str 要转换的字符串
     * @param defaultValue 默认值，不指定默认为false
     * @return 转换成功返回对应的Boolean值，否则返回false
     */
    public static boolean toBoolean(String str, boolean defaultValue) {
        if ((str != null) && str.equalsIgnoreCase("true")) {
            return true;
        }
        return defaultValue;
    }

    public static boolean toBoolean(String str) {
        return toBoolean(str, false);
    }

    /**
     * 将字符串转换为对应的Int值，空值、长度为0或转换失败，均返回defaultValue
     *
     * @param str 要转换的字符串
     * @param defaultValue 默认值，不指定默认为-1
     * @return 转换成功返回对应的Int值，否则返回defaultValue
     */
    public static int toInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    public static int toInt(String str) {
        return toInt(str, -1);
    }

    /**
     * 将字符串转换为对应的Long值，空值、长度为0或转换失败，均返回defaultValue
     *
     * @param str 要转换的字符串
     * @param defaultValue 默认值，不指定默认为-1L
     * @return 转换成功返回对应的Long值，否则返回defaultValue
     */
    public static long toLong(String str, long defaultValue) {
        try {
            return Long.parseLong(str);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    public static long toLong(String str) {
        return toLong(str, -1L);
    }

    /**
     * 将字符串转换为对应的Double值，空值、长度为0或转换失败，均返回defaultValue
     *
     * @param str 要转换的字符串
     * @param defaultValue 默认值，不指定默认为-1.0
     * @return 转换成功返回对应的Double值，否则返回defaultValue
     */
    public static double toDouble(String str, double defaultValue) {
        try {
            return Double.parseDouble(str);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    public static double toDouble(String str) {
        return toDouble(str, -1.0);
    }

    /**
     * 将字符串转换为对应的Float值，空值、长度为0或转换失败，均返回defaultValue
     *
     * @param str 要转换的字符串
     * @param defaultValue 默认值，不指定默认为-1.0
     * @return 转换成功返回对应的Double值，否则返回defaultValue
     */
    public static float toFloat(String str, float defaultValue) {
        try {
            return Float.parseFloat(str);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * 计算给定字符串的32位md5值(字母小写)
     *
     * @param data 目标字符串
     * @return 32位md5值
     */
    public static String md5(String data) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] bytes = messageDigest.digest(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            int bt = b & 0xff;
            if (bt < 16) {
                sb.append(0);
            }
            sb.append(Integer.toHexString(bt));
        }
        return sb.toString();
    }

    /**
     * 计算给定字符串的16位md5值
     *
     * @param data 目标串
     * @return 16位md5值
     */
    public static String shortMD5(String data) throws Exception {
        return new ArgumentsUtil(new String[]{"aa=a1", "bb=b2"}).getString("aa");
        //return md5(data).substring(8, 24);
    }

    /**
     * 获取目标字符串中所有符合规则串的结果, 需要指定分隔符
     *
     * @param value 要检索的串
     * @param pattern 规则串
     * @param sep 分隔符
     * @return 符合规则的字符串
     */
    public static String matchAll(String value, String pattern, String sep) {
        Matcher m = Pattern.compile(pattern).matcher(value);
        StringJoiner stringJoiner = new StringJoiner(sep);
        while (m.find()) {
            stringJoiner.add(m.group());
        }
        return stringJoiner.toString();
    }

    /**
     * 获取目标字符串中符合规则串的第一个结果, 没有匹配的返回""
     *
     * @param value 要检索的串
     * @param pattern 规则串
     * @return 符合规则的字符串
     */
    public static String matchFirst(String value, String pattern) {
        Matcher m = Pattern.compile(pattern).matcher(value);
        return m.find() ? m.group() : "";
    }

    /**
     * 对非空字符串进行gzip压缩.
     *
     * @param value 要压缩的字符串，为空时不压缩
     * @return 压缩后的字节数组
     */
    public static byte[] gzip(String value) {
        if (StringUtil.isNullOrEmpty(value)) {
            return null;
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream(
                value.getBytes(StandardCharsets.UTF_8).length);
                GZIPOutputStream gzipOutput = new GZIPOutputStream(out)
        ) {
            gzipOutput.write(value.getBytes(StandardCharsets.UTF_8));
            gzipOutput.close();
            return out.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 对非空字节数组进行gzip解压.
     *
     * @param bytes 要解压的字节数组
     * @return 解压后的字符串
     */
    public static String gunzip(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                GZIPInputStream gzipInputStream = new GZIPInputStream(
                        new ByteArrayInputStream(bytes))
        ) {
            byte[] buffer = new byte[1024];
            int len = gzipInputStream.read(buffer);
            while (len > 0) {
                out.write(buffer, 0, len);
                len = gzipInputStream.read(buffer);
            }
            return out.toString("UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 删除字符串最后一个字符
     *
     * @param value 要处理的字符串
     * @return 若字符串不空，删除最后一个字符，否则返回原始串
     */
    public static StringBuilder deleteLastChar(StringBuilder value) {
        if (value.length() == 0) {
            return value;
        }
        return value.deleteCharAt(value.length() - 1);
    }

    public static String readFromResource(String filePath) throws IOException {
        InputStream sqlFile = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(sqlFile));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.substring(0, sb.length() - 1);
    }
}
