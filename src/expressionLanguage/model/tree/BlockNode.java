package expressionLanguage.model.tree;

import expressionLanguage.EvaluationContext;
import expressionLanguage.model.position.Position;
import expressionLanguage.model.template.Block;
import expressionLanguage.model.template.Template;
import java.io.IOException;

public class BlockNode extends Node {

    private final BodyNode body;

    private String name;

    public BlockNode(Position position, String name) {
        this(position, name, null);
    }

    public BlockNode(Position position, String name, BodyNode body) {
        super(position);
        this.body = body;
        this.name = name;
    }

//    @Override
//    public void render(final Template self, Writer writer, EvaluationContext context) throws Exception {
//        self.block(writer, context, name, false);
//    }
//
//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

    public Block getBlock() {
        return new Block() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public void evaluate(Template self, EvaluationContext context) {
//                body.render(self, writer, context);
            }
        };
    }

    public BodyNode getBody() {
        return body;
    }

    public String getName() {
        return name;
    }
    
}
