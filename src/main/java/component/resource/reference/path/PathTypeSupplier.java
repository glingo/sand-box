package component.resource.reference.path;

import java.util.function.Supplier;

public class PathTypeSupplier implements Supplier<PathType> {

    @Override
    public PathType get() {
        String operatingSystem = System.getProperty("os.name").toLowerCase();
        if (operatingSystem.contains("win")) {
            return PathType.UNC;
        }
        
        return PathType.POSIX;
    }
}
