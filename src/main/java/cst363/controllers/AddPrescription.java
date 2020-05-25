package cst363.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import cst363.model.Prescription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.validation.Valid;

@Controller
public class AddPrescription {
	@Autowired
	JdbcTemplate jdbcTemplate;

	/*
	 * Display page for adding a new prescription.
	 */
	@GetMapping("/prescription/new")
	public String newPrescription(Model model) {
		model.addAttribute("prescription", new Prescription());
		return "newprescription";
	}

	/*
	 * process the submitted form
	 */
	@PostMapping("/prescription/new")
	public String processForm(@Valid Prescription prescription, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.out.println("Form errors, returning newprescription template");
			return "newprescription";
		}

		try {
			Connection conn = jdbcTemplate.getDataSource().getConnection();

			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO prescriptions (doctor_ssn, patient_ssn, drug_trade_name, prescription_date, quantity, refills_authorized) "
							+ "VALUES(?, ?, ?, ?, ?, ?)");

			ps.setString(1, prescription.getDoctorSsn());
			ps.setString(2, prescription.getPatientSsn());
			ps.setString(3, prescription.getDrugName());

			ps.setDate(4, prescription.getPrescriptionDate());
			ps.setInt(5, prescription.getQuantity());
			ps.setInt(6, prescription.getRefillsAuthorized());

			ps.executeUpdate();

			conn.close();
			model.addAttribute("result", prescription);
			return "newprescription-success";
		} catch (SQLException se) {
			System.out.println("Error:  Prescription#prescription/new SQLException " + se.getMessage());
			model.addAttribute("msg", se.getMessage());
			return "error";
		}
	}

}
