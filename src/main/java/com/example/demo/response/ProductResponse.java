package com.example.demo.response;

import java.util.List;

import com.example.demo.model.Product;

import lombok.Data;

@Data
public class ProductResponse {
	List<Product> products;

}
