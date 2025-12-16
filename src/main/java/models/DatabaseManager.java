package models;

import java.sql.*;
    import java.time.LocalDate;
    import java.util.ArrayList;

    public class DatabaseManager {

        private static final String URL = "jdbc:mysql://localhost:3306/pharmacyDB";
        private static final String USER = "root";
        private static final String PASS = "Mu$abGmai1";

        public static ArrayList<Patient> searchPatient(
                String firstName, String lastName, LocalDate DOB,
                String gender, String phoneNumber, String street1,
                String street2, String city, String state,
                String ZIP, String country) {

            ArrayList<Patient> results = new ArrayList<>();

            try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {

                // Normalize NULL -> "" to avoid .isEmpty() crash
                firstName = firstName == null ? "" : firstName.trim();
                lastName = lastName == null ? "" : lastName.trim();
                gender = gender == null ? "" : gender.trim();
                phoneNumber = phoneNumber == null ? "" : phoneNumber.trim();
                street1 = street1 == null ? "" : street1.trim();
                street2 = street2 == null ? "" : street2.trim();
                city = city == null ? "" : city.trim();
                state = state == null ? "" : state.trim();
                ZIP = ZIP == null ? "" : ZIP.trim();
                country = country == null ? "" : country.trim();

                String sql = "SELECT * FROM patients WHERE 1=1";

                if (!firstName.isEmpty()) sql += " AND firstName LIKE ?";
                if (!lastName.isEmpty()) sql += " AND lastName LIKE ?";
                if (DOB != null) sql += " AND DOB LIKE ?";
                if (!gender.isEmpty()) sql += " AND gender LIKE ?";
                if (!phoneNumber.isEmpty()) sql += " AND phoneNumber LIKE ?";
                if (!street1.isEmpty()) sql += " AND street1 LIKE ?";
                if (!street2.isEmpty()) sql += " AND street2 LIKE ?";
                if (!city.isEmpty()) sql += " AND city LIKE ?";
                if (!state.isEmpty()) sql += " AND state LIKE ?";
                if (!ZIP.isEmpty()) sql += " AND ZIP LIKE ?";
                if (!country.isEmpty()) sql += " AND country LIKE ?";

                PreparedStatement ps = conn.prepareStatement(sql);
                int index = 1;

                if (!firstName.isEmpty()) ps.setString(index++, firstName + "%");
                if (!lastName.isEmpty()) ps.setString(index++, lastName + "%");
                if (DOB != null) ps.setObject(index++, DOB + "%");
                if (!gender.isEmpty()) ps.setString(index++, gender + "%");
                if (!phoneNumber.isEmpty()) ps.setString(index++, phoneNumber + "%");
                if (!street1.isEmpty()) ps.setString(index++, street1 + "%");
                if (!street2.isEmpty()) ps.setString(index++, street2 + "%");
                if (!city.isEmpty()) ps.setString(index++, city + "%");
                if (!state.isEmpty()) ps.setString(index++, state + "%");
                if (!ZIP.isEmpty()) ps.setString(index++, ZIP + "%");
                if (!country.isEmpty()) ps.setString(index++, country + "%");

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    // NULL-safe DOB
                    Date dobColumn = rs.getDate("DOB");
                    LocalDate dobValue = dobColumn != null ? dobColumn.toLocalDate() : null;

                    Patient patient = new Patient(
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            dobValue,
                            rs.getString("gender"),
                            rs.getString("phoneNumber"),
                            rs.getString("street1"),
                            rs.getString("street2"),
                            rs.getString("city"),
                            rs.getString("state"),
                            rs.getString("ZIP"),
                            rs.getString("country"),
                            rs.getString("allergies")
                    );

                    results.add(patient);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return results;
        }

        public static ArrayList<Patient> searchPatient(String input) {
            String[] parts = input.split(",");
            String lastName = parts.length > 0 ? parts[0].trim() : "";
            String firstName = parts.length > 1 ? parts[1].trim() : "";

            // Call the full dynamic search
            return searchPatient(firstName, lastName, null, "", "", "", "", "", "", "", "");
        }



        public void addPrescriber(Prescriber p) {
            try {
                Connection conn = DriverManager.getConnection(URL, USER, PASS);

                String sql = "INSERT INTO prescribers (firstName, lastName, NPI, DEA, phoneNumber, faxNumber, street1, street2, city, state, ZIP, country) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, p.getFirstName());
                ps.setString(2, p.getLastName());
                ps.setString(3, p.getNPI());
                ps.setString(4, p.getDEA());
                ps.setString(5, p.getPhoneNumber());
                ps.setString(6, p.getFaxNumber());
                ps.setString(7, p.getStreet1());
                ps.setString(8, p.getStreet2());
                ps.setString(9, p.getCity());
                ps.setString(10, p.getState());
                ps.setString(11, p.getZIP());
                ps.setString(12, p.getCountry());

                ps.executeUpdate();
                System.out.println("Prescriber added successfully!");

                ps.close();
                conn.close();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public void addPatient(Patient p) {
            try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {

                String sql = "INSERT INTO patients " +
                        "(firstName, lastName, DOB, gender, phoneNumber, street1, street2, city, state, ZIP, country) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setString(1, p.getFirstName());
                ps.setString(2, p.getLastName());
                ps.setObject(3, p.getDOB());
                ps.setString(4, p.getGender());
                ps.setString(5, p.getPhoneNumber());
                ps.setString(6, p.getStreet1());
                ps.setString(7, p.getStreet2());
                ps.setString(8, p.getCity());
                ps.setString(9, p.getState());
                ps.setString(10, p.getZIP());
                ps.setString(11, p.getCountry());

                ps.executeUpdate();
                ps.close();

                System.out.println("Added patient successfully!");

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public void modifyPatient(int patientID, Patient p) {
            try {
                Connection conn = DriverManager.getConnection(URL, USER, PASS);
                String sql = "UPDATE patients SET firstName = ?, lastName = ?, DOB = ?, gender = ?, phoneNumber = ?, " +
                        "street1 = ?, street2 = ?, city = ?, state = ?, ZIP = ?, country = ? " +
                        "WHERE patientID = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, p.getFirstName());
                ps.setString(2, p.getLastName());
                ps.setObject(3, p.getDOB());
                ps.setString(4, p.getGender());
                ps.setString(5, p.getPhoneNumber());
                ps.setString(6, p.getStreet1());
                ps.setString(7, p.getStreet2());
                ps.setString(8, p.getCity());
                ps.setString(9, p.getState());
                ps.setString(10, p.getZIP());
                ps.setString(11, p.getCountry());
                ps.setInt(12, patientID);

                ps.executeUpdate();
                ps.close();
                System.out.println("models.Patient updated successfully!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        public ArrayList<Prescriber> searchPrescriber(String firstName, String lastName, String NPI, String DEA, String phoneNumber, String faxNumber, String street1, String street2,
                                                      String city, String state, String ZIP, String country) {
            ArrayList<Prescriber> results = new ArrayList<>();
            try {
                Connection conn = DriverManager.getConnection(URL, USER, PASS);
                String sql = "SELECT * FROM prescribers WHERE 1=1";
                if (!firstName.isEmpty()) sql += " AND firstName = ?";
                if (!lastName.isEmpty()) sql += " AND lastName = ?";
                if (!NPI.isEmpty()) sql += " AND NPI = ?";
                if (!DEA.isEmpty()) sql += " AND DEA = ?";
                if (!phoneNumber.isEmpty()) sql += " AND phoneNumber = ?";
                if (!faxNumber.isEmpty()) sql += " AND faxNumber = ?";
                if (!street1.isEmpty()) sql += " AND street1 = ?";
                if (!street2.isEmpty()) sql += " AND street2 = ?";
                if (!city.isEmpty()) sql += " AND city = ?";
                if (!state.isEmpty()) sql += " AND state = ?";
                if (!ZIP.isEmpty()) sql += " AND ZIP = ?";
                if (!country.isEmpty()) sql += " AND country = ?";

                PreparedStatement ps = conn.prepareStatement(sql);
                int index = 1;
                if (!firstName.isEmpty()) ps.setString(index++, firstName);
                if (!lastName.isEmpty()) ps.setString(index++, lastName);
                if (!NPI.isEmpty()) ps.setString(index++, NPI);
                if (!DEA.isEmpty()) ps.setString(index++, DEA);
                if (!phoneNumber.isEmpty()) ps.setString(index++, phoneNumber);
                if (!faxNumber.isEmpty()) ps.setString(index++, faxNumber);
                if (!street1.isEmpty()) ps.setString(index++, street1);
                if (!street2.isEmpty()) ps.setString(index++, street2);
                if (!city.isEmpty()) ps.setString(index++, city);
                if (!state.isEmpty()) ps.setString(index++, state);
                if (!ZIP.isEmpty()) ps.setString(index++, ZIP);
                if (!country.isEmpty()) ps.setString(index++, country);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    // Create models.Prescriber
                    Prescriber prescriber = new Prescriber(
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("NPI"),
                            rs.getString("DEA"),
                            rs.getString("phoneNumber"),
                            rs.getString("faxNumber"),
                            rs.getString("street1"),
                            rs.getString("street2"),
                            rs.getString("city"),
                            rs.getString("state"),
                            rs.getString("ZIP"),
                            rs.getString("country")

                    );
                    results.add(prescriber);

                }
                rs.close();
                ps.close();
                conn.close();
            } catch (RuntimeException | SQLException e) {
                throw new RuntimeException(e);
            }


            return results;
        }


        public static boolean validateLogin(String username, String password) {
            try {
                Connection conn = DriverManager.getConnection(URL, USER, PASS);
                String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                return rs.next();
            } catch (RuntimeException | SQLException e) {
                return false;
            }
        }


    }










