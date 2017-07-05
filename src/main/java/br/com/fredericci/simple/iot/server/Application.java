package br.com.fredericci.simple.iot.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		
		Server server = context.getBean(Server.class);
		
		try {
			
			server.start();
			
		} catch (Exception e) {
			
			//Fatal Error, exception
			
		}
	}
	
}
