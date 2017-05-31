package templating.node;

import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NodeVisitor;
import java.io.IOException;
import java.io.Writer;


/**
 * Represents static text in a template.
 */
public class TextNode extends AbstractRenderableNode {

    /**
     * Most Writers will convert strings to char[] so we might as well store it
     * as a char[] to begin with; small performance optimization.
     */
    private final char[] data;

    public TextNode(String text, int lineNumber) {
        super(lineNumber);

        int length = text.length();
        this.data = new char[text.length()];
        text.getChars(0, length, this.data, 0);
    }

    @Override
    public void render(Template self, Writer writer, EvaluationContext context) throws IOException {
        writer.write(data);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public char[] getData() {
        return data;
    }

}
