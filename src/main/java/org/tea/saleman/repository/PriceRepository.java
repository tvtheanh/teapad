package org.tea.saleman.repository;

import java.util.List;

import org.tea.saleman.domain.Price;


public interface PriceRepository {
	List<Price> listAll();
	Price findById(int id);
	List<Price> findByProduct(int productId);
	Price add(Price newPrice);
	Price update(int id, Price price);
	int delete(int id);
}
