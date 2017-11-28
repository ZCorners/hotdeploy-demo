package demo.hotdeploy.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class MyClassLoader extends ClassLoader {

    // 要加载的java类的classpath路径
    public String classpath;

    public MyClassLoader(String classpath) {
        super(ClassLoader.getSystemClassLoader());
        this.classpath = classpath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = this.loadClassData(name);
        return this.defineClass(name, data, 0, data.length);
    }

    private byte[] loadClassData(String name) {
        try {
            name = name.replace(".", "//");
            FileInputStream fis = new FileInputStream(new File(classpath + name + ".class"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int b = 0;
            while ((b = fis.read()) != -1) {
                baos.write(b);
            }
            fis.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
