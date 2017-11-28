package demo.hotdeploy.classloader;

/**
 * 实现java类的热加载功能
 */
public class MyManager implements BaseManager {
    @Override
    public void logic() {
        System.out.println("hot deploy test");
    }
}
