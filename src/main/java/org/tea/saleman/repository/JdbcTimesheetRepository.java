package org.tea.saleman.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.tea.saleman.domain.Timesheet;
import org.tea.saleman.domain.TimesheetExcel;

@Repository
public class JdbcTimesheetRepository implements TimesheetRepository {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcTimesheetRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<Timesheet> listAll() {
		final String SELECT_ALL_TIMESHEET = "SELECT t.employee_id, e.fullname, "
				+ "D01, D02, D03, D04, D05, D06, D07, D08, D09, D10, "
				+ "D11, D12, D13, D14, D15, D16, D17, D18, D19, D20, "
				+ "D21, D22, D23, D24, D25, D26, D27, D28, D29, D30, D31 "
				+ "FROM timesheet t JOIN employee e ON (t.employee_id=e.employee_id) ";
		
		return jdbcTemplate.query(SELECT_ALL_TIMESHEET, this::mapRowToTimesheet);
		
	}
	
	@Override
	public List<Timesheet> listByMonth(String month, String year) {
		final String SELECT_ALL_TIMESHEET = "SELECT t.employee_id, e.fullname, t.acc_month, t.acc_year, "
				+ "D01, D02, D03, D04, D05, D06, D07, D08, D09, D10, "
				+ "D11, D12, D13, D14, D15, D16, D17, D18, D19, D20, "
				+ "D21, D22, D23, D24, D25, D26, D27, D28, D29, D30, D31 "
				+ "FROM timesheet t JOIN employee e ON (t.employee_id=e.employee_id) "
				+ "WHERE t.acc_month=? AND t.acc_year=? AND t.del=false";
		
		return jdbcTemplate.query(SELECT_ALL_TIMESHEET, this::mapRowToTimesheet,
				month, year);
	}
	
	private Timesheet mapRowToTimesheet(ResultSet rs, int rowNum) throws SQLException {
		Timesheet ts = new Timesheet();
		ts.setEmployee_id(rs.getInt("employee_id"));
		ts.setFullname(rs.getString("fullname"));
		ts.setAcc_month(rs.getString("acc_month"));
		ts.setAcc_year(rs.getString("acc_year"));
		ts.setD01(rs.getInt("D01"));
		ts.setD02(rs.getInt("D02"));
		ts.setD03(rs.getInt("D03"));
		ts.setD04(rs.getInt("D04"));
		ts.setD05(rs.getInt("D05"));
		ts.setD06(rs.getInt("D06"));
		ts.setD07(rs.getInt("D07"));
		ts.setD08(rs.getInt("D08"));
		ts.setD09(rs.getInt("D09"));
		ts.setD10(rs.getInt("D10"));
		ts.setD11(rs.getInt("D11"));
		ts.setD12(rs.getInt("D12"));
		ts.setD13(rs.getInt("D13"));
		ts.setD14(rs.getInt("D14"));
		ts.setD15(rs.getInt("D15"));
		ts.setD16(rs.getInt("D16"));
		ts.setD17(rs.getInt("D17"));
		ts.setD18(rs.getInt("D18"));
		ts.setD19(rs.getInt("D19"));
		ts.setD20(rs.getInt("D20"));
		ts.setD21(rs.getInt("D21"));
		ts.setD22(rs.getInt("D22"));
		ts.setD23(rs.getInt("D23"));
		ts.setD24(rs.getInt("D24"));
		ts.setD25(rs.getInt("D25"));
		ts.setD26(rs.getInt("D26"));
		ts.setD27(rs.getInt("D27"));
		ts.setD28(rs.getInt("D28"));
		ts.setD29(rs.getInt("D29"));
		ts.setD30(rs.getInt("D30"));
		ts.setD31(rs.getInt("D31"));
		
		return ts;
	}

	@Override
	public void update(int employee_id, String day, int newValue) {
		String column = "D" + day.substring(1, 3);
		final String UPDATE_TIMESHEET = 
				String.format("UPDATE timesheet SET %s=? WHERE employee_id=?", column); 
		jdbcTemplate.update(UPDATE_TIMESHEET, newValue, employee_id);
	}
	
	@Override
	public Timesheet add(Timesheet timesheet) {
		final String INSERT_TIMESHEET = "INSERT INTO timesheet (employee_id, acc_month, acc_year) "
				+ "VALUES (?, ?, ?)";
		jdbcTemplate.update(INSERT_TIMESHEET,
				timesheet.getEmployee_id(),
				timesheet.getAcc_month(),
				timesheet.getAcc_year());
		return timesheet;
	}

	@Override
	public List<TimesheetExcel> excelByMonth(String month, String year) {
		final String SELECT_TIMESHEET_EXCEL =
				"select row_number() over(order by e.employee_id) as stt, \r\n" + 
				"	e.fullname as hoten, e.title as chucvu,\r\n" + 
				"	(select display from status s where s.value=t.D01) as ngay01,\r\n" + 
				"	(select display from status s where s.value=t.D02) as ngay02,\r\n" + 
				"	(select display from status s where s.value=t.D03) as ngay03,\r\n" + 
				"	(select display from status s where s.value=t.D04) as ngay04,\r\n" + 
				"	(select display from status s where s.value=t.D05) as ngay05,\r\n" + 
				"	(select display from status s where s.value=t.D06) as ngay06,\r\n" + 
				"	(select display from status s where s.value=t.D07) as ngay07,\r\n" + 
				"	(select display from status s where s.value=t.D08) as ngay08,\r\n" + 
				"	(select display from status s where s.value=t.D09) as ngay09,\r\n" + 
				"	(select display from status s where s.value=t.D10) as ngay10,\r\n" + 
				"	(select display from status s where s.value=t.D11) as ngay11,\r\n" + 
				"	(select display from status s where s.value=t.D12) as ngay12,\r\n" + 
				"	(select display from status s where s.value=t.D13) as ngay13,\r\n" + 
				"	(select display from status s where s.value=t.D14) as ngay14,\r\n" + 
				"	(select display from status s where s.value=t.D15) as ngay15,\r\n" + 
				"	(select display from status s where s.value=t.D16) as ngay16,\r\n" + 
				"	(select display from status s where s.value=t.D17) as ngay17,\r\n" + 
				"	(select display from status s where s.value=t.D18) as ngay18,\r\n" + 
				"	(select display from status s where s.value=t.D19) as ngay19,\r\n" + 
				"	(select display from status s where s.value=t.D20) as ngay20,\r\n" + 
				"	(select display from status s where s.value=t.D21) as ngay21,\r\n" + 
				"	(select display from status s where s.value=t.D22) as ngay22,\r\n" + 
				"	(select display from status s where s.value=t.D23) as ngay23,\r\n" + 
				"	(select display from status s where s.value=t.D24) as ngay24,\r\n" + 
				"	(select display from status s where s.value=t.D25) as ngay25,\r\n" + 
				"	(select display from status s where s.value=t.D26) as ngay26,\r\n" + 
				"	(select display from status s where s.value=t.D27) as ngay27,\r\n" + 
				"	(select display from status s where s.value=t.D28) as ngay28,\r\n" + 
				"	(select display from status s where s.value=t.D29) as ngay29,\r\n" + 
				"	(select display from status s where s.value=t.D30) as ngay30,\r\n" + 
				"	(select display from status s where s.value=t.D31) as ngay31\r\n" + 
				"from timesheet t\r\n" + 
				"	join employee e on (t.employee_id=e.employee_id)\r\n" + 
				"order by stt";
		
		return jdbcTemplate.query(SELECT_TIMESHEET_EXCEL, this::mapRowToTimesheetExcel);
	}
	
	private TimesheetExcel mapRowToTimesheetExcel(ResultSet rs, int rowNum) throws SQLException {
		TimesheetExcel tse = new TimesheetExcel();
		tse.setStt(rs.getInt("stt"));
		tse.setHoten(rs.getString("hoten"));
		tse.setChucvu(rs.getString("chucvu"));
		tse.setNgay01(rs.getString("ngay01"));
		tse.setNgay02(rs.getString("ngay02"));
		tse.setNgay03(rs.getString("ngay03"));
		tse.setNgay04(rs.getString("ngay04"));
		tse.setNgay05(rs.getString("ngay05"));
		tse.setNgay06(rs.getString("ngay06"));
		tse.setNgay07(rs.getString("ngay07"));
		tse.setNgay08(rs.getString("ngay08"));
		tse.setNgay09(rs.getString("ngay09"));
		tse.setNgay10(rs.getString("ngay10"));
		tse.setNgay11(rs.getString("ngay11"));
		tse.setNgay12(rs.getString("ngay12"));
		tse.setNgay13(rs.getString("ngay13"));
		tse.setNgay14(rs.getString("ngay14"));
		tse.setNgay15(rs.getString("ngay15"));
		tse.setNgay16(rs.getString("ngay16"));
		tse.setNgay17(rs.getString("ngay17"));
		tse.setNgay18(rs.getString("ngay18"));
		tse.setNgay19(rs.getString("ngay19"));
		tse.setNgay20(rs.getString("ngay20"));
		tse.setNgay21(rs.getString("ngay21"));
		tse.setNgay22(rs.getString("ngay22"));
		tse.setNgay23(rs.getString("ngay23"));
		tse.setNgay24(rs.getString("ngay24"));
		tse.setNgay25(rs.getString("ngay25"));
		tse.setNgay26(rs.getString("ngay26"));
		tse.setNgay27(rs.getString("ngay27"));
		tse.setNgay28(rs.getString("ngay28"));
		tse.setNgay29(rs.getString("ngay29"));
		tse.setNgay30(rs.getString("ngay30"));
		tse.setNgay31(rs.getString("ngay30"));
		
		return tse;
	}

	@Override
	public void delete(int id, String month, String year) {
		final String DELETE_TIMESHEET = "UPDATE timesheet "
				+ "SET del=true WHERE employee_id=? AND acc_month=? AND acc_year=?";
		jdbcTemplate.update(DELETE_TIMESHEET,
				id,
				month,
				year);
	}

}
