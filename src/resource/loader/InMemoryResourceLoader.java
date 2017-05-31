package resource.loader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import resource.exceptions.ResourceNotFoundException;

public class InMemoryResourceLoader implements ResourceLoader {
    public static InMemoryResourceLoader.Builder builder () {
        return new InMemoryResourceLoader.Builder();
    }

    private final Map<String, Supplier<InputStream>> inputStreamMap;

    public InMemoryResourceLoader(Map<String, Supplier<InputStream>> inputStreamMap) {
        this.inputStreamMap = inputStreamMap;
    }

    @Override
    public InputStream load(String path) {
        Supplier<InputStream> streamSupplier = inputStreamMap.get(path);
        if (streamSupplier == null) {
            throw new ResourceNotFoundException(String.format("Resource '%s' not found", path));
        }
        return streamSupplier.get();
    }

    @Override
    public boolean exists(String path) {
        return inputStreamMap.containsKey(path);
    }
    
    public static class Builder {
        private final Map<String, Supplier<InputStream>> supplierMap = new HashMap<>();

        public Builder withResource (String key, String content) {
            supplierMap.put(key, new StringInputStreamSupplier(content));
            return this;
        }

        public InMemoryResourceLoader build() {
            return new InMemoryResourceLoader(supplierMap);
        }
    }

    public static class StringInputStreamSupplier implements Supplier<InputStream> {
        private final String content;

        public StringInputStreamSupplier(String content) {
            this.content = content;
        }

        @Override
        public InputStream get() {
            return new ByteArrayInputStream(content.getBytes());
        }
    }
    
}
