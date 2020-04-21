package org.tea.saleman.repository;

import java.math.BigDecimal;
import java.util.List;

import org.tea.saleman.domain.Customer;

public interface CustomerRepository {
	List<Customer> listAll();
	Customer findById(int id);
	Customer add(Customer newCustomer);
	Customer update(int id, Customer updatedCustomer);
	void updateDebt(int id, BigDecimal oldInvoiceDebt, BigDecimal newInvoiceDebt);
	void delete(int id);
}
