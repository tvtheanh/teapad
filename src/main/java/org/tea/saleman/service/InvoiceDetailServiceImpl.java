package org.tea.saleman.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tea.saleman.domain.InvoiceDetail;
import org.tea.saleman.repository.InvoiceDetailRepository;
import org.tea.saleman.repository.InvoiceRepository;

@Service
public class InvoiceDetailServiceImpl implements InvoiceDetailService {
	
	@Autowired
	private InvoiceDetailRepository invoiceDetailRepository;
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	

	@Override
	public List<InvoiceDetail> findByInvoiceId(int invoiceId) {
		return invoiceDetailRepository.findByInvoiceId(invoiceId);
	}

	@Override
	public InvoiceDetail findById(int id) {
		return invoiceDetailRepository.findById(id);
	}

	@Override
	public InvoiceDetail add(InvoiceDetail invoiceDetail) {
		invoiceRepository.addTotalWeight(invoiceDetail.getInvoice_id(), 
				invoiceDetail.getProduct_price().multiply(invoiceDetail.getQuantity()),
				invoiceDetail.getProductWeight().multiply(invoiceDetail.getQuantity())
			);
		invoiceDetailRepository.add(invoiceDetail);
		return invoiceDetail;
	}

	@Override
	public InvoiceDetail update(int id, InvoiceDetail invoiceDetail) {
		return invoiceDetailRepository.update(id, invoiceDetail);
	}

	@Override
	public void delete(int id) {
		InvoiceDetail invoiceDetail = invoiceDetailRepository.findById(id);
		invoiceRepository.subtractTotalWeight(invoiceDetail.getInvoice_id(), 
				invoiceDetail.getAmount(),
				invoiceDetail.getProductWeight().multiply(invoiceDetail.getQuantity())
			);
		invoiceDetailRepository.delete(id);
	}
	
}
