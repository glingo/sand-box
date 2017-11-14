package others;

import java.util.regex.Pattern;

public class Command {
    
//    private static final String COMMAND_PATTERN = "(?<open>%s+)(?<expression>.*)(?<close>%s+)";
    
    private String name;
    private String open;
    private String close;

    public Command(String name, String open, String close) {
        this.name = name;
        this.open = open;
        this.close = close;
    }
}
