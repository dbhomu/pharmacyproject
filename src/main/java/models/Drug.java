package models;
public class Drug {
    private String drugName;
    private String drugStrength;
    private String drugNDC;
    private String drugLOT;
    private String drugForm;
    private String drugRoute;
    private String drugManufacturer;
    private int control;
    private int drugPackageSize;
    private int drugQOH;
    private String location;

    public Drug(String drugName, String drugStrength, String drugNDC, String drugLOT, String drugForm, String drugRoute, String drugManufacturer, int control, int drugPackageSize, int drugQOH, String location) {
        setDrugName(drugName);
        this.drugStrength = drugStrength;
        setDrugNDC(drugNDC);
        this.drugLOT = drugLOT;
        this.drugForm = drugForm;
        this.drugRoute = drugRoute;
        this.drugManufacturer = drugManufacturer;
        this.control = control;
        this.drugPackageSize = drugPackageSize;
        this.drugQOH = drugQOH;
        this.location = location;
    }

    public Drug() {

    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        if (drugName == null || drugName.isBlank()) {
            throw new RuntimeException("Drug name cannot be blank!");
        }
        this.drugName = drugName;
    }

    public String getDrugStrength() {
        return drugStrength;
    }

    public void setDrugStrength(String drugStrength) {
        this.drugStrength = drugStrength;
    }

    public String getDrugNDC() {
        return drugNDC;
    }

    public void setDrugNDC(String drugNDC) {
        if (drugNDC == null || drugNDC.length() != 11) {
            throw new RuntimeException("11-digit NDC required");
        }
        this.drugNDC = drugNDC;
    }


    public String getDrugLOT() {
        return drugLOT;
    }

    public void setDrugLOT(String drugLOT) {
        this.drugLOT = drugLOT;
    }

    public String getDrugForm() {
        return drugForm;
    }

    public void setDrugForm(String drugForm) {
        this.drugForm = drugForm;
    }

    public String getDrugRoute() {
        return drugRoute;
    }

    public void setDrugRoute(String drugRoute) {
        this.drugRoute = drugRoute;
    }

    public String getDrugManufacturer() {
        return drugManufacturer;
    }

    public void setDrugManufacturer(String drugManufacturer) {
        this.drugManufacturer = drugManufacturer;
    }

    public int getControl() {
        return control;
    }

    public void setControl(int control) {
        if (control != 0 && (control < 2 || control > 6)) {
            throw new RuntimeException("Control schedule must be 2-6 for prescription drugs, 0 for OTC/non-scheduled");
        }
        this.control = control;
    }


    public int getDrugPackageSize() {
        return drugPackageSize;
    }

    public void setDrugPackageSize(int drugPackageSize) {
        this.drugPackageSize = drugPackageSize;
    }

    public int getDrugQOH() {
        return drugQOH;
    }

    public void setDrugQOH(int drugQOH) {
        this.drugQOH = drugQOH;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if(location.isBlank()) {
            throw new RuntimeException("Drug must have a location");
        }
        this.location = location;
    }
}
