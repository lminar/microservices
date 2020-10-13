package net.lminar.jobservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Application entry point.
 *
 * @author Lukas Minar
 */
@EnableScheduling
@EnableEurekaClient
@SpringBootApplication
@EnableJpaRepositories(basePackages = JobServiceApplication.PROJECT_PACKAGE)
public class JobServiceApplication {

	/**
	 * Project package.
	 */
	static final String PROJECT_PACKAGE = "net.lminar";

	public static void main(String[] args) {
		SpringApplication.run(JobServiceApplication.class, args);
	}
}
