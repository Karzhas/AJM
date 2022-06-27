import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;

class CustomClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (name.startsWith("Test")) {
            return getClass(name);
        }
        return super.findClass(name);
    }

    private Class<?> getClass(String name){

        byte[] byteArr = null;
        try {
            byteArr = Files.readAllBytes(Paths.get("D:\\Desktop\\TestClass.class"));
            Class<?> c = defineClass(name, byteArr, 0, byteArr.length);
            resolveClass(c);
            return c;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

class DynamicClassLoader extends ClassLoader{
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (name.startsWith("Test")) {
            return getClass(name);
        }
        return super.findClass(name);
    }

    private Class<?> getClass(String name){

        byte[] byteArr = null;
        try {
            byteArr = Files.readAllBytes(Paths.get("D:\\Desktop\\TestClass.class"));
            Class<?> c = defineClass(name, byteArr, 0, byteArr.length);
            resolveClass(c);
            return c;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

public class Main {

    public static void main(String[] args) {
        // Load class from external class which is not in ClassPath (from DB, external memory, WEB)
        loadClassNotFromClassPathByName("TestClass");
        // Load class from dynamically without restarting program
        loadClassDynamically();
    }

    private static void loadClassDynamically() {
        while(true){
            DynamicClassLoader dynamicClassLoader = new DynamicClassLoader();
            try {
                Class<?> clazz = Class.forName("TestClass", true, dynamicClassLoader);
                Object object = clazz.getDeclaredConstructor().newInstance();
                System.out.println(object);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }

            try {
                Thread.sleep(3*1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private static void loadClassNotFromClassPathByName(String testClass) {
        CustomClassLoader customClassLoader = new CustomClassLoader();
        try {
            Class<?> clazz = customClassLoader.loadClass("TestClass");
            //Class<?> clazz = Class.forName("TestClass", true, customClassLoader);
            Object instance = clazz.getDeclaredConstructor().newInstance();

            clazz.getMethod("print").invoke(instance);
            System.out.println(instance);
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}



