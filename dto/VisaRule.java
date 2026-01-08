package dto;

import enums.Country;
import enums.PassportCountry;
import enums.TravelPurpose;
import enums.VisaType;
import enums.DocumentType;

import java.util.List;
import java.util.Collections;
import java.util.List;



public class VisaRule {
    private final Country destinationCountry;
    private final PassportCountry passportCountry;
    private final TravelPurpose travelPurpose;
    private final int maxStayDays;
    private final boolean visaRequired;
    private final VisaType visaType;
    private final List<DocumentType> requiredDocuments;
    private final int estimatedProcessingDays;
    private final List<String> warnings;

    public VisaRule(Country destinationCountry, PassportCountry passportCountry,
                    TravelPurpose travelPurpose, int maxStayDays,
                    boolean visaRequired, VisaType visaType,
                    List<DocumentType> requiredDocuments,
                    int estimatedProcessingDays, List<String> warnings) {
        this.destinationCountry = destinationCountry;
        this.passportCountry = passportCountry;
        this.travelPurpose = travelPurpose;
        this.maxStayDays = maxStayDays;
        this.visaRequired = visaRequired;
        this.visaType = visaType;
        this.requiredDocuments = Collections.unmodifiableList(requiredDocuments);
        this.estimatedProcessingDays = estimatedProcessingDays;
        this.warnings = Collections.unmodifiableList(warnings);
    }

    // Getters
    public Country getDestinationCountry() {
        return destinationCountry;
    }

    public PassportCountry getPassportCountry() {
        return passportCountry;
    }

    public TravelPurpose getTravelPurpose() {
        return travelPurpose;
    }

    public int getMaxStayDays() {
        return maxStayDays;
    }

    public boolean isVisaRequired() {
        return visaRequired;
    }

    public VisaType getVisaType() {
        return visaType;
    }

    public List<DocumentType> getRequiredDocuments() {
        return requiredDocuments;
    }

    public int getEstimatedProcessingDays() {
        return estimatedProcessingDays;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    @Override
    public String toString() {
        return "VisaRule{" +
                "destinationCountry=" + destinationCountry +
                ", passportCountry=" + passportCountry +
                ", travelPurpose=" + travelPurpose +
                ", maxStayDays=" + maxStayDays +
                ", visaRequired=" + visaRequired +
                ", visaType=" + visaType +
                ", requiredDocuments=" + requiredDocuments +
                ", estimatedProcessingDays=" + estimatedProcessingDays +
                ", warnings=" + warnings +
                '}';
    }
}
