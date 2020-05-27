package cst363.model;

public class PharmacyPrescriptions {
	String pharmacyName;
	String tradeName;
	Integer quantity;
	
	public PharmacyPrescriptions(String pharmacyName, String tradeName, Integer quantity) {
		super();
		this.pharmacyName = pharmacyName;
		this.tradeName = tradeName;
		this.quantity = quantity;
	}
	
	public String getPharmacyName() {
		return this.pharmacyName;
	}
	
	public String getTradeName() {
		return this.tradeName;
	}
	
	public Integer getQuantity() {
		return this.quantity;
	}
}
