package org.tea.saleman.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.tea.saleman.domain.Customer;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	@Override
	public List<Customer> listAll() {
		final String SELECT_ALL_CUSTOMERS = "SELECT id, name, address, district, province, contact, phone  "
				+ "FROM customer WHERE del=false";
		return jdbcTemplate.query(SELECT_ALL_CUSTOMERS, 
				new CustomerMapper());
		
	}
	
	private static final class CustomerMapper implements RowMapper<Customer> {

		@Override
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Customer customer = new Customer();
			customer.setId(rs.getInt("id"));
			customer.setName(rs.getString("name"));
			customer.setAddress(rs.getString("address"));
			customer.setDistrict(rs.getString("district"));
			customer.setProvince(rs.getString("province"));
			customer.setContact(rs.getString("contact"));
			customer.setPhone(rs.getString("phone"));
			return customer;
		}
		
	}

	
	@Override
	public Customer findById(int id) {
		final String SELECT_CUSTOMER_BY_ID = "SELECT id, name, address, district, province, contact, phone  "
				+ "FROM customer WHERE id=? AND del=false";
		return jdbcTemplate.queryForObject(SELECT_CUSTOMER_BY_ID,
				new CustomerMapper(), id);
	}


	@Override
	public Customer add(Customer newCustomer) {
		final String INSERT_NEW_CUSTOMER = 
				"INSERT INTO customer (name, address, district, province, contact, phone) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(INSERT_NEW_CUSTOMER,
				newCustomer.getName(),
				newCustomer.getAddress(),
				newCustomer.getDistrict(),
				newCustomer.getProvince(),
				newCustomer.getContact(),
				newCustomer.getPhone());
		return newCustomer;
	}


	@Override
	public Customer update(int id, Customer updatedCustomer) {
		final String UPDATE_CUSTOMER = 
				"UPDATE customer SET name=?, address=?, district=?, province=?, contact=?, phone=?   " +
				"WHERE id=?";
		jdbcTemplate.update(UPDATE_CUSTOMER,
				updatedCustomer.getName(),
				updatedCustomer.getAddress(),
				updatedCustomer.getDistrict(),
				updatedCustomer.getProvince(),
				updatedCustomer.getContact(),
				updatedCustomer.getPhone(),
				id);
		return updatedCustomer;
	}


	@Override
	public void delete(int id) {
		final String DELETE_CUSTOMER =
				"UPDATE customer SET del=true WHERE id=? ";
		jdbcTemplate.update(DELETE_CUSTOMER, id);
	}

	
}
