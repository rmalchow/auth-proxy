package me.rand0m.auth.impl.authenticators;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.rand0m.auth.api.Authenticator;
import me.rand0m.auth.impl.UserTokenWriter;

@Component
public class AuthenticatorService {

	private static Log log = LogFactory.getLog(AuthenticatorService.class);

	@Autowired
	private List<Authenticator> authenticators;
	
	@Autowired
	private UserTokenWriter writer;
	
	public String authenticate(String username, String password) {
		for(Authenticator a : authenticators) {
			log.info("trying authenticator: "+a.getClass());
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
	
}
