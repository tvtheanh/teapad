package org.tea.saleman.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.tea.saleman.domain.Price;

@Repository
public class JdbcPriceRepository implements PriceRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Price> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Price findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Price add(Price newPrice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Price update(int id, Price price) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(int id) {
		return 0;
		
	}
	
	

}
