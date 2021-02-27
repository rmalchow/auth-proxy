package me.rand0m.auth.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mcg.jwt.TokenWriter;

@Component
public class UserTokenWriter extends TokenWriter<String> {

	@Override
	public Map<String, Object> map(String arg0) {
		HashMap<String, Object> out = new HashMap<String, Object>();
		out.put("username", arg0);
		return out;
	}

}
