package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ICategory;
import com.example.demo.dao.IProductDao;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.response.ProductResponseRest;

@Service
public class ProductsServiceImpl implements IProductsServices {
	private ICategory categoryDao;
	private IProductDao productDao;
	
	public ProductsServiceImpl(ICategory categoryDao, IProductDao productDao) {
		super();
		this.categoryDao = categoryDao;
		this.productDao= productDao;
	}

	@Override
	public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		try{
			//search Category to set in the product object
			Optional<Category> category= categoryDao.findById(categoryId);
			if(category.isPresent()) {
				product.setCategory(category.get());
			}else {
				response.setMetadata("Respuest nook", "-1", "Categoria no encontrada asociada al producto");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			//save the product
			Product productSaved = productDao.save(product);
			if(productSaved != null) {
				list.add(productSaved);
				response.getProduct().setProducts(list);
				response.setMetadata("Respuest ok", "00", "Producto Guardado");
			}else {
				response.setMetadata("Respuest nook", "-1", "Producto No Guardado");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
			
		}catch(Exception e){
			e.getStackTrace();
			response.setMetadata("Respuest nook", "-1", "Error al guardar al producto");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}
	
}
