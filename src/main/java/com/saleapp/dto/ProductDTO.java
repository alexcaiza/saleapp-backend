package com.saleapp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
	
    private Integer idProduct;

    @NotEmpty
    @Size(min = 3, message = "{product.name.size}")
    private String name;

    @NotNull
    @Size(min = 3, message = "{product.brand.size}")
    private String brand;
}
