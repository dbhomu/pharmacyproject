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

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        if(!drugName.matches("[A-Za-z ]+")) {
            throw new RuntimeException("Invalid input!");

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
        if(drugNDC.length() != 11) {
            throw new RuntimeException("11 digits of input required for NDC");
        }
        if(drugNDC.isBlank()) {
            throw new RuntimeException("NDC must have a value");
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
        if(control < 2 || control > 6) {
            throw new RuntimeException("Drug Schedule value must be in between 2 and 6");
        }
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
