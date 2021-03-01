package me.rand0m.auth.impl.authenticators;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import me.rand0m.auth.api.Authenticator;
import me.rand0m.auth.impl.utils.ClientIpConfig;

@Component
@ConditionalOnProperty(name = "clientIp.enabled", havingValue = "true")
public class ClientIpAuthenticator implements Authenticator {
	
	@Autowired
	private ClientIpConfig config;

	public boolean match(String ip) {
		// IMPLEMENT THIS //
		return false;
	}


	
	@Override
	public String authenticate(HttpServletRequest request) {
		String clientIp = request.getHeader(config.getForwardedForHeader());
		if(clientIp==null) return null;
		if(match(clientIp)) {
			return "clientIp: "+clientIp;
		}
		return null;
	}
	

}
