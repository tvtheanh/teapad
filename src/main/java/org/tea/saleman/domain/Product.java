package org.tea.saleman.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Product {
	
	private int id;
	private String name;
	private String unit;
	private int provider_id;
	private String provider_name;
	private int weight;   // gram
	private boolean del;
	
}
