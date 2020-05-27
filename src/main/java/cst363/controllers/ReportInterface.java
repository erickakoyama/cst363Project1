package cst363.controllers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
	public String displayForm(Model model) {
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
		return "reports";
		} catch (SQLException se) {
			System.out.println("Error");
			model.addAttribute("msg", se.getMessage());
			return "error";
		}
	}
	
	@PostMapping("/reports")
	public String processForm(@RequestParam("start_date_input") String start_date,
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
					 sql, new Object[] {start_date, end_date, pharmacy_id } ,
					 (rs, rowNum) -> new PharmacyPrescriptions(rs.getString("name"),
					                               rs.getString("trade_name"),
					                               rs.getInt("quantity")));
			 
			 model.addAttribute("prescriptions", prescriptions);
					   
			conn.close();
			return "/reportsuccess";
			
		} catch (SQLException se) {
			System.out.println("Error");
			model.addAttribute("msg", se.getMessage());
			return "error";
		}
	}
}
