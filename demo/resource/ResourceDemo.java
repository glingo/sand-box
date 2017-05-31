package resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import resource.loader.ClasspathResourceLoader;
import resource.loader.FileResourceLoader;
import resource.loader.InMemoryResourceLoader;
import resource.loader.StringResourceLoader;
import resource.loader.TypedResourceLoader;
import resource.metatada.ResourceMetadata;
import resource.reference.ResourceReference;

public class ResourceDemo {
    
    public static void main(String[] args) {
//        details("", false);
//        details("/", false);
//        details("/src", false);
//        details("D:/sources/sand_box/demo/resource/demo.view", false);
        
        Collection<ResourceService> services = Arrays.asList(
                service(), service2(), service3(), service4(), service5());
        
        services.forEach((service) -> {
            System.out.println("======= metadata ".concat(service.getName()).concat("======="));
//            print(service.metadata("file:sources/sand_box/demo/resource/demo_file"));
            print(service.metadata("demo"));
            print(service.metadata("file:demo_file"));
//            print(service.metadata("classpath:sources/sand_box/demo/resource/demo_classpath"));
            print(service.metadata("classpath:demo_classpath"));
//            print(service.metadata("classpath:../../src/Test.java"));
            print(service.metadata("memory:test"));
            print(service.metadata("Voila voila"));
            print(service.metadata("test"));
            System.out.println("=======Fin - ".concat(service.getName()).concat("======="));
        });
        
        services.forEach((service) -> {
            System.out.println("======= load ".concat(service.getName()).concat("======="));
//            print(service.metadata("file:sources/sand_box/demo/resource/demo_file"));
            print(service.load("demo"));
            print(service.load("file:demo_file"));
//            print(service.metadata("classpath:sources/sand_box/demo/resource/demo_classpath"));
            print(service.load("classpath:demo_classpath"));
//            print(service.metadata("classpath:../../src/Test.java"));
            print(service.load("memory:test"));
            print(service.load("Voila voila"));
            print(service.load("test"));
            System.out.println("=======Fin - ".concat(service.getName()).concat("======="));
        });
    }
    
    public static ResourceService service() {
        return ResourceService.builder("1")
                .with(new TypedResourceLoader(ResourceReference.FILE, FileResourceLoader.instance()))
                .with(new TypedResourceLoader(ResourceReference.FILE, FileResourceLoader.instance(new File("/sources/sand_box/demo/resource"))))
                .with(new TypedResourceLoader(ResourceReference.CLASSPATH, new ClasspathResourceLoader(ClassLoader.getSystemClassLoader())))
                .with(new TypedResourceLoader(ResourceReference.CLASSPATH, new ClasspathResourceLoader(ResourceDemo.class.getClassLoader())))
                .with(new TypedResourceLoader(ResourceReference.MEMORY, InMemoryResourceLoader.builder().withResource("test", "Demo in memory String").build()))
                .with(new TypedResourceLoader(ResourceReference.ANY_TYPE, InMemoryResourceLoader.builder().withResource("test", "Demo in memory String ANY_TYPE").build()))
                .with(new TypedResourceLoader(ResourceReference.STRING, new StringResourceLoader()))
                .build();
    }
    
    public static ResourceService service2() {
        return ResourceService.builder("2")
                .with(new TypedResourceLoader(ResourceReference.FILE, FileResourceLoader.instance()))
                .with(new TypedResourceLoader(ResourceReference.FILE, FileResourceLoader.instance(new File("/sources/sand_box/demo/resource"))))
                .with(new TypedResourceLoader(ResourceReference.CLASSPATH, new ClasspathResourceLoader(ClassLoader.getSystemClassLoader())))
                .with(new TypedResourceLoader(ResourceReference.CLASSPATH, new ClasspathResourceLoader(ResourceDemo.class.getClassLoader())))
                .with(new TypedResourceLoader(ResourceReference.MEMORY, InMemoryResourceLoader.builder().withResource("test", "Content test").build()))
                .with(new TypedResourceLoader(ResourceReference.STRING, new StringResourceLoader()))
                .with(new TypedResourceLoader(ResourceReference.ANY_TYPE, new StringResourceLoader()))
                .build();
    }
    
    public static ResourceService service3() {
        return ResourceService.builder("3")
                .with(FileResourceLoader.instance())
                .with(FileResourceLoader.instance(new File("/sources/sand_box/demo/resource")))
                .with(new ClasspathResourceLoader(ClassLoader.getSystemClassLoader()))
                .with(new ClasspathResourceLoader(ResourceDemo.class.getClassLoader()))
                .with(InMemoryResourceLoader.builder().withResource("test", "Content test").build())
                .with(new StringResourceLoader())
                .build();
    }
    
    public static ResourceService service4() {
        return ResourceService.builder("4")
                .with(FileResourceLoader.instance())
                .with(FileResourceLoader.instance(new File("/sources/sand_box/demo/resource")))
                .with(new ClasspathResourceLoader(ClassLoader.getSystemClassLoader()))
                .with(new ClasspathResourceLoader(ResourceDemo.class.getClassLoader()))
                .with(InMemoryResourceLoader.builder().withResource("test", "Content test").build())
                .with(new StringResourceLoader())
                .build();
    }
    
    public static ResourceService service5() {
        return ResourceService.builder("5")
                .with(FileResourceLoader.instance(new File("/sources/sand_box/demo/resource")))
                .with(new ClasspathResourceLoader(ResourceDemo.class.getClassLoader()))
                .with(new StringResourceLoader())
                .build();
    }
    
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
                    .forEach((child) -> ResourceDemo.details(child, deep));
            }
        }
    }
    
    public static void details(String path, boolean deep) {
        details(new File(path), deep);
    }
    
    public static void details(File file, boolean deep) {
        exists(file, deep);
    }
    
    public static void print(InputStream stream) {
        Reader reader = new InputStreamReader(stream);
        BufferedReader bf = new BufferedReader(reader);
        bf.lines().forEach(System.out::println);
    }
    
    public static void print(ResourceMetadata metadata) {
        print(metadata.load());
    }
}
