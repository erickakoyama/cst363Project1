package cst363.model;

public class DoctorPrescriptions {
   String doctorName;
   String genericName;
   String tradeName;
   String quantity;
   
  public DoctorPrescriptions(String doc, String generic, String trade, String amount)
  {
     this.doctorName = doc;
     this.genericName = generic;
     this.tradeName = trade;
     this.quantity = amount;
  }
  
  public String getDoctorName() {return this.doctorName;}
  public String getGenericName() {return this.genericName;}
  public String getTradeName() {return this.tradeName;}
  public String getQuantity() {return this.quantity;}
}
