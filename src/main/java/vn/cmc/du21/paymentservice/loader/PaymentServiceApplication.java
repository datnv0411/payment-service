package vn.cmc.du21.paymentservice.loader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"vn.cmc.du21.paymentservice.presentation.external.controller",
		"vn.cmc.du21.paymentservice.presentation.internal.controller",
		"vn.cmc.du21.paymentservice.service"})
@EntityScan(basePackages = "vn.cmc.du21.paymentservice.persistence.internal.entity")
@EnableJpaRepositories(basePackages = "vn.cmc.du21.paymentservice.persistence.internal.repository")
public class PaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
	}

}
