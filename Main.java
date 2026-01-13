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
        
        
        RuleLoader ruleLoader = new RuleLoader();
        List rules = ruleLoader.loadRules(); 
        
        if (rules == null || rules.isEmpty()) {
            System.err.println("Error: No rules loaded! Check your JSON file path.");
            return;
        }
        
        
        RuleRepository ruleRepository = new RuleRepository(rules);
         
         
        VisaRuleEvaluator evaluator = new VisaRuleEvaluator(ruleRepository);
         
         
        Country country = (Country) getEnumInput(scanner, Country.class, "Destination Country");
        PassportCountry passportCountry = (PassportCountry) getEnumInput(scanner, PassportCountry.class, "Passport Country");
        TravelPurpose travelPurpose = (TravelPurpose) getEnumInput(scanner, TravelPurpose.class, "Travel Purpose");
        
        int stayDuration = 0;
        while (stayDuration <= 0) {
            System.out.print("\nEnter stay duration in days: ");
            String durationInput = scanner.nextLine().trim();
            try {
                stayDuration = Integer.parseInt(durationInput);
                if (stayDuration <= 0) System.out.println("Please enter a positive number.");
            } catch (NumberFormatException e) {
                System.out.println(" Invalid number format.");
            }
        }

        
        System.out.println("\n---  Verifying Inputs ---");
        System.out.println(" Destination: " + country);
        System.out.println(" Passport:    " + passportCountry);
        System.out.println(" Purpose:     " + travelPurpose);
        System.out.println(" Duration:    " + stayDuration + " days");
        System.out.println("---------------------------");

       
        try {
            VisaDecision decision = evaluator.evaluate(
                    country,
                    passportCountry,
                    travelPurpose,
                    stayDuration
            );

            
            if (decision != null) {
                System.out.println("\n === Visa Decision ===");
                System.out.println("Visa Required:      " + (decision.isVisaRequired() ? "YES" : "NO"));
                System.out.println("Visa Type:          " + decision.getVisaType());
                System.out.println("Documents Required: " + decision.getDocuments());
                System.out.println("Processing Days:    " + decision.getEstimatedProcessingDays());
                System.out.println("Warnings:           " + decision.getWarnings());
            } else {
                System.out.println("\n Error: Evaluator returned null. No matching rule found.");
            }
        } catch (Exception e) {
            System.out.println("\n Error during evaluation: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
    }

   
    private static <T extends Enum<T>> Enum<T> getEnumInput(Scanner scanner, Class<T> enumClass, String promptName) {
        Enum<T> result = null;
        String availableOptions = Arrays.stream(enumClass.getEnumConstants())
                                        .map(Enum::name)
                                        .collect(Collectors.joining(", "));

        while (result == null) {
            System.out.println("\nAvailable " + promptName + ": [" + availableOptions + "]");
            System.out.print("Enter " + promptName + ": ");
            
          
            String input = scanner.nextLine().trim().toUpperCase().replace(" ", "_");

            try {
                result = Enum.valueOf(enumClass, input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid " + promptName + "! Please choose strictly from the list above.");
            }
        }
        return result;
    }
}