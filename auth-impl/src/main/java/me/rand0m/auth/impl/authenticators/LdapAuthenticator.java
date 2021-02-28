package me.rand0m.auth.impl.authenticators;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.naming.NameClassPair;
import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.ldap.core.AuthenticationSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.NameClassPairCallbackHandler;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
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
	public void ldapTemplate() {
		template = ldapTemplate(config.getBinduser(), config.getBindpassword());
	}		

	public LdapTemplate ldapTemplate(String dn, String password) {
		
		LdapContextSource contextSource = new LdapContextSource();
		
		contextSource.setUrls(new String[] { config.getUrl() });
		
		contextSource.setAuthenticationSource(
			new AuthenticationSource() {
				
				@Override
				public String getPrincipal() {
					return dn;
				}
				
				@Override
				public String getCredentials() {
					return password;
				}
			}
		);
		contextSource.setPooled(true);
		contextSource.setCacheEnvironmentProperties(true);

		contextSource.afterPropertiesSet();
		// only local
		LdapTemplate template = new LdapTemplate(contextSource);
	    template.setIgnorePartialResultException(true);
	    template.setIgnoreSizeLimitExceededException(true);
	    template.setDefaultSearchScope(SearchScope.SUBTREE.getId());
	    return template;

	}
	
	
	@Override
	public String authenticate(String username, String password) {
		final String u = username.replace("[^a-zA-Z0-9_-@]+", "");
		final String f = config.getFilter().replaceAll("\\{\\{username\\}\\}", u);
		LdapQuery lq  = LdapQueryBuilder.query().base(config.getBase()).searchScope(SearchScope.SUBTREE).filter(f);
		
		List<String> usernames = new ArrayList<>();
		
		template.search(lq, new NameClassPairCallbackHandler() {
			
			@Override
			public void handleNameClassPair(NameClassPair nameClassPair) throws NamingException {
				try {
					System.err.println("trying to authenticate: "+u+" --> "+nameClassPair.getNameInNamespace());
					LdapTemplate t2 = ldapTemplate(nameClassPair.getNameInNamespace(), password);
					t2.lookup(nameClassPair.getNameInNamespace());
					usernames.add(u);
				} catch (Exception e) {
					throw new NamingException("auth failed");
				}
			}
		});
		
		System.err.println("ldap auth - # of results: "+usernames.size());
		
		for(String x : usernames) {
			return x;
		}
		
		return null;
	}
	
}
