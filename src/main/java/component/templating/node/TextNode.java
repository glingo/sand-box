package component.templating.node;

public class TextNode implements Node{

    private String value;

    public TextNode(String value) {
        this.value = value;
    }
    
    @Override
    public void accept(NodeVisitor visitor) {
        System.out.format("Text : %s%n", this.value);
    }
    
}
