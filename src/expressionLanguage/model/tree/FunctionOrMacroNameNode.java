package expressionLanguage.model.tree;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.Expression;
import expressionLanguage.model.position.Position;

public class FunctionOrMacroNameNode extends Node implements Expression<String> {

    private final String name;

    public FunctionOrMacroNameNode(Position position, String name) {
        super(position);
        this.name = name;
    }

    @Override
    public String evaluate(EvaluationContext context) throws Exception {
        throw new UnsupportedOperationException();
    }
//
//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

    public String getName() {
        return name;
    }

//    @Override
//    public int getLineNumber() {
//        return this.lineNumber;
//    }

}
