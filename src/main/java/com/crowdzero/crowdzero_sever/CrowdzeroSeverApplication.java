package com.crowdzero.crowdzero_sever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CrowdzeroSeverApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrowdzeroSeverApplication.class, args);
	}

}
