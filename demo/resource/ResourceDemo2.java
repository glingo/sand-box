package resource;

import java.io.File;
import resource.loader.ClasspathResourceLoader;
import resource.loader.FileResourceLoader;
import resource.loader.InMemoryResourceLoader;
import resource.loader.StringResourceLoader;
import resource.reference.ResourceReference;

public class ResourceDemo2 {

    public static void main(String[] args) throws ClassNotFoundException, Exception {
        ResourceService service = ResourceService.builder()
                .with(ResourceReference.FILE, FileResourceLoader.instance())
                .with(ResourceReference.FILE, FileResourceLoader.instance(new File("/Users/florian/Documents/sources/sand-box/demo")))
                .with(ResourceReference.CLASSPATH, new ClasspathResourceLoader(ClassLoader.getSystemClassLoader()))
                .with(ResourceReference.MEMORY, InMemoryResourceLoader.builder()
                        .withResource("test", "Demo in memory String")
                        .build())
                .with(ResourceReference.STRING, new StringResourceLoader())
                .with(ResourceReference.ANY_TYPE, new StringResourceLoader())
                .build();

        System.out.println("======= load ".concat(service.toString()).concat("======="));
        ResourceUtils.print(service.load("resource/demo"));
        ResourceUtils.print(service.load("file:resource/demo_1.txt"));
        ResourceUtils.print(service.load("classpath:resource/demo_classpath"));
        ResourceUtils.print(service.load("classpath:resource/demo_1.txt"));
        ResourceUtils.print(service.load("Hello World !"));
        ResourceUtils.print(service.load("test"));
        System.out.println("=======Fin - ".concat(service.toString()).concat("======="));
    }
}
