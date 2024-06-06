package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.ICategory;
import com.example.demo.model.Category;
import com.example.demo.response.Category_ResponseRest;




@Service
public class CategoryServiceImplement implements ICategoryServices {
	@Autowired
	private ICategory categoryDao;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<Category_ResponseRest> search() {
		Category_ResponseRest response= new Category_ResponseRest();
		try {
			List<Category> category = (List<Category>) categoryDao.findAll();
			response.getCategoryResponse().setCategory(category);
			response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
			
		}catch(Exception e){
			response.setMetadata("Respuesta no-ok", "-1", "!ERROR!");
			e.getStackTrace();
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
