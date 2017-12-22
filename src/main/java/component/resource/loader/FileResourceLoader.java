package component.resource.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import component.resource.exceptions.ResourceNotFoundException;

public class FileResourceLoader implements ResourceLoader {
    
    public static FileResourceLoader instance(String path) {
        return instance(new File(path));
    }
    
    public static FileResourceLoader instance(File file) {
        return new FileResourceLoader(file);
    }
    
    public static FileResourceLoader instance() {
        return instance(new File("/"));
    }

    private final File baseDirectory;

    public FileResourceLoader(File baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    @Override
    public InputStream load(String path) {
        try {
            return new FileInputStream(file(path));
        } catch (FileNotFoundException e) {
            throw new ResourceNotFoundException(e);
        }
    }

    @Override
    public boolean exists(String path) {
        return file(path).exists();
    }

    @Override
    public Optional<URL> toUrl(String path) {
        try {
            return Optional.of(file(path).toURI().toURL());
        } catch (MalformedURLException e) {
            return Optional.empty();
        }
    }

    private File file(String path) {
        File file = new File(path);
        if (file.isAbsolute()) {
            return file;
        } else {
            return new File(baseDirectory, path);
        }
    }
}
