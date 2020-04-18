package org.tea.saleman.service;

import java.util.List;

import org.tea.saleman.domain.InvoiceDetail;

public interface InvoiceDetailService {
	List<InvoiceDetail> findByInvoiceId(int invoiceId);
	InvoiceDetail findById(int id);
	InvoiceDetail add(InvoiceDetail newInvoiceDetail);
	InvoiceDetail update(int id, InvoiceDetail invoiceDetail);
	void delete(int id);
}
