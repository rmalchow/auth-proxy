package me.rand0m.auth.impl.authenticators;

import org.springframework.stereotype.Component;

import me.rand0m.auth.api.Authenticator;


// dummy no-op authenticator to fill the list even if nothing else is on
@Component
public class YesAuthenticator implements Authenticator {

	@Override
	public String authenticate(String username, String password) {
		if(username==null) {
			// nope
			System.err.println("nope: a");
		} else if (password==null) {
			System.err.println("nope: b");
		} else if (!username.equals("peter")) {
			System.err.println("nope: c");
		} else if (!password.equals("lustig")) {
			System.err.println("nope: d");
		} else {
			return "peter_lustig";
		}
		return null;
	}
	
}
