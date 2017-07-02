package templating;

import com.marvin.component.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.compile;
import static java.util.regex.Pattern.quote;

public class Tokenizer {
    
    private List<TokenParser> tokenParsers;
    
    private Pattern startPattern;
    
    public List<Token> tokenize(Source source) {
        List<Token> tokens = new ArrayList<>();
 
        while(source.length() > 0) {
            String text;
//            source.advanceThroughWhitespace();
            Matcher startMatcher = this.startPattern.matcher(source);

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
            
            this.tokenParsers.stream().forEach((parser) -> {
//                source.advanceThroughWhitespace();
                parser.parse(source, tokens);
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

    public Pattern getStartPattern() {
        return startPattern;
    }

    public void setStartPattern(Pattern startPattern) {
        this.startPattern = startPattern;
    }
    
    public static TokenizerBuilder builder() {
        return new TokenizerBuilder();
    }
    
    public static class TokenizerBuilder {
        
        private Pattern startPattern;
        private List<TokenParser> tokenParsers = new ArrayList<>();
        
        public TokenizerBuilder withParser(TokenParser parser) {
            this.tokenParsers.add(parser);
            return this;
        }
        
        public TokenizerBuilder withSyntax(Syntax syntax) {
            
            this.startPattern = compile(quote(syntax.getPrintOpen()) + "|" + 
                    quote(syntax.getExecuteOpen()) + "|" + 
                    quote(syntax.getCommentOpen()));
            
            withParser(TokenParser.group("evaluate", syntax.getPrintOpen(), syntax.getPrintClose()));
            withParser(TokenParser.group("execute", syntax.getExecuteOpen(), syntax.getExecuteClose()));
            withParser(TokenParser.group("comment", syntax.getCommentOpen(), syntax.getCommentClose()));
            
            return this;
        }
        
        public Tokenizer build() {
            Tokenizer tokenizer = new Tokenizer();
            
            tokenizer.setStartPattern(startPattern);
            tokenizer.setTokenParsers(tokenParsers);
            
            return tokenizer;
        }
        
    }
    
}
