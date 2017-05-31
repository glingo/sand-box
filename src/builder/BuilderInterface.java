package builder;

import java.util.function.Consumer;
import java.util.function.Function;

public interface BuilderInterface<P> {

    default P build() {
        return build(Function.identity());
    };
    public P build(Function<P, P> build);
    
//    public P getProduct();
    public BuilderInterface<P> consume(Consumer<P> visitor);
    
}
