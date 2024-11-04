package edu.systemia.consumer_ms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.reactive.function.client.WebClient;

@Order(1)
@Configuration
public class WebClientConfig {

	@Value("${services.service-products}")
	private String productService;

	@Bean
	public WebClient webClient() {
		return WebClient.builder()
			.baseUrl(productService)
			.build();
	}
}
