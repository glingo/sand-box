package templating.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import templating.Source;

@FunctionalInterface
public interface TokenParser {
    
    List<Token> parse(Source source) throws Exception;
    
    default List<Token> parse(Source source, boolean skipWhiteSpaces) throws Exception{
        if (skipWhiteSpaces) {
            source.advanceThroughWhitespace();
        }
        return parse(source);
    }
    
    default Optional<List<Token>> tryParse(Source source) {
        Source saved = source.save();
//        int offset = source.getOffset();
        try {
            return Optional.of(parse(source, true));
        } catch (Exception exception) {
            source = saved;
        }
        return Optional.empty();
    };
    
    default TokenParser skip(TokenParser skip) {
        return (source) -> {
            List<Token> result = parse(source, true);
            skip.parse(source, true);
            return result;
        };
    }
    
    default TokenParser then(TokenParser then) {
        return (source) -> {
            List<Token> result = parse(source, true);
            result.addAll(then.parse(source, true));
            return result;
        };
    }
    
    default TokenParser or(TokenParser then) {
        return (source) -> {
            int offset = source.getOffset();
            try {
                return parse(source, true);
            } catch(Exception exception) {
                source.setOffset(offset);
            }
            return then.parse(source);
        };
    }
    
    default TokenParser until(TokenParser end) {
        return source -> {
            List<Token> result = new ArrayList<>();
            while(!end.tryParse(source).isPresent()) {
                result.addAll(parse(source, true));
            }
            
            return result;
        };
    }
    
    default TokenParser optional() {
        return source -> {
            Optional<List<Token>> result = tryParse(source);
            if (result.isPresent()) {
                return result.get();
            }
            return Collections.emptyList();
        };
    }
    
    default TokenParser zeroOrMore() {
        return source -> {
            List<Token> result = new ArrayList<>();
            Optional<List<Token>> element;
            while((element = tryParse(source)).isPresent()) {
                result.addAll(element.get());
            }
            return result;
        };
    }
    
    default TokenParser zeroOrMore(TokenParser separator) {
        return source -> {
            List<Token> result = new ArrayList<>();
            Optional<List<Token>> element = tryParse(source);
            if(element.isPresent()) {
                result.addAll(element.get());
                while(separator.tryParse(source).isPresent()) {
                    result.addAll(parse(source, true));
                }
            }
            return result;
        };
    }
    
    default TokenParser oneOrMore() {
        return source -> {
            List<Token> result = new ArrayList<>();
            result.addAll(parse(source, true));
            Optional<List<Token>> element;
            while((element = tryParse(source)).isPresent()) {
                result.addAll(element.get());
            }
            return result;
        };
    }
    
    default TokenParser oneOrMore(TokenParser separator) {
        return source -> {
            List<Token> result = new ArrayList<>();
            result.addAll(parse(source, true));
            while(separator.tryParse(source).isPresent()) {
                result.addAll(parse(source, true));
            }
            return result;
        };
    }
    
    default TokenParser filter(Predicate predicate) {
        return source -> {
            List<Token> result = parse(source, true);
            
            if (!predicate.test(result)) {
                throw new Exception("Does not respect filter");
            }
            
            return result;
        };
    }
    
    default TokenParser map(Function<List<Token>, List<Token>> function) {
        return source -> {
            return function.apply(parse(source, true));
        };
    }
    
    static TokenParser from(String name, Pattern pattern) {
        return source -> {
            Matcher matcher = pattern.matcher(source);
            if(!matcher.lookingAt()) {
                String msg = String.format("%s not found at %s", pattern.pattern(), source.getPosition());
                throw new Exception(msg);
            }
            
            String value = source.substring(matcher.end()).trim();
            Token token = new Token(name, value, source.getPosition());
            source.advance(matcher.end());
            return new ArrayList(Arrays.asList(token));
        };
    }
    
    static TokenParser until(String name, String... values) {
        return until(name, Arrays.asList(values));
    }
    
    static TokenParser until(String name, List<String> values) {
        StringBuilder sb = new StringBuilder("^.*?(?=");
        sb.append(values.stream().map(Pattern::quote).collect(Collectors.joining("|")));
        sb.append(")");
        return from(name, Pattern.compile(sb.toString(), Pattern.DOTALL));
    }
    
    static TokenParser value(String name, String value) {
        return from(name, Pattern.compile(Pattern.quote(value)));
    }
    
    static TokenParser in(String name, String value) {
        StringBuilder sb = new StringBuilder();
        for (char c: value.toCharArray()) {
            if (sb.length() > 0) {
                sb.append("|");
            }
            sb.append("\\Q").append(c).append("\\E");
        }
        return from(name, Pattern.compile(sb.toString()));
    }
}
