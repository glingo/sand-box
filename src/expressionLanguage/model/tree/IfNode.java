package expressionLanguage.model.tree;

import expressionLanguage.expression.Expression;
import expressionLanguage.model.position.Position;
import java.util.List;
import javafx.util.Pair;

public class IfNode extends Node {

    private final List<Pair<Expression<?>, BodyNode>> conditionsWithBodies;

    private final BodyNode elseBody;

    public IfNode(Position position, List<Pair<Expression<?>, BodyNode>> conditionsWithBodies) {
        this(position, conditionsWithBodies, null);
    }

    public IfNode(Position position, List<Pair<Expression<?>, BodyNode>> conditionsWithBodies, BodyNode elseBody) {
        super(position);
        this.conditionsWithBodies = conditionsWithBodies;
        this.elseBody = elseBody;
    }

//    @Override
//    public void render(Template self, Writer writer, EvaluationContext context) throws Exception {
//
//        boolean satisfied = false;
//        for (Pair<Expression<?>, BodyNode> ifStatement : conditionsWithBodies) {
//
//            Expression<?> conditionalExpression = ifStatement.getKey();
//
//            try {
//
//                Object result = conditionalExpression.evaluate(self, context);
//
//                if (result != null) {
//                    try {
//                        satisfied = (Boolean) result;
//                    } catch (ClassCastException ex) {
//                        String msg = String.format("Expected a Boolean in \"if\" statement at line %s in file %s.", getLineNumber(), self.getName());
//                        throw new Exception(msg);
//                    }
//                }
//
//            } catch (RuntimeException ex) {
//                String msg = String.format("Wrong operand(s) type in conditional expression at line %s in file %s.", getLineNumber(), self.getName());
//                throw new Exception(msg);
//            }
//
//            if (satisfied) {
//                ifStatement.getValue().render(self, writer, context);
//                break;
//            }
//        }
//
//        if (!satisfied && elseBody != null) {
//            elseBody.render(self, writer, context);
//        }
//    }

//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

    public List<Pair<Expression<?>, BodyNode>> getConditionsWithBodies() {
        return conditionsWithBodies;
    }

    public BodyNode getElseBody() {
        return elseBody;
    }

}
