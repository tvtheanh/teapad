package org.tea.saleman.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tea.saleman.domain.Invoice;
import org.tea.saleman.repository.CustomerRepository;
import org.tea.saleman.repository.InvoiceRepository;

@Service
public class InvoiceServiceImpl implements InvoiceService {
	
	@Autowired
	private InvoiceRepository invoiceRepo;
	
	@Autowired
	private CustomerRepository customerRepo;
	

	@Override
	public List<Invoice> listAll() {
		return invoiceRepo.listAll();
	}

	@Override
	public Invoice findById(int id) {
		return invoiceRepo.findById(id);
	}

	@Override
	public int add(Invoice newInvoice) {
		return invoiceRepo.add(newInvoice);
	}

	@Override
	@Transactional
	public Invoice update(int id, Invoice updatedInvoice) {
		BigDecimal oldInvoiceDebt = invoiceRepo.findById(id).getDebt();
		BigDecimal newInvoiceDebt = updatedInvoice.getDebt();
		customerRepo.updateDebt(updatedInvoice.getCustomer_id(), 
				oldInvoiceDebt, newInvoiceDebt);
		return invoiceRepo.update(id, updatedInvoice);
	}

	@Override
	public void delete(int id) {
		invoiceRepo.delete(id);
		
	}
	
}
