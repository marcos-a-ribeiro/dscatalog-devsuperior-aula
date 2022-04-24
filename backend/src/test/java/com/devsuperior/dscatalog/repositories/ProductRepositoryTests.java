package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository repository;
	
	private long exintingId = 1L;
	private long nonexintingId = 1000L;
	private long countTotalProducts = 1000L;

	@BeforeEach
	void setUp() throws Exception {
		exintingId = 1L;
		nonexintingId = 1000L;
		countTotalProducts = 25L;
	}

	@Test
	public void saveShoudPersistWithAutoincrementWhenIdIsNull() {
		Product product = Factory.createProduct();
		product.setId(null);
		
		product = repository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1, product.getId());
	}
	
	@Test
	public void deleteShoudDeleteObjectWhenIdExists() {
		
		repository.deleteById(exintingId);
		
		Optional<Product> result = repository.findById(exintingId);
		Assertions.assertFalse(result.isPresent());
		
	}
	
	@Test
	public void deleteShoudEmptyResultDataAccessExceptionWhenIdExists() {
	
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonexintingId);
		});
	}
	
	
	@Test
	public void findByIdShoudReturnObjectWhenIdExists() {
		
		Optional<Product> result = repository.findById(exintingId);
		Assertions.assertTrue(result.isPresent());
		
	}
	
	@Test
	public void findByIdShoudReturnEmptyWhenIdExists() {
		
		Optional<Product> result = repository.findById(nonexintingId);
		Assertions.assertTrue(result.isEmpty());
		
	}
}
