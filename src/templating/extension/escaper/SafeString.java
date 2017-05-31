package templating.extension.escaper;

import java.util.Objects;

/**
 * Wrap a string in this to mark the string as safe to ignore by the Escape
 * extension.
 *
 * <p>
 *  <b>Warning:</b> 
 *  The EscaperExtension will never escape a string that is
 *  wrapped with this class.
 * </p>
 *
 */
public class SafeString {

    private final String content;

    public SafeString(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof SafeString && this.content.equals(((SafeString) o).content);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.content);
        return hash;
    }
}
