package component.expressionLanguage.function;

import component.expressionLanguage.EvaluationContext;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public interface Function {
    
    String getName();
    List<String> getArgumentNames();
    Object evaluate(EvaluationContext context, Map<String, Object> args);
    
    public static FunctionBuilder builder() {
        return new FunctionBuilder();
    }
    
    public static class FunctionBuilder {
        
        private String name;
        
        private BiFunction<EvaluationContext, Map<String, Object>, String> runnable;
        
        private List<String> argumentsNames;
        
        public FunctionBuilder named(String name) {
            this.name = name;
            return this;
        }
        
        public FunctionBuilder with(String argName) {
            this.argumentsNames.add(argName);
            return this;
        }
        
        public FunctionBuilder evaluation(BiFunction<EvaluationContext, Map<String, Object>, String> runnable) {
            this.runnable = runnable;
            return this;
        }
        
        public Function build() {
            return new Function() {
                @Override
                public String getName() {
                    return name;
                }
                
                @Override
                public String evaluate(EvaluationContext context, Map<String, Object> args) {
                    return runnable.apply(context, args);
                }

                @Override
                public List<String> getArgumentNames() {
                   return argumentsNames;
                }
            };
        }
    }
}
