package lserver;

import lclient.ConfigConstants;

import java.io.IOException;
import java.net.Socket;

/**
 * Class desc：//TODO
 * Created by ll
 * 2016/7/14 17:40
 */
public class LDFSClientPool {
    private static ThreadLocal<Socket> local = new ThreadLocal<>();

    public static Socket getSocketFromPool(String serverHost){
        return getSocketFromPool(serverHost,ConfigConstants.MASTER_SERVER_PORT);
    }

    public static Socket getSocketFromPool(String serverHost,int port){
        if(local.get() != null){
            return local.get();
        }
        try {
            local.set(new Socket(serverHost,port));
            return local.get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
