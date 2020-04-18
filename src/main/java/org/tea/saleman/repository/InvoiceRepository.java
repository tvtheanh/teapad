package org.tea.saleman.repository;

import java.math.BigDecimal;
import java.util.List;

import org.tea.saleman.domain.Invoice;

public interface InvoiceRepository {
	List<Invoice> listAll();
	Invoice findById(int id);
	int add(Invoice newInvoice);
	Invoice update(int id, Invoice invoice);
	void addTotal(int id, BigDecimal amount);
	void subtractTotal(int id, BigDecimal amount);
	void delete(int id);
}
