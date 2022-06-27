import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

class CustomClassLoader extends ClassLoader {

    private Map classesHash = new HashMap();

    public CustomClassLoader invalidate(){
        return new CustomClassLoader();
    }

    public void clearCache(){
        classesHash.clear();
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class clazz = (Class) classesHash.get(name);
        if (clazz != null) {
            return clazz;
        }
        File file = new File("D:\\WORK\\EPAM\\AJM\\Classloader\\External Source\\" + name + ".class");
        if (!file.exists())
            return findSystemClass(name);
        clazz = getClass(name);
        if (clazz != null)
            classesHash.put(name, clazz);
        return clazz;

    }



    private Class<?> getClass(String name){

        byte[] byteArr = null;
        try {

            byteArr = Files.readAllBytes(Paths.get("D:\\WORK\\EPAM\\AJM\\Classloader\\External Source\\" + name + ".class"));

            Class<?> c = defineClass(name, byteArr, 0, byteArr.length);
            resolveClass(c);
            return c;
        } catch (IOException e) {
            return null;
        }
    }
}

class TestClass{

    @Override
    public String toString() {
        return "TestClass FROM CLASSPATH";
    }
}


public class Main {

    public static void main(String[] args) throws Exception {
        // Load class dynamically which is not in ClassPath (from DB, external memory, WEB)
        CustomClassLoader dynamicClassLoader = new CustomClassLoader();
        boolean refreshClass = true;

        while(true){
            if(refreshClass) {
                dynamicClassLoader = dynamicClassLoader.invalidate();
//                dynamicClassLoader.clearCache();
            }
            Class clazz =  Class.forName("TestClass", true, dynamicClassLoader);
//             Class clazz =  dynamicClassLoader.loadClass("TestClass");
            Object  object =  clazz.getDeclaredConstructor().newInstance();

            System.out.println(object);

            Thread.sleep(2*1000);


        }
    }


}



