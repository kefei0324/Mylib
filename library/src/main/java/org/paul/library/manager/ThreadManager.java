package org.paul.library.manager;

import org.paul.library.base.BaseBean;

import java.util.Map;
import java.util.concurrent.*;

public class ThreadManager {

    private NetManager netManager = NetManager.getInstance();

    private static class Holder {
        private static ThreadManager instance = new ThreadManager();
    }

    private ThreadManager() {
        int cpuNum = Runtime.getRuntime().availableProcessors();
        fixedThreadPool = Executors.newFixedThreadPool(cpuNum + 1);
    }

    public static ThreadManager getInstance() {
        return Holder.instance;
    }

    private ExecutorService fixedThreadPool;

    public <T extends BaseBean> T submitPost(final String spec, final Map<String, Object> params, final Class<T> clz) {

        try {
            Future<T> future = fixedThreadPool.submit(new Callable<T>() {
                @Override
                public T call() throws Exception {
                    return netManager.post(spec, params, clz);
                }
            });
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    public <T extends BaseBean> T submitGet(final String spec, final Class<T> clz) {

        try {
            Future<T> future = fixedThreadPool.submit(new Callable<T>() {
                @Override
                public T call() throws Exception {
                    return netManager.get(spec, clz);
                }
            });
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
