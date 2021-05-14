package me.rand0m.auth.impl.authenticators;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import me.rand0m.auth.api.Authenticator;
import me.rand0m.auth.impl.utils.WebLdapConfig;

@Component
@ConditionalOnProperty(name = "web-ldap.enabled", havingValue = "true")
public class WebLdapAuthenticator implements Authenticator {

	private static Log log = LogFactory.getLog(WebLdapAuthenticator.class);
	
	@Autowired
	private WebLdapConfig config;
	
	@Override
	public String authenticate(String username, String password) {
		
		
		try {
			CloseableHttpClient c = HttpClients.createDefault();

			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			nvps.add(new BasicNameValuePair("username", username));
			nvps.add(new BasicNameValuePair("password", password));
			if(config.getGroupIds()!=null && config.getGroupIds().size()>0) {
				nvps.add(new BasicNameValuePair("groupIds", StringUtils.join(",",config.getGroupIds())));
			}
			
			URIBuilder u = new URIBuilder(config.getBaseUrl())
					.setPath("/api/authenticate");
			
			HttpPost p = new HttpPost(u.build());
			p.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));		
			
			HttpResponse r = c.execute(p);
			
			log.info("authenticating "+username+" / status: "+r.getStatusLine().getStatusCode());
			
			if(r.getStatusLine().getStatusCode() == 200) {
				return username;
			}
		} catch (Exception e) {
			log.warn("error getting authentication: ",e);
		}
		return null;
	}
	
}
