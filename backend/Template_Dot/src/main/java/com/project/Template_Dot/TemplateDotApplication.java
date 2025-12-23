package com.project.Template_Dot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class TemplateDotApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemplateDotApplication.class, args);
	}

}
