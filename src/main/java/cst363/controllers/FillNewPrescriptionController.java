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
   @PostMapping("/PrescriptionDetails")
   public String getPrescriptionDetails(@RequestParam("ssn") String ssn,
         @RequestParam("prescNum") String prescNum, Model model)
   {
      // format of doctorId-patiendId-drugId
      String[] parsePresc = prescNum.split("-");

      PrescriptionDetails details = new PrescriptionDetails();
      
      try
      {
         Connection con = jdbcTemplate.getDataSource().getConnection();
         
         // query statement
         PreparedStatement ps = con.prepareStatement("select concat(p.first_name, \" \", p.last_name) as patient_name, concat(d.first_name, \" \", d.last_name) as doctor_name, drugs.generic_name as drug_name, price.price as amount from patients p, doctors d, drugs drugs, drug_price price where d.doctor_id = ? AND p.patient_id = ? AND price.drug_id = ?");
         
         // set query values based on parsed
         ps.setString(1, parsePresc[0]);
         ps.setString(2, parsePresc[1]);         
         ps.setString(3, parsePresc[2]);
         

         ResultSet rs = ps.executeQuery();
         // assign results to object         
         while(rs.next()) {
            details.name = rs.getString(1);
            details.doctor = rs.getString(2);
            details.drugName = rs.getString(3);
            details.rxCost = rs.getDouble(4);   
         }
           
         con.close();
         model.addAttribute("detailsModel", details);
         
      } catch (SQLException e)
      {
         System.out.println("Error:  Fill New Prescription SQLException " + e.getMessage());
         model.addAttribute("msg", e.getMessage());
         return "error";
      } catch (Exception e)
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
   @GetMapping("/ConfirmPrescription")
   public String submitFillNewPrescription()
   {
      // update prescription here
      // subtract from refills column when patient picks up meds
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
