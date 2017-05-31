package templating.node;

import templating.extension.NodeVisitor;

public interface Node {

    void accept(NodeVisitor visitor);

}
