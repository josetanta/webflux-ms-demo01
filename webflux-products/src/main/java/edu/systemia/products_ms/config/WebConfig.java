package edu.systemia.products_ms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Order(1)
@Configuration
public class WebConfig {

	@Bean
	CorsWebFilter corsWebFilter() {
		var config = new CorsConfiguration();
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");
		config.addAllowedOriginPattern("http://localhost:3000");
		config.setAllowCredentials(true);

		var source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return new CorsWebFilter(source);
	}
}
