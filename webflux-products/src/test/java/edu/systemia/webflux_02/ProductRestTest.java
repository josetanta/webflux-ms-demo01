package edu.systemia.webflux_02;

import edu.systemia.webflux_02.dto.ProductDTO;
import edu.systemia.webflux_02.rest.ProductRest;
import edu.systemia.webflux_02.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ProductRest.class)
class ProductRestTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private ProductService productService;

	@Test
	void whenCreateProduct_thenResponseOK() {
		Mono<ProductDTO> productDTOMono = Mono.just(
			ProductDTO.builder()
				.id("123")
				.name("prod-1")
				.price(23.3)
				.quantity(2)
				.build()
		);

		when(productService.createProduct(productDTOMono))
			.thenReturn(productDTOMono);

		webTestClient.post()
			.uri("/products")
			.body(Mono.just(productDTOMono), ProductDTO.class)
			.exchange()
			.expectStatus().isOk();
	}

	@Test
	void whenRequestGetAllProducts_thenResponseWithAllProducts() {
		Flux<ProductDTO> allProducts = Flux.just(
			ProductDTO.builder()
				.id("123")
				.name("prod-1")
				.price(23.3)
				.quantity(2)
				.build(),
			ProductDTO.builder()
				.id("223")
				.name("prod-2")
				.price(54.1)
				.quantity(2)
				.build(),
			ProductDTO.builder()
				.id("323")
				.name("prod-3")
				.price(43.3)
				.quantity(22)
				.build()
		);

		when(productService.getProducts())
			.thenReturn(allProducts);

		Flux<ProductDTO> responseBody = webTestClient.get()
			.uri("/products")
			.exchange()
			.expectStatus().isOk()
			.returnResult(ProductDTO.class)
			.getResponseBody();

		StepVerifier.create(responseBody)
			.expectSubscription()
			.expectNext(ProductDTO.builder()
				.id("123")
				.name("prod-1")
				.price(23.3)
				.quantity(2)
				.build())
			.expectNext(ProductDTO.builder()
				.id("223")
				.name("prod-2")
				.price(54.1)
				.quantity(2)
				.build())
			.expectNext(ProductDTO.builder()
				.id("323")
				.name("prod-3")
				.price(43.3)
				.quantity(22)
				.build())
			.verifyComplete();
	}

	@Test
	void givenAnID_thenReturnAProductWithID() {
		String id = "123";
		ProductDTO product = ProductDTO.builder()
			.id(id)
			.name("prod-1")
			.price(23.3)
			.quantity(2)
			.build();
		Mono<ProductDTO> productDTOMono = Mono.just(product);

		when(productService.getProductByID(id))
			.thenReturn(productDTOMono);

		Flux<ProductDTO> responseBody = webTestClient.get()
			.uri("/products/" + id)
			.exchange()
			.expectStatus().isOk()
			.returnResult(ProductDTO.class)
			.getResponseBody();

		StepVerifier.create(responseBody)
			.expectSubscription()
			.expectNext(product)
			.verifyComplete();
	}

	@Test
	void givenExistingProduct_whenUpdateRequested_thenProductIsUpdated() {
		String id = "123";
		ProductDTO product = ProductDTO.builder()
			.id(id)
			.name("prod-1")
			.price(23.3)
			.quantity(2)
			.build();

		ProductDTO productUpdated = ProductDTO.builder()
			.id(id)
			.name("prod-1[UPDATE]")
			.price(23)
			.quantity(1)
			.build();

		Mono<ProductDTO> productDTOMono = Mono.just(product);
		Mono<ProductDTO> productDTOMonoUpdated = Mono.just(productUpdated);

		when(productService.updateProduct(id, productDTOMono))
			.thenReturn(productDTOMonoUpdated);

		webTestClient.put()
			.uri("/products/" + id)
			.exchange()
			.expectStatus().isOk();
	}

	@Test
	void givenExistingProductID_whenDeleteRequested_thenProductIsDeleted() {
		given(productService.deleteProduct(any()))
			.willReturn(Mono.empty());

		webTestClient.delete()
			.uri("/products/23")
			.exchange()
			.expectStatus().isOk();
	}
}
