package es.ericsson.masterCraftmanship.tfm.checkers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;



@SpringBootApplication(scanBasePackages = { "es.ericsson.masterCraftmanship.tfm.apiRestControllers",
		"es.ericsson.masterCraftmanship.tfm.businessControllers", "es.ericsson.masterCraftmanship.tfm.models",
		"es.ericsson.masterCraftmanship.tfm.daos", "es.ericsson.masterCraftmanship.tfm.dtos", "es.ericsson.masterCraftmanship.tfm.services"},
        exclude = {ErrorMvcAutoConfiguration.class})
@EntityScan("es.ericsson.masterCraftmanship.tfm.models")
@EnableMongoRepositories("es.ericsson.masterCraftmanship.tfm.daos")
public class Application {

	private static ConfigurableApplicationContext app;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}
	
	public static void start() {
		start(new String[] {});
	}

	private static void start(String[] args) {
		if (app == null) {
			app = SpringApplication.run(Application.class, args);
		}
	}

	public static void stop() {
		if (app != null) {
			app.close();
		}
	}


}
