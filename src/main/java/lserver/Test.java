package lserver;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class desc：//TODO
 * Created by ll
 * 2016/7/14 16:06
 */
public class Test {

    public void start(){
        try {
            ServerSocket ss = new ServerSocket(38888);
            Socket s = ss.accept();
            InputStream is = s.getInputStream();
            byte b[] = new byte[1024];
            int len = 0;
            int temp=0;          //所有读取的内容都使用temp接收
            while((temp=is.read())!=-1){    //当没有读取完时，继续读取
                b[len]=(byte)temp;
                len++;
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
