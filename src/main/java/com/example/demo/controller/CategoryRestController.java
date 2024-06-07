package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.response.Category_ResponseRest;
import com.example.demo.services.ICategoryServices;

@RestController
@RequestMapping("/api/v1")
public class CategoryRestController {
	@Autowired
	private ICategoryServices service;
	@GetMapping("/categories")
	public ResponseEntity<Category_ResponseRest> searchCategories(){
		ResponseEntity<Category_ResponseRest> response = service.search();
		return response;
	}
	@GetMapping("/categories/{id}")
	public ResponseEntity<Category_ResponseRest> searchCategoriesById(@PathVariable Long id){
		ResponseEntity<Category_ResponseRest> response = service.searchById(id);
		return response;
	}
	
}
