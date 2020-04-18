package org.tea.saleman.service;

import java.util.List;

import org.tea.saleman.domain.Invoice;

public interface InvoiceService {
	List<Invoice> listAll();
	Invoice findById(int id);
	int add(Invoice newInvoice);
	Invoice update(int id, Invoice invoice);
	void delete(int id);
}
