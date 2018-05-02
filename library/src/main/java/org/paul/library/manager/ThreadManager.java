package org.paul.library.manager;

public class ThreadManager {

    private static class Holder {
        private static ThreadManager instance = new ThreadManager();
    }
    private ThreadManager(){}
    public static ThreadManager getInstance(){
        return Holder.instance;
    }
}
