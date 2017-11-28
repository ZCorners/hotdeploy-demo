package demo.hotdeploy.classloader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 加载manager的工厂
 */
public class ManagerFactory {
    // 记录热加载类的加载信息
    private static final Map<String, LoadInfo> loadTimeMap = new HashMap<>();

    // 要加载的类的classpath
    public static final String CLASS_PATH = "E:/Code_ideaU/hotdeploy/target/classes/";

    // 实现热加载类的全名称
    public static final String MY_MANAGER = "demo.hotdeploy.classloader.MyManager";

    public static BaseManager getManager(String className) {
        File loadFIle = new File(CLASS_PATH + className.replaceAll("\\.", "/") + ".class");
        long lastModified = loadFIle.lastModified();
        if(loadTimeMap.get(className) == null) {
            load(className, lastModified);
        } else if(loadTimeMap.get(className).getLoadTime() != lastModified) {
            load(className, lastModified);
        }
        return loadTimeMap.get(className).getManager();
    }

    private static void load(String className, long lastModified) {
        MyClassLoader myClassLoader = new MyClassLoader(CLASS_PATH);
        Class<?> loadClass = null;
        try {
            loadClass = myClassLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        BaseManager manager = newInstance(loadClass);
        LoadInfo loadInfo = new LoadInfo(myClassLoader, lastModified);
        loadInfo.setManager(manager);
        loadTimeMap.put(className, loadInfo);
    }

    // 以反射方式创建BaseManager子类对象
    private static BaseManager newInstance(Class<?> loadClass) {
        try {
            return (BaseManager)loadClass.getConstructor(new Class[]{}).newInstance(new Object[]{});
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
