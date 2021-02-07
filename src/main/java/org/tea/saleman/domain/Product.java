package org.tea.saleman.domain;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Product {
	
	private int id;
	private String name;
	private String unit;
	private int provider_id;
	private String provider_name;
	private BigDecimal weight;   // kg
	private boolean del;
	
}
