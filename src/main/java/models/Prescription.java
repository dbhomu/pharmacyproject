package models;

import java.time.LocalDate;

public class Prescription {

    private Patient patient;
    private Prescriber prescriber;
    private Drug drug;
    private String rxNumber;
    private String SIG;
    private String refills;
    private LocalDate fillDate;
    private String earliestFillDate;
    private String prescriptionExpirationDate;
    private int prescribedQuantity;
    private String writtenDate;
    private String diagnosisCode;
    private String prescriptionType;
    private boolean isActive;

    public Prescription() {}

    public Prescription(Patient patient, Prescriber prescriber, Drug drug, String rxNumber, String SIG,
                        String refills, LocalDate fillDate, String earliestFillDate, String prescriptionExpirationDate,
                        int prescribedQuantity, String writtenDate, String diagnosisCode, String prescriptionType,
                        boolean isActive) {
        this.patient = patient;
        this.prescriber = prescriber;
        this.drug = drug;
        this.rxNumber = rxNumber;
        this.SIG = SIG;
        this.refills = refills;
        this.fillDate = fillDate;
        this.earliestFillDate = earliestFillDate;
        this.prescriptionExpirationDate = prescriptionExpirationDate;
        this.prescribedQuantity = prescribedQuantity;
        this.writtenDate = writtenDate;
        this.diagnosisCode = diagnosisCode;
        this.prescriptionType = prescriptionType;
        this.isActive = isActive;
    }

    // Getters and setters
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Prescriber getPrescriber() { return prescriber; }
    public void setPrescriber(Prescriber prescriber) { this.prescriber = prescriber; }

    public Drug getDrug() { return drug; }
    public void setDrug(Drug drug) { this.drug = drug; }

    public String getRxNumber() { return rxNumber; }
    public void setRxNumber(String rxNumber) { this.rxNumber = rxNumber; }

    public String getSIG() { return SIG; }
    public void setSIG(String SIG) { this.SIG = SIG; }

    public String getRefills() { return refills; }
    public void setRefills(String refills) { this.refills = refills; }

    public LocalDate getFillDate() { return fillDate; }
    public void setFillDate(LocalDate fillDate) { this.fillDate = fillDate; }

    public String getEarliestFillDate() { return earliestFillDate; }
    public void setEarliestFillDate(String earliestFillDate) { this.earliestFillDate = earliestFillDate; }

    public String getPrescriptionExpirationDate() { return prescriptionExpirationDate; }
    public void setPrescriptionExpirationDate(String prescriptionExpirationDate) { this.prescriptionExpirationDate = prescriptionExpirationDate; }

    public int getPrescribedQuantity() { return prescribedQuantity; }
    public void setPrescribedQuantity(int prescribedQuantity) { this.prescribedQuantity = prescribedQuantity; }

    public String getWrittenDate() { return writtenDate; }
    public void setWrittenDate(String writtenDate) { this.writtenDate = writtenDate; }

    public String getDiagnosisCode() { return diagnosisCode; }
    public void setDiagnosisCode(String diagnosisCode) { this.diagnosisCode = diagnosisCode; }

    public String getPrescriptionType() { return prescriptionType; }
    public void setPrescriptionType(String prescriptionType) { this.prescriptionType = prescriptionType; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}
