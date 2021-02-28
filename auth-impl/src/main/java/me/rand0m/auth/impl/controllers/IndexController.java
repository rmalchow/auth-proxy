package me.rand0m.auth.impl.controllers;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mcg.jwt.KeyGenerator;
import com.mcg.jwt.crypto.DefaultKeyGenerator;

import me.rand0m.auth.impl.service.AuthenticatorService;

@Controller
@RequestMapping(value = "")
public class IndexController {
	
	private static Log log = LogFactory.getLog(IndexController.class);

	
	@Value(value = "${host:http://127.0.0.1:8080/}")
	private String host;
	
	@Autowired
	private AuthenticatorService authenticatorService;
	
	@Bean
	public KeyGenerator keyGenerator() {
		return new DefaultKeyGenerator();
	}

	@RequestMapping(value = "/__auth__",method = {RequestMethod.GET, RequestMethod.POST} )
	public ModelAndView overview(
			@RequestParam(required = false) String username, 
			@RequestParam(required = false) String password,
			HttpServletResponse response
			) throws IOException {
		ModelAndView out = new ModelAndView("index");
		out.addObject("password", password);
		out.addObject("username", username);
		if(username!=null) {
			log.info("authenticating: "+username);
			String s = authenticatorService.authenticate(username, password);
			log.info("user is: "+s);
			if(s!=null) {
				Cookie c = new Cookie("__jwt__", s);
				c.setHttpOnly(true);
				c.setPath("/");
				c.setMaxAge(60*60*24);
				response.addCookie(c);
				response.sendRedirect(host);
				return null;
			} else {
				out.addObject("hasError", true);
			}
		}
		return out;
	}
	
	
}
