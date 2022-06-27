import java.io.*;
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
        File file = new File("External Source\\" + name + ".class");
        if (!file.exists())
            return findSystemClass(name);
        try {
            byte[] classBytes= loadFileAsBytes(file);
            clazz = defineClass(name, classBytes, 0,
                    classBytes.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (clazz != null)
            classesHash.put(name, clazz);
        return clazz;

    }



    private byte[] loadFileAsBytes(File file)
            throws IOException{
        byte[] result = new byte[(int)file.length()];
        FileInputStream f = new FileInputStream(file);
        try {
            f.read(result,0,result.length);
        } finally {
            try {
                f.close();
            } catch (Exception e) {
            }
        }
        return result;
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



