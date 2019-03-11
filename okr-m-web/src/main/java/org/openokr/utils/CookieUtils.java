package org.openokr.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public final class CookieUtils {

    private static final String cookiePath = "/";
    private static final String cookieDomain = "";

    private static final String REQUEST_MESSAGE = "not has request";
    private static final String RESPONSE_MESSAGE = "not has response";
    private static final String NAME_MESSAGE = "not has name";

    public static void addCookie(HttpServletRequest request, HttpServletResponse response,
                                 String name, String value, Integer maxAge, String path, String domain, Boolean secure, Boolean isHttpOnly) {
        Assert.notNull(request, REQUEST_MESSAGE);
        Assert.notNull(response, RESPONSE_MESSAGE);
        Assert.hasText(name, NAME_MESSAGE);
        try {
            name = URLEncoder.encode(name, "UTF-8");
            value = URLEncoder.encode(value, "UTF-8");
            Cookie cookie = new Cookie(name, value);
            if (maxAge != null) {
                cookie.setMaxAge(maxAge.intValue());
            }
            if (StringUtils.isNotEmpty(path)) {
                cookie.setPath(path);
            }
            if (StringUtils.isNotEmpty(domain)) {
                cookie.setDomain(domain);
            }
            if (secure != null) {
                cookie.setSecure(secure.booleanValue());
            }
            if (isHttpOnly != null) {
                cookie.setHttpOnly(isHttpOnly);
            }

            response.addCookie(cookie);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    public static void addCookie(HttpServletRequest request, HttpServletResponse response,
                                 String name, String value, Integer maxAge, Boolean isHttpOnly) {
        addCookie(request, response, name, value, maxAge, cookiePath,
                cookieDomain, null, isHttpOnly);
    }

    public static void addCookie(HttpServletRequest request, HttpServletResponse response,
                                 String name, String value, Boolean isHttpOnly) {
        addCookie(request, response, name, value, null, cookiePath,
                cookieDomain, null, isHttpOnly);
    }

    public static String getCookie(HttpServletRequest request, String name) {
        Assert.notNull(request, REQUEST_MESSAGE);
        Assert.hasText(name, NAME_MESSAGE);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            try {
                name = URLEncoder.encode(name, "UTF-8");
                for (Cookie cookie : cookies) {
                    if (name.equals(cookie.getName())) {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    }
                }
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response,
                                    String name, String path, String domain) {
        Assert.notNull(request, REQUEST_MESSAGE);
        Assert.notNull(response, RESPONSE_MESSAGE);
        Assert.hasText(name, NAME_MESSAGE);
        try {
            name = URLEncoder.encode(name, "UTF-8");
            Cookie cookie = new Cookie(name, null);
            cookie.setMaxAge(0);
            if (StringUtils.isNotEmpty(path)) {
                cookie.setPath(path);
            }
            if (StringUtils.isNotEmpty(domain)) {
                cookie.setDomain(domain);
            }
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        removeCookie(request, response, name, cookiePath, cookieDomain);
    }
}