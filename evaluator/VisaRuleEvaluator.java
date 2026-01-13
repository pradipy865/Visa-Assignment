package evaluator;

import dto.VisaDecision;
import dto.VisaRule;
import enums.Country;
import enums.PassportCountry;
import enums.TravelPurpose;
import enums.VisaType;
import repository.RuleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VisaRuleEvaluator {

    private final RuleRepository ruleRepository;

    public VisaRuleEvaluator(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public VisaDecision evaluate(
            Country country,
            PassportCountry passportCountry,
            TravelPurpose travelPurpose,
            int stayDuration
    ) {

        List<VisaRule> allRules = ruleRepository.getAllRules();

        // 1. Find matching rules - (Duration check removed from here to catch nulls)
        List<VisaRule> matchedRules = allRules.stream()
                .filter(rule ->
                        rule.getDestinationCountry() == country &&
                        rule.getPassportCountry() == passportCountry &&
                        rule.getTravelPurpose() == travelPurpose
                )
                .collect(Collectors.toList());

        List<String> warnings = new ArrayList<>();

        // 2. No object found
        if (matchedRules.isEmpty()) {
            warnings.add("No matching visa rule found");
            return new VisaDecision(true, VisaType.UNKNOWN, new ArrayList<>(), 0, warnings);
        }

        // 3. Multiple object found
        if (matchedRules.size() > 1) {
            warnings.add("Multiple visa rules matched.We are Working on Bug.Stay With us.");
            return new VisaDecision(true, VisaType.UNKNOWN, new ArrayList<>(), 0, warnings);
        }

        // 4. Exactly one object is found
        VisaRule rule = matchedRules.get(0);

        // --- NEW TERMINAL LOGIC FOR MISSING ATTRIBUTES ---
        // We check if the matching object has any missing (null) values
        if (rule.getMaxStayDays() == null) {
            System.err.println("--------------------------------------------------");
            System.err.println("TERMINAL ERROR: Missing Configuration Found!");
            System.err.println("Attribute 'maxStayDays' is missing in JSON for: " + country);
            System.err.println("--------------------------------------------------");
            
            warnings.add("Configuration error: maxStayDays is missing");
            return new VisaDecision(true, VisaType.UNKNOWN, new ArrayList<>(), 0, warnings);
        }

        // 5. Final Duration Check (Original logic, moved after the null check)
        if (stayDuration > rule.getMaxStayDays()) {
            warnings.add("Stay duration exceeds limit");
            return new VisaDecision(true, VisaType.UNKNOWN, new ArrayList<>(), 0, warnings);
        }

        return new VisaDecision(
                rule.isVisaRequired(),
                rule.getVisaType(),
                rule.getRequiredDocuments(),
                rule.getEstimatedProcessingDays(),
                rule.getWarnings()
        );
    }
}