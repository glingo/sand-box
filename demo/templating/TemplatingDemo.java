package templating;

import java.io.StringWriter;
import java.io.Writer;
import templating.template.Template;

public class TemplatingDemo {
    
    public static void main(String[] args) throws Exception {
        EngineBuilder engineBuilder = new EngineBuilder();
        Engine engine = engineBuilder.build();
        
        Template template = engine.getTemplate("demo/templating/demo.view");
        
        Writer writer = new StringWriter();
        template.evaluate(writer);
        
        System.out.println(writer.toString());
        
    }
}
