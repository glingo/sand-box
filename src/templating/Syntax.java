package templating;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Syntax {
    
    private final String EXPRESSION_PATTERN = "(?<name>%s+)(?<expression>.*)";
    private final String COMMAND_PATTERN = "(?<start>%s+)(?<expression>.*)";
    
    private Map<String, Command> commands;
    private Map<String, Expression> expressions;
    
    private String wsTrim;
    
    private String commentOpen;
    private String commentClose;
    
    private String executeOpen;
    private String executeClose;
    
    private String printOpen;
    private String printClose;

    public String getCommentClose() {
        return commentClose;
    }

    public String getCommentOpen() {
        return commentOpen;
    }

    public String getExecuteClose() {
        return executeClose;
    }

    public String getExecuteOpen() {
        return executeOpen;
    }

    public String getPrintClose() {
        return printClose;
    }

    public String getPrintOpen() {
        return printOpen;
    }

    public String getWsTrim() {
        return wsTrim;
    }

    public void setCommentClose(String commentClose) {
        this.commentClose = commentClose;
    }

    public void setCommentOpen(String commentOpen) {
        this.commentOpen = commentOpen;
    }

    public void setExecuteClose(String executeClose) {
        this.executeClose = executeClose;
    }

    public void setExecuteOpen(String executeOpen) {
        this.executeOpen = executeOpen;
    }

    public void setPrintClose(String printClose) {
        this.printClose = printClose;
    }

    public void setPrintOpen(String printOpen) {
        this.printOpen = printOpen;
    }

    public void setWsTrim(String wsTrim) {
        this.wsTrim = wsTrim;
    }
    
    public Map<String, Expression> getExpressions() {
        return expressions;
    }

    public void setExpressions(Map<String, Expression> expressions) {
        this.expressions = expressions;
    }
    
//    private Map<String, Pattern> commands;
//    private Map<String, Pattern> expressions;
//    
//    private Pattern leadingWhiteSpacePattern;
//    
//    private Pattern startPattern;

//    public Map<String, Pattern> getCommands() {
//        return commands;
//    }
//
//    public void setCommands(Map<String, Pattern> commands) {
//        this.commands = commands;
//    }
//    
//    
//    public Map<String, Pattern> getExpressions() {
//        return expressions;
//    }
//
//    public void setExpressions(Map<String, Pattern> expressions) {
//        this.expressions = expressions;
//    }
//
//    public Pattern getStartPattern() {
//        return startPattern;
//    }
//
//    public void setStartPattern(Pattern startPattern) {
//        this.startPattern = startPattern;
//    }
//    
//    public Matcher matchLTrim(Source source) {
//        return this.leadingWhiteSpacePattern.matcher(source);
//    }
//    
//    public Pattern getLeadingWhiteSpacePattern() {
//        return leadingWhiteSpacePattern;
//    }
//
//    public void setLeadingWhiteSpacePattern(Pattern leadingWhiteSpacePattern) {
//        this.leadingWhiteSpacePattern = leadingWhiteSpacePattern;
//    }
//    
    public static SyntaxBuilder builder() {
        return new SyntaxBuilder();
    }

    public static class SyntaxBuilder {
        
        private Map<String, Command> commands = new HashMap<>();
        private Map<String, Expression> expressions = new HashMap<>();
        
        private String wsTrim = "-";
        
        private String commentOpen = "{#";
        private String commentClose = "#}";

        private String executeOpen = "{%";
        private String executeClose = "%}";

        private String printOpen = "{{";
        private String printClose = "}}";
        
        public SyntaxBuilder trim(String wsTrim) {
            this.wsTrim = wsTrim;
            return this;
        }
        
        public SyntaxBuilder comment(String open, String close) {
            this.commentOpen = open;
            this.commentClose = close;
            return this;
        }
        
        public SyntaxBuilder execute(String open, String close) {
            this.executeOpen = open;
            this.executeClose = close;
            return this;
        }
        
        public SyntaxBuilder print(String open, String close) {
            this.printOpen = open;
            this.printClose = close;
            return this;
        }
        
        public SyntaxBuilder binary(String name, String[] operators, BiConsumer<String, Context> consumer) {
            Expression expression = new BinaryExpression(name, operators, consumer);
            expressions.put(name, expression);
            return this;
        }
        
        public SyntaxBuilder expression(String name, BiConsumer<String, Context> consumer) {
            Expression expression = new Expression(name, consumer);
            expressions.put(name, expression);
            return this;
        }

//        // while .
//        // if .
//        // not .
//        // is .
//        // for . in .
//        private String buildPattern(String name, String operator) {
//            String base = //"^"
//                    ""
//                    .concat("(?<name>").concat(Pattern.quote(name)).concat("+)")
//                    .concat("(?<expression>.*?)");
//
//            if (StringUtils.hasText(operator)) {
//                base = base
//                        .concat("(?<operator>").concat(Pattern.quote(operator)).concat("+)")
//                        .concat("(?<nested>.*)");
//            }
//
//            return base;
//        }
        
        public Syntax build() {
            Syntax syntax = new Syntax();
            
            syntax.setExpressions(expressions);
            
            syntax.setWsTrim(wsTrim);
            
            syntax.setCommentOpen(commentOpen);
            syntax.setCommentClose(commentClose);
            syntax.setExecuteOpen(executeOpen);
            syntax.setExecuteClose(executeClose);
            syntax.setPrintOpen(printOpen);
            syntax.setPrintClose(printClose);
            
            return syntax;
        }
    }
}
