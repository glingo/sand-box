package expressionLanguage.lexer;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;
import static java.util.regex.Pattern.quote;

public class Syntax {

    private static final String POSSIBLE_NEW_LINE = "(\r\n|\n\r|\r|\n|\u0085|\u2028|\u2029)?";
    
    private String commentOpen;

    private String commentClose;

    private String executeOpen;

    private String executeClose;

    private String printOpen;

    private String printClose;

    private String wsTrim;

    private Pattern regexPrintClose;

    private Pattern regexExecuteClose;

    private Pattern regexCommentClose;

    private Pattern regexStartDelimiters;

    private Pattern regexLeadingWhitespaceTrim;

    private Pattern regexTrailingWhitespaceTrim;
    
    protected Pattern regexOperators;

    public Pattern getRegexPrintClose() {
        if (this.regexPrintClose == null) {
            this.regexPrintClose = compile(
                    "^\\s*" + quote(wsTrim) + 
                    "?" + quote(printClose) + 
                    POSSIBLE_NEW_LINE);
        }
        return regexPrintClose;
    }

    public Pattern getRegexExecuteClose() {
        if (this.regexExecuteClose == null) {
            this.regexExecuteClose = compile(
                    "^\\s*" + quote(wsTrim) + 
                    "?" + quote(executeClose) + 
                    POSSIBLE_NEW_LINE);
        }
        return regexExecuteClose;
    }

    public Pattern getRegexCommentClose() {
        if (this.regexCommentClose == null) {
            this.regexCommentClose = compile(
                quote(commentClose) + 
                POSSIBLE_NEW_LINE);
        }
        return regexCommentClose;
    }

    public Pattern getRegexStartDelimiters() {
        if (regexStartDelimiters == null) {
             this.regexStartDelimiters = compile(
                    quote(printOpen) + "|" + 
                    quote(executeOpen) + "|" + 
                    quote(commentOpen));
        }
        return regexStartDelimiters;
    }

    public Pattern getRegexLeadingWhitespaceTrim() {
        if (regexLeadingWhitespaceTrim == null) {
            this.regexLeadingWhitespaceTrim = compile(quote(wsTrim) + "\\s+");
        }
        return regexLeadingWhitespaceTrim;
    }

    public Pattern getRegexTrailingWhitespaceTrim() {
        if (regexTrailingWhitespaceTrim == null) {
            this.regexTrailingWhitespaceTrim = compile(
                    "^\\s*" + quote(wsTrim) + 
                    "(" + quote(printClose) + 
                        "|" + quote(executeClose) + 
                        "|" + quote(commentClose) + 
                    ")");
        }
        return regexTrailingWhitespaceTrim;
    }

    public String getCommentOpen() {
        return commentOpen;
    }

    public void setCommentOpen(String commentOpen) {
        this.commentOpen = commentOpen;
    }

    public String getCommentClose() {
        return commentClose;
    }

    public void setCommentClose(String commentClose) {
        this.commentClose = commentClose;
    }

    public String getExecuteOpen() {
        return executeOpen;
    }

    public void setExecuteOpen(String executeOpen) {
        this.executeOpen = executeOpen;
    }

    public String getExecuteClose() {
        return executeClose;
    }

    public void setExecuteClose(String executeClose) {
        this.executeClose = executeClose;
    }

    public String getPrintOpen() {
        return printOpen;
    }

    public void setPrintOpen(String printOpen) {
        this.printOpen = printOpen;
    }

    public String getPrintClose() {
        return printClose;
    }

    public void setPrintClose(String printClose) {
        this.printClose = printClose;
    }

    public String getWsTrim() {
        return wsTrim;
    }

    public void setWsTrim(String wsTrim) {
        this.wsTrim = wsTrim;
    }

    public static SyntaxBuilder builder() {
        return new SyntaxBuilder();
    }

    public static class SyntaxBuilder {

        private String commentOpen;

        private String commentClose;

        private String executeOpen;

        private String executeClose;

        private String printOpen;

        private String printClose;

        private String wsTrim;

        public SyntaxBuilder comment(String open, String close) {
            this.commentOpen = open;
            this.commentClose = close;
            return this;
        }

        public SyntaxBuilder commentOpen(String commentOpen) {
            this.commentOpen = commentOpen;
            return this;
        }

        public SyntaxBuilder commentClose(String commentClose) {
            this.commentClose = commentClose;
            return this;
        }

        public SyntaxBuilder execute(String open, String close) {
            this.executeOpen = open;
            this.executeClose = close;
            return this;
        }

        public SyntaxBuilder executeOpen(String executeOpen) {
            this.executeOpen = executeOpen;
            return this;
        }

        public SyntaxBuilder executeClose(String executeClose) {
            this.executeClose = executeClose;
            return this;
        }

        public SyntaxBuilder print(String open, String close) {
            this.printOpen = open;
            this.printClose = close;
            return this;
        }
        
        public SyntaxBuilder printClose(String printClose) {
            this.printClose = printClose;
            return this;
        }

        public SyntaxBuilder printOpen(String printOpen) {
            this.printOpen = printOpen;
            return this;
        }

        public SyntaxBuilder wsTrim(String wsTrim) {
            this.wsTrim = wsTrim;
            return this;
        }

        public Syntax build() {
            Syntax syntax = new Syntax();

            syntax.setCommentClose(commentClose);
            syntax.setCommentOpen(commentOpen);

            syntax.setExecuteClose(executeClose);
            syntax.setExecuteOpen(executeOpen);

            syntax.setPrintClose(printClose);
            syntax.setPrintOpen(printOpen);

            syntax.setWsTrim(wsTrim);

            return syntax;
        }
    }

}
