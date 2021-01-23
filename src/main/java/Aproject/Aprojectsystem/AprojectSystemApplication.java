package Aproject.Aprojectsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// Spring Boot 2.x

@SpringBootApplication
public class AprojectSystemApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AprojectSystemApplication.class, args);

	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	private static Class<AprojectSystemApplication> applicationClass = AprojectSystemApplication.class;

}