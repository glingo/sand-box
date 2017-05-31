package templating.extension.core;

import templating.extension.Filter;
import java.util.List;
import java.util.Map;

public class TitleFilter implements Filter {

    @Override
    public List<String> getArgumentNames() {
        return null;
    }

    @Override
    public Object apply(Object input, Map<String, Object> args) {
        if (input == null) {
            return null;
        }
        String value = (String) input;

        if (value.length() == 0) {
            return value;
        }

        StringBuilder result = new StringBuilder();

        boolean capitalizeNextCharacter = true;

        for (char c : value.toCharArray()) {
            if (Character.isWhitespace(c)) {
                capitalizeNextCharacter = true;
            } else if (capitalizeNextCharacter) {
                c = Character.toTitleCase(c);
                capitalizeNextCharacter = false;
            }
            result.append(c);
        }

        return result.toString();
    }

}
