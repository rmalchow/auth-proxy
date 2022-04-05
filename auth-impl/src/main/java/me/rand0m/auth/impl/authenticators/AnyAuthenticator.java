package me.rand0m.auth.impl.authenticators;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import me.rand0m.auth.api.Authenticator;

@Component
@Order()
@ConditionalOnProperty(name = "any.enabled", havingValue = "true")
public class AnyAuthenticator implements Authenticator {

	private static Log log = LogFactory.getLog(AnyAuthenticator.class);
	
	@Override
	public String authenticate(HttpServletRequest request) {
		return "public";
	}
	

}
