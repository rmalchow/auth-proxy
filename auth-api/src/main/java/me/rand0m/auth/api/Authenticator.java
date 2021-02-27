package me.rand0m.auth.api;

public interface Authenticator {

	public String authenticate(String username, String password);
	
}
