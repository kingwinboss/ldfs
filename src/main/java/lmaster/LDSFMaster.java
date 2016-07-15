package lmaster;

import lclient.ConfigConstants;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * Class desc：主控机
 * Created by ll
 * 2016/7/15 15:17
 */
public class LDSFMaster {

    public LDSFMaster(){
        try {
            ServerSocket mServer = new ServerSocket(ConfigConstants.MASTER_SERVER_PORT);
            ServerSocket mDataServer = new ServerSocket(ConfigConstants.MASTER_DATASERVER_CONNECT_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File f = new File("E:/l");

    }

}
