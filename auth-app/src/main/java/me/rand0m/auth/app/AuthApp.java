package me.rand0m.auth.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableZuulProxy
@SpringBootApplication
@ComponentScan(basePackages={"me.rand0m"})
@EnableScheduling
public class AuthApp {
	
	public static void main(String[] args) throws Exception {
	    SpringApplication.run(AuthApp.class, args);
	}

}
