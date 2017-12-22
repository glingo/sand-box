package component.expressionLanguage.model.tree;

import component.expressionLanguage.model.position.Position;

/**
 * Represents static text in a template.
 */
public class TextNode extends Node {

    /**
     * Most Writers will convert strings to char[] so we might as well store it
     * as a char[] to begin with; small performance optimization.
     */
    private final char[] data;

    public TextNode(Position position, String text) {
        super(position);

        int length = text.length();
        this.data = new char[text.length()];
        text.getChars(0, length, this.data, 0);
    }

//    @Override
//    public void render(Template self, Writer writer, EvaluationContext context) throws IOException {
//        writer.write(data);
//    }
//
//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

    public char[] getData() {
        return data;
    }

}
