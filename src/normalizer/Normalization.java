package normalizer;

import java.util.function.Function;

public interface Normalization<O> extends Function<O , O> {

    @Override
    public default O apply(O value) {
        return value;
    }
}
