package component.resource.reference;

import java.io.File;
import component.resource.FileSystem;

public class ResourceReference {
    
    public static final String ANY_TYPE = "any";
    public static final String STRING = "string";
    public static final String FILE = "file";
    public static final String MEMORY = "memory";
    public static final String CLASSPATH = "classpath";

    public static ResourceReference reference(String type, String resource) {
        return new ResourceReference(type, resource);
    }
    
    public static ResourceReference inline(String resource) {
        return reference(STRING, resource);
    }

    public static ResourceReference memory(String name) {
        return reference(MEMORY, name);
    }

    public static ResourceReference file(String path) {
        return reference(FILE, path);
    }

    public static ResourceReference file(File path) {
        return reference(FILE, path.getAbsolutePath());
    }

    public static ResourceReference classpath(String path) {
        return reference(CLASSPATH, path);
    }
 
    private final String type;
    private final String path;

    public ResourceReference(String type, String path) {
        this.type = type;
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public String getPath() {
        return path;
    }
    
    public ResourceReference relativeTo(ResourceReference parent) {
        if (!getType().equals(parent.getType())) {
            // show smthing
            return this;
        }
        
        if (FileSystem.isRelative(getPath())) {
            return new ResourceReference(parent.getType(), FileSystem.resolve(parent.getPath(), getPath()));
        }
        
        return this;
    }

    @Override
    public String toString() {
        return "ResourceReference{" + "type=" + type + ", path=" + path + '}';
    }
}
