package cst363.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * return the form for user input
 */
@Controller
public class FillNewPrescriptionController
{  
   @Autowired
   JdbcTemplate jdbcTemplate;
   
   /**
    * default page view for FillNewPrescription
    * 
    * @return view for the customer to input ssn and prescription
    */
   @GetMapping("/FillNewPrescription")
   public String FillNewPrescription()
   {
      return "FillNewPrescriptionView";
   }

   /**
    * Queries the db for the user with SSN and prescription from user input
    * 
    * @return confirmation page
    */
   @PostMapping("/PrescriptionDetails")
   public String getPrescriptionDetails(@RequestParam("ssn") String ssn,
         @RequestParam("prescription_number") String prescNum, Model model)
   {
      // TODO: get prescription details
    
      try
      {
         Connection con = jdbcTemplate.getDataSource().getConnection();
         PreparedStatement ps = con.prepareStatement("");
      } catch (SQLException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      // set query details in model
      PrescriptionDetails details = new PrescriptionDetails();

      // add model details to view
      model.addAttribute("cost", details.prescriptionCost);
      model.addAttribute("fname", details.firstName);
      model.addAttribute("lname", details.lastName);
      model.addAttribute("prescName", details.prescriptionName);
      model.addAttribute("refills", details.availableRefills);
      model.addAttribute("doctor", details.doctor);
      
      // possible to add the entire model to the model attribute
      

      return "PrescriptionDetailsView";
   }

   /**
    * called when the user continues / agrees with details page
    */
   @PostMapping("/ConfirmPrescription")
   public String submitFillNewPrescription()
   {
      // update prescription here
      // subtract from refills column
      // TODO: send to pharmacy so they can fill prescription and ship
      return "FillNewPrescriptionSuccessView";
   }

   /**
    * helper class that populates the prescription details from the query
    */
   protected class PrescriptionDetails
   {
      public String prescriptionName;
      public String firstName;
      public String lastName;
      public String doctor;
      public double prescriptionCost;
      public int availableRefills;
   }

}// end class
