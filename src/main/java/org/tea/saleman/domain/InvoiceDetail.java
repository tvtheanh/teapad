package org.tea.saleman.domain;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InvoiceDetail {
	
	private int id;
	private int invoice_id;
	private int product_id;
	private String productName;
	private BigDecimal productWeight;
	private BigDecimal product_price;
	private BigDecimal quantity;
	private BigDecimal amount;
	private boolean del;
	
}
