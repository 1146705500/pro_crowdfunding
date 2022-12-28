package com.gitee.pro.util;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CrowdUtil {

    /**
     * 判断当前请求是否为 Ajax 请求
     *
     * @param request 请求对象
     * @return true：当前请求是 Ajax 请求；false：当前请求不是 Ajax 请求
     */
    public static boolean judgeRequestType(HttpServletRequest request) {

        // 获取请求消息头
        String acceptHeader = request.getHeader("Accept");
        String xRequestheader = request.getHeader("X-Requested-With");
        // 判断
        return (acceptHeader != null && acceptHeader.contains("application/json")) || (xRequestheader != null && xRequestheader.equals("XMLHttpRequest"));

    }

    /**
     * 对明文字符串进行MD5加密
     *
     * @param source 传入的明文字符串
     * @return 加密结果
     */
    public static String md5(String source) {
        // 判断source是否有效
        if (source == null || source.length() == 0) {
            // 如果不是有效的字符串抛出异常
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        try {
            // 获取MessageDigest对象
            String algorithm = "md5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            // 获取明文字符串对应的字节数组
            byte[] input = source.getBytes();
            // 执行加密
            byte[] output = messageDigest.digest(input);
            // 创建BigInteger对象
            int signum = 1; // -1 为负, 0 为零, 1 为正
            BigInteger bigInteger = new BigInteger(signum, output);
            // 按照16进制将bigInteger的值转换为字符串
            int radix = 16;
            return bigInteger.toString(radix).toUpperCase(); // 加密好的字符串（转大写）
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
