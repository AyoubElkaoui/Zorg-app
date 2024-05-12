
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
class Patient {
   static final int RETURN      = 0;
   static final int SURNAME     = 1;
   static final int FIRSTNAME   = 2;
   static final int DATEOFBIRTH = 3;
   static final int AGE         = 4;
   static final int BMI         = 5;
   static final int LENGTH      = 6;
   static final int WEIGTHT     = 7;



   int       id;
   String    surname;
   String    firstName;
   LocalDate dateOfBirth;
   double weight;
   double length;
    ArrayList<Medication> medicationList;
    HashMap<Medication, LocalDate> medicationDates;
    ArrayList<Double> bmiHistory;


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    private Consultation consultation;
    /**
     * Constructor
     */
    Patient(int id, String surname, String firstName, LocalDate dateOfBirth,  double length,
            double weight, String[] medication, LocalDate[] medicationDates,
            String consultType, LocalDate consultDate, double consultPrice) {
        this.id = id;
        this.surname = surname;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.length = length;
        this.weight = weight;
        this.medicationList = new ArrayList<>();
        this.medicationDates = new HashMap<>(); // Initialize with the same size
        MedicationList.addMedicationsToPatient(this, medication, medicationDates);
        this.consultation = new Consultation(consultType, consultDate, consultPrice);
        this.bmiHistory = new ArrayList<>();
        this.dates = new ArrayList<>();
        this.random = new Random();
        generateRandomBMIHistory();
        generateRandomDates();
        this.bmiHistory.add(calculateBMI());

    }

    String getSurname() {
        return surname;
    }

    String getFirstName() {
        return firstName;
    }

    int calculateAge() {
        LocalDate today = LocalDate.now();
        Period period = Period.between(dateOfBirth, today);
        return period.getYears();
    }

    double calculateBMI() {
        double bmi = weight / (length * length);
        return bmi;
    }


    void setSurname(String newSurname) {
        this.surname = newSurname;
    }

    void setFirstName(String newFirstName) {
        this.firstName = newFirstName;
    }

    void setDateOfBirth(LocalDate newDateOfBirth) {
        this.dateOfBirth = newDateOfBirth;
    }

    void setLength(double newLength) {
        this.length = newLength;
    }

    void setWeight(double newWeight) {
        this.weight = newWeight;
    }

    public void addConsultation(String type, LocalDate date, double price) {
        this.consultation = new Consultation(type, date, price);
    }

    public void deleteConsultation() {
        this.consultation = null;
    }
    public void addConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    public void updateConsultation(String type, LocalDate date) {
        if (this.consultation != null) {
            this.consultation.setType(type);
            this.consultation.setDate(date);
        }
    }

    public void viewConsultation() {
        if (consultation != null) {
            consultation.viewConsultationData();
        } else {
            System.out.println("No consultation available.");
        }
    }

    public void generateRandomBMIHistory() {
        Random random = new Random();
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < 5; i++) {
            double randomBMI = 18 + random.nextDouble() * 10;
            updateBMIHistory(randomBMI);
            dates.add(currentDate.minusMonths(i));
        }
        updateBMIHistory(calculateBMI());
        dates.add(currentDate);
    }

    void viewBMIHistory() {
        // Voeg hier het tekenen van de grafiek toe
        drawBMIChart();
    }

    void drawBMIChart() {
        System.out.println("BMI Grafiek:");

        int numBars = Math.min(bmiHistory.size(), 5); // Maximaal 5 staven

        // Print horizontal line
        System.out.println("-".repeat(numBars * 8));

        // Print datums onderaan
        for (int j = numBars - 1; j >= 0; j--) {
            LocalDate date = dates.get(j);
            System.out.print(String.format("%02d-%02d |", date.getDayOfMonth(), date.getMonthValue()));

            // Print bars for this date
            double bmi = bmiHistory.get(j);
            int scaledValue = (int) (bmi * 1);
            for (int i = 0; i < scaledValue; i++) {
                System.out.print("█");
            }
            System.out.println();
        }

        // Print BMI-waarden aan de linkerkant
        System.out.println("-".repeat(numBars * 8));
        System.out.print("      ");
        for (int i = 0; i <= 35; i += 5)  {
            System.out.print(String.format("%2d   ", i));
        }
        System.out.println();
    }

    public void updateBMIHistory(double bmi) {
        this.bmiHistory.add(bmi);
    }
    ArrayList<LocalDate> dates = new ArrayList<>();
    Random random = new Random();


    void generateRandomDates() {
        for (int i = 0; i < bmiHistory.size(); i++) {
            int randomDays = random.nextInt(365) + 1;
            LocalDate randomDate = LocalDate.now().minusDays(randomDays);
            dates.add(randomDate);
        }
    }

    /**
     * Display patient data.
     */
    void viewData(User user) {

//        System.out.format("\u001B[32m Patient id = %d \n\u001B[0m", id);
        System.out.format("%-17s %s\n", "Achternaam:", surname);
        System.out.format("%-17s %s\n", "Voornaam:", firstName);
        System.out.format("%-17s %s %s %s %s\n", "Geboortedatum:", dateOfBirth.format(formatter),
                "(",calculateAge(),"jaar )");
        if (user.hasAccessToBMI()) {
            System.out.format("%-17s %.2f %s\n", "Lengte:", length, "m");
            System.out.format("%-17s %.1f %s\n", "Gewicht:", weight , "kg");
            System.out.format("%-17s %.1f\n", "BMI", calculateBMI());
        } else {
            System.out.println("Geen toegang tot lengte, gewicht en BMI");
        }
        System.out.format("\u001B[36m%s\u001B[0m\n", "-".repeat(80));
        System.out.format("%-17s %6s %16s %25s\n", "Medicatie", "Type", "Dosering", "Start datum gebruik");
        System.out.format("\u001B[36m%s\u001B[0m\n", "-".repeat(80));
        for (Medication medication : medicationList) {
            LocalDate medicationDate = medicationDates.get(medication);
            medication.viewMedicationData(medicationDate);
        }
        System.out.format("\u001B[36m%s\u001B[0m\n", "-".repeat(80));
        System.out.format("%-17s %6s %16s %18s\n", "Consult", "Type", "Datum", "Prijs");
        System.out.format("\u001B[36m%s\u001B[0m\n", "-".repeat(80));
        System.out.format("%-17s %6s %18s %14s\n", "",
                consultation != null ? consultation.getType() : "Geen Consulten",
                consultation != null ? consultation.getDate().format(formatter) : "-",
                consultation != null ? consultation.getPrice() : "-");

    }



    public void addMedication(Medication medication, LocalDate startDate) {
        // Add the medication to the patient's medication list
        medicationList.add(medication);

        // Associate the medication with its date
        medicationDates.put(medication, startDate);
    }

    public void removeMedication(Medication medication) {
        // Remove the medication from the patient's medication list
        medicationList.remove(medication);

        // Remove the association with its date
        medicationDates.remove(medication);
    }

//    public LocalDate getMedicationDate(String medicationName) {
//        return medicationDates.getOrDefault(medicationName, null);
//    }

//    public void editMedicationData(Medication medication) {
//        // Add the medication to the patient's medication list
//        medicationList.set(medication);
//    }

    /**
     * Shorthand for a Patient's full name
     */
    String fullName() {
        return String.format("%s %s %s [%s] ", id, firstName, surname, dateOfBirth.format(formatter) );
    }
}
