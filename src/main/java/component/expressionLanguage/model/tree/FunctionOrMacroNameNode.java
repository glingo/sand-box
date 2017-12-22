package component.expressionLanguage.model.tree;

import component.expressionLanguage.EvaluationContext;
import component.expressionLanguage.expression.Expression;
import component.expressionLanguage.model.position.Position;

public class FunctionOrMacroNameNode extends Node implements Expression<String> {

    private final String name;

    public FunctionOrMacroNameNode(Position position, String name) {
        super(position);
        this.name = name;
    }

    @Override
    public String evaluate(EvaluationContext context) {
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
