package me.rand0m.auth.impl.authenticators;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import me.rand0m.auth.api.Authenticator;
import me.rand0m.auth.impl.utils.BearerTokenConfig;

@Component
@ConditionalOnProperty(name = "bearer-token.enabled", havingValue = "true")
public class BearerTokenAuthenticator implements Authenticator {
	
	private static Log log = LogFactory.getLog(BearerTokenAuthenticator.class);

	@Autowired
	private BearerTokenConfig config;

	@Override
	public String authenticate(HttpServletRequest request) {
		String auth = request.getHeader("Authorization");
		if(auth==null) {
			return null;
		}

		if(config.getAllow()==null || config.getAllow().size()==0) {
		}

		for(String a : config.getAllow()) {
			if(auth.endsWith(a)) {
				return a;
			}
		}
		return null;
	}
	

}
