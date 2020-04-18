package org.tea.saleman.domain;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Price {
	private int id;
	private int product_id;
	private BigDecimal price;
	private String cate;
	private boolean enabled;
}
