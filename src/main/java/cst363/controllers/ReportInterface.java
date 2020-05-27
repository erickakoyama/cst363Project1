package cst363.controllers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cst363.model.PharmacyPrescriptions;
import cst363.model.Pharmacy;

@Controller
public class ReportInterface {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@GetMapping("/reports")
	public String loadDefaultPage(Model model){
		return "reports";
	}
 
	@GetMapping("/reports/select")
	public String getReportDisplay(@RequestParam("report_name") String report_name, Model model) {
		if(report_name.equals("pr_by_pharma")) {
			try {
				Connection conn = jdbcTemplate.getDataSource().getConnection();
	
				String sql = "select\n" + 
						"	distinct\n" + 
						"	p.name as name,\n" + 
						"    p.pharmacy_id as pharmacy_id\n" + 
						"from prescriptions pr\n" + 
						"inner join pharmacy p on pr.pharmacy_id = p.pharmacy_id\n" + 
						"order by 2;";
				
				 List<Pharmacy> pharmacyId = jdbcTemplate.query(
						 sql, new Object[] {} ,
						 (rs, rowNum) -> new Pharmacy(rs.getString("name"),
						                               rs.getString("pharmacy_id")));
				 
			 	model.addAttribute("pharmacyId", pharmacyId);
					   
				conn.close();
				return "pr_by_pharma";
			} catch (SQLException se) {
				System.out.println("Error");
				model.addAttribute("msg", se.getMessage());
				return "error";
			}
		
		} else if (report_name.equals("pr_by_doc")) { 
			return "pr_by_doc";
		} else {
			return "reports";
		}
	}
	@PostMapping("/reports/select/pr_by_doc")
	public String processPrByDoc(
			@RequestParam("start_date_input") String start_date,
			@RequestParam("end_date_input") String end_date,
			Model model) {
		try {
			Connection conn = jdbcTemplate.getDataSource().getConnection();
	
			String sql = "SELECT CONCAT(last_name,', ',first_name) as full_name, trade_name, generic_name, SUM(quantity) as quantity\n" + 
					"FROM doctors d\n" + 
					"JOIN prescriptions p ON d.doctor_id = p.doctor_id\n" + 
					"JOIN drugs dgs ON dgs.drug_id = p.drug_id\n" + 
					"WHERE prescription_date between ? and ?\n" + 
					"GROUP BY 1, 2, 3;";
			
			 List<CONTROLLER> prescriptions = jdbcTemplate.query(
					 sql, new Object[] {start_date, end_date} ,
					 (rs, rowNum) -> new {{CONTROLLER}}(rs.getString("full_name"),
					                               rs.getString("trade_name"),
					                               rs.getString("generic_name"),
					                               rs.getInt("quantity"));
			 
			 model.addAttribute("prescriptions", prescriptions);
					   
			conn.close();
			return "reportsuccess";
		} catch (SQLException se) {
			System.out.println("Error");
			model.addAttribute("msg", se.getMessage());
			return "error";
		}
	}
	
	@PostMapping("/reports/select/pr_by_pharma")
	public String processPrByPharma( 
			@RequestParam("start_date_input") String start_date,
			@RequestParam("end_date_input") String end_date, 
			@RequestParam("pharmacy_id") String pharmacy_id,
			Model model) {
		try {
			Connection conn = jdbcTemplate.getDataSource().getConnection();
	
			String sql = "select\n" + 
					"	p.name as name,\n" + 
					"	d.trade_name as trade_name,\n" + 
					"    sum(pr.quantity) as quantity\n" + 
					"from prescriptions pr\n" + 
					"join drugs d on pr.drug_id = d.drug_id\n" + 
					"left join pharmacy p on pr.pharmacy_id = p.pharmacy_id\n" + 
					"where  \n" + 
					"	pr.prescription_date between ? and ?\n" + 
					"    and pr.pharmacy_id = ?\n" + 
					"group by\n" + 
					"1, 2;";
			
			 List<PharmacyPrescriptions> prescriptions = jdbcTemplate.query(
					 sql, new Object[] {start_date, end_date, pharmacy_id} ,
					 (rs, rowNum) -> new PharmacyPrescriptions(rs.getString("name"),
					                               rs.getString("trade_name"),
					                               rs.getInt("quantity")));
			 
			 model.addAttribute("prescriptions", prescriptions);
					   
			conn.close();
			return "reportsuccess";
		} catch (SQLException se) {
			System.out.println("Error");
			model.addAttribute("msg", se.getMessage());
			return "error";
		}
	}
}
