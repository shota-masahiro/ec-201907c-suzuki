package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@EnableAsync
@SpringBootApplication
public class Ec201907cSuzukiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Ec201907cSuzukiApplication.class, args);
	}


	/**
	 * WebAPI通信をする際に使用するオブジェクトをDIコンテナに登録します.
	 * 
	 * @return RestTemplate
	 */
	@Bean
	public RestTemplate setUpRestTemplate() {
		return new RestTemplate();
	}
	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Ec201907cSuzukiApplication.class);
	}

}