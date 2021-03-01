package me.rand0m.auth.impl.crypto;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.mcg.jwt.TokenReader;

@Component
public class UserTokenReader extends TokenReader<String> {

	@Override
	public String unmap(Map<String, Object> arg0) {
		return (String) arg0.get("username");
	}

}
