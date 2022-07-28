package com.saleapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.saleapp.repo.IGenericRepo;
import com.saleapp.service.ICRUD;

@Service
public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID> {
	
	protected abstract IGenericRepo<T, ID> getRepo();
	

	@Override
    public T save(T t) {
        return getRepo().save(t);
    }

    @Override
    public T update(T t) {
        return getRepo().save(t);
    }

    @Override
    public T findById(ID id) {
        return getRepo().findById(id).orElse(null);
    }

    @Override
    public List<T> findAll() {
        return getRepo().findAll();
    }

    @Override
    public void delete(ID id) {
        getRepo().deleteById(id);
    }
}
