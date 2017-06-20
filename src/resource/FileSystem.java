package resource;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.InvalidPathException;
import resource.exceptions.ResourceException;

public class FileSystem {
    
    public static final String ROOT_PATH = "/";
    private final File baseDirectory;

    public FileSystem() {
        this.baseDirectory = new File(ROOT_PATH);
    }
    
    public FileSystem(File baseDirectory) {
        this.baseDirectory = baseDirectory;
    }
    
    private File file(String path) {
        File file = new File(path);
        if (file.isAbsolute()) {
            return file;
        } else {
            return new File(baseDirectory, path);
        }
    }
    
    public static boolean isRelative(String path) {
        return !path.startsWith(ROOT_PATH);
    }
    
    public static String resolve(String parent, String child) {
        try {
            return new URI(String.format("%s/../%s", parent, child)).normalize().toString();
        } catch (InvalidPathException | URISyntaxException e) {
            throw new ResourceException("Invalid path", e);
        }
    }
    
}
