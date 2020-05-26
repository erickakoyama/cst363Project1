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
      details.prescNum = prescNum;

      try
      {
         Connection con = jdbcTemplate.getDataSource().getConnection();

         PreparedStatement prefPharmPs = con.prepareStatement(
               "select p.preferred_pharmacy_id from patients p where p.patient_id = ?");
         prefPharmPs.setString(1, parsePresc[1]);
         ResultSet prefPharmRs = prefPharmPs.executeQuery();

         if (prefPharmRs.next())
         {
            details.prefPharm = prefPharmRs.getInt(1);
         }

         // query statement
         PreparedStatement ps = con.prepareStatement("select \r\n"
               + "   concat(p.first_name, \" \", p.last_name) as patient_name,\r\n"
               + "   concat(d.first_name, \" \", d.last_name) as doctor_name,\r\n"
               + "    drugs.trade_name as drug_name,\r\n"
               + "   price.price as amount\r\n" + "from \r\n"
               + "   patients p, doctors d, drugs drugs, drug_price price\r\n"
               + "where\r\n" + "   d.doctor_id = ? AND\r\n"
               + "   p.patient_id = ? AND\r\n" + "   drugs.drug_id = ? AND\r\n"
               + "   price.pharmacy_id = ? AND\r\n" + "    price.drug_id = ?");

         // set query values based on parsed
         ps.setString(1, parsePresc[0]);
         ps.setString(2, parsePresc[1]);
         ps.setString(3, parsePresc[2]);
         ps.setInt(4, details.prefPharm);
         ps.setString(5, parsePresc[2]);

         ResultSet rs = ps.executeQuery();
         // assign results to object
         while (rs.next())
         {
            details.name = rs.getString(1);
            details.doctor = rs.getString(2);
            details.drugName = rs.getString(3);
            details.rxCost = rs.getDouble(4);
         }

         /*
          * limited sample data, some quries may return 0 results this is due to
          * the pharmacy not having the drug listed in the DB in a real DB with
          * actual data, the drug would exists at the pharmacy for testing
          * purposes a 0 result return is acceptable until acutual
          * 
          */

         con.close();
         model.addAttribute("detailsModel", details);

      } catch (SQLException e)
      {
         System.out.println(
               "Error:  Fill New Prescription SQLException " + e.getMessage());
         model.addAttribute("msg", e.getMessage());
         return "error";
      } catch (Exception e)
      {
         System.out
               .println("Error:  Prescription#prescription/new SQLException "
                     + e.getMessage());
         model.addAttribute("msg", e.getMessage());
         return "error";
      }

      return "PrescriptionDetailsView";
   }

   /**
    * called when the user continues / agrees with details page
    * updates the prescription with the preferred pharmacy
    */
   @PostMapping("/ConfirmPrescription")
   public String submitFillNewPrescription(
         @RequestParam("prescNum") String prescNum, @RequestParam("prefPharm") String prefPharm, Model model)
   {
      // TODO: send to pharmacy so they can fill prescription and ship
         // subtract from refills column when patient picks up meds
  
      String[] parsePresc = prescNum.split("-");
    
      try
      {
         Connection con = jdbcTemplate.getDataSource().getConnection();

         // query statement
         PreparedStatement ps = con.prepareStatement("update\r\n" + 
               "   prescriptions\r\n" + 
               "set\r\n" + 
               "   pharmacy_id = ?\r\n" + 
               "where\r\n" + 
               "   patient_id = ? AND\r\n" + 
               "   doctor_id = ? AND\r\n" + 
               "   drug_id = ?");

         // set query values based on parsed
         ps.setInt(1, Integer.parseInt(prefPharm));
         ps.setString(3, parsePresc[0]);
         ps.setString(2, parsePresc[1]);         
         ps.setString(4, parsePresc[2]);

         // update prescription with pharmacy number
         ps.executeUpdate();
         
         con.close();

      } catch (SQLException e)
      {
         System.out.println(
               "Error:  Fill New Prescription SQLException " + e.getMessage());
         model.addAttribute("msg", e.getMessage());
         return "error";
      } catch (Exception e)
      {
         System.out
               .println("Error:  Prescription#prescription/new SQLException "
                     + e.getMessage());
         model.addAttribute("msg", e.getMessage());
         return "error";
      }
      
      
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
      public int pharmacyId;
      public String prescNum;
      public int prefPharm;
   }

}// end class
