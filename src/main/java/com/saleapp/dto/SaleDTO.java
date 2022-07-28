package com.saleapp.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SaleDTO {
    
	@EqualsAndHashCode.Include
	private Integer idSale;
	
	@NotNull
	private PersonDTO person;
	
	@NotNull
	private Double total;
	
	@NotNull
	private LocalDateTime datetime;
	
	@NotNull
	private List<SaleDetailDTO> details;
	
}
