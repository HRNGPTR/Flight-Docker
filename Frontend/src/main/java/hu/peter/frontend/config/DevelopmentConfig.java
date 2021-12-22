package hu.peter.frontend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@Configuration
@Profile("dev")
public class DevelopmentConfig {
	private static Logger logger = LoggerFactory.getLogger(DevelopmentConfig.class);

	@PostConstruct
	public void initDevelopmentProperties() throws Exception {
//		logger.info("**************************************************************");
//		logger.info("**** ZK-Springboot-Demo: development configuration active ****");
//		logger.info("**************************************************************");
	}
}
