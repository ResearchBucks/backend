package org.researchbucks.ResearcherService_API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
public class ResearcherServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResearcherServiceApiApplication.class, args);
	}

}
