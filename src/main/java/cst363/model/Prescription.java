package cst363.model;

import java.sql.Date;

/**
 * A Prescription
 * 
 * @author erickakoyama
 *
 */
public class Prescription {
	public static int MAX_REFILLS = 6;
	int doctorId;
	int patientId;
	int drugId;
	String doctorSsn;
	String patientSsn;
	String drugName;
	Integer pharmacyId; // null until patient fills a prescription at a pharmacy
	Date prescriptionDate; // date doctor prescribed
	Integer quantity;
	Integer refillsAuthorized; // 0-6 allowed
	Integer refillsUsed; // should not exceed refillsAuthorized

	public Prescription() {
		doctorSsn = "";
		patientSsn = "";
		drugName = "";
		quantity = 0;
		pharmacyId = null;
		prescriptionDate = new Date(System.currentTimeMillis());
		refillsAuthorized = 0;
		refillsUsed = 0;
	}

	public Prescription(String doctorSsn, String patientSsn, String drugName, Integer quantity,
			Integer refillsAuthorized) {
		this.setDoctorSsn(doctorSsn);
		this.setPatientSsn(patientSsn);
		this.setDrugName(drugName);
		this.setPharmacyId(null);
		this.setPrescriptionDate(new Date(System.currentTimeMillis()));
		this.setQuantity(quantity);
		this.setRefillsAuthorized(refillsAuthorized);
		this.setRefillsUsed(0);
	}

	public String getDoctorSsn() {
		return doctorSsn;
	}

	public void setDoctorSsn(String doctorSsn) {
		this.doctorSsn = doctorSsn;
	}

	public String getPatientSsn() {
		return patientSsn;
	}

	public void setPatientSsn(String patientSsn) {
		this.patientSsn = patientSsn;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public Integer getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Integer pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public Date getPrescriptionDate() {
		return prescriptionDate;
	}

	public void setPrescriptionDate(Date prescriptionDate) {
		this.prescriptionDate = prescriptionDate;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getRefillsAuthorized() {
		return refillsAuthorized;
	}

	public void setRefillsAuthorized(Integer refillsAuthorized) {
		if (refillsAuthorized > MAX_REFILLS) {
			this.refillsAuthorized = MAX_REFILLS;
			return;
		}
		
		this.refillsAuthorized = refillsAuthorized;
	}

	public Integer getRefillsUsed() {
		return refillsUsed;
	}

	public void setRefillsUsed(Integer refillsUsed) {
		if (this.refillsUsed < this.refillsAuthorized) {
			this.refillsUsed = refillsUsed;
		}
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public int getDrugId() {
		return drugId;
	}

	public void setDrugId(int drugId) {
		this.drugId = drugId;
	}
}
