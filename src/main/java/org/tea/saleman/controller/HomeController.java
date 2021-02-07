package org.tea.saleman.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tea.saleman.domain.Invoice;
import org.tea.saleman.service.InvoiceService;
import org.tea.saleman.util.ChuyenTienRaChu;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperRunManager;

@Controller
public class HomeController {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private InvoiceService invoiceService;

	@GetMapping("/")
	public String home(Model model, Principal principal) {
		model.addAttribute("username", principal.getName());
		return "home";
	}
	
	@GetMapping("/tiepthi")
	public String tiepthi() {
		return "marketing";
	}

	
	@GetMapping("/timesheet/")
	public String chamcong() {
		return "timesheet";
	}
	
	/**
	 * create Jasper Report and export PDF
	 */
	
	@RequestMapping(value="/pdf/invoice/{id}")
	public void invoiceReport(@PathVariable int id,
			HttpServletRequest request, HttpServletResponse response) {
		
		Invoice invoice = invoiceService.findById(id);

		// PDF byte stream
		byte[] bytes = null;
		try {
			File jasperFile = ResourceUtils.getFile("classpath:report/invoice.jasper");
			// parameters for Jasper report
			Map<String, Object> parameters = new HashMap<>();
			parameters.put(JRParameter.REPORT_LOCALE, Locale.ITALY);
			parameters.put("invoice_id", id);
			parameters.put("customerName", invoice.getCustomerName());
			parameters.put("customerAddress", invoice.getCustomerAddress());
			parameters.put("customerPhone", invoice.getCustomerPhone());
			LocalDate saledate = invoice.getSaledate();
			int day = saledate.getDayOfMonth();
			int month = saledate.getMonthValue();
			int year = saledate.getYear();
			parameters.put("day", String.valueOf(day));
			parameters.put("month", String.valueOf(month));
			parameters.put("year", String.valueOf(year));
			//parameters.put("weight", invoice.getWeight());   // weight kg
			parameters.put("discount", invoice.getDiscount());
			BigDecimal realval = invoice.getTotal().subtract(invoice.getDiscount());
			parameters.put("realval", realval);
			parameters.put("bangchu", ChuyenTienRaChu.ChuyenSangChu(String.valueOf(realval)));
			parameters.put("giveaway", invoice.getGivecontent());
			bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parameters, dataSource.getConnection());
		} catch (JRException | SQLException | FileNotFoundException e) {
			e.printStackTrace();
		}

		// now write PDF to the out stream of response
		response.setContentType("application/pdf");
		response.setContentLength(bytes.length);
		ServletOutputStream outStream;
		try {
			outStream = response.getOutputStream();
			outStream.write(bytes, 0, bytes.length);
			outStream.flush();
			outStream.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
