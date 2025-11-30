import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        DatabaseManager db = new DatabaseManager();
        Patient p = new Patient("Layla", "Rivera", "20010308");

        p.setGender("female");
        p.setPhoneNumber("9155554419");
        p.setStreet1("84 Sunset Terrace");
        p.setCity("El Paso");
        p.setState("TX");
        p.setZIP("79912");
        p.setCountry("USA");
        p.setAllergies("Ibuprofen");

        db.addPatient(p);



    }
}