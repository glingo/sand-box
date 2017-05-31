package converter;

import java.util.List;

public interface Converter<O> {
    
    List<Object> getTypes();
    
    default boolean support(Object type) {
        return getTypes().contains(type);
    }
    
    O convert(Object parsed);
    
    default O convert(Object parsed, Object type) {
        if (support(type)) {
            return convert(parsed);
        }
        
        return null;
    }
}
