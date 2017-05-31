package templating.extension;

import templating.template.Template;
import templating.node.*;

/**
 * A base node visitor that can be extended for the sake of using it's
 * navigational abilities.
 *
 */
public class AbstractNodeVisitor implements NodeVisitor {

    private final Template template;

    public AbstractNodeVisitor(Template template) {
        this.template = template;
    }

    /**
     * Default method used for unknown nodes such as nodes from a user provided
     * extension.
     */
    @Override
    public void visit(Node node) {
    }

    /*
     * OVERLOADED NODES (keep alphabetized)
     */
    @Override
    public void visit(ArgumentsNode node) {
        if (node.getNamedArgs() != null) {
            node.getNamedArgs().stream().forEach((arg) -> {
                arg.accept(this);
            });
        }
        if (node.getPositionalArgs() != null) {
            node.getPositionalArgs().stream().forEach((arg) -> {
                arg.accept(this);
            });
        }
    }

    @Override
    public void visit(AutoEscapeNode node) {
        node.getBody().accept(this);
    }

    @Override
    public void visit(BlockNode node) {
        node.getBody().accept(this);
    }

    @Override
    public void visit(BodyNode node) {
        node.getChildren().stream().forEach((child) -> {
            child.accept(this);
        });
    }

    @Override
    public void visit(ExtendsNode node) {
        node.getParentExpression().accept(this);
    }

    @Override
    public void visit(FlushNode node) {

    }

    @Override
    public void visit(ForNode node) {
        node.getIterable().accept(this);
        node.getBody().accept(this);
        if (node.getElseBody() != null) {
            node.getElseBody().accept(this);
        }
    }

    @Override
    public void visit(IfNode node) {
        node.getConditionsWithBodies().stream().map((pairs) -> {
            pairs.getKey().accept(this);
            return pairs;
        }).forEach((pairs) -> {
            pairs.getValue().accept(this);
        });
        if (node.getElseBody() != null) {
            node.getElseBody().accept(this);
        }
    }

    @Override
    public void visit(ImportNode node) {
        node.getImportExpression().accept(this);
    }

    @Override
    public void visit(IncludeNode node) {
        node.getIncludeExpression().accept(this);
    }

    @Override
    public void visit(MacroNode node) {
        node.getBody().accept(this);
        node.getArgs().accept(this);
    }

    @Override
    public void visit(NamedArgumentNode node) {
        if (node.getValueExpression() != null) {
            node.getValueExpression().accept(this);
        }
    }

    @Override
    public void visit(ParallelNode node) {
        node.getBody().accept(this);
    }

    @Override
    public void visit(PositionalArgumentNode node) {
        node.getValueExpression().accept(this);
    }

    @Override
    public void visit(PrintNode node) {
        node.getExpression().accept(this);
    }

    @Override
    public void visit(RootNode node) {
        node.getBody().accept(this);
    }

    @Override
    public void visit(SetNode node) {
        node.getValue().accept(this);
    }

    @Override
    public void visit(TextNode node) {

    }

    protected Template getTemplate() {
        return this.template;
    }

}
