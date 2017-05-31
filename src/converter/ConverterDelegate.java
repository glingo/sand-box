package converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConverterDelegate implements Converter {
    
    List<Converter> converters;

    public ConverterDelegate() {
        this.converters = new ArrayList<>();
    }
    
    @Override
    public boolean support(Object toConvert) {
        return this.converters.stream()
                .map((converter) -> converter.getTypes())
                .collect(ArrayList::new, List::addAll, List::addAll)
                .contains(toConvert);
    }

    @Override
    public Object convert(Object toConvert, Object type) {
        Optional<Converter> first = this.converters.stream().filter((Converter converter)->{
            return converter.support(type);
        }).findFirst();
        
        if (first.isPresent()) {
            return first.get().convert(toConvert, type);
        }
        
        return null;      
    }

    @Override
    public List<Object> getTypes() {
        return this.converters.stream()
                .map((converter) -> converter.getTypes())
                .collect(ArrayList::new, List::addAll, List::addAll);
    }
}
