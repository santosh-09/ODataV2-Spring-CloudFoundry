package com.sap.cf.odata.spring.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.sap.cf.odata.spring.config.CloudConfig;

/**
 * Main class for this application. extends {@link SpringBootServletInitializer}
 * it making a web application for war deployment.
 * 
 * @author i324363
 *
 */

@SpringBootApplication
public class AppController extends SpringBootServletInitializer {

	// Main method gives control to Spring by invoking run on Spring Application.
	// This enables Spring to Bootstrap the application
	public static void main(String[] args) {
		SpringApplication.run(AppController.class, args);

	}

	// This method adds Configuration class for Spring Application Context builder
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AppController.class, CloudConfig.class);
	}
}
