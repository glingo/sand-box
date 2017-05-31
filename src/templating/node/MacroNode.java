package templating.node;

import templating.expression.Expression;
import templating.EvaluationContext;
import templating.template.Macro;
import templating.scope.ScopeChain;
import templating.template.Template;
import templating.extension.NodeVisitor;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MacroNode extends AbstractRenderableNode {

    private final String name;

    private final ArgumentsNode args;

    private final BodyNode body;

    public MacroNode(String name, ArgumentsNode args, BodyNode body) {
        this.name = name;
        this.args = args;
        this.body = body;
    }

    @Override
    public void render(Template self, Writer writer, EvaluationContext context) throws Exception {
        // do nothing
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public Macro getMacro() {
        return new Macro() {

            @Override
            public List<String> getArgumentNames() {
                List<String> names = new ArrayList<>();
                getArgs().getNamedArgs().stream().forEach((arg) -> {
                    names.add(arg.getName());
                });
                return names;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String call(Template self, EvaluationContext context, Map<String, Object> macroArgs) throws Exception {
                Writer writer = new StringWriter();
                ScopeChain scopeChain = context.getScopeChain();

                // scope for default arguments
                scopeChain.pushLocalScope();
                for (NamedArgumentNode arg : getArgs().getNamedArgs()) {
                    Expression<?> valueExpression = arg.getValueExpression();
                    if (valueExpression == null) {
                        scopeChain.put(arg.getName(), null);
                    } else {
                        scopeChain.put(arg.getName(), arg.getValueExpression().evaluate(self, context));
                    }
                }

                // scope for user provided arguments
                scopeChain.pushScope(macroArgs);

//                try {
                    getBody().render(self, writer, context);
//                } catch (IOException e) {
//                    throw new RuntimeException("Could not evaluate macro [" + name + "]", e);
//                }

                scopeChain.popScope(); // user arguments
                scopeChain.popScope(); // default arguments

                return writer.toString();
            }

        };
    }

    public BodyNode getBody() {
        return body;
    }

    public ArgumentsNode getArgs() {
        return args;
    }

    public String getName() {
        return name;
    }

}
