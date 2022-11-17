package com.api.teaeduc;


import javax.annotation.PostConstruct;

import com.api.teaeduc.utils.JWTUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.Getter;

@SpringBootApplication
// @RestController
public class TeaEducApplication {

	@Getter
	@Value("${expiration_time_token_miliseconds}")
	private Long expiration;

	@PostConstruct
    public void onStartup() {

		JWTUtil.setExpiration(expiration);
    }

	public static void main(String[] args) {	
		SpringApplication.run(TeaEducApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT", "PATCH");
				registry.addMapping("/**").exposedHeaders("Content-Disposition");
			}
		};
	}
}
