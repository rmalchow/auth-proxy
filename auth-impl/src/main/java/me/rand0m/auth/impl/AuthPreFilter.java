package me.rand0m.auth.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import me.rand0m.auth.impl.service.AuthenticatorService;

@Configuration
public class AuthPreFilter extends ZuulFilter {

	private static Log log = LogFactory.getLog(AuthPreFilter.class);
	
	@Value(value = "${host:http://127.0.0.1:8080/}")
	private String host;

	@Autowired
	private AuthenticatorService authenticatorService;
	
	public Object run() {
		
		WebMvcConfigurer wc;
		
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest q = ctx.getRequest();
		HttpServletResponse r = ctx.getResponse();
		
		String user = authenticatorService.authenticate(q);
		
		if(user==null) {
			// not authenticated
			ctx.setSendZuulResponse(false);
	        ctx.setResponseStatusCode(HttpStatus.SC_TEMPORARY_REDIRECT);
	        r.setHeader("Location", host+"__auth__");
		}
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
	public static AuthPreFilter jwtPreFilter() {
		return new AuthPreFilter();
	}

}
