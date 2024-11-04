package edu.systemia.products_ms.services;

import edu.systemia.products_ms.dto.ProductDTO;
import edu.systemia.products_ms.repository.ProductRepository;
import edu.systemia.products_ms.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductStreamService {

	private final ProductRepository productRepository;
	//	private final Sinks.Many<ProductDTO> sink = Sinks.many().replay().latest();
	private final Sinks.Many<ProductDTO> sink = Sinks.many().replay().all();

	public Flux<ProductDTO> getProductsStream() {
		return sink.asFlux();
	}

	public Mono<ProductDTO> createProduct(Mono<ProductDTO> dtoMono) {
		return dtoMono.map(AppUtils::toEntity)
			.flatMap(productRepository::insert)
			.map(AppUtils::toDto);
	}
}
