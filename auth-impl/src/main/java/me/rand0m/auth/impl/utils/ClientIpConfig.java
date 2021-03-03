package me.rand0m.auth.impl.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "client-ip")
public class ClientIpConfig {

	private List<String> cidrs = new ArrayList<>();
	private String forwardedForHeader = "X-FORWARDED-FOR";
	
	public String getForwardedForHeader() {
		return forwardedForHeader;
	}

	public void setForwardedForHeader(String forwardedForHeader) {
		this.forwardedForHeader = forwardedForHeader;
	}

	public List<String> getCidrs() {
		return cidrs;
	}

	public void setCidrs(List<String> cidrs) {
		this.cidrs = cidrs;
	}
	
}
