package converter;

import java.util.Collections;
import java.util.List;


@FunctionalInterface
public interface Converter<I, O> {
    
    default List<Object> getTypes(){
        return Collections.EMPTY_LIST;
    };
//    
    default boolean support(Object type) {
        return getTypes().contains(type);
    }
    
    O convert(I value);
    
    default O convert(I value, Object type) {
        if (support(type)) {
            return convert(value);
        }
        
        return null;
    }
}
