package cst363.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * return the form for user input
 */
public class FillNewPrescriptionController
{

   /**
    * default page view for FillNewPrescription
    * 
    * @return view for the customer to input ssn and prescription
    */
   @GetMapping("/FillNewPrescription")
   public String FillNewPrescription()
   {
      return "FillNewPrescription";
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
      // TODO: get prescription details
      // query goes here

      // set query details in model
      PrescriptionDetails details = new PrescriptionDetails();

      // add model details to view
      model.addAttribute("cost", details.prescriptionCost);
      model.addAttribute("fname", details.firstName);
      model.addAttribute("lname", details.lastName);
      model.addAttribute("prescName", details.prescriptionName);
      model.addAttribute("refills", details.availableRefills);
      model.addAttribute("doctor", details.doctor);

      return "PrescriptionDetails";
   }

   /**
    * called when the user continues / agrees with details page
    */
   public String submitFillNewPrescription()
   {
      // TODO: send to pharmacy so they can fill prescription and ship
      return "FillNewPrescriptionSuccess";
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
