package org.paul.library.manager;

public class NetManager {
    private static class Holder {
        private static NetManager instance = new NetManager();
    }
    private NetManager(){}
    public static NetManager getInstance(){
        return Holder.instance;
    }
}
