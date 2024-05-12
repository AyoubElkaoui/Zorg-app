import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

class Medication {


    static final int RETURN = 0;
    static final int ADD_MEDICATION = 1;
    static final int EDIT_MEDICATION = 2;
    static final int REMOVE_MEDICATION = 3;

    String name;
    String type;
    String dosage;
    LocalDate medicationDate;

    public Medication(String name, String type, String dosage) {
        this.name = name;
        this.type = type;
        this.dosage = dosage;
    }

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDosage() {
        return dosage;
    }

    public void setMedicationDate(LocalDate medicationDate) {
        this.medicationDate = medicationDate;
    }

    public LocalDate getMedicationDate() {
        return this.medicationDate;
    }

    // Add a method to set medication date to today
    public void setMedicationDateToToday() {
        this.medicationDate = LocalDate.now();
    }

    public void viewMedicationData(LocalDate medicationDate) {
        System.out.format("%-17s %5s %10s %22s\n", name, type, dosage, (medicationDate != null) ?
                medicationDate.format(formatter) : "");
    }

//    public static void MedicationMenu(Patient patient) {
//        var scanner = new Scanner(System.in);
//        boolean nextCycle = true;
//        while (nextCycle) {
////            final int ADD_MEDICATION = patient.medicationList.size() + 1;
////            final int REMOVE_MEDICATION = patient.medicationList.size() + 2;
////            final int RETURN = patient.medicationList.size() + 3;
//            System.out.format("\033[33m%d: Terug\033[0m\n", RETURN);
//            System.out.format("\033[32m%d: Nieuwe medicatie toevoegen\033[0m\n", ADD_MEDICATION);
//            System.out.format("\033[31m%d: Medicatie verwijderen\033[0m\n", REMOVE_MEDICATION);
////            for (Medication medication : patient.medicationList) {
////                System.out.format("%d: Bewerk %s\n", patient.medicationList.indexOf(medication) + 3,
// medication.getName());
////            }
//            System.out.print("Voer uw keuze in: ");
//            int choice = scanner.nextInt();
////            if (choice > 2 && choice <= patient.medicationList.size() + 3) {
////                patient.medicationList.get(choice - 3).editMedicationData();
////                choice = RETURN;
////            }
//            switch (choice) {
//                case RETURN ->
//                        // Interrupt the loop
//                        nextCycle = false;
//                case ADD_MEDICATION -> addNewMedication(patient);
//                case REMOVE_MEDICATION -> removeMedication(patient);
////                case EDIT_MEDICATION -> editMedicationData(patient);
//                default -> System.out.format("\033[31mOngeldige medicatie ID: %d\033[0m \n", choice);
//            }
//        }
//    }

    public static void addNewMedication(Patient patient) {
        var scanner = new Scanner(System.in);
        boolean nextCycle = true;
        while (nextCycle) {
            System.out.format("%s\n", "-".repeat(50));
            System.out.format("%s", "Voeg nieuwe medicatie toe\n");
            System.out.format("\033[33m%d: Terug\033[0m\n", RETURN);

            for (Medication medication : MedicationList.getMedications()) {
                if (!patient.medicationList.contains(medication)) {
                    System.out.format("%d: %s | %s | %s\n", MedicationList.getMedications().indexOf(medication) + 1,
                            medication.getName(), medication.getType(), medication.getDosage());
                }
            }

            System.out.print("Voer uw keuze in: ");
            int choice = scanner.nextInt();
            if (choice > 0 && choice <= MedicationList.getMedications().size()) {
                if (!patient.medicationList.contains(MedicationList.getMedications().get(choice - 1))) {
                    MedicationList.addMedicationsToPatient(patient,
                            new String[]{MedicationList.getMedications().get(choice - 1).getName()});

                    // Set the date of medication for the added medication
                    Medication addedMedication = MedicationList.getMedications().get(choice - 1);
                    addedMedication.setMedicationDateToToday();  // Set to today's date

                    System.out.format("\033[32mMedicatie %s toegevoegd aan patient %s\033[0m\n",
                            addedMedication.getName(), patient.getFirstName());

                } else {
                    System.out.format("\033[31mMedicatie %s zit al in de medicatielijst van patient\033[0m\n",
                            new String[]{MedicationList.getMedications().get(choice - 1).getName()},
                            patient.getFirstName());
                }
                choice = RETURN;
            }

            switch (choice) {
                case RETURN ->
                        nextCycle = false;
                default ->
                        System.out.format("\033[31mOngeldige medicatie ID: %d\033[0m \n", choice);
            }
        }
    }


    public static void removeMedication(Patient patient) {
        var scanner = new Scanner(System.in);
        boolean nextCycle = true;
        while (nextCycle) {
            System.out.format("%s\n", "-".repeat(50));
            System.out.format("%s", "Verwijder medicatie\n");
            System.out.format("\033[33m%d: Terug\033[0m\n", RETURN);
            for (Medication medication : patient.medicationList) {
                System.out.format("%d: %s\n", patient.medicationList.indexOf(medication) + 1, medication.getName());

            }
            System.out.print("Voer uw keuze in: ");
            int choice = scanner.nextInt();
            if (choice > 0 && choice <= patient.medicationList.size()) {
                // Add the medication to the patient's medication list
                System.out.format("\033[31mMedicatie %s verwijderd van patient %s\033[0m\n",
                        patient.medicationList.get(choice - 1).getName(), patient.getFirstName());
                MedicationList.removeMedicationFromPatient(patient,
                        (MedicationList.getMedications().get(choice - 1).getName()));
                choice = RETURN;
            }
            switch (choice) {
                case RETURN ->
                        // Interrupt the loop
                        nextCycle = false;
                default -> System.out.format("\033[31mOngeldige medicatie ID: %d\033[0m \n", choice);
            }
        }
    }
    public void editMedication(String newDosage) {
        this.dosage = newDosage;
    }
}
