package org.tea.saleman.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tea.saleman.domain.TimesheetExcel;
import org.tea.saleman.service.TimesheetService;


@Controller
@RequestMapping("/chamcong")
public class ExcelReportController {
	
	@Autowired
	TimesheetService timesheetService;
	
	public static final String reportFolder = "C:/webapp/timesheet/";
	public static final String templateFilePath = reportFolder + "bangchamcong.xlsx";
	public static final String outputFilePath = reportFolder + "timesheet_excel_output.xlsx";
	
	@RequestMapping("/excel")
	public String excel(@RequestParam String month, @RequestParam String year) {
		List<TimesheetExcel> allTimesheet = timesheetService.excelByMonth(month, year);
		File initialFile = new File(templateFilePath);
		try (InputStream is = new FileInputStream(initialFile)) {
			try (OutputStream os = new FileOutputStream(outputFilePath)) {
				Context context = new Context();
				context.putVar("allTimesheet", allTimesheet);
				context.putVar("acc_month", month);
				context.putVar("acc_year", year);
				JxlsHelper.getInstance().processTemplate(is, os, context);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/chamcong/excel/download";
	}
	
	@RequestMapping("/excel/download") 
	public void download(HttpServletResponse response) {
		// download file
		try {
			File file = new File(outputFilePath);
			byte[] data = FileUtils.readFileToByteArray(file);
			
			response.setContentType("application/octet-stream");
		    response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
			response.setContentLength(data.length);
			FileCopyUtils.copy(data, response.getOutputStream());
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
