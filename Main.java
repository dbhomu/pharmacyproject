import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        DatabaseManager db = new DatabaseManager(); // create object
        ArrayList<Patient> results = db.searchPatient("John","","","","","","","","","","");
        // Print results
        for (Patient p : results) {
            p.printInfo();
        }
    }







}
