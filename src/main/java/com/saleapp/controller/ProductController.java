package com.saleapp.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.saleapp.dto.ProductDTO;
import com.saleapp.exception.ModelNotFoundException;
import com.saleapp.model.Product;
import com.saleapp.service.IProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
    private IProductService service;

    @Autowired
    private ModelMapper mapper;
	
    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        List<ProductDTO> list = service.findAll().stream().map(p -> mapper.map(p, ProductDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
	
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable("id") Integer id) {
        ProductDTO dtoResponse;
        Product obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(obj, ProductDTO.class);
        }
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }
	
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ProductDTO dto) {
    	Product p = service.save(mapper.map(dto, Product.class));
        //localhost:8080/products/3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getIdProduct()).toUri();
        return ResponseEntity.created(location).build();
    }
	
    @PutMapping
    public ResponseEntity<Product> update(@Valid @RequestBody ProductDTO dto) {
    	Product obj = service.findById(dto.getIdProduct());
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getIdProduct());
        }

        return new ResponseEntity<>(service.update(mapper.map(dto, Product.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
    	Product obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            service.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
    @GetMapping("/hateoas/{id}")
    public EntityModel<ProductDTO> findByIdHateoas(@PathVariable("id") Integer id) {
        ProductDTO dtoResponse;
        Product obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(obj, ProductDTO.class);
        }
        
        EntityModel<ProductDTO> resource = EntityModel.of(dtoResponse);
        
        WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(id));
        
        resource.add(link1.withRel("person-info1"));
        
        return resource;
    }
}
