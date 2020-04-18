package org.tea.saleman.repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.tea.saleman.domain.Invoice;

@Repository
public class JdbcInvoiceRepository implements InvoiceRepository {
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcInvoiceRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Invoice> listAll() {
		final String SELECT_ALL_INVOICES = 
				"SELECT i.id, i.customer_id, c.name, c.address, i.saledate, i.delivered, i.paid, i.total "
				+ "FROM invoice i JOIN customer c ON (i.customer_id=c.id) "
				+ "WHERE i.del=false";
		return jdbcTemplate.query(SELECT_ALL_INVOICES, new InvoiceMapper());
	}
	
	private static final class InvoiceMapper implements RowMapper<Invoice> {

		@Override
		public Invoice mapRow(ResultSet rs, int rowNum) throws SQLException {
			Invoice invoice = new Invoice();
			invoice.setId(rs.getInt("id"));
			invoice.setCustomer_id(rs.getInt("customer_id"));
			invoice.setCustomerName(rs.getString("name"));
			invoice.setCustomerAddress(rs.getString("address"));
			invoice.setSaledate(rs.getObject("saledate", LocalDate.class));
			invoice.setTotal(rs.getBigDecimal("total"));
			return invoice;
		}
		
	}

	@Override
	public Invoice findById(int id) {
		final String SELECT_INVOICE_BY_ID = 
				"SELECT i.id, i.customer_id, c.name, c.address, i.saledate, i.delivered, i.paid, i.total "
				+ "FROM invoice i JOIN customer c ON (i.customer_id=c.id) "
				+ "WHERE i.id=? AND i.del=false";
		return jdbcTemplate.queryForObject(SELECT_INVOICE_BY_ID, new InvoiceMapper(), id);
	}

	@Override
	public int add(Invoice invoice) {
		final String INSERT_INVOICE = "INSERT INTO invoice (customer_id, saledate) "
				+ " VALUES (?, ?) RETURNING id";
		Map<String, Object> resultMap = jdbcTemplate.queryForMap(INSERT_INVOICE,
				invoice.getCustomer_id(),
				invoice.getSaledate());
		return (int) resultMap.get("id");
	}

	@Override
	public Invoice update(int id, Invoice invoice) {
		final String UPDATE_INVOICE = "UPDATE invoice SET customer_id=?, saledate=?, delivered=?, paid=?, total=? "
				+ " WHERE id=?";
		jdbcTemplate.update(UPDATE_INVOICE,
				invoice.getCustomer_id(),
				invoice.getSaledate(),
				invoice.getDelivered(),
				invoice.getPaid(),
				invoice.getTotal(),
				id);
		return invoice;
	}

	@Override
	public void delete(int id) {
		final String DELETE_INVOICE = "UPDATE invoice SET del=true WHERE id=?";
		jdbcTemplate.update(DELETE_INVOICE, id);
	}

	@Override
	public void addTotal(int id, BigDecimal amount) {
		final String ADD_TOTAL = "UPDATE invoice SET total=total+? WHERE id=?";
		jdbcTemplate.update(ADD_TOTAL, amount, id);
		
	}
	
	@Override
	public void subtractTotal(int id, BigDecimal amount) {
		final String SUBTRACT_TOTAL = "UPDATE invoice SET total=total-? WHERE id=?";
		jdbcTemplate.update(SUBTRACT_TOTAL, amount, id);
		
	}

}
