package org.tea.saleman.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Invoice {
	
	private int id;
	private int customer_id;
	private String customerName;
	private String customerAddress;
	private LocalDate saledate;
	private short delivered;
	private short paid;
	private BigDecimal total;
	private boolean del;
	
}
