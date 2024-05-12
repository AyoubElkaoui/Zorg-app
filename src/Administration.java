import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;


class Administration {
    static final int STOP = 0;
    static final int LIST = 1;
    static final int EDIT = 2;
    static final int MEDICATIONS = 4;
    static final int MEDICATIONLIST = 3;
    static final int CONSULTATIONS = 5;
    static final int BMI_HISTORY = 6;
    static final int BACKTOPATIENT = 7;
    static final int CHOOSEUSER = 8;

    ArrayList<Patient> patients;   // List of patients
    User currentUser;
    Patient currentPatient;
    LocalDate[][] medicationStartDates;

    void menu() {
        var scanner = new Scanner(System.in);

        boolean nextCycle = true;
        while (nextCycle) {
            System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));

            // Display patient data if a patient is selected
            if (currentPatient != null) {
                System.out.format("Patient: \u001B[32m%s\n\u001B[0m", currentPatient.fullName());
                currentPatient.viewData(currentUser);
                System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));
            } else {
                System.out.println("\u001B[31mGeen patient geselecteerd\u001B[0m");
                System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));
            }

            System.out.format("%d:  Stop\n", STOP);

            if (currentPatient == null) {
                System.out.format("%d:  Selecteer een patient\n", LIST);
            } else {
                System.out.format("%d:  Bewerk de patient\n", EDIT);
                System.out.format("%d:  Bekijk medicatie lijst\n", MEDICATIONLIST);
                System.out.format("%d:  Wijzig medicatie van patient\n", MEDICATIONS);
                System.out.format("%d:  Wijzig consulten van patient\n", CONSULTATIONS);
                System.out.format("%d:  Bekijk BMI geschiedenis van patient\n", BMI_HISTORY);
                System.out.format("%d:  Selecteer een patient\n", BACKTOPATIENT);
            }

            // Add the new option to go back to user selection screen
            System.out.format("%d:  Kies een andere gebruiker\n", CHOOSEUSER);

            System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));
            int choice = scanner.nextInt();
            System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));
            switch (choice) {
                case STOP:
                    nextCycle = false;
                    break;

                case LIST:
                    listPatients();
                    break;

                case EDIT:
                    editPatientData(currentUser);
                    break;

                case MEDICATIONS:
                    medicationMenu();
                    break;

                case MEDICATIONLIST:
                    viewMedicationsList();
                    break;

                case BMI_HISTORY:
                    if (currentPatient != null) {
                        currentPatient.viewBMIHistory();
                    } else {
                        System.out.println("\u001B[31mGeen patient geselecteerd\u001B[0m");
                    }
                    break;


                case CONSULTATIONS:
                    consultationMenu();
                    break;

                case BACKTOPATIENT:
                    listPatients(); // Go back to patient list
                    break;

                case CHOOSEUSER:
                    currentUser = null; // Go back to user selection screen
                    currentPatient = null; // Reset current patient
                    selectUserType(); // Re-run user selection
                    break;

                default:
                    System.out.println("Selecteer een geldige keuze");
                    break;
            }
        }
    }


    Administration() {

        MedicationList.addMedications();
        // Initialize the list of patients
        patients = new ArrayList<>();
        medicationStartDates = new LocalDate[][]  {
                {LocalDate.of(2023, 5, 20),
                        LocalDate.of(2023, 5, 21)}, // Patient 1's medication start dates
                {LocalDate.of(2023, 5, 22),
                        LocalDate.of(2023, 5, 23)}, // Patient 2's medication start dates
                // Add more patients with their respective medication start dates
        };

        patients.add(new Patient(1, "HU", "Ayoub",
                LocalDate.of(2000, 3, 12), 1.80, 80,
                new String[]{"Paracetamol", "Ibuprofen"},
                new LocalDate[]{LocalDate.of(2023, 5, 20),
                        LocalDate.of(2023, 5, 21)},
                "STANDAARD", LocalDate.of(2023, 5, 25), 21.50));
        patients.get(patients.size() - 1).generateRandomBMIHistory();

        patients.add(new Patient(2, "HU", "Jan",
                LocalDate.of(1995, 5, 20), 1.75, 70,
                new String[]{"Paracetamol", "Ibuprofen"},
                new LocalDate[]{LocalDate.of(2023, 5, 20),
                        LocalDate.of(2023, 5, 21)},
                "STANDAARD", LocalDate.of(2023, 5, 25),21.50));
        patients.get(patients.size() - 1).generateRandomBMIHistory();

        patients.add(new Patient(3, "HU", "Mohammed",
                LocalDate.of(1995, 5, 20), 1.75, 70,
                new String[]{"Paracetamol", "Ibuprofen"},
                new LocalDate[]{LocalDate.of(2023, 5, 20),
                        LocalDate.of(2023, 5, 20)},
                "STANDAARD", LocalDate.of(2023, 5, 25), 21.50));
        patients.get(patients.size() - 1).generateRandomBMIHistory();

        patients.add(new Patient(4, "HU", "Fatima",
                LocalDate.of(1995, 5, 20), 1.75, 70,
                new String[]{"Paracetamol", "Ibuprofen"},
                new LocalDate[]{LocalDate.of(2023, 5, 20),
                        LocalDate.of(2023, 5, 20)},
                "STANDAARD", LocalDate.of(2023, 5, 25), 21.50));
        patients.get(patients.size() - 1).generateRandomBMIHistory();

        patients.add(new Patient(5, "HU", "Klaas",
                LocalDate.of(1995, 5, 20), 1.75, 70,
                new String[]{"Paracetamol", "Ibuprofen"},
                new LocalDate[]{LocalDate.of(2023, 5, 20),
                        LocalDate.of(2023, 5, 20)},
                "STANDAARD", LocalDate.of(2023, 5, 25), 21.50));
        patients.get(patients.size() - 1).generateRandomBMIHistory();
        System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));
        // Add more patients as needed

    }

    void selectUserType() {
        var scanner = new Scanner(System.in);

        boolean validChoice = false;

        while (!validChoice) {
            System.out.println("Gebruikers lijst");
            System.out.println("1: Huisarts");
            System.out.println("2: Tandarts");
            System.out.println("3: Fysiotherapeut");
            System.out.println("4: Apotheker");

            System.out.print("Selecteer een gebruiker: \n");
            System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    currentUser = new Huisarts(1, "Dr. Ayoub");
                    validChoice = true;
                    break;

                case 2:
                    currentUser = new Tandarts(2, "Dr. Klaas");
                    validChoice = true;
                    break;

                case 3:
                    currentUser = new Fysiotherapeut(3, "Dr. Pool");
                    validChoice = true;
                    break;

                case 4:
                    currentUser = new Apotheker(4, "Dr. Bruin");
                    validChoice = true;
                    break;

                default:
                    System.out.println("Ongeldige keuze. Probeer opnieuw.");
                    break;
            }
        }
        System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));
        System.out.format("Huidige gebruiker: [%d] %s (Gebruiker type: %s)\n",
                currentUser.getUserID(), currentUser.getUserName(), currentUser.getUserType());
    }

    void medicationMenu() {
        var scanner = new Scanner(System.in);
        boolean nextCycle = true;
        while (nextCycle) {
            System.out.format("Medicatie Menu\n");
            System.out.format("%d: Terug\n", Medication.RETURN);
            System.out.format("%d: Voeg nieuwe medicatie toe aan patient\n", Medication.ADD_MEDICATION);
            System.out.format("%d: Bewerk medicatie van patient\n", Medication.EDIT_MEDICATION);
            System.out.format("%d: Verwijder medicatie van patient\n", Medication.REMOVE_MEDICATION);

            System.out.print("Voer uw keuze in: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case Medication.RETURN:
                    nextCycle = false;
                    break;

                case Medication.ADD_MEDICATION:
                    Medication.addNewMedication(currentPatient);
                    break;

                case Medication.EDIT_MEDICATION:
                    editMedicationDosage(currentPatient);
                    break;

                case Medication.REMOVE_MEDICATION:
                    Medication.removeMedication(currentPatient);
                    break;



                default:
                    System.out.format("\033[31mOngeldige keuze: %d\033[0m\n", choice);
            }
        }
    }

    public void editMedicationDosage(Patient patient) {
        var scanner = new Scanner(System.in);
        boolean nextCycle = true;
        while (nextCycle) {
            System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));
            System.out.format("%s", "Bewerk medicatie dosering\n");
            System.out.format("\033[33m%d: Terug\033[0m\n", Medication.RETURN);

            for (Medication medication : patient.medicationList) {
                System.out.format("%d: %s | %s | %s\n", patient.medicationList.indexOf(medication) + 1,
                        medication.getName(), medication.getType(), medication.getDosage());
            }
            System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));

            System.out.print("Selecteer een medicatie om de dosering aan te passen: ");
            int choice = scanner.nextInt();
            if (choice == Medication.RETURN) {
                nextCycle = false;
            } else if (choice > 0 && choice <= patient.medicationList.size()) {
                scanner.nextLine(); // Consume newline character
                System.out.print("Voer de nieuwe dosering in: ");
                String newDosage = scanner.nextLine();

                // Validate dosage format
                if (!newDosage.matches("\\d+mg")) {
                    System.out.format("\033[31mOngeldige dosering! Voer een geheel getal " +
                            "gevolgd door 'mg' in.\033[0m\n");
                } else {
                    Medication selectedMedication = patient.medicationList.get(choice - 1);
                    selectedMedication.setDosage(newDosage);

                    // Prompt user for new medication date
                    System.out.print("Voer de nieuwe startdatum van de medicatie in (dd-MM-yyyy): ");
                    String newDateInput = scanner.next();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate newDate = LocalDate.parse(newDateInput, formatter);

                    // Update the medication date in the HashMap
                    patient.medicationDates.put(selectedMedication, newDate);

                    System.out.format("\033[32mDosering van medicatie %s aangepast.\033[0m\n",
                            selectedMedication.getName());
                }
            } else {
                System.out.format("\033[31mOngeldige medicatie ID: %d\033[0m \n", choice);
            }
            break;
        }
    }



    void viewMedicationsList() {
        System.out.println("Medication list:");
        for (Medication medication : MedicationList.medications) {
            // Get medication with index as id
            System.out.format("%d: Naam: %s, Type: %s, Dosering: %s %n", MedicationList.medications.indexOf(medication),
                    medication.getName(), medication.getType(), medication.getDosage());
        }
    }

    void listPatients() {
        System.out.println("Beschikbare Patienten:");
        System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));
        System.out.println("0: Ga terug");
        for (int i = 0; i < patients.size(); i++) {
            System.out.format("\u001B[32m%d: %s\n\u001B[0m", i + 1, patients.get(i).fullName());
        }

        var scanner = new Scanner(System.in);

        System.out.print("Selecteer een patient (Of ga terug 0): ");
        int choice = scanner.nextInt();
        if (choice >= 1 && choice <= patients.size()) {
            currentPatient = patients.get(choice - 1);
//            currentPatient.viewData(currentUser); // Show patient data directly
        } else if (choice == 0) {
            // Go back to patient list
        } else {
            System.out.println("Ongeldige keuze");
        }
        System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));
    }

    void editPatientData(User user) {
        if (currentPatient == null) {
            System.out.println("\u001B[41mSelecteer eerst een patient\u001B[0m\n");
            return;
        }

        var scanner = new Scanner(System.in).useLocale(Locale.US);
        boolean validChoice = false;

        while (!validChoice) {
            System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));
            System.out.println("\u001B[41mWat wil je bewerken?\u001B[0m");
            System.out.println("1: Achternaam");
            System.out.println("2: Voornaam");
            System.out.println("3: Geboortedatum");

            if (user.canEditPatient()) {
                System.out.println("4: Lengte");
                System.out.println("5: Gewicht");
            }

            System.out.println("6: Stoppen met bewerken");
            System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));
            System.out.print("Selecteer een nummer: \n");
            int choice = scanner.nextInt();

            scanner.nextLine(); // Consume newline character

            if (choice == 1) {
                System.out.print("Voer de nieuwe achternaam in: ");
                String newSurName = scanner.nextLine();
                currentPatient.setSurname(newSurName);
                validChoice = true;
            } else if (choice == 2) {
                System.out.print("Voer de nieuwe voornaam in: ");
                String newFirstName = scanner.nextLine();
                currentPatient.setFirstName(newFirstName);
                validChoice = true;
            } else if (choice == 3) {
                System.out.print("Voer de nieuwe geboortedatum in (dd-MM-yyyy): ");
                String newDate = scanner.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate newBirthdate = LocalDate.parse(newDate, formatter);
                currentPatient.setDateOfBirth(newBirthdate);
                validChoice = true;
            } else if (choice == 4 && user.canEditPatient()) {
                System.out.print("Voer de nieuwe lengte in (bijv. 1.75): ");
                String newHeightString = scanner.nextLine();
                // Replace any commas with dots
                newHeightString = newHeightString.replace(',', '.');
                double newHeight = Double.parseDouble(newHeightString);
                currentPatient.setLength(newHeight);
                validChoice = true;
            } else if (choice == 5 && user.canEditPatient()) {
                System.out.print("Voer het nieuwe gewicht in (bijv 80.0):  ");
                String newWeightString = scanner.nextLine();
                // Replace any commas with dots
                newWeightString = newWeightString.replace(',', '.');
                double newWeight = Double.parseDouble(newWeightString);
                currentPatient.setWeight(newWeight);
                validChoice = true;
            } else if (choice == 6) {
                System.out.println("Bewerken gestopt.");
                validChoice = true;
            } else {
                System.out.println("Ongeldige keuze");
            }
        }
    }
    void consultationMenu() {
        var scanner = new Scanner(System.in);
        boolean nextCycle = true;
        while (nextCycle) {
            System.out.println("Consulten Menu");
            System.out.format("%d: Terug\n", Consultation.RETURN);
            System.out.format("%d: Voeg nieuwe consultatie toe aan patient\n", Consultation.ADD_CONSULTATION);
            System.out.format("%d: Bewerk consultatie van patient\n", Consultation.EDIT_CONSULTATION);
            System.out.format("%d: Verwijder consultatie van patient\n", Consultation.REMOVE_CONSULTATION);

            System.out.print("Voer uw keuze in: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case Consultation.RETURN:
                    nextCycle = false;
                    break;

                case Consultation.ADD_CONSULTATION:
                    if (currentUser instanceof Tandarts) {
                        Consultation.chooseConsultationTandarts(currentPatient);
                    } else if (currentUser instanceof Fysiotherapeut){
                        Consultation.chooseConsultationFysio(currentPatient);
                    } else if (currentUser instanceof Huisarts){
                        Consultation.chooseConsultationHuisarts(currentPatient);
                    } else {
                        System.out.println("U heeft hier geen toegang tot");
                    }
                    break;

                case Consultation.EDIT_CONSULTATION:
                    editConsultation();
                    break;

                case Consultation.REMOVE_CONSULTATION:
                    removeConsultation();
                    break;

                default:
                    System.out.format("\033[31mOngeldige keuze: %d\033[0m\n", choice);
            }
        }
    }



//    public void addConsultation() {
//        var scanner = new Scanner(System.in);
//        scanner.nextLine(); // Consume newline character
//
//        System.out.print("Voer het type consultatie in: ");
//        String type = scanner.nextLine();
//
//        System.out.print("Voer de datum van de consultatie in (dd-MM-yyyy): ");
//        String dateInput = scanner.nextLine();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        LocalDate date = LocalDate.parse(dateInput, formatter);
//
//        currentPatient.addConsultation(type, date, price);
//        System.out.println("Consultation toegevoegd.");
//    }

    public void editConsultation() {
        var scanner = new Scanner(System.in);

        System.out.println("Bewerk consulten");

        System.out.print("Voer het nieuwe type consult in: ");
        String type = scanner.nextLine();

        System.out.print("Voer de nieuwe datum van de consult in (dd-MM-yyyy): ");
        String dateInput = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(dateInput, formatter);

        currentPatient.updateConsultation(type, date);
        System.out.println("Consultation bewerkt.");
    }

    public void removeConsultation() {
        currentPatient.deleteConsultation();
        System.out.println("Consult verwijderd.");
    }

}