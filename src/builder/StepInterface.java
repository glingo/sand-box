package builder;

import builder.BuilderInterface;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface StepInterface<P> extends BiConsumer<P, BuilderInterface<P>> {

    @Override
    public void accept(P product, BuilderInterface<P> builder);
    
}
