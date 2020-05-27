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
import java.sql.ResultSet;
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
		try {
			Integer doctorId = null;
			Integer patientId = null;
			Integer drugId = null;

			Connection conn = jdbcTemplate.getDataSource().getConnection();

			// Get the doctor id
			PreparedStatement doctorIdPs = conn.prepareStatement("SELECT doctor_id FROM doctors WHERE doctor_ssn = ?");
			doctorIdPs.setString(1, prescription.getDoctorSsn());

			// Get the patient id
			PreparedStatement patientIdPs = conn
					.prepareStatement("SELECT patient_id FROM patients WHERE patient_ssn = ?");
			patientIdPs.setString(1, prescription.getPatientSsn());

			// Get the drug id
			PreparedStatement drugIdPs = conn.prepareStatement("SELECT drug_id FROM drugs WHERE trade_name = ?");
			drugIdPs.setString(1, prescription.getDrugName());

			ResultSet doctorIdRes = doctorIdPs.executeQuery();
			ResultSet patientIdRes = patientIdPs.executeQuery();
			ResultSet drugIdRes = drugIdPs.executeQuery();

			if (doctorIdRes.next()) {
				doctorId = doctorIdRes.getInt(1);
				prescription.setDoctorId(doctorId);
			}
			if (patientIdRes.next()) {
				patientId = patientIdRes.getInt(1);
				prescription.setPatientId(patientId);
			}
			if (drugIdRes.next()) {
				drugId = drugIdRes.getInt(1);
				prescription.setDrugId(drugId);
			}
			
			if (doctorId == null) {
				model.addAttribute("msg", "There was no doctor found with an ssn of: " + prescription.getDoctorSsn() + ".");
				return "error";
			}
			
			if (patientId == null) {
				model.addAttribute("msg", "There was no patient found with an ssn of: " + prescription.getPatientSsn() + ".");
				return "error";
			}
			
			if (drugId == null) {
				model.addAttribute("msg", "There was no drug found with a trade name of: " + prescription.getDrugName() + ".");
				return "error";
			}

			PreparedStatement ps = conn.prepareStatement(
					"REPLACE INTO prescriptions (patient_id, doctor_id, drug_id, prescription_date, quantity, refills_authorized, pharmacy_id) "
							+ "VALUES(?, ?, ?, ?, ?, ?, null)");

			ps.setInt(1, patientId);
			ps.setInt(2, doctorId);
			ps.setInt(3, drugId);

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
=======
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
import java.sql.ResultSet;
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
		try {
			Integer doctorId = null;
			Integer patientId = null;
			Integer drugId = null;

			Connection conn = jdbcTemplate.getDataSource().getConnection();

			// Get the doctor id
			PreparedStatement doctorIdPs = conn.prepareStatement("SELECT doctor_id FROM doctors WHERE doctor_ssn = ?");
			doctorIdPs.setString(1, prescription.getDoctorSsn());

			// Get the patient id
			PreparedStatement patientIdPs = conn
					.prepareStatement("SELECT patient_id FROM patients WHERE patient_ssn = ?");
			patientIdPs.setString(1, prescription.getPatientSsn());

			// Get the drug id
			PreparedStatement drugIdPs = conn.prepareStatement("SELECT drug_id FROM drugs WHERE trade_name = ?");
			drugIdPs.setString(1, prescription.getDrugName());

			ResultSet doctorIdRes = doctorIdPs.executeQuery();
			ResultSet patientIdRes = patientIdPs.executeQuery();
			ResultSet drugIdRes = drugIdPs.executeQuery();

			if (doctorIdRes.next()) {
				doctorId = doctorIdRes.getInt(1);
				prescription.setDoctorId(doctorId);
			}
			if (patientIdRes.next()) {
				patientId = patientIdRes.getInt(1);
				prescription.setPatientId(patientId);
			}
			if (drugIdRes.next()) {
				drugId = drugIdRes.getInt(1);
				prescription.setDrugId(drugId);
			}

			PreparedStatement ps = conn.prepareStatement(
					"REPLACE INTO prescriptions (patient_id, doctor_id, drug_id, prescription_date, quantity, refills_authorized) "
							+ "VALUES(?, ?, ?, ?, ?, ?)");

			ps.setInt(1, patientId);
			ps.setInt(2, doctorId);
			ps.setInt(3, drugId);

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
