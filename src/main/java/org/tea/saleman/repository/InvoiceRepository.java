package org.tea.saleman.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.tea.saleman.domain.Invoice;

public interface InvoiceRepository {
	List<Invoice> listAll();
	List<Invoice> listByDate(LocalDate fromdate, LocalDate tilldate);
	Invoice findById(int id);
	int add(Invoice newInvoice);
	Invoice update(int id, Invoice invoice);
	void addTotalWeight(int id, BigDecimal amount, BigDecimal weight);
	void subtractTotalWeight(int id, BigDecimal amount, BigDecimal weight);
	void delete(int id);
}
