package builder;

import builder.Builder;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public abstract class ConditionalBuilder<T> extends Builder<T> {
    
    private boolean ifCond = Boolean.TRUE;

    public ConditionalBuilder(T product) {
        super(product);
    }

//    public ConditionalBuilder(T product) {
//        super(product);
//    }

//    @Override
//    protected Builder<T> with(Consumer<T> setter) {
//        if (!ifCond) {
//           super.with(setter);
//        }
//        return this;
//    }
    
    public Builder<T> require(BooleanSupplier condition) {
        this.ifCond = condition.getAsBoolean();
        return this;
    }

    public Builder<T> endRequire() {
        this.ifCond = true;
        return this;
    }
}
