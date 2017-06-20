package templating;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.StringUtils;

public class Syntax {
    
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
        private String wsTrim;
        
        private String commentOpen;
        private String commentClose;

        private String executeOpen;
        private String executeClose;

        private String printOpen;
        private String printClose;
        
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
        
        public Syntax build() {
            Syntax syntax = new Syntax();
            
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
