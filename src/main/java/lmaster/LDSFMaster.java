package lmaster;

import lclient.ConfigConstants;

import java.io.*;
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
        try {
            File f = new File("E:/l");
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
            String test = "djdifdji非得所属等等 打发打发等等444";
            byte[] buf = test.getBytes();
            bos.write(buf);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
