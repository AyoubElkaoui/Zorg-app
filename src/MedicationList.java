import java.util.ArrayList;
import java.time.LocalDate;

class MedicationList {
    static ArrayList<Medication> medications = new ArrayList<>();

    // Add hard coded medications
    static void addMedications() {
        medications.add(new Medication("Paracetamol", "Pijnstiller", "500mg"));
        medications.add(new Medication("Ibuprofen", "Pijnstiller", "400mg"));
        medications.add(new Medication("Diazepam", "Anti-angst", "10mg"));
        medications.add(new Medication("Euthyrox", "Hormoon", "100mg"));
        medications.add(new Medication("Warfarin", "Bloedverdunner", "5mg"));
        medications.add(new Medication("Ultram", "Pijnstiller", "50mg"));
        medications.add(new Medication("Morfine", "Pijnstiller", "10mg"));
        medications.add(new Medication("Oxycodon", "Pijnstiller", "20mg"));
    }


    // Get the list of medications
    static ArrayList<Medication> getMedications() {
        return medications;
    }

    public static Medication getMedicationByName(String name) {
        for (Medication medication : medications) {
            if (medication.getName().equals(name)) {
                return medication;
            }
        }
        return null; // Return null if no medication with the given name is found
    }
    // Check if the medication name is in the list then add it to the patient's medication list
    public static void addMedicationsToPatient(Patient patient, String[] medicationNames, LocalDate[] startDates) {
        for (int i = 0; i < medicationNames.length; i++) {
            Medication medication = getMedicationByName(medicationNames[i]);
            if (medication != null) {
                patient.addMedication(medication, startDates[i]);
            }
        }
    }

    // Add an overloaded method to handle case where only medication names are provided
    public static void addMedicationsToPatient(Patient patient, String[] medicationNames) {
        for (String medicationName : medicationNames) {
            Medication medication = getMedicationByName(medicationName);
            if (medication != null) {
                patient.addMedication(medication, LocalDate.now()); // Set start date to today
            }
        }
    }

    // Does the same thing as addMedicationToPatient but uses String array as a parameter


    // Remove the medication from the patient's medication list
    static void removeMedicationFromPatient(Patient patient, String medicationName) {
        for (Medication medication : medications) {
            // If the medication name is the same as the medication name in the list then
            // remove it from the patient's medication list
            if (medicationName.equals(medication.getName())) {
                patient.removeMedication(medication);
            }
        }
    }

    // View all the medications in the list

}
