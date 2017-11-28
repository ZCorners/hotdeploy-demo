package demo.hotdeploy.classloader;

/**
 * 不断的去刷新热加载的类
 */
public class MsgHandler implements Runnable {
    @Override
    public void run() {
        while(true) {
            BaseManager manager = ManagerFactory.getManager(ManagerFactory.MY_MANAGER);
            manager.logic();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
