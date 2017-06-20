package converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConverterDelegate<I, O> {
    
    List<Converter<I, O>> converters;

    public ConverterDelegate() {
        this.converters = new ArrayList<>();
    }

    public O convert(I value, Object type) {
        Optional<Converter<I, O>> first = this.converters.stream().filter((Converter<I, O> converter)->{
            return converter.support(type);
        }).findFirst();
        
        if (first.isPresent()) {
            return first.get().convert(value, type);
        }
        
        return null;      
    }
    
}
