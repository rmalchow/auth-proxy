package me.rand0m.auth.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
@ConditionalOnProperty(name = "dynamic_target", havingValue = "true", matchIfMissing = false)
public class RouteFilter extends ZuulFilter {

	@Value(value = "${host:http://127.0.0.1:8080/}")
	private String host;

	@Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
    	return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
		HttpServletResponse response = context.getResponse();
        
        if(request.getParameter("___target___")!=null) {
            String target = null;
        	target = request.getParameter("___target___");
			Cookie c = new Cookie("___target___", target);
			c.setHttpOnly(true);
			c.setPath("/");
			c.setMaxAge(60*60*24);
			response.addCookie(c);
			log.info("target set to: "+target);
			try {
				response.sendRedirect(host);
			} catch (IOException e) {
			}
			return null;
        }
        
        if(request.getCookies()==null) {
        	// nothing
        } else {
        	for (Cookie c : request.getCookies()) {
        		if(!c.getName().equalsIgnoreCase("___target___")) {
        			continue;
        		}
        		try {
        			URL u = new URL(c.getValue());
					context.setRouteHost(u);
					context.addZuulRequestHeader("Host", u.getHost());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
        	}
        }
        return null;
    }
}