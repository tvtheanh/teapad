package org.tea.saleman.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.tea.saleman.domain.InvoiceDetail;

@Repository
public class JdbcInvoiceDetailRepository implements InvoiceDetailRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	@Override
	public List<InvoiceDetail> findByInvoiceId(int invoiceId) {
		final String SELECT_INVOICE_DETAIL_BY_INVOICE_ID = 
				"SELECT i.id, i.invoice_id, i.product_id, i.price_id,  "
				+ "     p.name, p.unit, p.weight, i.product_price, i.quantity, i.amount "
				+ "FROM invoicedetail i JOIN product p ON (i.product_id=p.id) "
				+ "WHERE i.invoice_id=? AND i.del=false";
		return jdbcTemplate.query(SELECT_INVOICE_DETAIL_BY_INVOICE_ID, new InvoiceDetailMapper(), invoiceId);
	}
	
	private static final class InvoiceDetailMapper implements RowMapper<InvoiceDetail> {

		@Override
		public InvoiceDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
			InvoiceDetail invoiceDetail = new InvoiceDetail();
			invoiceDetail.setId(rs.getInt("id"));
			invoiceDetail.setInvoice_id(rs.getInt("invoice_id"));
			invoiceDetail.setProduct_id(rs.getInt("product_id"));
			invoiceDetail.setPrice_id(rs.getInt("price_id"));
			invoiceDetail.setProductName(rs.getString("name"));
			invoiceDetail.setProductUnit(rs.getString("unit"));
			invoiceDetail.setProductWeight(rs.getBigDecimal("weight"));
			invoiceDetail.setProduct_price(rs.getBigDecimal("product_price"));
			invoiceDetail.setQuantity(rs.getBigDecimal("quantity"));
			invoiceDetail.setAmount(rs.getBigDecimal("amount"));
			return invoiceDetail;
		}
		
	}

	@Override
	public InvoiceDetail findById(int id) {
		final String SELECT_INVOICE_DETAIL_BY_ID =
				"SELECT i.id, i.invoice_id, i.product_id, i.price_id, "
				+ "     p.name, p.unit, p.weight, i.product_price, i.quantity, i.amount "
				+ "FROM invoicedetail i JOIN product p ON (i.product_id=p.id) "
				+ "WHERE i.id=? AND i.del=false";
		return jdbcTemplate.queryForObject(SELECT_INVOICE_DETAIL_BY_ID, new InvoiceDetailMapper(), id);
	}

	@Override
	public InvoiceDetail add(InvoiceDetail invoiceDetail) {
		final String INSERT_INVOICE_DETAIL =
				"INSERT INTO invoicedetail (invoice_id, product_id, price_id, product_price, quantity, amount) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(INSERT_INVOICE_DETAIL,
				invoiceDetail.getInvoice_id(),
				invoiceDetail.getProduct_id(),
				invoiceDetail.getPrice_id(),
				invoiceDetail.getProduct_price(),
				invoiceDetail.getQuantity(),
				invoiceDetail.getProduct_price().multiply(invoiceDetail.getQuantity()));
		return invoiceDetail;
	}

	@Override
	public InvoiceDetail update(int id, InvoiceDetail invoiceDetail) {
		final String UPDATE_INVOICE_DETAIL = 
				"UPDATE invoicedetail   "
				+ "SET invoice_id=?, product_id=?, price_id=?, product_price=?, quantity=? "
				+ "WHERE id=?";
		jdbcTemplate.update(UPDATE_INVOICE_DETAIL,
				invoiceDetail.getInvoice_id(),
				invoiceDetail.getProduct_id(),
				invoiceDetail.getPrice_id(),
				invoiceDetail.getProduct_price(),
				invoiceDetail.getQuantity(),
				invoiceDetail.getId());
		return invoiceDetail;
	}

	@Override
	public void delete(int id) {
		final String DELETE_INVOICE_DETAIL =
				"UPDATE invoicedetail SET del=true WHERE id=?";
		jdbcTemplate.update(DELETE_INVOICE_DETAIL, id);
	}
	
}
