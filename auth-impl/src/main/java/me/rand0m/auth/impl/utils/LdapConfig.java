package me.rand0m.auth.impl.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ldap")
public class LdapConfig {

	private boolean enabled;
	private String url;
	private String base;

	private String binduser;
	private String bindpassword;

	private String filter;

	public String getBinduser() {
		return binduser;
	}

	public void setBinduser(String binduser) {
		this.binduser = binduser;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getBindpassword() {
		return bindpassword;
	}

	public void setBindpassword(String bindpassword) {
		this.bindpassword = bindpassword;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

}
