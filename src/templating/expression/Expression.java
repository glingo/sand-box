package templating.expression;

import templating.EvaluationContext;
import templating.node.Node;
import templating.template.Template;

public interface Expression<T> extends Node {

    T evaluate(Template self, EvaluationContext context) throws Exception;

    /**
     * Returns the line number on which the expression is defined on.
     *
     * @return the line number on which the expression is defined on.
     */
    int getLineNumber();
}
