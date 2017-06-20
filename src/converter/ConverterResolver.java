package converter;

import java.util.Arrays;
import converter.support.*;

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
            return Arrays.asList(converter.getTypes()).contains(toConvert);
        });
    }
    
    public Converter resolve(Object toConvert) {
        return Arrays.stream(this.converters).filter((Converter converter)->{
            return Arrays.asList(converter.getTypes()).contains(toConvert);
        }).findFirst().get();
    }
    
}
