package templating;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import templating.token.Token;
import java.util.List;
import java.util.Map;
import static java.util.regex.Pattern.compile;
import static java.util.regex.Pattern.quote;
import templating.expression.Expression;
import templating.extention.Extension;
import templating.extention.core.CoreExtension;
import templating.node.Node;
import templating.node.NodeParser;
import templating.token.TokenParser;
import templating.token.TokenParsers;
import templating.token.TokenStream;
import templating.token.Tokenizer;

public class Engine {

    private final Environment environment;
    
    private final Map<String, Renderer> renderers;
    
    private final Tokenizer tokenizer;

    public Engine(Environment environment, Map<String, Renderer> renderers, Tokenizer tokenizer) {
        this.environment = environment;
        this.renderers = renderers;
        this.tokenizer = tokenizer;
    }

    public Template load(String path) throws Exception {
        Source source = getEnvironment().load(path);
        List<Token> tokens = this.tokenizer.tokenize(source);
        Context context = new Context(tokens, path);
        List<Node> parse = NodeParser.fromTypes().parse(context.getStream());
        return Template.builder().context(context).build();
    }
    
    public void evaluate(Template template) throws Exception {
        Context context = template.getContext();
        TokenStream stream = template.getContext().getStream();
        
        while (!Token.isEOF(stream.current())) {
            Token token = stream.current();
            
        }
    }

    public void render(Template template) throws Exception {
        Context context = template.getContext();
        TokenStream stream = template.getContext().getStream(true);
        while (!Token.isEOF(stream.current())) {
            Token token = stream.current();
            System.out.println(token.getType() + "(" + token.getValue() + ")");
            stream.next();
        }
    }

    public Environment getEnvironment() {
        return environment;
    }
    
    public static EngineBuilder builder() {
        return new EngineBuilder();
    }

    public static class EngineBuilder {
        
        private final static TokenParser n = TokenParser.from("name", compile("^[a-zA-Z_][a-zA-Z0-9_]*"));
        private final static TokenParser s = TokenParsers.string();
        private final static TokenParser p = TokenParsers.punctuation();
        private final static TokenParser nb = TokenParsers.number();
        private final static TokenParser eof = source -> {
                if (source.length() == 0) {
                    return Arrays.asList(Token.EOF());
                }
                throw new Exception(String.format("EOF is not reached rest (%s)%n", source));
            };
        

        Tokenizer.TokenizerBuilder tokenizerBuilder = Tokenizer.builder();
        private Environment environment;
        private List<String> starts = new ArrayList<>();
        private List<Extension> extensions = new ArrayList<>();
        private TokenParser execute;
        private TokenParser comment;
        private TokenParser print;

        public EngineBuilder environment(Environment environment) {
            this.environment = environment;
            return this;
        }
        
        public EngineBuilder extension(Extension extension) {
            this.extensions.add(extension);
            return this;
        }
        
        public EngineBuilder extensions(Extension... extensions) {
            this.extensions.addAll(Arrays.asList(extensions));
            return this;
        }
        
        public EngineBuilder execute(String open, String close) {
            TokenParser starter = TokenParser.from("execute_open", compile(quote(open)));
            TokenParser ender = TokenParser.from("execute_close", compile(quote(close)));
            
            TokenParser expression = this.n.or(this.nb).or(this.p).or(this.s).zeroOrMore();
            
            this.execute = starter
                    .then(n)
                    .then(expression)
                    .then(ender);
            this.starts.add(open);
            return this;
        }

        public EngineBuilder comment(String open, String close) {
            TokenParser starter = TokenParser.from("comment_open", compile(quote(open)));
            TokenParser inner = TokenParser.until("comment", close);
            TokenParser ender = TokenParser.from("comment_close", compile(quote(close)));
            
            this.comment = starter
                    .then(inner.optional())
                    .then(ender);
            this.starts.add(open);
            return this;
        }
        
        public EngineBuilder print(String open, String close) {
            TokenParser starter = TokenParser.from("print_open", compile(quote(open)));
            TokenParser ender = TokenParser.from("print_close", compile(quote(close)));
            
            this.print = starter
                    .then(this.n.or(this.nb).or(this.p).or(this.s).oneOrMore())
                    .then(ender);
            this.starts.add(open);
            return this;
        }
        
        public Engine build() {
            this.extensions.add(new CoreExtension());
            
            TokenParser text = TokenParser.until("text", starts);
            TokenParser principal = print.or(comment).or(execute).or(text).zeroOrMore().then(eof);
            this.tokenizerBuilder.parser(principal);
            Tokenizer tokenizer = this.tokenizerBuilder.build();
            
            Map<String, Renderer> renderers = new HashMap<>();
            this.extensions.forEach(extension -> {
                renderers.putAll(extension.getRenderers());
            });
            
            Engine engine = new Engine(environment, renderers, tokenizer);
            return engine;
        }

    }
}
