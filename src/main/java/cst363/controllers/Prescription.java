package cst363.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.validation.Valid;

@Controller
public class Prescription {
	
	String name;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public Prescription() {}
	
	/*
	 * Add a new prescription
	 */
	@GetMapping("/prescription/new")
	public String newPrescription(Model model) {
		return "newprescription";
	}
}
