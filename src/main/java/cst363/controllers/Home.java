package cst363.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Home {
	@Autowired
	JdbcTemplate jdbcTemplate;

	/*
	 * Display page for adding a new prescription.
	 */
	@GetMapping("/")
	public String homePage() {
		return "home";
	}
}

