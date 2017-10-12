package com.example.trece.two;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class Trece2Application {

	private final Logger logger= LoggerFactory.getLogger(Trece2Application.class);

	@RequestMapping("/trace-2")
	private String trace(){
		logger.info("===<call tarce-2>===");
		return "Trace";
	}
	public static void main(String[] args) {
		SpringApplication.run(Trece2Application.class, args);
	}
}
