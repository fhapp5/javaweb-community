package io.javaweb.community.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSON;

import io.javaweb.community.common.Message;

/**
 * WebUtils
 * @author KevinBlandy
 *
 */
public class WebUtils {
	
	private static final String AJAX_HEADER_NAME = "x-requested-with";	//ajax 请求头key
	
	private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";	//ajax 请求头value
	
	/**
	 * 是否是ajax请求
	 * @param request
	 * @return
	 */
    public static boolean isAjaxRequest(HttpServletRequest request){
    	String header = request.getHeader(AJAX_HEADER_NAME);
	    return header == null ? false : header.equalsIgnoreCase(AJAX_HEADER_VALUE);
    }
    
//    /**
//     * 是否是OPTIONS请求(CORS预请求)
//     * @param request
//     * @return
//     */
//    public static boolean isOptionsRequest(HttpServletRequest request) {
//    	return request.getMethod().equalsIgnoreCase(RequestMethod.OPTIONS.name());
//    }
    
    /**
     * 跨域支持
     * @param response
     * @param allowOrigin
     */
    public static void supporCors(HttpServletResponse response,String allowOrigin) {
    	response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, allowOrigin);
    	response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
    	response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET,POST,PUT,OPTIONS,DELETE");
    }
    
    /**
     * 响应JSON数据
     * @param responseBody
     * @param response
     * @throws IOException
     */
    public static void responseToJson(Message<?> responseBody,HttpServletResponse response) throws IOException {
    	response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    	response.getWriter().write(JSON.toJSONString(responseBody));
    	response.setStatus(HttpServletResponse.SC_OK);
    	response.flushBuffer();
    }
    
    /**
     * JS重定向
     * @param response
     * @param path
     * @throws IOException
     */
    public static void jsRedirect(HttpServletResponse response,String path) throws IOException{
    	response.setContentType(MediaType.TEXT_HTML_VALUE);
        response.getWriter().write("<script>top.location.href=\"" + path + "\"</script>");
        response.flushBuffer();
    }
    
    /**
     * 获取客户端真实的IP
     * @param request
     * @return
     */
    public static String getClientIP(HttpServletRequest request) {
    	String ip = request.getHeader("X-Requested-For");
		if (GeneralUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (GeneralUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (GeneralUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (GeneralUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (GeneralUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if(GeneralUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (GeneralUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
    }

	public static InetAddress getLocalHostLANAddress()  {
		try {
			InetAddress candidateAddress = null;
			// 遍历所有的网络接口
			for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
				NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
				// 在所有的接口下再遍历IP
				for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
					InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
					if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
						if (inetAddr.isSiteLocalAddress()) {
							// 如果是site-local地址，就是它了
							return inetAddr;
						} else if (candidateAddress == null) {
							// site-local类型的地址未被发现，先记录候选地址
							candidateAddress = inetAddr;
						}
					}
				}
			}
			if (candidateAddress != null) {
				return candidateAddress;
			}
			// 如果没有发现 non-loopback地址.只能用最次选的方案
			InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
			return jdkSuppliedAddress;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    /**
     * 获取指定名称的cookie 
     * @param request
     * @param cookieName
     * @return
     */
    public static Cookie getCookie(HttpServletRequest request,String cookieName){
    	Cookie[] cookies = request.getCookies();
    	if(cookies != null) {
    		for(Cookie cookie : cookies) {
    			if(cookie.getName().equals(cookieName)) {
    				return cookie;
    			}
    		}
    	}
    	return null;
    }
    
    /**
     * 获取指定名称cookie的值
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request,String cookieName) {
    	Cookie cookie = getCookie(request, cookieName);
    	return cookie == null ? null : cookie.getValue();
    }
}
