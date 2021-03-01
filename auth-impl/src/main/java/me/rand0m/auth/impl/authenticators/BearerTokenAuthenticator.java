package me.rand0m.auth.impl.authenticators;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import me.rand0m.auth.api.Authenticator;
import me.rand0m.auth.impl.utils.BearerTokenConfig;

@Component
@ConditionalOnProperty(name = "bearerToken.enabled", havingValue = "true")
public class BearerTokenAuthenticator implements Authenticator {
	
	@Autowired
	private BearerTokenConfig config;

	@Override
	public String authenticate(HttpServletRequest request) {
		String auth = request.getHeader("Authorization");
		if(auth==null) return null;
		if(!auth.toUpperCase().startsWith("BEARER ")) return null;
		auth = auth.substring(7).trim();
		if(config.checkToken(auth)) {
			return auth;
		}
		return null;
	}
	

}
