package me.rand0m.auth.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Configuration
public class ProxyJwtPreFilter extends ZuulFilter {

	private static Log log = LogFactory.getLog(ProxyJwtPreFilter.class);
	
	@Autowired
	private UserTokenReader reader;

	@Value(value = "${host:http://127.0.0.1:8080/}")
	private String host;

	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest q = ctx.getRequest();
		HttpServletResponse r = ctx.getResponse();
		if(q.getCookies()!=null) {
			for(Cookie c : q.getCookies()) {
				if(c.getName().equals("__jwt__")) {
					try {
						String user = reader.readToken(c.getValue());
						return null;
					} catch (Exception e) {
					}
				}
			}
		}
		log.info("no token found: redirecting ... ");
		ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(HttpStatus.SC_TEMPORARY_REDIRECT);
        r.setHeader("Location", host+"__auth__");
		return null;
	}

	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public String filterType() {
	    return "pre";
	}
	
	@Bean
	public static ProxyJwtPreFilter jwtPreFilter() {
		return new ProxyJwtPreFilter();
	}

}
