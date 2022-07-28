package com.saleapp.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saleapp.model.Sale;
import com.saleapp.model.SaleDetail;
import com.saleapp.repo.IGenericRepo;
import com.saleapp.repo.ISaleDetailRepo;
import com.saleapp.repo.ISaleRepo;
import com.saleapp.service.ISaleService;


@Service
public class SaleServiceImpl extends CRUDImpl<Sale, Integer> implements ISaleService {

    @Autowired
    private ISaleRepo saleRepo;
    
    @Autowired
    private ISaleDetailRepo saleDetailRepo;

    @Override
    protected IGenericRepo<Sale, Integer> getRepo() {
        return saleRepo;
    }
    
    /**
     * 
     */
    @Transactional
    @Override    
    public Sale saveTransactional(Sale sale) {
    	// Sale + SaleDetail
    	sale.getDetails().forEach(det->det.setSale(sale));
    	saleRepo.save(sale);
    	
    	//exams.forEach(ex-> ceRepo.saveExam(consult.getIdConsult(), ex.getIdExam()));
    	
    	return sale;
    }
}
