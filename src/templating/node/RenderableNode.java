package templating.node;

import templating.EvaluationContext;
import templating.template.Template;
import java.io.Writer;

public interface RenderableNode extends Node {

    void render(Template self, Writer writer, EvaluationContext context) throws Exception;
}
