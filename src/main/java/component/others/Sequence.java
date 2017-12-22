package component.others;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import component.utils.StringUtils;

public class Sequence {
    
    private List<Rule> rules;
    
    private Sequence parent;
    
    private List<Sequence> subSequences;

    public Sequence() {
        
    }
    
    public Sequence(Sequence parent, List<Rule> rules, List<Sequence> subSequences) {
        this.parent = parent;
        this.rules = rules;
        this.subSequences = subSequences;
    }
    
    public List<String> groups(String value) {
        List<String> results = new ArrayList<>();
        
        if (Objects.isNull(this.rules)) {
            return results;
        }
        
//        this.rules.stream().map((rule) -> rule.extract(value))
//                .peek(System.out::println);

        List<String> names = new ArrayList<>();
        this.rules.stream().map(Rule::getName).forEach(names::add);
        
        for (Rule rule : this.rules) {
            System.out.print("value : ");
            System.out.println(value);
            String extracted = rule.extract(value);
            if (StringUtils.hasText(extracted)) {
                value = value.replaceFirst(Pattern.quote(extracted), "");
            }
            System.out.print("Result : ");
            System.out.println(extracted);
        }
        Pattern compiled = Pattern.compile(this.rules.stream()
            .peek((rule) -> names.add(rule.getName()))
            .map((rule) -> rule.getIntern().pattern())
            .collect(Collectors.joining()));
        
        System.out.print("Pattern : ");
        System.out.println(compiled.pattern());
//        
//        Matcher matcher = compiled.matcher(value);
        
//        if(!matcher.find()) {
//            return result;
//        }
//        int count = matcher.groupCount();
        
//        System.out.print("Count : ");
//        System.out.println(count);
//        
//        System.out.print("Name Count : ");
//        System.out.println(names.size());
//        
//        names.stream().forEach((name) -> {
//            System.out.print(name);
//            System.out.print(" : ");
//            System.out.println(matcher.group(name));
//            
//            result.add(matcher.group(name));
//        });
//        
//        System.out.print("Result : ");
//        System.out.println(result);
        
//        for (int i = 0; i < count; i++) {
//            result.add(matcher.group(i));
//            System.out.print("Result : ");
//            System.out.println(result);
//        }
        
//        while(matcher.find()) {
//            result.add(matcher.group());
//            System.out.print("Result : ");
//            System.out.println(result);
//        }
        return results;
    }
    
    public boolean validate(String value) {
        boolean invalid = false;
        
        if (!Objects.isNull(this.rules)) {
            invalid = this.rules.stream().anyMatch((rule) -> !rule.validate(value));
        }
        
        if (!Objects.isNull(this.subSequences)) {
            invalid = this.subSequences.stream().anyMatch((sequence) -> !sequence.validate(value));
        }
        
        return !invalid;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }
    
    public void addRule(Rule rule) {
        if (Objects.isNull(rules)) {
            rules = new ArrayList<>();
        }
        
        rules.add(rule);
    }

    public Sequence getParent() {
        return parent;
    }

    public void setParent(Sequence parent) {
        this.parent = parent;
    }

    public List<Sequence> getSubSequences() {
        return subSequences;
    }

    public void setSubSequences(List<Sequence> subSequences) {
        this.subSequences = subSequences;
    }
    
    public void subSequence(Sequence sequence) {
        if (Objects.isNull(subSequences)) {
            subSequences = new ArrayList<>();
        }
        sequence.setParent(this);
        subSequences.add(sequence);
    }
    
    public static SequenceBuilder builder() {
        return new SequenceBuilder();
    }
    
    public static class SequenceBuilder {
        
        private List<Rule> rules = new ArrayList<>();

        private Sequence parent;

        private List<Sequence> subSequences;
        
        public SequenceBuilder withParent(Sequence parent) {
            this.parent = parent;
            return this;
        }
        
        public SequenceBuilder withSequence(Sequence... subSequences) {
            this.subSequences = Arrays.asList(subSequences);
            return this;
        }
        
        public SequenceBuilder withRule(Rule... rules) {
            this.rules.addAll(Arrays.asList(rules));
            return this;
        }
        
        public Sequence build() {
            Sequence sequence = new Sequence();
            
            sequence.setParent(parent);
            
            this.rules.forEach(sequence::addRule);
            
            if (!Objects.isNull(this.subSequences)) {
                this.subSequences.forEach(sequence::subSequence);
            }
            
            return sequence;
        }
    }
}
