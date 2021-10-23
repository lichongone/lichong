package com.example.one.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
public class WebUtils {

    public static boolean isAjaxRequest(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (servletRequestAttributes != null) {
            request = servletRequestAttributes.getRequest();
        }
        return request;
    }

    /**
     * @return javax.servlet.http.HttpServletResponse
     * @Author zhangbaofeng
     * @Description
     * @Date 17:04 2018/12/7
     * @Param []
     **/
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = null;
        if (servletRequestAttributes != null) {
            response = servletRequestAttributes.getResponse();
        }
        return response;
    }

    public static String getIpAddress() {
        HttpServletRequest request = getRequest();
        return getIpAddress(request);
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

    public static String addParam(String url, String key, String value) {
        if (url.indexOf("?") == -1) {
            url = url + "?" + key + "=" + value;
        } else {
            url = url + "&" + key + "=" + value;
        }
        return url;
    }

    public static String getParam(String url, String name) {
        String value = "";
        String paramString = url.substring(url.indexOf("?") + 1);
        Matcher match = Pattern.compile("(^|&)" + name + "=([^&]*)").matcher(paramString);
        if (match != null) {
            match.lookingAt();
            value = match.group(2);
        }
        return value;
    }

    public static String encode(String text, String charset) {
        try {
            return URLEncoder.encode(text, charset);
        } catch (UnsupportedEncodingException e) {
            log.error(" text -> {}, charset -> {} decode error, message is -> {} ", text, charset, e.getMessage());
        }
        return null;
    }

    public static String encode(String text) {
        return encode(text, "UTF-8");
    }

    public static String decode(String text, String charset) {
        try {
            return URLDecoder.decode(text, charset);
        } catch (UnsupportedEncodingException e) {
            log.error(" text -> {}, charset -> {} encode error, message is -> {} ", text, charset, e.getMessage());
        }
        return null;
    }

    public static String decode(String text) {
        return decode(text, "UTF-8");
    }



    public static String decodeFileNameByAgent(String fileName) throws UnsupportedEncodingException {

        String browserCode = getBrowserCode();

        //判断是否是火狐浏览器
        //谷歌
        if (browserCode.contains("chrome")){
            return URLEncoder.encode(fileName, "utf-8");
        }
        //ie
        if (browserCode.contains("trident")){
            return URLEncoder.encode(fileName, "utf-8");
        }
        //火狐
        if (browserCode.contains("gecko")){
            BASE64Encoder base64Encoder = new BASE64Encoder();
            return "=?utf-8?B?" +
                    base64Encoder.encode(fileName.getBytes(StandardCharsets.UTF_8)) + "?=";
        }
        //其他
        return URLEncoder.encode(fileName,"utf-8");

    }

    public static void downloadZip(HttpServletResponse response, String zipPath, String filename) {
        InputStream in = null;
        OutputStream out = null;
        if(null==response) response=getResponse();
        try {
            out = response.getOutputStream();
            response.reset();
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(filename, "utf-8"));
            response.setHeader("Access-Control-Allow-Origin", "*");
            in = new FileInputStream(zipPath + File.separator + filename);
            int b = 0;
            byte[] buffer = new byte[512];
            while (b != -1) {
                b = in.read(buffer);
                out.write(buffer, 0, b);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }
    }




    public static String getBrowserCode() {
        return getRequest().getHeader("User-Agent").toLowerCase();
    }

}
