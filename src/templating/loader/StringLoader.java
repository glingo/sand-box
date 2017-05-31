package templating.loader;

import java.io.Reader;
import java.io.StringReader;

/**
 * This loader is not intended to be used in a production system;
 * it is primarily for testing and debugging. Many tags do not
 * work when using this loader, such as "extends", "imports", "include".
 */
public class StringLoader implements LoaderInterface<String> {

    @Override
    public Reader getReader(String templateName) throws Exception {
        return new StringReader(templateName);
    }

    @Override
    public void setPrefix(String prefix) {

    }

    @Override
    public void setSuffix(String suffix) {

    }

    @Override
    public void setCharset(String charset) {

    }

    @Override
    public String resolveRelativePath(String relativePath, String anchorPath) {
        return null;
    }

    @Override
    public String createCacheKey(String templateName) {
        return templateName;
    }

}
