package edu.systemia.webflux_consuming.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ProductServiceAdapter {

	private final WebClient webClient;

	public Flux<String> subscribeEvent() {
		return webClient.get()
			.uri("/products")
			.accept(MediaType.TEXT_EVENT_STREAM)
			.retrieve()
			.bodyToFlux(String.class)
			.log();
	}

	public Flux<String> subscribeEventDelay() {
		return webClient.get()
			.uri("/products/delay")
			.accept(MediaType.TEXT_EVENT_STREAM)
			.retrieve()
			.bodyToFlux(String.class)
			.log();
	}
}