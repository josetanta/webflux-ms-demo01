package edu.systemia.webflux_02.utils;

import edu.systemia.webflux_02.dto.ProductDTO;
import edu.systemia.webflux_02.entities.Product;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

@UtilityClass
public class AppUtils {

	public static ProductDTO toDto(Product product) {
		var productDTO = new ProductDTO();
		BeanUtils.copyProperties(product, productDTO);
		return productDTO;
	}

	public static Product toEntity(ProductDTO dto) {
		var product = new Product();
		BeanUtils.copyProperties(dto, product);
		return product;
	}
}
