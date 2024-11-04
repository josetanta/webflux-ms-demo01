package edu.systemia.webflux_02.rest;

import edu.systemia.webflux_02.dto.ProductDTO;
import edu.systemia.webflux_02.services.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Validated
public class ProductRest {

	private final ProductService productService;

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ProductDTO> getProducts() {
		//		return ServerResponse.ok().body(products, ProductDTO.class);
		return productService.getProducts();
	}

	@GetMapping("/{product-id}")
	public Mono<ProductDTO> getProductByID(@PathVariable("product-id") @Size(min = 1) String id) {
		return productService.getProductByID(id);
	}

	@GetMapping("/between-range")
	public Flux<ProductDTO> getProductByRange(@RequestParam @Min(1) double min, @RequestParam @Min(1) double max) {
		return productService.getProductInRange(min, max);
	}

	@PostMapping
	public Mono<ProductDTO> postCreateProduct(@RequestBody @Valid Mono<ProductDTO> dtoMono) {
		return productService.createProduct(dtoMono);
	}

	@PutMapping("/{product-id}")
	public Mono<ProductDTO> puUpdateProduct(
		@PathVariable("product-id") String id,
		@RequestBody @Valid Mono<ProductDTO> dtoMono
	) {
		return productService.updateProduct(id, dtoMono);
	}

	@DeleteMapping("/{product-id}")
	public Mono<Void> deleteRemoveProduct(@PathVariable("product-id") @Size(min = 1) String id) {
		return productService.deleteProduct(id);
	}

	@GetMapping(value = "/delay", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ProductDTO> getProductsDelay1Second() {
		//		return ServerResponse.ok().body(products, ProductDTO.class);
		return productService.getProducts()
			.delayElements(Duration.ofSeconds(1));
	}
}
