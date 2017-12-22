package demo.resource;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class FileUtils {

    public static void exists(File file, boolean deep) {
        System.out.format("File %s exists ? %s \n", file.getPath(), file.exists());
        if (file.exists()) {
            directory(file, deep);
        }
    }

    public static void directory(File file, boolean deep) {
        System.out.format("File %s is directory ? %s \n", file.getPath(), file.isDirectory());

        if (file.isDirectory()) {
            File[] children = file.listFiles();
            System.out.format("Children : %s \n", Arrays.toString(children));

            if (deep && Objects.nonNull(children)) {
                Arrays.stream(file.listFiles())
                        .filter(Objects::nonNull)
                        .forEach((child) -> details(child, deep));
            }
        }
    }

    public static void details(String path, boolean deep) {
        details(new File(path), deep);
    }

    public static void details(File file, boolean deep) {
        exists(file, deep);
    }

}
