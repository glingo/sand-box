package templating.loader;

import com.marvin.component.util.PathUtils;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Uses a classloader to find templates located on the classpath.
 *
 */
public class ClasspathLoader implements LoaderInterface<String> {

    private static final Logger logger = Logger.getLogger(ClasspathLoader.class.getName());

    private String prefix;

    private String suffix;

    private String charset = "UTF-8";

    private final char expectedSeparator = '/';

    private final ClassLoader rcl;

    public ClasspathLoader(ClassLoader classLoader) {
        rcl = classLoader;
    }

    public ClasspathLoader() {
        this(ClasspathLoader.class.getClassLoader());
    }

    @Override
    public Reader getReader(String templateName) throws Exception {

        Reader reader = null;
        InputStreamReader isr;
        InputStream is;

        // append the prefix and make sure prefix ends with a separator character
        StringBuilder path = new StringBuilder(128);
        if (getPrefix() != null) {

            path.append(getPrefix());

            // we do NOT use OS dependent separators here; getResourceAsStream
            // explicitly requires forward slashes.
            if (!getPrefix().endsWith(Character.toString(expectedSeparator))) {
                path.append(expectedSeparator);
            }
        }
        
        path.append(templateName);
        
        if (getSuffix() != null) {
            path.append(getSuffix());
        }
        
        String location = path.toString();
        String msg = String.format("Looking for template in \"%s\"", location);
        logger.log(Level.INFO, msg);

        // perform the lookup
        is = rcl.getResourceAsStream(location);

        if (is == null) {
            msg = String.format("Could not find template \"%s\"", location);
            throw new Exception(msg);
        }

        try {
            isr = new InputStreamReader(is, charset);
            reader = new BufferedReader(isr);
        } catch (UnsupportedEncodingException e) {
        }

        return reader;
    }

    public String getSuffix() {
        return suffix;
    }

    @Override
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getCharset() {
        return charset;
    }

    @Override
    public void setCharset(String charset) {
        this.charset = charset;
    }

    @Override
    public String resolveRelativePath(String relativePath, String anchorPath) {
        return PathUtils.resolveRelativePath(relativePath, anchorPath, expectedSeparator);
    }

    @Override
    public String createCacheKey(String templateName) {
        return templateName;
    }
}
