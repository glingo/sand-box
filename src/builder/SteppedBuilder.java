package builder;

import builder.BuilderInterface;
import builder.Builder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SteppedBuilder<P> extends Builder<P> {
    
    public final static String AFTER       = "AFTER";
    public final static String BEFORE      = "BEFORE";
    public final static String WITH        = "WITH";
    public final static String VISIT        = "VISIT";
    
    protected Map<String, List<StepInterface<P>>> steps;

    public SteppedBuilder(P product) {
        super(product);
    }

    public BuilderInterface<P> step(String event, StepInterface<P> step) {
        this.steps.computeIfAbsent(event, (e) -> new ArrayList<>()).add(step);
        return this;
    }
    
    public P build() {
        getSteps().stream().forEach((step) -> {
            step.accept(product, this);
        });
        
        return product;
    }
    
    protected List<StepInterface<P>> getSteps(String step) {
        return this.steps.computeIfAbsent(step, (a) -> new ArrayList<>());
    }
    
    protected List<StepInterface<P>> getSteps() {
        return Arrays.asList(
                getSteps(BEFORE), 
                getSteps(WITH), 
                getSteps(AFTER), 
                getSteps(VISIT)).stream()
                .collect(ArrayList::new, List::addAll, List::addAll);
    }
    
//    public BuilderInterface<P> step(String event, StepInterface<P> step);
//    public BuilderInterface<P> with(Consumer<P> setter);
//    public BuilderInterface<P> after(StepInterface<P> step);
//    public BuilderInterface<P> before(StepInterface<P> step);
    
    public BuilderInterface<P> with(Consumer<P> setter) {
        step(WITH, (P product, BuilderInterface<P> builder) -> {
            setter.accept(product);
        });
//        setter.accept(product);
        return this;
    }
    
    public BuilderInterface<P> before(StepInterface step) {
        return step(BEFORE, step);
    }
    
    public BuilderInterface<P> after(StepInterface step) {
        return step(AFTER, step);
    }
    
    public BuilderInterface<P> visit(StepInterface step) {
        return step(VISIT, step);
    }
}
