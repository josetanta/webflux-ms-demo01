package edu.systemia.webflux_02.repository;

import edu.systemia.webflux_02.dto.ProductDTO;
import edu.systemia.webflux_02.entities.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

	Flux<ProductDTO> findAllByPriceBetween(Range<Double> priceRange);
}
