package normalizer;

import java.util.List;
import java.util.function.Function;

public abstract class Normalizer<O> implements NormalizerInterface<O> {
    
    protected Normalization<O> preNormalization;
    protected List<Normalization<O>> normalizations;
    
    public Normalizer() {
        super();
    }
    
    public Normalizer(Normalization<O> preNormalizationClosures) {
        this();
        this.preNormalization = preNormalizationClosures;
    }

    public Normalizer(Normalization<O> preNormalization, 
            List<Normalization<O>> normalizations) {
        this(preNormalization);
        this.normalizations = normalizations;
    }
    
    @Override
    public O normalize(O value) {
        if(this.preNormalization == null) {
            return value;
        }
        
        value = this.preNormalization.apply(value);
        
        if(this.normalizations == null) {
            return value;
        }
        
        for (Function<O, O> closure : this.normalizations) {
            value = closure.apply(value);
        }
        
        return value;
    }
    
}
