package me.rand0m.auth.impl.authenticators;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import me.rand0m.auth.api.Authenticator;
import me.rand0m.auth.impl.utils.PathConfig;

@Component
@Order()
@ConditionalOnProperty(name = "path.enabled", havingValue = "true")
public class PathAuthenticator implements Authenticator {

	private static Log log = LogFactory.getLog(PathAuthenticator.class);
	
	@Autowired
	private PathConfig config;

	@Override
	public String authenticate(HttpServletRequest request) {
		try {
			if(config.checkPath(request.getRequestURI())) {
				return "public";
			}
		} catch (Exception e) {
			log.warn("failed to check path: ",e);
		}
		return null;
	}
	

}
