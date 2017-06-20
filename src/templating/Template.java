package templating;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Template {
    
    private String name;
    
    private Template extend;
    
    private List<Template> imports;
    
    private List<Token> tokens;
    
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

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }
    
    @Override
    public String toString() {
        return "Template{" + "name=" + name + ", extend=" + extend + ", imports=" + imports + '}';
    }
    
    public static TemplateBuilder builder() {
        return new TemplateBuilder();
    }
    
    public static class TemplateBuilder {
        
        private String name;
        
        private Template extend;
        
        private List<Template> imports = new ArrayList<>();
        
        private List<Token> tokens = new ArrayList<>();
    
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
        
        public TemplateBuilder token(Token token) {
            this.tokens.add(token);
            return this;
        }
        
        public TemplateBuilder tokens(List<Token> tokens) {
            this.tokens = tokens;
            return this;
        }
        
        public Template build() {
            Template template = new Template();
            
            template.setName(this.name);
            template.setExtend(extend);
            template.setImports(imports);
            template.setTokens(tokens);
            
            return template;
        }
    }
}
