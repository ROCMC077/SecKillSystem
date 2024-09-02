package utils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Cookie工具類
 *
 * @author HathZhou on 2021/5/9 21:31
 */
public class CookieUtil {
    /**
     * 得到Cookie的值, 不編碼
     *
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        return getCookieValue(request, cookieName, false);
    }

    /**
     * 得到Cookie的值
     *
     * @param request
     * @param cookieName
     * @param isDecoder
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || cookieName == null) {
            return null;
        }
        String retValue = null;
        try {
            for (int i = 0; i < cookieList.length; i++) {
                if (cookieList[i].getName().equals(cookieName)) {
                    if (isDecoder) {
                        retValue = URLDecoder.decode(cookieList[i].getValue(), "UTF-8");
                    } else {
                        retValue = cookieList[i].getValue();
                    }
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return retValue;
    }

    /**
     * 得到Cookie的值
     *
     * @param request
     * @param cookieName
     * @param encodeString
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, String encodeString) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || cookieName == null) {
            return null;
        }
        String retValue = null;
        try {
            for (int i = 0; i < cookieList.length; i++) {
                if (cookieList[i].getName().equals(cookieName)) {
                    retValue = URLDecoder.decode(cookieList[i].getValue(), encodeString);
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return retValue;
    }

    /**
     * 設置Cookie的值 不設置生效時間默認瀏覽器關閉即失效,也不編碼
     *
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue) {
        setCookie(request, response, cookieName, cookieValue, -1);
    }

    /**
     * 設置Cookie的值 在指定時間內生效,但不編碼
     *
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxage) {
        setCookie(request, response, cookieName, cookieValue, cookieMaxage, false);
    }

    /**
     * 設置Cookie的值 不設置生效時間,但編碼
     *
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param isEncode
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, boolean isEncode) {
        setCookie(request, response, cookieName, cookieValue, -1, isEncode);
    }

    /**
     * 設置Cookie的值 在指定時間內生效, 編碼參數
     *
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage
     * @param isEncode
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxage, boolean isEncode) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, isEncode);
    }

    /**
     * 設置Cookie的值 在指定時間內生效, 編碼參數(指定編碼)
     *
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage
     * @param encodeString
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxage, String encodeString) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, encodeString);
    }

    /**
     * 刪除Cookie帶cookie域名
     *
     * @param request
     * @param response
     * @param cookieName
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        doSetCookie(request, response, cookieName, "", -1, false);
    }

    /**
     * 設置Cookie的值，並使其在指定時間內生效
     *
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage
     * @param isEncode
     */
    private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxage, boolean isEncode) {
        try {
            if (cookieValue == null) {
                cookieValue = "";
            } else if (isEncode) {
                cookieValue = URLEncoder.encode(cookieValue, "utf-8");
            }
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if (cookieMaxage > 0)
                cookie.setMaxAge(cookieMaxage);
            if (null != request) {// 設置域名的cookie
                String domainName = getDomainName(request);
                if (!"localhost".equals(domainName)) {
                    // 如果域名為localhost，直接設置則會報錯
                    System.out.println("Not localhost, can set domain. ");
                    System.out.println("cookie.setDomain(" + domainName + ");");
                    cookie.setDomain(domainName);
                }
                System.out.println(domainName);
            }
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 設置Cookie的值，並使其在指定時間內生效
     *
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage
     * @param encodeString
     */
    private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxage, String encodeString) {
        try {
            if (cookieValue == null) {
                cookieValue = "";
            } else {
                cookieValue = URLEncoder.encode(cookieValue, encodeString);
            }
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if (cookieMaxage > 0) {
                cookie.setMaxAge(cookieMaxage);
            }
            if (null != request) {// 設置域名的cookie
                String domainName = getDomainName(request);
                System.out.println(domainName);
                if (!"localhost".equals(domainName)) {
                    cookie.setDomain(domainName);
                }
            }
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到cookie的域名
     *
     * @param request
     * @return
     */
    private static String getDomainName(HttpServletRequest request) {
        //region 原項目代碼
        // String domainName = null;
        // // 通過request對象獲取訪問的url地址
        // String serverName = request.getRequestURL().toString();
        // if (serverName == null || serverName.equals("")) {
        //     domainName = "";
        // } else {
        //     // 將url地下轉換為小寫
        //     serverName = serverName.toLowerCase();
        //     // 如果url地址是以http://開頭  將http://截取
        //     if (serverName.startsWith("http://")) {
        //         serverName = serverName.substring(7);
        //     }
        //     int end = serverName.length();
        //     // 判斷url地址是否包含"/"
        //     if (serverName.contains("/")) {
        //         //得到第一個"/"出現的位置
        //         end = serverName.indexOf("/");
        //     }
        //
        //     // 截取
        //     serverName = serverName.substring(0, end);
        //     // 根據"."進行分割
        //     final String[] domains = serverName.split("\\.");
        //     int len = domains.length;
        //     if (len > 3) {
        //         // www.xxx.com.cn
        //         domainName = domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
        //     } else if (len <= 3 && len > 1) {
        //         // xxx.com or xxx.cn
        //         domainName = domains[len - 2] + "." + domains[len - 1];
        //     } else {
        //         domainName = serverName;
        //     }
        // }
        //
        // if (domainName != null && domainName.indexOf(":") > 0) {
        //     String[] ary = domainName.split("\\:");
        //     domainName = ary[0];
        // }
        //endregion

        try {
            URL url = new URL(request.getRequestURL().toString());
            return url.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }
}