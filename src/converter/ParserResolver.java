package converter;

import java.util.Arrays;
import converter.support.BigDecimalParser;
import converter.support.BooleanParser;
import converter.support.ByteParser;
import converter.support.CharacterParser;
import converter.support.DoubleParser;
import converter.support.FloatParser;
import converter.support.IdentityParser;
import converter.support.IntegerParser;
import converter.support.LongParser;
import converter.support.ObjectParser;
import converter.support.ShortParser;
import converter.support.SqlDateParser;
import converter.support.SqlTimestampParser;
import converter.support.StringParser;

public class ParserResolver {

    Converter[] converters;

    public ParserResolver() {
        this.converters = new Converter[]{
            new BigDecimalParser(),
            new BooleanParser(),
            new ByteParser(),
            new CharacterParser(),
            new DoubleParser(),
            new FloatParser(),
            new IdentityParser(),
            new IntegerParser(),
            new LongParser(),
            new ObjectParser(),
            new ShortParser(),
            new SqlDateParser(),
            new SqlTimestampParser(),
            new StringParser()
        };
    }
    
    public boolean support(Object toConvert) {
        return Arrays.stream(this.parsers).anyMatch((Converter converter)->{
            return Arrays.asList(converter.getTypeKeys()).contains(toConvert);
        });
    }
    
    public Converter resolve(Object toConvert) {
        return Arrays.stream(this.parsers).filter((Converter converter)->{
            return Arrays.asList(converter.getTypeKeys()).contains(toConvert);
        }).findFirst().get();
    }
    
}
