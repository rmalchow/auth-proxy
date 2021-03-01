package me.rand0m.auth.impl.utils;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "path")
public class PathConfig {
	
	private List<String> allowPatterns;
	private List<String> denyPatterns;
	
	public boolean checkPath(String path) {
		if(getDenyPatterns()!=null) {
			for(String p : getDenyPatterns()) {
				if(path.matches(p)) return false;
			}
		}
		if (getAllowPatterns()!=null) {
			for(String p : getAllowPatterns()) {
				if(path.matches(p)) return true;
			}
		}
		return false;
	}

	public List<String> getAllowPatterns() {
		return allowPatterns;
	}

	public void setAllowPatterns(List<String> allowPatterns) {
		this.allowPatterns = allowPatterns;
	}

	public List<String> getDenyPatterns() {
		return denyPatterns;
	}

	public void setDenyPatterns(List<String> denyPatterns) {
		this.denyPatterns = denyPatterns;
	}
	

}
