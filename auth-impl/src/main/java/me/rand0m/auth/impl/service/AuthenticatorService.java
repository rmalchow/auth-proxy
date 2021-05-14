package me.rand0m.auth.impl.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.rand0m.auth.api.Authenticator;
import me.rand0m.auth.impl.crypto.UserTokenWriter;

@Component
public class AuthenticatorService {

	private static Log log = LogFactory.getLog(AuthenticatorService.class);

	@Autowired
	private List<Authenticator> authenticators;
	
	@Autowired
	private UserTokenWriter writer;
	
	public String authenticate(String username, String password) {
		for(Authenticator a : authenticators) {
			log.info("authenticator: "+a.getClass().getSimpleName());
			try {
				String user = a.authenticate(username, password);
				if(user!=null) {
					return writer.createToken(user, DateUtils.addDays(new Date(), 7));
				}
			} catch (Exception e) {
				log.warn("error in authenticator: "+a.getClass(),e);
			
			}
		}
		return null;
	}
	
	public String authenticate(HttpServletRequest request) {
		for(Authenticator a : authenticators) {
			try {
				String user = a.authenticate(request);
				if(user!=null) {
					return user;
				}
			} catch (Exception e) {
				log.warn("error in authenticator: "+a.getClass(),e);
			}
		}
		return null;
	}
	
}
