package com.saleapp.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Data
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Sale {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idSale;
	
	@Column(nullable = false)	
	private LocalDateTime datetime;
	
	@Column(nullable = false)	
	private Double total;
	
	@ManyToOne
    @JoinColumn(name = "id_person", nullable = false, foreignKey = @ForeignKey(name = "FK_SALE_PERSON"))
    private Person person;
	
	@OneToMany(mappedBy = "sale", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<SaleDetail> details;
	
	
}
