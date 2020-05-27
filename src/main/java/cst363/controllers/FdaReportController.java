package cst363.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for FDA government official report
 */
@Controller
public class FdaReportController
{
   @Autowired
   JdbcTemplate jdbcTemplate;

   /**
    * called from home page
    * @return - view for FDA official to input date range
    */
   @GetMapping("/fdaReport")
   public String fdaReport()
   {
      return "<insert view name here>";
   }

   /**
    * called from view to generate FDA report
    * @param startDate - beginning date to search
    * @param endDate - end date to search
    * @param model - passes ArrayList<FdaReport> to the view
    * @return - full report of doctors and drugs with quantity
    */
   @GetMapping("/generateFdaReport")
   public String generateFdaReport(
         @RequestParam("<start date>") String startDate,
         @RequestParam("<end date>") String endDate, Model model)
   {
      ArrayList<FdaReport> reports = new ArrayList<>();

      try
      {
         Connection con = jdbcTemplate.getDataSource().getConnection();
         PreparedStatement ps = con.prepareStatement(
               "SELECT d.doctor_id, CONCAT(first_name, \" \", last_name) as name, trade_name, generic_name, SUM(quantity) as quantity\r\n"
                     + "FROM doctors d\r\n"
                     + "JOIN prescriptions p ON d.doctor_id = p.doctor_id\r\n"
                     + "JOIN drugs dgs ON dgs.drug_id = p.drug_id\r\n"
                     + "WHERE prescription_date >= '?' AND prescription_date <= '?'\r\n"
                     + "GROUP BY d.doctor_id, dgs.drug_id;");
         ps.setString(1, startDate);
         ps.setString(2, endDate);

         ResultSet rs = ps.executeQuery();
         while (rs.next())
         {
            FdaReport report = new FdaReport(rs.getString(2), rs.getString(3),
                  rs.getString(4), rs.getInt(5));

            reports.add(report);
         }

      } catch (SQLException e)
      {
         System.out
               .println("Error:  FDA Report SQLException " + e.getMessage());
         model.addAttribute("msg", e.getMessage());
         return "error";
      } catch (Exception e)
      {
         System.out
               .println("Error:  Unexpected Error occured" + e.getMessage());
         model.addAttribute("msg", e.getMessage());
         return "error";
      }

      model.addAttribute("fdaReportDetails", reports);

      return "<insert view name here>";
   }

   /**
    * helper class to display in the model holds information to display to the
    * FDA gov. official
    */
   protected class FdaReport
   {
      FdaReport(String doc, String generic, String trade, int amount)
      {
         this.doctorName = doc;
         this.drugGenericName = generic;
         this.drugTradeName = trade;
         this.quantity = amount;
      }

      public String doctorName;
      public String drugGenericName;
      public String drugTradeName;
      public int quantity;
   }

}// end controller
