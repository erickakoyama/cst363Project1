package cst363.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
   @GetMapping("/PrescriptionDetails")
   public String getPrescriptionDetails(@RequestParam("ssn") String ssn,
         @RequestParam("prescription_number") String prescNum, Model model)
   {
      // format of dssn-pssn-genericName
      String[] parsePresc = prescNum.split("-");

      PrescriptionDetails details = new PrescriptionDetails();
      
      try
      {
         Connection con = jdbcTemplate.getDataSource().getConnection();
         
         /*
          * select 
   concat(p.first_name, " ", p.last_name) as patient_name,
   @pharm = p.prefered_pharmacy,
   concat(d.first_name, " ", d.last_name) as doctor_name,
   drugs.generic_name as drug_name,
   @drugId = drugs.drug_id,
   c.price as cost
from 
   patients p, doctors d, drugs drugs, drug_price c
where
   d.doctor_ssn = ? AND
   p.patient_ssn = ? AND
   drugs.generic_name = ? AND
   c.pharmacy_id = @pharm AND
   c.drug_id = @drugId
   
   */
         // query statement
         PreparedStatement ps = con.prepareStatement("select \r\n" + 
               "   concat(p.first_name, \" \", p.last_name) as patient_name,\r\n" + 
               "   @pharm = p.prefered_pharmacy,\r\n" + 
               "   concat(d.first_name, \" \", d.last_name) as doctor_name,\r\n" + 
               "   drugs.generic_name as drug_name,\r\n" + 
               "   @drugId = drugs.drug_id,\r\n" + 
               "   c.price as cost\r\n" + 
               "from \r\n" + 
               "   patients p, doctors d, drugs drugs, drug_price c\r\n" + 
               "where\r\n" + 
               "   d.doctor_ssn = ? AND\r\n" + 
               "   p.patient_ssn = ? AND\r\n" + 
               "   drugs.generic_name = ? AND\r\n" + 
               "   c.pharmacy_id = @pharm AND\r\n" + 
               "   c.drug_id = @drugId");
         
         // set query values based on parsed
         ps.setString(1, parsePresc[0]);
         ps.setString(2, parsePresc[1]);         
         ps.setString(3, parsePresc[2]);
         
         // assign results to object
         ResultSet rs = ps.executeQuery();
         details.name = rs.getString(1);
         details.doctor = rs.getString(2);
         details.drugName = rs.getString(3);
         details.rxCost = rs.getDouble(4);  
         
         model.addAttribute("detailsModel", details.rxCost);
      } catch (SQLException e)
      {
         System.out.println("Error:  Prescription#prescription/new SQLException " + e.getMessage());
         model.addAttribute("msg", e.getMessage());
         return "error";
      }

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
      public String name;
      public String doctor;
      public String drugName;
      public double rxCost;
   }

}// end class
