package org.tea.saleman.repository;

import java.util.List;

import org.tea.saleman.domain.InvoiceDetail;

public interface InvoiceDetailRepository {
	List<InvoiceDetail> findByInvoiceId(int invoiceId);
	InvoiceDetail findById(int id);
	InvoiceDetail add(InvoiceDetail invoiceDetail);
	InvoiceDetail update(int id, InvoiceDetail invoiceDetail);
	void delete(int id);
}
