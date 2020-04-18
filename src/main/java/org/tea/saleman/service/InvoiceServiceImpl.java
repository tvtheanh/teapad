package org.tea.saleman.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.tea.saleman.domain.Invoice;
import org.tea.saleman.repository.InvoiceRepository;

@Service
public class InvoiceServiceImpl implements InvoiceService {
	
	private InvoiceRepository invoiceRepository;
	
	public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
		this.invoiceRepository = invoiceRepository;
	}

	@Override
	public List<Invoice> listAll() {
		return invoiceRepository.listAll();
	}

	@Override
	public Invoice findById(int id) {
		return invoiceRepository.findById(id);
	}

	@Override
	public int add(Invoice newInvoice) {
		return invoiceRepository.add(newInvoice);
	}

	@Override
	public Invoice update(int id, Invoice invoice) {
		return invoiceRepository.update(id, invoice);
	}

	@Override
	public void delete(int id) {
		invoiceRepository.delete(id);
		
	}
	
}
