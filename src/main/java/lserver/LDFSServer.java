package lserver;

import lclient.ConfigConstants;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class desc：//TODO
 * Created by ll
 * 2016/7/14 17:24
 */
public class LDFSServer {

    private ServerSocket ss;

    private void startServer(){
        ExecutorService threadpool = Executors.newCachedThreadPool();
        try {
            ss = new ServerSocket(ConfigConstants.MASTER_SERVER_PORT);
            //一直监听 否则为传输一次完成后即关闭
            while (true) {
                threadpool.execute(new TrastportThread());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class TrastportThread implements Runnable{
        @Override
        public void run() {
            try {
                Socket s = ss.accept();
                BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
                byte[] b1 = new byte[280];
                bis.read(b1,0,280);
                System.out.println(new String(b1,0,ConfigConstants.TRANSPORT_TYPE_LENGTH));
                System.out.println(new String(b1,ConfigConstants.TRANSPORT_TYPE_LENGTH,ConfigConstants.FILE_NAME_LENGTH));
                System.out.println(new String(b1,(ConfigConstants.TRANSPORT_TYPE_LENGTH+ConfigConstants.FILE_NAME_LENGTH)
                        ,ConfigConstants.FILE_LENGTH_LENGTH));
                String file_name = new String(b1,ConfigConstants.TRANSPORT_TYPE_LENGTH,ConfigConstants.FILE_NAME_LENGTH).trim();
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("E:/"+file_name));
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = bis.read(buf)) != -1) {
                    bos.write(buf, 0, len);
                }
                bos.close();
                bis.close();
                s.close();
                System.out.println(file_name+"  文件传输成功");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        LDFSServer server = new LDFSServer();
        server.startServer();
    }
}
