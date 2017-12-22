package demo.event.model;

public class Role {

    private String name;

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Role{" + "name=" + name + '}';
    }
}
