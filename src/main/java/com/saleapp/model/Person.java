package com.saleapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Person {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPerson;

    @Column(length = 70, nullable = false)
    private String firstName;

    @Column(length = 70, nullable = false)
    private String lastName;
}
