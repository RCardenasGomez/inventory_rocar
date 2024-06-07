package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		Category_ResponseRest response = new Category_ResponseRest();
		try {
			List<Category> category = (List<Category>) categoryDao.findAll();
			response.getCategoryResponse().setCategory(category);
			response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");

		} catch (Exception e) {
			response.setMetadata("Respuesta no-ok", "-1", "!ERROR!");
			e.getStackTrace();
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<Category_ResponseRest> searchById(Long id) {
		Category_ResponseRest response = new Category_ResponseRest();
		List<Category> list = new ArrayList<>();
		try {
			Optional<Category> category = categoryDao.findById(id);

			if (category.isPresent()) {
				list.add(category.get());
				response.getCategoryResponse().setCategory(list);
				response.setMetadata("Respuesta ok", "00", "Categoria encontrada");
			} else {
				response.setMetadata("Respuesta no-ok", "-1", "Categoria no encontrada");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			response.setMetadata("Respuesta no-ok", "-1", "!ERROR Al CONSULTAR ID");
			e.getStackTrace();
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<Category_ResponseRest> save(Category category) {
		Category_ResponseRest response = new Category_ResponseRest();
		List<Category> list = new ArrayList<>();
		try {
			Category categorySaved = categoryDao.save(category);

			if (categorySaved != null) {
				list.add(categorySaved);
				response.getCategoryResponse().setCategory(list);
				response.setMetadata("Respuesta ok", "00", "Categoria guardada");
			} else {
				response.setMetadata("Respuesta no-ok", "-1", "Categoria no guardada");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			response.setMetadata("Respuesta no-ok", "-1", "!ERROR Al GRABAR CATEGORIA");
			e.getStackTrace();
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<Category_ResponseRest> update(Category category, Long id) {
		Category_ResponseRest response = new Category_ResponseRest();
		List<Category> list = new ArrayList<>();
		try {
			Optional<Category> categorySearch = categoryDao.findById(id);
			if (categorySearch.isPresent()) {
				categorySearch.get().setName(category.getName());
				categorySearch.get().setDescription(category.getDescription());

				Category categoryToUpdate = categoryDao.save(categorySearch.get());

				if (categoryToUpdate != null) {
					list.add(categoryToUpdate);
					response.getCategoryResponse().setCategory(list);
					response.setMetadata("Respuesta ok", "00", "Categoria Actualizada");
				} else {
					response.setMetadata("Respuesta no-ok", "-1", "Categoria no actualizada");
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				}

			} else {
				response.setMetadata("Respuesta no-ok", "-1", "Categoria no encontrada");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			response.setMetadata("Respuesta no-ok", "-1", "!ERROR Al GRABAR CATEGORIA");
			e.getStackTrace();
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<Category_ResponseRest> deleteById(Long id) {
		Category_ResponseRest response = new Category_ResponseRest();
		try {
			categoryDao.deleteById(id);
			response.setMetadata("Respuesta ok", "00", "Registro Eliminado");

		} catch (Exception e) {
			response.setMetadata("Respuesta no-ok", "-1", "!ERROR AL ELIMINAR CATEGORIA!");
			e.getStackTrace();
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
