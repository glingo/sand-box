package templating;

import templating.token.TokenStream;
import templating.token.Token;
import java.util.ArrayList;
import java.util.List;

public class Template {
    
    private Context context;
    
    private String name;
    
    private Template extend;
    
    private List<Template> imports;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Template getExtend() {
        return extend;
    }

    public List<Template> getImports() {
        return imports;
    }

    public void setExtend(Template extend) {
        this.extend = extend;
    }

    public void setImports(List<Template> imports) {
        this.imports = imports;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public static TemplateBuilder builder() {
        return new TemplateBuilder();
    }
    
    public static class TemplateBuilder {
        
        private String name;
        
        private Template extend;
        
        private List<Template> imports = new ArrayList<>();
        
        private Context context;
    
        public TemplateBuilder named(String name) {
            this.name = name;
            return this;
        }
        
        public TemplateBuilder importTemplate(Template template) {
            this.imports.add(template);
            return this;
        }
        
        public TemplateBuilder extend(Template template) {
            this.extend = template;
            return this;
        }
        
        public TemplateBuilder context(Context context) {
            this.context = context;
            return this;
        }
        
        public Template build() {
            Template template = new Template();
            
            template.setName(this.name);
            template.setExtend(extend);
            template.setImports(imports);
            template.setContext(context);
            return template;
        }
    }
}
