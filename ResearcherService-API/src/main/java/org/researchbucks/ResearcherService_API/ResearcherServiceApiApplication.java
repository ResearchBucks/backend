package org.researchbucks.ResearcherService_API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ResearcherServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResearcherServiceApiApplication.class, args);
	}

}
