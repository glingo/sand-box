package templating.extension.debug;

import templating.node.*;
import templating.expression.*;
import templating.template.Template;
import templating.extension.AbstractNodeVisitor;

public class PrettyPrintNodeVisitor extends AbstractNodeVisitor {

    private final StringBuilder output;
    private int level = 0;

    public PrettyPrintNodeVisitor(Template template) {
        super(template);
        this.output = new StringBuilder();
    }

    private void write(String message) {
        
        for (int i = 0; i < level - 1; i++) {
            output.append("| ");
        }
        
        if (level > 0) {
            output.append("|-");
        }
        
        output.append(message.toUpperCase()).append("\n");
    }

    @Override
    public String toString() {
        return output.toString();
    }

    /**
     * Default method used for unknown nodes such as nodes from a user provided
     * extension.
     * @param node
     */
    @Override
    public void visit(Node node) {
        write("unknown");
        level++;
        super.visit(node);
        level--;
    }

    @Override
    public void visit(BodyNode node) {
        write("body");
        level++;
        super.visit(node);
        level--;
    }

    @Override
    public void visit(IfNode node) {
        write("if");
        level++;
        super.visit(node);
        level--;
    }

    @Override
    public void visit(ForNode node) {
        write("for");
        level++;
        super.visit(node);
        level--;
    }

    public void visit(BinaryExpression<?> node) {
        write("binary");
        level++;
        super.visit(node);
        level--;
    }

    public void visit(UnaryExpression node) {
        write("unary");
        level++;
        super.visit(node);
        level--;
    }

    public void visit(ContextVariableExpression node) {
        write(String.format("context variable [%s]", node.getName()));
        level++;
        super.visit(node);
        level--;
    }

    public void visit(FilterInvocationExpression node) {
        write("filter");
        level++;
        super.visit(node);
        level--;
    }

    public void visit(FunctionOrMacroInvocationExpression node) {
        write("function or macro");
        level++;
        super.visit(node);
        level--;
    }

    public void visit(GetAttributeExpression node) {
        write("get attribute");
        level++;
        super.visit(node);
        level--;
    }

    @Override
    public void visit(NamedArgumentNode node) {
        write("named argument");
        level++;
        super.visit(node);
        level--;
    }

    @Override
    public void visit(ArgumentsNode node) {
        write("named arguments");
        level++;
        super.visit(node);
        level--;
    }

    public void visit(ParentFunctionExpression node) {
        write("parent function");
        level++;
        super.visit(node);
        level--;
    }

    public void visit(TernaryExpression node) {
        write("ternary");
        level++;
        super.visit(node);
        level--;
    }

    public void visit(TestInvocationExpression node) {
        write("test");
        level++;
        super.visit(node);
        level--;
    }

    @Override
    public void visit(BlockNode node) {
        write(String.format("block [%s]", node.getName()));
        level++;
        super.visit(node);
        level--;
    }

    @Override
    public void visit(FlushNode node) {
        write("flush");
        level++;
        super.visit(node);
        level--;
    }

    @Override
    public void visit(ImportNode node) {
        write("import");
        level++;
        super.visit(node);
        level--;
    }

    @Override
    public void visit(IncludeNode node) {
        write("include");
        level++;
        super.visit(node);
        level--;
    }

    @Override
    public void visit(ParallelNode node) {
        write("parallel");
        level++;
        super.visit(node);
        level--;
    }

    @Override
    public void visit(PrintNode node) {
        write("print");
        level++;
        super.visit(node);
        level--;
    }

    @Override
    public void visit(RootNode node) {
        write("root");
        level++;
        super.visit(node);
        level--;
    }

    @Override
    public void visit(SetNode node) {
        write("set");
        level++;
        super.visit(node);
        level--;
    }

    @Override
    public void visit(TextNode node) {
        String text = new String(node.getData());
        String preview = text.length() > 10 ? text.substring(0, 10) + "..." : text;
        write(String.format("text [%s]", preview));
        level++;
        super.visit(node);
        level--;
    }
}
