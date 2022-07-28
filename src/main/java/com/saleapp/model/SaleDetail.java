package com.saleapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.ForeignKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SaleDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idSaleDetail;
	
	@ManyToOne
    @JoinColumn(name = "id_sale", nullable = false, foreignKey = @ForeignKey(name = "FK_SALE_DETAIL_SALE"))
    private Sale sale;
	
	@ManyToOne
    @JoinColumn(name = "id_product", nullable = false, foreignKey = @ForeignKey(name = "FK_SALE_DETAIL_PRODUCT"))
    private Product product;
	
	@Column(nullable = false)
    private Integer quantity;

}
