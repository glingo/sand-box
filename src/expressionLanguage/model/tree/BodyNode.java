package expressionLanguage.model.tree;

import expressionLanguage.model.position.Position;
import expressionLanguage.model.visitor.NodeVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BodyNode extends Node {

    private final List<Node> children;

    private static final List<Class<? extends Node>> nodesToRenderInChild = new ArrayList<>();

    static {
        nodesToRenderInChild.add(SetNode.class);
        nodesToRenderInChild.add(ImportNode.class);
    }

    /**
     * When a template extends a parent template there are very few nodes in the
     * child that should actually get rendered such as set and import. All
     * others should be ignored.
     */
    private boolean onlyRenderInheritanceSafeNodes = false;

    public BodyNode(Position position, List<Node> children) {
        super(position);
        this.children = children;
    }

//    @Override
//    public void render(Template self, Writer writer, EvaluationContext context) throws Exception {
//        for (RenderableNode child : children) {
//            if (onlyRenderInheritanceSafeNodes && context.getHierarchy().getParent() != null) {
//                if (!nodesToRenderInChild.contains(child.getClass())) {
//                    continue;
//                }
//            }
//            child.render(self, writer, context);
//        }
//    }

    @Override
    public void accept(NodeVisitor visitor) {
        super.accept(visitor);
        
        this.children.forEach((node) -> node.accept(visitor));
    }

    public List<Node> getChildren() {
        return children;
    }

    public boolean isOnlyRenderInheritanceSafeNodes() {
        return onlyRenderInheritanceSafeNodes;
    }

    public void setOnlyRenderInheritanceSafeNodes(boolean onlyRenderInheritanceSafeNodes) {
        this.onlyRenderInheritanceSafeNodes = onlyRenderInheritanceSafeNodes;
    }

}
