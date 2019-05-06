package com.saliai.wechat_3rdparty.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @Author:chenyiwu
 * @Describtion:
 * @Create Time:2018/6/8
 */
public class WebUtils {
    private final static List<String> languages = Arrays.asList(new String[]{"en", "ar", "de", "es", "fr", "ja", "ko", "pt", "ru"});

    /**
     * get the current request Locale,default en,
     * set Locale in requestAttribute
     *
     * @return
     */
    public static Locale getRequestLocale() {
        String lang = WebUtils.getRequest().getParameter("locale");
        if (!languages.contains(lang)) {
            lang = "en";
        }
        Locale locale = null;
        if (StringUtils.isNotBlank(lang)) {
            locale = new Locale(lang);
        } else {
            locale = new Locale("en");// Default is English
        }
        WebUtils.getRequest().setAttribute("locale", locale.getLanguage());
        return locale;
    }

    public static String getLocaleLanguage() {
        return getRequestLocale().getLanguage();
    }

    public static boolean isEnLocale() {
        String language = null;
        try {
            language = getLocaleLanguage();
        } catch (Exception e) {
        }

        if (StringUtils.isBlank(language) || "en".equals(language)) {
            return true;
        }
        return false;
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 功能描述: 获取ip地址
     *
     * @auther: Martin、chen
     * @date: 2018/6/8 16:34
     * @return: java.lang.String
     */
    public static String getRemoteIp() {

        HttpServletRequest request = WebUtils.getRequest();
        String ip = request.getHeader("True-Client-IP");
        if (StringUtils.isNotEmpty(ip)) {
            String[] IPArray = ip.split(",\\s*");
            if (IPArray.length > 0) {
                ip = IPArray[IPArray.length - 1];
            }
            return ip;
        }

        ip = request.getHeader("cdn-src-ip");
        if (StringUtils.isNotEmpty(ip)) {
            String[] IPArray = ip.split(",\\s*");
            if (IPArray.length > 0) {
                ip = IPArray[IPArray.length - 1];
            }
            return ip;
        }

        // nginx
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip)) {
            String[] proxys = ip.split(",\\s*");// nginx don't have space
            for (String proxy : proxys) {
                if (!(proxy.startsWith("127.") || proxy.startsWith("172.")
                        || proxy.startsWith("192.") || proxy.startsWith("10."))) {
                    ip = proxy;
                    break;
                }
            }
            return ip;
        }

        // tomcat
        ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip)) {
            String[] proxys = ip.split(",\\s*");
            for (String proxy : proxys) {
                if (!(proxy.startsWith("127.") || proxy.startsWith("172.")
                        || proxy.startsWith("192.") || proxy.startsWith("10."))) {
                    ip = proxy;
                    break;
                }
            }
            return ip;
        }

        ip = request.getHeader("WL-Proxy-Client-IP");
        if (StringUtils.isNotEmpty(ip)) {
            ip = request.getRemoteAddr();
            if (StringUtils.isNotEmpty(ip)) {
                String[] IPArray = ip.split(",\\s*");
                if (IPArray.length > 0) {
                    ip = IPArray[IPArray.length - 1];
                }
            }
        }
        return ip;
    }

}
