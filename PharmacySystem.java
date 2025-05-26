package pharmacy;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class PharmacySystem {
    private final Scanner scanner = new Scanner(System.in);
    private final Map<String, List<Product>> catalog;
    private final List<Product> cart = new ArrayList<>();  // polymorphism - list sng products ang unod sng cart, pero sari2 nga subclass
    private final List<Integer> quantities = new ArrayList<>();

    public PharmacySystem() {
        catalog = createCatalog();
    }

    public void startTransaction() {
        System.out.println("Welcome to HealthPlus Pharmacy!");

        boolean shopping = true;
        while (shopping) {
            displayCategories();
            String categoryChoice = scanner.nextLine().toLowerCase();

            List<Product> selectedCategory = getCategoryProducts(categoryChoice);
            if (selectedCategory == null) {
                System.out.println("Invalid category. Try again.\n");
                continue;
            }

            displayProducts(selectedCategory);

            System.out.print("\nEnter the number of the item you want to buy: ");
            int itemIndex = scanner.nextInt() - 1;
            scanner.nextLine();

            if (itemIndex < 0 || itemIndex >= selectedCategory.size()) {
                System.out.println("Invalid item. Try again.\n");
                continue;
            }

            Product selectedProduct = selectedCategory.get(itemIndex); // polymorphism - list sng products ang unod sng cart, pero sari2 nga subclass

            System.out.print("\nEnter quantity: ");
            int qty = scanner.nextInt();
            scanner.nextLine();

            cart.add(selectedProduct);  // polymorphism - list sng products ang unod sng cart, pero sari2 nga subclass
            quantities.add(qty);

            System.out.print("\nAdd another item? [y/n]: ");
            String more = scanner.nextLine();
            if (!more.equalsIgnoreCase("y")) {
                shopping = false;
            }
        }

        System.out.print("\nAre you over 60 years old? [y/n]: ");
        boolean isSenior = scanner.nextLine().equalsIgnoreCase("y");

        double total = computeTotal();
        double discount = isSenior ? total * 0.20 : 0;
        double finalAmount = total - discount;

        System.out.printf("\nTotal Amount: ₱%.2f\n", finalAmount);
        System.out.print("\nEnter payment amount: ₱ ");
        double payment = scanner.nextDouble();
        scanner.nextLine();

        if (payment >= finalAmount) {
            double change = payment - finalAmount;
            printReceipt(total, discount, finalAmount, payment, change);
        } else {
            System.out.println("\nInsufficient payment.");
        }
    }

    private void displayCategories() {
        System.out.println("\nWe offer:");
        System.out.println("[A] Prescription Drugs");
        System.out.println("[B] Vitamins");
        System.out.println("[C] Toiletries");
        System.out.println("[D] Medical Tools");
        System.out.print("\nChoose a category [A/B/C/D]: ");
    }

    private void displayProducts(List<Product> products) {
        System.out.println("\nAvailable items:");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.println("[" + (i + 1) + "] " + p.getName() + " - ₱" + p.getPrice());
        }
    }

    private List<Product> getCategoryProducts(String choice) {
        return switch (choice) {
            case "a" -> catalog.get("Prescription Drugs");
            case "b" -> catalog.get("Vitamins");
            case "c" -> catalog.get("Toiletries");
            case "d" -> catalog.get("Medical Tools");
            default -> null;
        };
    }

    private double computeTotal() {
        double total = 0;
        for (int i = 0; i < cart.size(); i++) {
            total += cart.get(i).getPrice() * quantities.get(i);
        }
        return total;
    }

    private void printReceipt(double total, double discount, double finalAmount, double payment, double change) {
        // mag generate receipt number
        Random random = new Random();
        int receiptNumber = 100000 + random.nextInt(900000); // 6-digit number

        // magdisplay current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = now.format(formatter);

        System.out.println("\n================= HealthPlus Pharmacy =================");
        System.out.println("            Arnaldo Blvd., Roxas City, Capiz");
        System.out.println("---------------S A L E S  I N V O I C E----------------");
        System.out.println("Receipt No:                                     #" + receiptNumber);
        System.out.println("Date/Time:                             " + formattedDateTime);

        for (int i = 0; i < cart.size(); i++) {
            Product p = cart.get(i);
            int qty = quantities.get(i);
            double lineTotal = p.getPrice() * qty;
            System.out.printf("\n%s (%s) x %d - ₱%.2f\n", p.getName(), p.getCategory(), qty, lineTotal);
        }

        System.out.printf("\nSubtotal: ₱%.2f\n", total);
        if (discount > 0) {
            System.out.printf("Senior Discount (20%%): -₱%.2f\n", discount);
        }
        System.out.printf("\nTOTAL AMOUNT: ₱%.2f\n", finalAmount);
        System.out.printf("Cash Tendered: ₱%.2f\n", payment);
        System.out.printf("CHANGE: ₱%.2f\n", change);
        System.out.println("-------------------------------------------------------");
        System.out.println("        Thank you for purchasing! Stay healthy!");
        System.out.println("=======================================================");
    }


    private Map<String, List<Product>> createCatalog() {
        Map<String, List<Product>> catalog = new HashMap<>();

        catalog.put("Prescription Drugs", Arrays.asList(
            new PrescriptionDrug("Amoxicillin", 25.0),
            new PrescriptionDrug("Losartan", 28.0),
            new PrescriptionDrug("Metformin", 30.0),
            new PrescriptionDrug("Omeprazole", 40.0),
            new PrescriptionDrug("Paracetamol (Rx)", 15.0),
            new PrescriptionDrug("Prednisone", 27.0),
            new PrescriptionDrug("Salbutamol Inhaler", 220.0)
        ));

        catalog.put("Vitamins", Arrays.asList(
            new Vitamins("B-Complex", 17.0),
            new Vitamins("Calcium + Vitamin D", 20.0),
            new Vitamins("Fish Oil Omega-3", 30.0),
            new Vitamins("Iron + Folic Acid", 12.0),
            new Vitamins("Multivitamins", 15.0),
            new Vitamins("Vitamin C 500mg", 10.0),
            new Vitamins("Zinc Supplement", 14.0)
        ));

        catalog.put("Toiletries", Arrays.asList(
            new Toiletries("Antibacterial Soap", 22.0),
            new Toiletries("Alcohol 70% (250ml)", 70.0),
            new Toiletries("Conditioner 200ml", 48.0),
            new Toiletries("Sanitary Pads (10 pcs)", 45.0),
            new Toiletries("Shampoo 200ml", 45.0),
            new Toiletries("Toothpaste", 20.0),
            new Toiletries("Wet Wipes", 30.0)
        ));

        catalog.put("Medical Tools", Arrays.asList(
            new MedicalTools("Blood Pressure Monitor", 950.0),
            new MedicalTools("Digital Thermometer", 120.0),
            new MedicalTools("Face Mask (box of 50)", 150.0),
            new MedicalTools("First Aid Kit", 300.0),
            new MedicalTools("Glucometer Set", 1200.0),
            new MedicalTools("Nebulizer Machine", 1500.0),
            new MedicalTools("Pulse Oximeter", 750.0)
        ));

        return catalog;
    }
}
