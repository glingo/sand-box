package templating;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rule {
    
    private Pattern intern;
    private Predicate<Matcher> validate;

    public Rule(Pattern intern, Predicate<Matcher> validate) {
        this.intern = intern;
        this.validate = validate;
    }
    
    public boolean validate(String value) {
        return this.validate.test(match(value));
    }
    
    public Matcher match(String value) {
        return this.intern.matcher(value);
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
    
    public static Rule expect(String value) {
        return new Rule(Pattern.compile(value), (matcher) -> {
            return matcher.find();
        });
    }
}
