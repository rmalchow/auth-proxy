package me.rand0m.auth.api;

import javax.servlet.http.HttpServletRequest;

public interface Authenticator {

	default String authenticate(String username, String password) {
		return null;
	}
	
	default String authenticate(HttpServletRequest request) {
		return null;
	}
	
	
}
