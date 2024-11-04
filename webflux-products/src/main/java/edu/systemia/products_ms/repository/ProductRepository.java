package edu.systemia.products_ms.repository;

import edu.systemia.products_ms.dto.ProductDTO;
import edu.systemia.products_ms.entities.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

	Flux<ProductDTO> findAllByPriceBetween(Range<Double> priceRange);
}
