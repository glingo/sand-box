package demo.resource;

import component.resource.ResourceService;
import component.resource.loader.ClasspathResourceLoader;
import component.resource.loader.FileResourceLoader;
import component.resource.loader.InMemoryResourceLoader;
import component.resource.loader.StringResourceLoader;
import component.resource.reference.ResourceReference;
import java.io.File;

public class ResourceDemo {

    public static void main(String[] args) throws ClassNotFoundException, Exception {
        ResourceService service = service();

//        services.forEach((service) -> {
//            System.out.println("======= metadata ".concat(service.toString()).concat("======="));
////            print(service.metadata("file:sources/sand_box/demo/resource/demo_file"));
//            print(service.metadata("demo"));
//            print(service.metadata("file:demo_file"));
////            print(service.metadata("classpath:sources/sand_box/demo/resource/demo_classpath"));
//            print(service.metadata("classpath:demo_classpath"));
////            print(service.metadata("classpath:../../src/Test.java"));
//            print(service.metadata("memory:test"));
//            print(service.metadata("Voila voila"));
//            print(service.metadata("test"));
//            System.out.println("=======Fin - ".concat(service.toString()).concat("======="));
//        });
        System.out.println("======= load ".concat(service.toString()).concat("======="));
//            print(service.metadata("file:sources/sand_box/demo/resource/demo_file"));
//            print(service.load("demo"));
//            print(service.load("file:demo_file"));
//            print(service.metadata("classpath:sources/sand_box/demo/resource/demo_classpath"));
        //ResourceUtils.print(service.load("classpath:demo_classpath"));
        //ResourceUtils.print(service.load("classpath:demo_1.txt"));
        ResourceUtils.print(service.load("classpath:demo/resource/demo_classpath"));
//            print(service.metadata("classpath:../../src/Test.java"));
//            print(service.load("memory:test"));
//            print(service.load("Voila voila"));
//            print(service.load("test"));
        System.out.println("=======Fin - ".concat(service.toString()).concat("======="));
    }

    public static ResourceService service() {

        return ResourceService.builder()
                .with(ResourceReference.FILE, FileResourceLoader.instance())
                .with(ResourceReference.FILE, FileResourceLoader.instance(new File("/sources/sand_box/demo/resource")))
                .with(ResourceReference.FILE, FileResourceLoader.instance(new File("/Users/florian/Documents/sources/sand-box/demo/resource")))
                .with(ResourceReference.CLASSPATH, new ClasspathResourceLoader(ClassLoader.getSystemClassLoader()))
                .with(ResourceReference.MEMORY, InMemoryResourceLoader.builder().withResource("test", "Demo in memory String").build())
                .with(ResourceReference.MEMORY, InMemoryResourceLoader.builder().withResource("test", "Demo in memory String ANY_TYPE").build())
                .with(ResourceReference.STRING, new StringResourceLoader())
                .with(ResourceReference.ANY_TYPE, new StringResourceLoader())
                .build();
    }

}
