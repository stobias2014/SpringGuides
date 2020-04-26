package com.tobias.saul.RESTfulConsumption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.tobias.saul.RESTfulConsumption.domain.Quote;

@SpringBootApplication
public class ResTfulConsumptionApplication {
	
	private static final Logger log = LoggerFactory.getLogger(ResTfulConsumptionApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ResTfulConsumptionApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception{
		return args -> {
			Quote quote = restTemplate.getForObject("https://gturnquist-quoters.cfapps.io/api/random", Quote.class);
			log.info(quote.toString());
		};
	}

}
