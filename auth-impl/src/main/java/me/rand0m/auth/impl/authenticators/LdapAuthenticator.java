package me.rand0m.auth.impl.authenticators;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.ldap.core.AuthenticationSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.odm.core.ObjectDirectoryMapper;
import org.springframework.ldap.query.SearchScope;
import org.springframework.stereotype.Component;

import me.rand0m.auth.api.Authenticator;
import me.rand0m.auth.impl.utils.LdapConfig;

@Component
@ConditionalOnProperty(name = "ldap.enabled", havingValue = "true")
public class LdapAuthenticator implements Authenticator {

	
	@Autowired
	private LdapConfig config;
	
	private LdapTemplate template;
	
	@PostConstruct
	public LdapTemplate ldapTemplate() {
		if(template!=null) return template;
		
		LdapContextSource contextSource = new LdapContextSource();
		
		contextSource.setUrls(new String[] { config.getUrl() });
		
		contextSource.setAuthenticationSource(
			new AuthenticationSource() {
				
				@Override
				public String getPrincipal() {
					return config.getBinduser();
				}
				
				@Override
				public String getCredentials() {
					return config.getBindpassword();
				}
			}
		);
		contextSource.setPooled(true);
		contextSource.setCacheEnvironmentProperties(true);

		contextSource.afterPropertiesSet();
	    template = new LdapTemplate(contextSource);
	    template.setIgnorePartialResultException(true);
	    template.setIgnoreSizeLimitExceededException(true);
	    template.setDefaultSearchScope(SearchScope.SUBTREE.getId());
	    return template;

	}
	
	
	@Override
	public String authenticate(String username, String password) {
		return null;
	}
	
}
