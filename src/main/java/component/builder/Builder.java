package component.builder;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Builder<P> implements BuilderInterface<P> {
    
    protected P product;

    public Builder(P product) {
        this.product = product;
    }
    
    public static <P> BuilderInterface<P> of(Class<P> clazz) throws Exception {
        return of(clazz.newInstance());
    }
    
    public static <P> BuilderInterface<P> of(Supplier<P> supplier) {
        return of(supplier.get());
    }
    
    public static <P> BuilderInterface<P> of(P product) {
        return new Builder(product);
    }

    @Override
    public P build(Function<P, P> build) {
        return build.apply(product);
    }
    
    @Override
    public BuilderInterface consume(Consumer<P> visitor) {
        visitor.accept(product);
        return this;
    }

    public P getProduct() {
        return product;
    }

}
