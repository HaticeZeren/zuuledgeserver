package com.example.zuuledgeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@EnableDiscoveryClient
@EnableEurekaClient
@EnableZuulProxy
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@SpringBootApplication
public class ZuulEdgeServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulEdgeServerApplication.class, args);
	}

	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true); //CORS yapılandırmasında kimlik doğrulama bilgilerine izin verir
		config.setAllowedOrigins(Collections.singletonList("*")); //herhangi bir domain'den gelen istek kabul edilecek.
		config.setAllowedHeaders(Collections.singletonList("*")); //istemciden gelen tüm başlıklar (headers) için izin verilir.
		config.setAllowedMethods(Arrays.stream(HttpMethod.values()).map(HttpMethod::name).collect(Collectors.toList())); // tüm HTTP metodlarına izin verir (GET, POST, PUT, DELETE, vb.)
		source.registerCorsConfiguration("/**", config); //CORS yapılandırması tüm URL'ler ("/**") için geçerli olacak
		return new CorsFilter(source);
	}

}
