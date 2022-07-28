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

import com.saleapp.dto.SaleDTO;
import com.saleapp.exception.ModelNotFoundException;
import com.saleapp.model.Sale;
import com.saleapp.service.ISaleService;

@RestController
@RequestMapping("/sales")
public class SaleController {

	@Autowired
    private ISaleService saleService;

    @Autowired
    private ModelMapper mapper;
	
    @GetMapping
    public ResponseEntity<List<SaleDTO>> findAll() {
        List<SaleDTO> list = saleService.findAll().stream().map(p -> mapper.map(p, SaleDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
	
    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> findById(@PathVariable("id") Integer id) {
        SaleDTO dtoResponse;
        Sale obj = saleService.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(obj, SaleDTO.class);
        }
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }
	
    /*
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ConsultDTO dto) {
        Consult p = service.save(mapper.map(dto, Consult.class));
        //localhost:8080/patients/3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getIdConsult()).toUri();
        return ResponseEntity.created(location).build();
    }
    */
    
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody SaleDTO dto) {
        Sale c = mapper.map(dto, Sale.class);
        //List<Exam> exams = mapper.map(dto.getLstExam(), new TypeToken<List<Exam>>() {}.getType());

        Sale obj = saleService.saveTransactional(c);

        //localhost:8080/sales/3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdSale()).toUri();
        return ResponseEntity.created(location).build();
    }
    
	
    @PutMapping
    public ResponseEntity<Sale> update(@Valid @RequestBody SaleDTO dto) {
    	Sale obj = saleService.findById(dto.getIdSale());
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getIdSale());
        }

        return new ResponseEntity<>(saleService.update(mapper.map(dto, Sale.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        Sale obj = saleService.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            saleService.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
    @GetMapping("/hateoas/{id}")
    public EntityModel<SaleDTO> findByIdHateoas(@PathVariable("id") Integer id) {
        SaleDTO dtoResponse;
        Sale obj = saleService.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(obj, SaleDTO.class);
        }
        
        EntityModel<SaleDTO> resource = EntityModel.of(dtoResponse);
        
        WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(id));
        
        resource.add(link1.withRel("patient-info1"));
        
        return resource;
    }
}
