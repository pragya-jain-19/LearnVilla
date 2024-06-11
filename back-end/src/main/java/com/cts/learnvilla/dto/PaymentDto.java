package com.cts.learnvilla.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

	private Integer paymentId;

	@NotNull
	private Integer studentId;

	@NotNull
	private List<String> courseIds = new ArrayList<>();

	private Double amount;

	private Boolean status;

}
