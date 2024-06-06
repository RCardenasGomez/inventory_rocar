package com.example.demo.response;

import java.util.List;

import com.example.demo.model.Category;

import lombok.Data;

@Data
public class CategoryResponse {
	private List<Category> category;

}
