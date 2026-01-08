import dto.VisaDecision;
import loader.RuleLoader;
import repository.RuleRepository;
import evaluator.VisaRuleEvaluator;
import enums.Country;
import enums.PassportCountry;
import enums.TravelPurpose;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Initializing Visa System...");
        
        // 1️⃣ Load rules from JSON
        RuleLoader ruleLoader = new RuleLoader();
        List rules = ruleLoader.loadRules(); 
        
        if (rules == null || rules.isEmpty()) {
            System.err.println("❌ Error: No rules loaded! Check your JSON file path.");
            return;
        }

        // 2️⃣ Store rules in repository
        RuleRepository ruleRepository = new RuleRepository(rules);

        // 3️⃣ Create evaluator
        VisaRuleEvaluator evaluator = new VisaRuleEvaluator(ruleRepository);

        // 4️⃣ Take user input (with helper method for cleaner code)
        Country country = (Country) getEnumInput(scanner, Country.class, "Destination Country");
        PassportCountry passportCountry = (PassportCountry) getEnumInput(scanner, PassportCountry.class, "Passport Country");
        TravelPurpose travelPurpose = (TravelPurpose) getEnumInput(scanner, TravelPurpose.class, "Travel Purpose");
        
        int stayDuration = 0;
        while (stayDuration <= 0) {
            System.out.print("\nEnter stay duration in days: ");
            String durationInput = scanner.nextLine().trim();
            try {
                stayDuration = Integer.parseInt(durationInput);
                if (stayDuration <= 0) System.out.println("⚠️ Please enter a positive number.");
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid number format.");
            }
        }

        // 🔍 DEBUG: Confirm inputs before sending to Evaluator
        System.out.println("\n--- 🛠 Verifying Inputs ---");
        System.out.println("📍 Destination: " + country);
        System.out.println("🛂 Passport:    " + passportCountry);
        System.out.println("✈️ Purpose:     " + travelPurpose);
        System.out.println("📅 Duration:    " + stayDuration + " days");
        System.out.println("---------------------------");

        // 5️⃣ Evaluate rules based on user input
        try {
            VisaDecision decision = evaluator.evaluate(
                    country,
                    passportCountry,
                    travelPurpose,
                    stayDuration
            );

            // 6️⃣ Print result
            if (decision != null) {
                System.out.println("\n✅ === Visa Decision ===");
                System.out.println("Visa Required:      " + (decision.isVisaRequired() ? "YES" : "NO"));
                System.out.println("Visa Type:          " + decision.getVisaType());
                System.out.println("Documents Required: " + decision.getDocuments());
                System.out.println("Processing Days:    " + decision.getEstimatedProcessingDays());
                System.out.println("Warnings:           " + decision.getWarnings());
            } else {
                System.out.println("\n❌ Error: Evaluator returned null. No matching rule found.");
            }
        } catch (Exception e) {
            System.out.println("\n❌ Error during evaluation: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
    }

    // --- Helper Method to handle Enum Input generically ---
    private static <T extends Enum<T>> Enum<T> getEnumInput(Scanner scanner, Class<T> enumClass, String promptName) {
        Enum<T> result = null;
        String availableOptions = Arrays.stream(enumClass.getEnumConstants())
                                        .map(Enum::name)
                                        .collect(Collectors.joining(", "));

        while (result == null) {
            System.out.println("\nAvailable " + promptName + ": [" + availableOptions + "]");
            System.out.print("Enter " + promptName + ": ");
            
            // Normalize input: trim spaces, uppercase, replace spaces with underscores
            String input = scanner.nextLine().trim().toUpperCase().replace(" ", "_");

            try {
                result = Enum.valueOf(enumClass, input);
            } catch (IllegalArgumentException e) {
                System.out.println("⚠️ Invalid " + promptName + "! Please choose strictly from the list above.");
            }
        }
        return result;
    }
}