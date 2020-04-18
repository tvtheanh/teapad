package org.tea.saleman.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Provider {
	private int id;
	private String name;
	private boolean del;
	
	public Provider() {
		super();
	}
	
	
}
