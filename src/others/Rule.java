package others;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rule {
    
    private String name;
    private Pattern intern;
    private Predicate<Matcher> validate;

    public Rule(String name, Pattern intern, Predicate<Matcher> validate) {
        this.name = name;
        this.intern = intern;
        this.validate = validate;
    }
    
    public boolean validate(String value) {
        return this.validate.test(match(value));
    }
    
    public Matcher match(String value) {
        return this.intern.matcher(value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Pattern getIntern() {
        return intern;
    }

    public Predicate<Matcher> getValidate() {
        return validate;
    }

    public void setIntern(Pattern intern) {
        this.intern = intern;
    }

    public void setValidate(Predicate<Matcher> validate) {
        this.validate = validate;
    }
    
    public String extract(String from) {
        Matcher matcher = match(from);
        if (!matcher.find()) {
            return "";
        }
        return matcher.group(name);
    }
    
    public static Rule group(String name, String value) {
        return group(name, value, "");
    }
    
    public static Rule group(String name, String value, String quantity) {
        String pattern = "(?<".concat(name).concat(">")
                .concat(value).concat(quantity).concat(")");
        return new Rule(name, Pattern.compile(pattern), (matcher) -> {
            return matcher.find();
        });
    }
}
