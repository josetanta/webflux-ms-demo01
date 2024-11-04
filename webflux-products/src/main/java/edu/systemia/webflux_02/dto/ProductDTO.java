package edu.systemia.webflux_02.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
	private String id;

	@NotBlank
	@NotNull
	@Size(max = 20)
	private String name;

	@Min(1)
	private int quantity;

	@Digits(fraction = 2, integer = 5)
	private double price;
}
