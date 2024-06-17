package com.example.demo.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.response.ProductResponseRest;


public interface IProductsServices {
	public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId);
}
