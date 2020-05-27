package cst363.model;

public class Pharmacy {
	String pharmacyName;
	String pharmacyId;
	
	public Pharmacy(String pharmacyName, String pharmacyId) {
		super();
		this.pharmacyName = pharmacyName;
		this.pharmacyId = pharmacyId;
	}
	
	public String getPharmacyName() {
		return this.pharmacyName;
	}
	
	public String getPharmacyId() {
		return this.pharmacyId;
	}
}
