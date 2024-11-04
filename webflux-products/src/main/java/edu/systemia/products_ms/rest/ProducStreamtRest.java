package edu.systemia.products_ms.rest;

import edu.systemia.products_ms.dto.ProductDTO;
import edu.systemia.products_ms.services.ProductStreamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products-stream")
@RequiredArgsConstructor
@Validated
public class ProducStreamtRest {

	private final ProductStreamService productService;

	@PostMapping
	public Mono<ProductDTO> postCreateProduct(@RequestBody @Valid Mono<ProductDTO> dtoMono) {
		return productService.createProduct(dtoMono);
	}

	@GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ProductDTO> getProductsDelay1Second() {
		//		return ServerResponse.ok().body(products, ProductDTO.class);
		return productService.getProductsStream();
	}
}
