package org.tea.saleman.repository;

import java.util.List;

import org.tea.saleman.domain.Product;

public interface ProductRepository {
	List<Product> listAll();
	Product findById(int id);
	Product add(Product newProduct);
	Product update(int id, Product product);
	void delete(int id);
}
