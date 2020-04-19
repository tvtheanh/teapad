package org.tea.saleman.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.tea.saleman.domain.Product;


@Repository
public class JdbcProductRepository implements ProductRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	@Override
	public List<Product> listAll() {
		final String SELECT_ALL_PRODUCT = 
				"SELECT p.id, p.name, p.unit, p.provider_id, v.name as provider_name, p.weight   " + 
				"FROM product p  " + 
				"	join provider v on (p.provider_id=v.id)   " +
				"WHERE p.del=false ";
		return jdbcTemplate.query(SELECT_ALL_PRODUCT, new ProductMapper());
	}
	
	private static final class ProductMapper implements RowMapper<Product> {

		@Override
		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
			Product product = new Product();
			product.setId(rs.getInt("id"));
			product.setName(rs.getString("name"));
			product.setUnit(rs.getString("unit"));
			product.setProvider_id(rs.getInt("provider_id"));
			product.setProvider_name(rs.getString("provider_name"));
			product.setWeight(rs.getInt("weight"));
			
			return product;
		}
		
	}

	@Override
	public Product findById(int id) {
		final String SELECT_PRODUCT_BY_ID =
				"SELECT p.id, p.name, p.unit, p.provider_id, v.name as provider_name, p.weight   " + 
				"FROM product p  " + 
				"	join provider v on (p.provider_id=v.id)   " +
				"WHERE p.id=? AND p.del=false";
		return jdbcTemplate.queryForObject(SELECT_PRODUCT_BY_ID, new ProductMapper(), id);
	}

	@Override
	public Product add(Product newProduct) {
		final String INSERT_NEW_PRODUCT = 
				"INSERT INTO product(name, unit, provider_id, weight) "
				+ "VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(INSERT_NEW_PRODUCT,
				newProduct.getName(),
				newProduct.getUnit(),
				newProduct.getProvider_id(),
				newProduct.getWeight()
		);
		return newProduct;
	}

	@Override
	public Product update(int id, Product updatedProduct) {
		final String UPDATE_PRODUCT = 
				"UPDATE product SET name=?, unit=?, provider_id=?, weight=? "
				+ " WHERE id=?";
		jdbcTemplate.update(UPDATE_PRODUCT,
				updatedProduct.getName(),
				updatedProduct.getUnit(),
				updatedProduct.getProvider_id(),
				updatedProduct.getWeight(),
				updatedProduct.getId());
		return updatedProduct;
	}

	@Override
	public void delete(int id) {
		final String DELETE_PRODUCT = "UPDATE product SET del=true WHERE id=?";
		jdbcTemplate.update(DELETE_PRODUCT, id);
	}

	
}
