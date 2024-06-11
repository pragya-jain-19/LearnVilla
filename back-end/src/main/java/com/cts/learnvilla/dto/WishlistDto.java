package com.cts.learnvilla.dto;

import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WishlistDto {

	private Integer wishlistId;

	@NotNull
	private Integer studentId;

	private List<String> courseIds;

	@NotNull
	@DecimalMin("0.0")
	private Double price = 0.0;

}
