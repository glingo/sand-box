package converter;

import java.util.Arrays;
import converter.support.*;
import java.util.Optional;

public class ConverterResolver {

    Converter[] converters;

    public ConverterResolver() {
        this.converters = new Converter[]{
            new BigDecimalConverter(),
            new BooleanConverter(),
            new ByteConverter(),
            new CharacterConverter(),
            new DoubleConverter(),
            new FloatConverter(),
            new IdentityConverter(),
            new IntegerConverter(),
            new LongConverter(),
            new ObjectConverter(),
            new ShortConverter(),
            new SqlDateConverter(),
            new SqlTimestampConverter(),
            new StringConverter()
        };
    }
    
    public boolean support(Object toConvert) {
        return Arrays.stream(this.converters).anyMatch((Converter converter)->{
            return converter.getTypes().contains(toConvert);
        });
    }
    
    public Converter resolve(Object toConvert) {
        Optional<Converter> converter = Arrays.stream(this.converters)
                .filter((Converter c) -> {
            return c.getTypes().contains(toConvert);
        }).findFirst();
        
        if (!converter.isPresent()) {
            return null;
        }
        
        return converter.get();
    }
    
}
