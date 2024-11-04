package edu.systemia.webflux_consuming.rest;

import edu.systemia.webflux_consuming.services.ProductServiceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductConsumerRest {

	private final ProductServiceAdapter productService;

	@GetMapping(value = "/subs", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> getProducts() {
		return productService.subscribeEvent();
	}

	@GetMapping(value = "/subs-delay", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> getProductsDelay() {
		return productService.subscribeEventDelay();
	}
}
