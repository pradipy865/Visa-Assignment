package loader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.VisaRule;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class RuleLoader {

    private static final String RULES_FILE = "./resources/rules.json";

    public List<VisaRule> loadRules() {

        InputStream inputStream =
                getClass().getClassLoader().getResourceAsStream("rules.json");

        if (inputStream == null) {
            throw new RuntimeException("rules.json file not found in resources");
        }

       
        InputStreamReader reader = new InputStreamReader(inputStream);

       
        Gson gson = new Gson();
        Type ruleListType = new TypeToken<List<VisaRule>>() {}.getType();

        List<VisaRule> rules = gson.fromJson(reader, ruleListType);

       
        if (rules == null) {
            return Collections.emptyList();
        }

        return rules;
    }
} 