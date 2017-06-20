package templating;

import com.marvin.component.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    
    private List<TokenParser> tokenParsers;
    
    private Pattern startPattern;
    
    public List<Token> tokenize(Source source) {
        TokenSequence root = new TokenSequence();
        
        List<Token> tokens = new ArrayList<>();
 
        while(source.length() > 0) {
            String text;
            Matcher startMatcher = this.syntax.getStartPattern().matcher(source);

            if (!startMatcher.find()) {
                // nothing to execute.
                text = source.toString();
            } else {
                text = source.substring(startMatcher.start());
            }
            
            if (StringUtils.hasText(text)) {
                tokens.add(new Token("text", text, source.getPosition()));
            }
            
            source.advance(text.length());

            this.syntax.getCommands().forEach((name, pattern) -> {
                source.advanceThroughWhitespace();
                Matcher matcher = pattern.matcher(source);
                if (matcher.find()) {
                    String start = matcher.group("start");
                    String ltrim = matcher.group("ltrim");
                    String value = StringUtils.trimWhitespace(matcher.group("value"));
                    String rtrim = matcher.group("rtrim");
                    String end = matcher.group("end");
                    if (StringUtils.hasText(ltrim)) {
                        value = StringUtils.trimLeadingWhitespace(value);
                    }
                    if (StringUtils.hasText(rtrim)) {
                        value = StringUtils.trimTrailingWhitespace(value);
                    }
                    
//                    tokens.add(new Token(name.concat("_start"), start, source.getPosition()));
//                    tokens.add(new Token("expression", value, source.getPosition()));
//                    tokens.add(new Token(name.concat("_end"), end, source.getPosition()));
                    
                    tokens.add(new Token(name, value, source.getPosition()));
                    source.advance(matcher.end());
                }
            });
        }
        
        tokens.add(Token.EOF());
        
        return tokens;
    }
    
//    public void lTrim(Source source, Token token) {
//        Matcher matcher = this.syntax.matchLTrim(source);
//        if (matcher.lookingAt()) {
//            token.setValue(StringUtils.trimLeadingWhitespace(token.getValue()));
//            source.advance(matcher.end());
//        }
//    }
    
    public List<TokenParser> getTokenParsers() {
        return tokenParsers;
    }

    public void setTokenParsers(List<TokenParser> tokenParsers) {
        this.tokenParsers = tokenParsers;
    }
    
    public static TokenizerBuilder builder() {
        return new TokenizerBuilder();
    }
    
    public static class TokenizerBuilder {
        
        private List<TokenParser> tokenParsers = new ArrayList<>();
        
        public TokenizerBuilder withParser(TokenParser parser) {
            this.tokenParsers.add(parser);
            return this;
        }
        
        public Tokenizer build() {
            Tokenizer tokenizer = new Tokenizer();
            
            tokenizer.setTokenParsers(tokenParsers);
            
            return tokenizer;
        }
        
    }
    
}
