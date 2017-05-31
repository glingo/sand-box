package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.Expression;
import expressionLanguage.model.tree.ArgumentsNode;

public class  FunctionOrMacroInvocationExpression implements Expression<Object> {

    private final String functionName;

    private final ArgumentsNode args;

    public FunctionOrMacroInvocationExpression(String functionName, ArgumentsNode arguments) {
        this.functionName = functionName;
        this.args = arguments;
    }

    @Override
    public Object evaluate(EvaluationContext context) throws Exception {
        return null;
//        Function function = context.getExtensionRegistry().getFunction(functionName);
//        if (function != null) {
//            return applyFunction(context, function, args);
//        }
//        return self.macro(context, functionName, args, false);
    }

//    private Object applyFunction(EvaluationContext context, Function function, ArgumentsNode args) throws Exception {
//        List<Object> arguments = new ArrayList<>();
//
//        Collections.addAll(arguments, args);
//
//        Map<String, Object> namedArguments = args.getArgumentMap(context, function);
//        return function.execute(namedArguments);
//    }

//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

    public String getFunctionName() {
        return functionName;
    }

    public ArgumentsNode getArguments() {
        return args;
    }

//    @Override
//    public int getLineNumber() {
//        return this.lineNumber;
//    }

}
