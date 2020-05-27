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

@Controller
public class RefillController
{
   
   PrescriptionDetails details = new PrescriptionDetails();
   
   @Autowired
   JdbcTemplate jdbcTemplate;
   
   @GetMapping("/Refill")
   public String Refill() 
   {
      return "refill";
   }
   
   @PostMapping("/RefillDetails")
   public String getRefillDetails(@RequestParam("ssn") String ssn,
         @RequestParam("prescNum") String prescNum, Model model) 
   {
      String returnValue = "";
      String[] parsePresc = prescNum.split("-");      
      details.perscNum = prescNum;
      ResultSet rs;
      
      try 
      {
         Connection con = jdbcTemplate.getDataSource().getConnection();
         PreparedStatement ps = con.prepareStatement
               (
                     "SELECT refills_authorized, refills_used\r\n" + 
                     "From prescriptions \r\n" + 
                     "WHERE doctor_id = ?\r\n" + 
                     "AND patient_id = ?\r\n" + 
                     " AND drug_id = ?\r\n");
         
         ps.setInt(1, Integer.parseInt(parsePresc[0]));
         ps.setInt(2, Integer.parseInt(parsePresc[1]));
         ps.setInt(3, Integer.parseInt(parsePresc[2]));
         
         rs = ps.executeQuery();
         
         while(rs.next()) 
         {
            details.refillsAuth = rs.getInt(1);
            details.refillsUsed = rs.getInt(2);
         }
         
         if(details.refillsAuth > details.refillsUsed) 
         {
            ps = con.prepareStatement("UPDATE prescriptions\r\n" + 
                  "SET refills_used = refills_used + 1\r\n" + 
                  "WHERE doctor_id = ?\r\n" + 
                  "AND patient_id = ?\r\n" + 
                  "AND drug_id = ?\r\n");
            
            ps.setInt(1, Integer.parseInt(parsePresc[0]));
            ps.setInt(2, Integer.parseInt(parsePresc[1]));
            ps.setInt(3, Integer.parseInt(parsePresc[2]));    
            int i =ps.executeUpdate();            
            return "RefillSuccess";
         }
         
         con.close();

      }
      catch(SQLException se) 
      {
         System.out.println(
               "Error:  Refill Prescription SQLException " + se.getMessage());
         model.addAttribute("msg", se.getMessage());
         return "error";
      }
      return "RefillFailed";
   }
 
   protected class PrescriptionDetails
   {
      String perscNum;
      int refillsAuth;
      int refillsUsed;
   }
}
