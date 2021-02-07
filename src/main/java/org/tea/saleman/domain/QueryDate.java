package org.tea.saleman.domain;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class QueryDate {
	LocalDate fromdate;
	LocalDate tilldate;
}
