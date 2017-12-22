package component.expressionLanguage.model.tree;

import component.expressionLanguage.model.position.Position;
import java.util.logging.Logger;

public class ParallelNode extends Node {

    private static final Logger LOGGER = Logger.getLogger(ParallelNode.class.getName());

    private final BodyNode body;

    /**
     * If the user is using the parallel tag but doesn't provide an
     * ExecutorService we will warn them that this tag will essentially be
     * ignored but it's important that we only warn them once because this tag
     * may show up in a loop.
     */
    private boolean hasWarnedAboutNonExistingExecutorService = false;

    public ParallelNode(Position position, BodyNode body) {
        super(position);
        this.body = body;
    }

//    @Override
//    public void render(final Template self, Writer writer, final EvaluationContext context) throws Exception {
//
//        ExecutorService es = context.getExecutorService();
//
//        if (es == null) {
//
//            if (!hasWarnedAboutNonExistingExecutorService) {
//                LOGGER.info(String.format(
//                        "The parallel tag was used [%s:%d] but no ExecutorService was provided. The parallel tag will be ignored "
//                                + "and it's contents will be rendered in sequence with the rest of the template.",
//                        self.getName(), getLineNumber()));
//                hasWarnedAboutNonExistingExecutorService = true;
//            }
//
//            /*
//             * If user did not provide an ExecutorService, we simply ignore the
//             * parallel tag and render it's contents like we normally would.
//             */
//            body.render(self, writer, context);
//            
//        } else {
//
//            final EvaluationContext contextCopy = context.threadSafeCopy(self);
//
//            final StringWriter newStringWriter = new StringWriter();
//            final Writer newFutureWriter = new FutureWriter(newStringWriter);
//
//            Future<String> future = es.submit(() -> {
//                body.render(self, newFutureWriter, contextCopy);
//                newFutureWriter.flush();
//                newFutureWriter.close();
//                return newStringWriter.toString();
//            });
//            
//            ((FutureWriter) writer).enqueue(future);
//        }
//    }
//
//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

    public BodyNode getBody() {
        return body;
    }
}
