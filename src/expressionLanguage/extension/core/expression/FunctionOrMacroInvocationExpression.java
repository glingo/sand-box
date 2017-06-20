package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.Expression;
import expressionLanguage.function.Function;
import expressionLanguage.model.tree.ArgumentsNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class  FunctionOrMacroInvocationExpression implements Expression<Object> {

    private final String functionName;

    private final ArgumentsNode args;

    public FunctionOrMacroInvocationExpression(String functionName, ArgumentsNode arguments) {
        this.functionName = functionName;
        this.args = arguments;
    }

    @Override
    public Object evaluate(EvaluationContext context) {
//        return null;
        Function function = context.getExtensionRegistry().getFunction(functionName);
        if (function != null) {
            return applyFunction(context, function, args);
        }
        return context.getHierarchy().get().macro(context, functionName, args, false);
    }

    private Object applyFunction(EvaluationContext context, Function function, ArgumentsNode args) {
        List<Object> arguments = new ArrayList<>();

        Collections.addAll(arguments, args);

        Map<String, Object> namedArguments = args.getArgumentMap(context, function);
        return function.evaluate(context, namedArguments);
    }

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
