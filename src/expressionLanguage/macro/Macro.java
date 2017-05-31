package expressionLanguage.macro;

import expressionLanguage.EvaluationContext;
import expressionLanguage.NamedArguments;
import java.util.Map;

public interface Macro extends NamedArguments {

    String getName();

    String call(EvaluationContext context, Map<String, Object> args) throws Exception;
}
