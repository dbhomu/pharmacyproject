package models;

public class Prescription {
    private Patient patient;
    private Prescriber prescriber;
    private Drug drug;
    private String SIG;
    private int drugRefills;
    private String earliestFillDate;
    private String prescriptionExpirationDate;
    private String prescribedQuantity;
    private String writtenDate;
    private String diagnosisCode;
    private String prescriptionType;


    public Prescription(Patient patient, Prescriber prescriber, Drug drug, String SIG, int refills, String earliestFillDate, String prescriptionExpirationDate, String prescribedQuantity, String writtenDate, String diagnosisCode, String prescriptionType) {
        this.patient = patient;
        this.prescriber = prescriber;
        this.drug = drug;
        this.SIG = SIG;
        this.drugRefills = drugRefills;
        this.earliestFillDate = earliestFillDate;
        this.prescriptionExpirationDate = prescriptionExpirationDate;
        this.prescribedQuantity = prescribedQuantity;
        this.writtenDate = writtenDate;
        this.diagnosisCode = diagnosisCode;
        this.prescriptionType = prescriptionType;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Prescriber getPrescriber() {
        return prescriber;
    }

    public void setPrescriber(Prescriber prescriber) {
        this.prescriber = prescriber;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public String getSIG() {
        return SIG;
    }

    public void setSIG(String SIG) {
        this.SIG = SIG;
    }

    public int getRefills() {
        return drugRefills;
    }

    public void setRefills(int refills) {

    }

    public String getEarliestFillDate() {
        return earliestFillDate;
    }

    public void setEarliestFillDate(String earliestFillDate) {
        this.earliestFillDate = earliestFillDate;
    }

    public String getPrescriptionExpirationDate() {
        return prescriptionExpirationDate;
    }

    public void setPrescriptionExpirationDate(String prescriptionExpirationDate) {
        this.prescriptionExpirationDate = prescriptionExpirationDate;
    }

    public String getPrescribedQuantity() {
        return prescribedQuantity;
    }

    public void setPrescribedQuantity(String prescribedQuantity) {
        this.prescribedQuantity = prescribedQuantity;
    }

    public String getWrittenDate() {
        return writtenDate;
    }

    public void setWrittenDate(String writtenDate) {
        this.writtenDate = writtenDate;
    }

    public String getDiagnosisCode() {
        return diagnosisCode;
    }

    public void setDiagnosisCode(String diagnosisCode) {
        this.diagnosisCode = diagnosisCode;
    }

    public String getPrescriptionType() {
        return prescriptionType;
    }

    public void setPrescriptionType(String prescriptionType) {
        this.prescriptionType = prescriptionType;
    }
}
