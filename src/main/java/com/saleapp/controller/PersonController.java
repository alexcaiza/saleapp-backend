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

import com.saleapp.dto.PersonDTO;
import com.saleapp.exception.ModelNotFoundException;
import com.saleapp.model.Person;
import com.saleapp.service.IPersonService;

@RestController
@RequestMapping("/persons")
public class PersonController {

	@Autowired
    private IPersonService service;

    @Autowired
    private ModelMapper mapper;
	
    @GetMapping
    public ResponseEntity<List<PersonDTO>> findAll() {
        List<PersonDTO> list = service.findAll().stream().map(p -> mapper.map(p, PersonDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
	
    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable("id") Integer id) {
        PersonDTO dtoResponse;
        Person obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(obj, PersonDTO.class);
        }
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }
	
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody PersonDTO dto) {
        Person p = service.save(mapper.map(dto, Person.class));
        //localhost:8080/patients/3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getIdPerson()).toUri();
        return ResponseEntity.created(location).build();
    }
	
    @PutMapping
    public ResponseEntity<Person> update(@Valid @RequestBody PersonDTO dto) {
        Person obj = service.findById(dto.getIdPerson());
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getIdPerson());
        }

        return new ResponseEntity<>(service.update(mapper.map(dto, Person.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        Person obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            service.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
    @GetMapping("/hateoas/{id}")
    public EntityModel<PersonDTO> findByIdHateoas(@PathVariable("id") Integer id) {
        PersonDTO dtoResponse;
        Person obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(obj, PersonDTO.class);
        }
        
        EntityModel<PersonDTO> resource = EntityModel.of(dtoResponse);
        
        WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(id));
        
        resource.add(link1.withRel("person-info1"));
        
        return resource;
    }
}
