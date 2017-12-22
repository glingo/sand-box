package demo.resource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import component.resource.metatada.ResourceMetadata;

public class ResourceUtils {

    public static void print(InputStream stream) {
        Reader reader = new InputStreamReader(stream);
        BufferedReader bf = new BufferedReader(reader);
        bf.lines().forEach(System.out::println);
    }

    public static void print(ResourceMetadata metadata) {
        System.out.println();
        print(metadata.load());
    }
}
