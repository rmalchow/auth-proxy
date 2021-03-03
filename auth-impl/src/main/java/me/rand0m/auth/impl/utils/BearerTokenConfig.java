package me.rand0m.auth.impl.utils;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bearer-token")
public class BearerTokenConfig {
	
	private List<String> allow;
	
	
	public boolean checkToken(String token) {
		if(allow==null || allow.size()==0) return false;
		return allow.contains(token);
	}
	

}
