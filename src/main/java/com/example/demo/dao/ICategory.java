package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Category;

public interface ICategory extends CrudRepository<Category ,Long> {

}
