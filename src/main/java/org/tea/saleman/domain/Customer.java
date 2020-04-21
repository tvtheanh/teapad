package org.tea.saleman.domain;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Customer {
	
	private int id;
	private String name;
	private String address;
	private String district;
	private String province;
	private String contact;
	private String phone;
	private BigDecimal debt;
	private boolean del;
	
}
