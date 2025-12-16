package com.example.HeThongQuanLyTaiChinhThongMinh;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;


@SpringBootApplication
@Slf4j
public class HeThongQuanLyTaiChinhThongMinhApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(HeThongQuanLyTaiChinhThongMinhApplication.class, args);
		Environment env = context.getEnvironment();

		String appName = env.getProperty("spring.application.name", "Application").toUpperCase();
		String port = env.getProperty("local.server.port", env.getProperty("server.port", "8080"));

		log.info("------------------------- START {} ------------------------------", appName);
		log.info("   Application         : {}", appName);
		log.info("   Swagger UI          : http://localhost:{}/swagger-ui/index.html", port);
		log.info("   OpenAPI Docs        : http://localhost:{}/v3/api-docs", port);
		log.info("   URL Login           : http://localhost:{}/page/home", port);
		log.info("------------------------- START SUCCESS {} -----------------------", appName);
	}
}
