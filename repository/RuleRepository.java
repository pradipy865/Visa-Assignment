package repository;

import dto.VisaRule;

import java.util.Collections;
import java.util.List;

public class RuleRepository {

    private final List<VisaRule> rules;

    public RuleRepository(List<VisaRule> rules) {
        // Defensive copy: prevents external modification
        this.rules = Collections.unmodifiableList(rules);
    }

    public List<VisaRule> getAllRules() {
        return rules;
    }
}
