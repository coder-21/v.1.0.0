/*Author : Diana P Lazar
Date created : 03/Mar/201
Copyright@ FindGoose
*/
package com.FG.utils;

import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FG.user.LoginService;
import com.FG.user.UserRegistrationInfo;

public class CookieUtil {
	private final static String COOKIE_TOKEN =Messages.getString("UTILS.cookieToken");
	
	@SuppressWarnings("unused")
	private boolean cookieExists(Cookie[] cookies, String cookieName) {
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if (cookie.getName() != null && cookie.getName().equals(cookieName)) {
	                return true;
	            }
	        }
	    }
	    return false;
	}
	 
	
	public static String addCookieToResponse_and_DB(HttpServletRequest request, HttpServletResponse response,UserRegistrationInfo user) 
	{		
		 String cookieToken = LoginService.processNewSessionToken();
		 Cookie sessionCookie = new Cookie(COOKIE_TOKEN, cookieToken); //$NON-NLS-1$
		 sessionCookie.setMaxAge(365 * 24 * 60 * 60); // one year
		 sessionCookie.setDomain(request.getServerName());
		 Cookie[] array = request.getCookies();
		 for (Cookie c:array) {
			System.out.println("path:" + c.getPath()+"---" + "name: "+c.getName());
		}
		 sessionCookie.setPath(request.getContextPath());
		 response.addCookie(sessionCookie);
		 return cookieToken;
	}

	public static void removeCookieFromResponse_and_DB(HttpServletRequest request, HttpServletResponse response, String cookieToken) {
	     Cookie[] cookies = request.getCookies();                
	     for (int i = 0; i < cookies.length; i++) {
	          Cookie c = cookies[i];            
	          if (c.getName().equals(cookieToken)) {        
	              c.setMaxAge(0);
	              c.setValue(null);
	              response.addCookie(c);    
	          } 
	     }     
	}
	
	public static String readCookieValue(HttpServletRequest request,String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for (Cookie cookie : cookies) {
			System.out.println("cookiePath: "+cookie.getPath());
			if (cookie.getName().equals(cookieName)) {
				System.out.println("InsidecookiePath: "+cookie.getPath());
				return cookie.getValue();
			}
		}
		return null;
	}
	
	private static void setNocache(HttpServletResponse response, String value, int age, String noCacheCookieName) {
	    Cookie cookie = new Cookie(noCacheCookieName, value);
	    cookie.setComment("Bypass proxy cache when logged in");
	    cookie.setMaxAge(age);
	    cookie.setHttpOnly(true);
	    cookie.setPath("/");
	    response.addCookie(cookie);
	}
	
	private void clearAllCookies(final HttpServletRequest request, final HttpServletResponse response) {
	    final Cookie cookies[] = request.getCookies();
	    if (cookies == null || cookies.length < 1)
	        return;
	    for (final Cookie cookie : cookies) {
	        cookie.setMaxAge(0);
	        cookie.setPath("/");
	        cookie.setValue(null);
	        response.addCookie(cookie);
	    }
	}
	
	/**
	 * Encode a cookie as per RFC 2109.  The resulting string can be used
	 * as the value for a <code>Set-Cookie</code> header.
	 *
	 * @param cookie The cookie to encode.
	 * @return A string following RFC 2109.
	 */
	public static String encodeCookie(Cookie cookie) {

	  StringBuffer buf = new StringBuffer(cookie.getName());
	  buf.append("=");
	  buf.append(cookie.getValue());

	  if (cookie.getComment() != null) {
	    buf.append("; Comment=\"");
	    buf.append(cookie.getComment());
	    buf.append("\"");
	  }

	  if (cookie.getDomain() != null) {
	    buf.append("; Domain=\"");
	    buf.append(cookie.getDomain());
	    buf.append("\"");
	  }

	  long age = cookie.getMaxAge();
	  if (cookie.getMaxAge() >= 0) {
	    buf.append("; Max-Age=\"");
	    buf.append(cookie.getMaxAge());
	    buf.append("\"");
	  }

	  if (cookie.getPath() != null) {
	    buf.append("; Path=\"");
	    buf.append(cookie.getPath());
	    buf.append("\"");
	  }

	  if (cookie.getSecure()) {
	    buf.append("; Secure");
	  }

	  if (cookie.getVersion() > 0) {
	    buf.append("; Version=\"");
	    buf.append(cookie.getVersion());
	    buf.append("\"");
	  }

	  return (buf.toString());
	}
	
	/**
	 * Given a cookie object, build a http-compliant cookie string header
	 *
	 * @param Cookie the cookie source object.
	 * @return the cookie string formatted as a http header.
	 * 
	 */
	public static String buildCookieString(Cookie cookie)
	{
	    StringBuffer buffer = new StringBuffer();

	    int version = cookie.getVersion();
	    if (version != -1) {
	       buffer.append("$Version=\"");          
	       buffer.append(cookie.getVersion());
	       buffer.append("\"; ");
	    }
	    // cookie name/value
	    buffer.append(cookie.getName());
	    //buffer.append("=\"");
	    buffer.append("=");
	    buffer.append(cookie.getValue());
	    //buffer.append("\"; ");

	    // cookie path
	    String path = cookie.getPath();
	    if (path != null) {
	        //buffer.append("path=\""); // $Path
	        buffer.append("; path=");
	        buffer.append(path);
	        //buffer.append("\"");        
	    }

	    String cookieHeader = buffer.toString();

	    return cookieHeader;
	}
	
	/**
	 * Parses cookies from the HTTP response header string
	 * and stores them into this instance's cookies collection
	 *
	 * @param cookieHeader the string from the response header with the cookies.
	 * @return true when cookies were found, otherwise false
	 *
	 */

//	public static boolean parseCookies(String cookieHeader, SiteSession session)
//	{                
//	    StringTokenizer st = new StringTokenizer(cookieHeader, " =;");
//	    String token, value;
//	    boolean firstTime = true; // cookie name/value always comes first
//	    Cookie cookie = null;
//
//	    while (st.hasMoreTokens()) 
//	    {
//	        token = st.nextToken(); 
//	        if (firstTime) {
//	            value  = st.nextToken(); 
//	            cookie = new Cookie(token, value);
//	            cookie.setVersion(-1);
//	            firstTime = false;
//	        }
//	        else if (token.equalsIgnoreCase("path")) {
//	            cookie.setPath(st.nextToken());
//	        }
//	        else if (token.equalsIgnoreCase("version")) {
//	            cookie.setVersion(Integer.getInteger(st.nextToken()).intValue());                
//	        }            
//	        else if (token.equalsIgnoreCase("max-age")) {
//	            cookie.setMaxAge( Integer.getInteger(st.nextToken()).intValue() );
//	        }
//	        else if (token.equalsIgnoreCase("domain")) {
//	            cookie.setDomain( st.nextToken() );
//	        }
//	        else if (token.equalsIgnoreCase("secure")) {
//	            cookie.setSecure(true);
//	        }
//	        else
//	        {
//	            if (null != cookie)                
//	                session.addCookieToSession(cookie);
//	            if (!st.hasMoreTokens()) {
//	                break;
//	            }
//	            value = st.nextToken(); 
//	            cookie = new Cookie(token, value);
//	            cookie.setVersion(-1);
//	       }           
//	    }
//	    if (null != cookie) 
//	       session.addCookieToSession(cookie);
//
//	    return (null != cookie); // found a cookie
//	}
//	 
//
}
