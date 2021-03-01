package me.rand0m.auth.impl.authenticators;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import me.rand0m.auth.api.Authenticator;
import me.rand0m.auth.impl.crypto.UserTokenReader;

@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class JwtAuthenticator implements Authenticator {
	
	@Autowired
	private UserTokenReader reader;

	private static Log log = LogFactory.getLog(PathAuthenticator.class);

	@Override
	public String authenticate(HttpServletRequest request) {
		try {
			if(request.getCookies()!=null) {
				for(Cookie c : request.getCookies()) {
					if(c.getName().equals("__jwt__")) {
						try {
							return reader.readToken(c.getValue());
						} catch (Exception e) {
						}
					}
				}
			}
		} catch (Exception e) {
			log.warn("failed to check jwt: ",e);
		}
		return null;
	}
	

}
