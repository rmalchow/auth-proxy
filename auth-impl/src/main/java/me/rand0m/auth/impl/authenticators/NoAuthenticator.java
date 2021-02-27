package me.rand0m.auth.impl.authenticators;

import org.springframework.stereotype.Component;

import me.rand0m.auth.api.Authenticator;


// dummy no-op authenticator to fill the list even if nothing else is on
@Component
public class NoAuthenticator implements Authenticator {

	@Override
	public String authenticate(String username, String password) {
		return null;
	}
	
}
