package expressionLanguage.model.tree;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.Expression;
import expressionLanguage.function.Function;
import expressionLanguage.model.position.Position;
import expressionLanguage.scope.ScopeChain;
import java.util.Map;

public class MacroNode extends Node {

    private final String name;

    private final ArgumentsNode args;

    private final BodyNode body;

    public MacroNode(Position position, String name, ArgumentsNode args, BodyNode body) {
        super(position);
        this.name = name;
        this.args = args;
        this.body = body;
    }

//    @Override
//    public void render(Template self, Writer writer, EvaluationContext context) throws Exception {
//        // do nothing
//    }
//
//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

    public Function getMacro() {
        return Function.builder()
                .named(name)
                .evaluation((EvaluationContext context, Map<String, Object> macroArgs) -> {
                    ScopeChain scopeChain = context.getScopeChain();
                    scopeChain.pushLocalScope();
                    getArgs().getArgs().stream().forEach((arg) -> {
                        Expression<?> valueExpression = arg.getValue();
                        if (valueExpression == null) {
                            scopeChain.put(arg.getName(), null);
                        } else {
                            scopeChain.put(arg.getName(), valueExpression.evaluate(context));
                        }
                    });
                    
                    // scope for user provided arguments
                    scopeChain.pushScope(macroArgs);

//                    getBody().render(self, writer, context);

                    scopeChain.popScope(); // user arguments
                    scopeChain.popScope(); // default arguments

                    return "";
                })
                .build();
                
//                new Macro() {
//
//            @Override
//            public List<String> getArgumentNames() {
//                List<String> names = new ArrayList<>();
//                getArgs().getNamedArgs().stream().forEach((arg) -> {
//                    names.add(arg.getName());
//                });
//                return names;
//            }
//
//            @Override
//            public String getName() {
//                return name;
//            }
//
//            @Override
//            public String call(EvaluationContext context, Map<String, Object> macroArgs) throws Exception {
//                Writer writer = new StringWriter();
//                ScopeChain scopeChain = context.getScopeChain();
//
//                // scope for default arguments
//                scopeChain.pushLocalScope();
//                for (NamedArgumentNode arg : getArgs().getNamedArgs()) {
//                    Expression<?> valueExpression = arg.getValueExpression();
//                    if (valueExpression == null) {
//                        scopeChain.put(arg.getName(), null);
//                    } else {
//                        scopeChain.put(arg.getName(), arg.getValueExpression().evaluate(context));
//                    }
//                }
//
//                // scope for user provided arguments
//                scopeChain.pushScope(macroArgs);
//                
////                getBody().render(self, writer, context);
//
//                scopeChain.popScope(); // user arguments
//                scopeChain.popScope(); // default arguments
//
//                return writer.toString();
//            }

//        };
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
