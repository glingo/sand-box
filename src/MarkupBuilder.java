

import node.builder.NodeBuilder;
import java.util.function.Supplier;

public abstract class MarkupBuilder extends NodeBuilder {

    boolean rootElement = false;

    public MarkupBuilder() {
        super();
    }

    protected String spacer(int len){
       StringBuilder b = new StringBuilder();
        for (int i = 0; i < len; i++) {
             b.append('\t');
        }
        return b.toString();
    }

    public MarkupBuilder element(String name) {
        this.child(name);
        return this;
    }

    public MarkupBuilder content(String name) {
        this.child(name);
        return this;
    }
}
