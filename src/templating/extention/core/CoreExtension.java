package templating.extention.core;

import java.util.HashMap;
import java.util.Map;
import templating.extention.Extension;
import templating.extention.TokenParser;

public class CoreExtension implements Extension {

    @Override
    public Map<String, TokenParser> getTokenParsers() {
        Map<String, TokenParser> parsers = new HashMap<>();
        
        parsers.put("ws_trim", TokenParser.from("ws_trim", "-"));
        
        parsers.put("execute_start", TokenParser.from("execute_start", "{%"));
        parsers.put("execute_end", TokenParser.from("execute_end", "%}"));
        
        parsers.put("comment_start", TokenParser.from("comment_start", "{#"));
        parsers.put("comment_end", TokenParser.from("comment_end", "#}"));
        
        parsers.put("print_start", TokenParser.from("print_start", "{{"));
        parsers.put("print_end", TokenParser.from("print_end", "}}"));
        
        parsers.put("punctuation", TokenParser.from("punctuation", "(", ")", "[", "]", "{", "}", "?", ":", ".", ",", "|", "="));
        
        return parsers;
    }
    
}
