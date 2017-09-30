package com.example.fegin;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class FeginConsumerApplication {

	//调整日志级别
	//NONE:不记录任何信息
	//BASIC:记录请求方法，URL以及响应状态码和执行时间
	//HEADERS：除了记录BASIC级别的信息之外，还会记录请求和响应的头信息
	//FULL:记录所有的请求与相应的明细
	@Bean
	Logger.Level feignLoggerLevel(){
		return Logger.Level.FULL;
	}
	public static void main(String[] args) {
		SpringApplication.run(FeginConsumerApplication.class, args);
	}
}
