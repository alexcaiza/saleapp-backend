package com.saleapp.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SaleDetailDTO {
	
	@EqualsAndHashCode.Include
	private Integer idSaleDetail;
	
	@JsonIgnore
	// Escritura ilimitada
	// Que no se exprese en el json
	// Se ignora a nivel de request y response
    private SaleDTO sale;
	
	@NotNull
	private ProductDTO product;

	@NotNull
    private Integer quantity;

}
