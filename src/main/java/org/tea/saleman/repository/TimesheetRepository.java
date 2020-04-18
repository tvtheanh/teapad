package org.tea.saleman.repository;

import java.util.List;

import org.tea.saleman.domain.Timesheet;
import org.tea.saleman.domain.TimesheetExcel;

public interface TimesheetRepository {
	List<Timesheet> listAll();
	List<Timesheet> listByMonth(String month, String year);
	List<TimesheetExcel> excelByMonth(String month, String year);
	void update(int employee_id, String day, int newValue);
	Timesheet add(Timesheet timesheet);
	void delete(int id, String month, String year);
}
