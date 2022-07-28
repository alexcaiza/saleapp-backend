package com.saleapp.service;

import com.saleapp.model.Sale;

public interface ISaleService extends ICRUD<Sale, Integer>{
	
	Sale saveTransactional(Sale sale);

}
