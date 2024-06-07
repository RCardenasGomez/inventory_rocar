package com.example.demo.services;

import org.springframework.http.ResponseEntity;

import com.example.demo.model.Category;
import com.example.demo.response.Category_ResponseRest;

public interface ICategoryServices {
	public ResponseEntity<Category_ResponseRest> search();
	public ResponseEntity<Category_ResponseRest> searchById(Long id);
	public ResponseEntity<Category_ResponseRest> save(Category category);
	public ResponseEntity<Category_ResponseRest> update(Category category, Long id);
	public ResponseEntity<Category_ResponseRest> deleteById(Long id);

}
