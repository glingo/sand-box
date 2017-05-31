package templating.expression;

import templating.EvaluationContext;
import templating.Hierarchy;
import templating.template.Template;
import templating.extension.NodeVisitor;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class ParentFunctionExpression implements Expression<String> {

    private final String blockName;

    private final int lineNumber;

    public ParentFunctionExpression(String blockName, int lineNumber) {
        this.blockName = blockName;
        this.lineNumber = lineNumber;
    }

    @Override
    public String evaluate(Template self, EvaluationContext context) throws Exception {
        Writer writer = new StringWriter();
        try {
            Hierarchy hierarchy = context.getHierarchy();
            if (hierarchy.getParent() == null) {
                String msg = String.format("Can not use parent function if template does not extend another template at line %s in file %s.", lineNumber, self.getName());
                throw new Exception(msg);
            }
            Template parent = hierarchy.getParent();

            hierarchy.ascend();
            parent.block(writer, context, blockName, true);
            hierarchy.descend();
        } catch (IOException e) {
            String msg = String.format("Could not render block [%s] at line %s in file %s.", blockName, this.getLineNumber(), self.getName());
            throw new Exception(msg);
        }
        return writer.toString();
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int getLineNumber() {
        return this.lineNumber;
    }

}
