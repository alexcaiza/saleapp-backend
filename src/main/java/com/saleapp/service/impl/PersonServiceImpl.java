package com.saleapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saleapp.model.Person;
import com.saleapp.repo.IGenericRepo;
import com.saleapp.repo.IPersonRepo;
import com.saleapp.service.IPersonService;

@Service
public class PersonServiceImpl extends CRUDImpl<Person, Integer> implements IPersonService {

    @Autowired
    private IPersonRepo repo;

    @Override
    protected IGenericRepo<Person, Integer> getRepo() {
        return repo;
    }
}
