package org.tea.saleman.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.tea.saleman.domain.Price;

@Repository
public class JdbcPriceRepository implements PriceRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Price> listAll() {
		final String SELECT_PRICE = 
				"select e.id, e.product_id, p.name, p.unit, e.cate, e.price   " + 
				"from price e   " + 
				"	join product p on (p.id=e.product_id)   " + 
				"where e.del=false";
		return jdbcTemplate.query(SELECT_PRICE, new PriceMapper());
	}
	
	private final class PriceMapper implements RowMapper<Price> {

		@Override
		public Price mapRow(ResultSet rs, int rowNum) throws SQLException {
			Price price = new Price();
			price.setId(rs.getInt("id"));
			price.setProduct_id(rs.getInt("product_id"));
			price.setProductName(rs.getString("name"));
			price.setProductUnit(rs.getString("unit"));
			price.setCate(rs.getString("cate"));
			price.setPrice(rs.getBigDecimal("price"));
			return price;
		}
		
	}

	@Override
	public Price findById(int id) {
		final String SELECT_PRICE_BY_ID = 
				"select e.id, e.product_id, p.name, p.unit, e.cate, e.price   " + 
				"from price e   " + 
				"	join product p on (p.id=e.product_id)   " + 
				"where e.id=? AND e.del=false";
		return jdbcTemplate.queryForObject(SELECT_PRICE_BY_ID, new PriceMapper(), id);
	}
	
	
	@Override
	public List<Price> findByProduct(int productId) {
		final String SELECT_PRICE_BY_PRODUCT = 
				"select e.id, e.product_id, p.name, p.unit, e.cate, e.price   " + 
				"from price e   " + 
				"	join product p on (p.id=e.product_id)   " + 
				"where e.product_id=? AND e.del=false";
		return jdbcTemplate.query(SELECT_PRICE_BY_PRODUCT, new PriceMapper(), productId);
	}

	@Override
	public Price add(Price newPrice) {
		final String INSERT_NEW_PRICE =
				"insert into price(product_id, cate, price)   " + 
				"values (?, ?, ?);";
		jdbcTemplate.update(INSERT_NEW_PRICE,
				newPrice.getProduct_id(),
				newPrice.getCate(),
				newPrice.getPrice());
		return newPrice;
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
