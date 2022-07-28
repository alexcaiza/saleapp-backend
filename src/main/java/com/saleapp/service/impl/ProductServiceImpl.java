package com.saleapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saleapp.model.Product;
import com.saleapp.repo.IGenericRepo;
import com.saleapp.repo.IProductRepo;
import com.saleapp.service.IProductService;

@Service
public class ProductServiceImpl extends CRUDImpl<Product, Integer> implements IProductService {

    @Autowired
    private IProductRepo repo;

    @Override
    protected IGenericRepo<Product, Integer> getRepo() {
        return repo;
    }
}
