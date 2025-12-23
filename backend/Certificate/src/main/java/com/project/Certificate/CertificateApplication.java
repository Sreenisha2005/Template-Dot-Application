package com.project.Certificate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CertificateApplication {

	public static void main(String[] args) {
		SpringApplication.run(CertificateApplication.class, args);
	}

}
