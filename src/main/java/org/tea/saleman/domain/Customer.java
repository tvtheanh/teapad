package org.tea.saleman.domain;

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
	private boolean del;
	
}
