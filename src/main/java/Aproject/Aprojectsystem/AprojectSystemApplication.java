package Aproject.Aprojectsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.jms.annotation.EnableJms;

// Spring Boot 2.x

@SpringBootApplication
@EnableJms
@EnableCaching
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