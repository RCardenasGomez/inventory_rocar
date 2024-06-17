package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Product;

public interface IProductDao extends CrudRepository<Product, Long> {
	List<Product> findByNameContainingIgnoreCase(String name);
}
