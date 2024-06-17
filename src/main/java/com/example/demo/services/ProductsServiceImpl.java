package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.ICategory;
import com.example.demo.dao.IProductDao;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.response.ProductResponseRest;
import com.example.demo.utils.Util;

@Service
public class ProductsServiceImpl implements IProductsServices {
	private ICategory categoryDao;
	private IProductDao productDao;

	public ProductsServiceImpl(ICategory categoryDao, IProductDao productDao) {
		super();
		this.categoryDao = categoryDao;
		this.productDao = productDao;
	}

	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		try {
			// search Category to set in the product object
			Optional<Category> category = categoryDao.findById(categoryId);
			if (category.isPresent()) {
				product.setCategory(category.get());
			} else {
				response.setMetadata("Respuest nook", "-1", "Categoria no encontrada asociada al producto");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}

			// save the product
			Product productSaved = productDao.save(product);
			if (productSaved != null) {
				list.add(productSaved);
				response.getProduct().setProducts(list);
				response.setMetadata("Respuest ok", "00", "Producto Guardado");
			} else {
				response.setMetadata("Respuest nook", "-1", "Producto No Guardado");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("Respuest nook", "-1", "Error al guardar al producto");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductResponseRest> searchById(Long id) {
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		try {
			// search Product by Id
			Optional<Product> product = productDao.findById(id);
			if (product.isPresent()) {
				byte[] imageDescompressed = Util.decompressZLib(product.get().getPicture());
				product.get().setPicture(imageDescompressed);
				list.add(product.get());
				response.getProduct().setProducts(list);
				response.setMetadata("Respuesta ok", "00", "Producto encontrado");
			} else {
				response.setMetadata("Respuest nook", "-1", "Producto no encontrado");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("Respuest nook", "-1", "Error al guardar al producto");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductResponseRest> searchByName(String name) {
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		List<Product> Auxlist = new ArrayList<>();
		try {
			// search Product by name

			Auxlist = productDao.findByNameContainingIgnoreCase(name);

			if (Auxlist.size() > 0) {
				Auxlist.stream().forEach((p) -> {
					byte[] imageDescompressed = Util.decompressZLib(p.getPicture());
					p.setPicture(imageDescompressed);
					list.add(p);
				});
				response.getProduct().setProducts(list);
				response.setMetadata("Respuesta ok", "00", "Producto encontrado");
			} else {
				response.setMetadata("Respuest nook", "-1", "Error al buscar el nombre del producto");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("Respuest nook", "-1", "Error al guardar al producto");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductResponseRest> deleteById(Long id) {
		ProductResponseRest response = new ProductResponseRest();
		try {
			// search Product by name
			productDao.deleteById(id);
			response.setMetadata("Respuesta ok", "00", "Producto encontrado");
			

		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("Respuest nook", "-1", "Error al eliminar el producto");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> search() {
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		List<Product> Auxlist = new ArrayList<>();
		try {
			// search Product by name

			Auxlist = (List<Product>) productDao.findAll();
			if (Auxlist.size() > 0) {
				Auxlist.stream().forEach((p) -> {
					byte[] imageDescompressed = Util.decompressZLib(p.getPicture());
					p.setPicture(imageDescompressed);
					list.add(p);
				});
				response.getProduct().setProducts(list);
				response.setMetadata("Respuesta ok", "00", "Productos encontrados");
			} else {
				response.setMetadata("Respuest nook", "-1", "Productos no encontrados");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("Respuest nook", "-1", "Error al mostrar los productos");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> updateProducts(Product product, Long categoryId, Long id) {
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		try {
			// search Category to set in the product object
			Optional<Category> category = categoryDao.findById(categoryId);
			if (category.isPresent()) {
				product.setCategory(category.get());
			} else {
				response.setMetadata("Respuest nook", "-1", "Categoria no encontrada asociada al producto");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}

			// search the product to update
			Optional<Product> productSearch = productDao.findById(id);
			if (productSearch.isPresent()) {
				// to update the product
				productSearch.get().setCount(product.getCount());
				productSearch.get().setCategory(product.getCategory());
				productSearch.get().setName(product.getName());
				productSearch.get().setPicture(product.getPicture());
				productSearch.get().setPrice(product.getPrice());
				
				//save the product in database
				Product productToUpdate=productDao.save(productSearch.get());
				if(productToUpdate != null) {
					list.add(productToUpdate);
					response.getProduct().setProducts(list);
					response.setMetadata("Respuest ok", "00", "Producto Actualizado");
				}else {
					response.setMetadata("Respuest ok", "00", "Producto no actualizado");
					return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
				}
			} else {
				response.setMetadata("Respuest nook", "-1", "Producto No Actualizado");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("Respuest nook", "-1", "Error al guardar al producto");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}

}
