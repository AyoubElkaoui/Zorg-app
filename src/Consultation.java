import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Consultation {
    public static final int RETURN = 0;
    public static final int ADD_CONSULTATION = 1;
    public static final int EDIT_CONSULTATION = 2;
    public static final int REMOVE_CONSULTATION = 3;
    private String type;
    private LocalDate date;
    private final double price;

    public Consultation(String type, LocalDate date, double price) {
        this.type = type;
        this.date = date;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }
    public double getPrice() {
        return price;
    }
    public void setType(String type) {
        this.type = type;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public double setPrice(double price) {
        return price;
    }

    public void viewConsultationData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.format("%-17s %s %.2f\n", type, date.format(formatter), price);
        System.out.format("%-17s %s\n", "Datum:", date.format(formatter));
        System.out.format("%-17s %s\n", "Prijs in Eur:", price);
    }

    public static void chooseConsultationTandarts(Patient currentPatient) {
        var scanner = new Scanner(System.in);

        System.out.println("Kies een Consult type:");
        System.out.println("1: STANDAARD (20.00 euro)");
        System.out.println("2: SIMPEL (30.00 euro)");
        System.out.println("3: COMPLEX (55.00 euro)");
        System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        String type;
        double price;

        switch (choice) {
            case 1:
                type = "STANDAARD";
                price = 20.00;
                break;
            case 2:
                type = "SIMPEL";
                price = 30.00;
                break;
            case 3:
                type = "COMPLEX";
                price = 55.00;
                break;
            default:
                System.out.println("Verkeerde invoer. STANDAARD wordt gebruikt.");
                type = "STANDAARD";
                price = 20.00;
        }

        // Prompt user for consultation date
        System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));
        System.out.println("Vul start datum in: (format: dd-MM-yyyy):");
        String dateInput = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));

        Consultation newConsultation = new Consultation(type, date, price);

        // Now you can do something with the newConsultation object, like assigning it to a patient
        if (currentPatient != null) {
            currentPatient.addConsultation(newConsultation);
            System.out.println("Consult toegevoegd.");
        } else {
            System.out.println("Geen huidige patient geselecteerd.");
        }
    }
    public static void chooseConsultationFysio(Patient currentPatient) {
        var scanner = new Scanner(System.in);

        System.out.println("Kies een Consult type:");
        System.out.println("1: STANDAARD (17.50 euro)");
        System.out.println("2: KORT (22.50 euro)");
        System.out.println("3: UITGEBREID (45.00 euro)");
        System.out.println("4: FACILITEITEN *gebruik van oefenbad* (5.00 euro)");
        System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        String type;
        double price;

        switch (choice) {
            case 1:
                type = "STANDAARD";
                price = 17.50;
                break;
            case 2:
                type = "KORT";
                price = 22.50;
                break;
            case 3:
                type = "UITGEBREID";
                price = 45.00;
                break;
            case 4:
                type = "FACILITEITEN";
                price = 5.00;
                break;
            default:
                System.out.println("Verkeerde invoer. STANDAARD wordt gebruikt.");
                type = "STANDAARD";
                price = 17.50;
        }

        // Prompt user for consultation date
        System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));
        System.out.println("Vul start datum in: (format: dd-MM-yyyy):");
        String dateInput = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));

        Consultation newConsultation = new Consultation(type, date, price);

        // Now you can do something with the newConsultation object, like assigning it to a patient
        if (currentPatient != null) {
            currentPatient.addConsultation(newConsultation);
            System.out.println("Consult toegevoegd.");
        } else {
            System.out.println("Geen huidige patient geselecteerd.");
        }
    }
    public static void chooseConsultationHuisarts(Patient currentPatient) {
        var scanner = new Scanner(System.in);

        System.out.println("Kies een Consult type:");
        System.out.println("1: STANDAARD (21.50 euro)");
        System.out.println("2: UITGEBREID  (43.00 euro)");
        System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        String type;
        double price;

        switch (choice) {
            case 1:
                type = "STANDAARD";
                price = 21.50;
                break;
            case 2:
                type = "UITGEBREID";
                price = 43.00;
                break;
            default:
                System.out.println("Verkeerde invoer. STANDAARD wordt gebruikt.");
                type = "STANDAARD";
                price = 21.50;
        }

        // Prompt user for consultation date
        System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));
        System.out.println("Vul start datum in: (format: dd-MM-yyyy):");
        String dateInput = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        System.out.format("\u001B[33m%s\u001B[0m\n", "=".repeat(80));

        Consultation newConsultation = new Consultation(type, date, price);

        // Now you can do something with the newConsultation object, like assigning it to a patient
        if (currentPatient != null) {
            currentPatient.addConsultation(newConsultation);
            System.out.println("Consult toegevoegd.");
        } else {
            System.out.println("Geen huidige patient geselecteerd.");
        }
    }
}