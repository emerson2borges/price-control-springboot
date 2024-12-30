package com.emerson.pricecontrol;

import com.emerson.pricecontrol.entity.Supermarket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.emerson.pricecontrol.repository")
@EntityScan("com.emerson.pricecontrol.entity")  // Entidades JPA
public class PriceControlSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceControlSpringbootApplication.class, args);
	}

}
