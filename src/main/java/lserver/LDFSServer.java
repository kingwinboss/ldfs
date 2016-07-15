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

    public static void main(String[] args) {
        ExecutorService threadpool = Executors.newCachedThreadPool();
        try {
            ServerSocket ss = new ServerSocket(ConfigConstants.SERVER_PORT);
            while (true) {
                threadpool.execute(new UploadThread(ss.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class UploadThread implements Runnable {
    private Socket s;

    UploadThread(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        System.out.println(s.getInetAddress().getHostAddress());
        try {
            BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
            byte[] info = new byte[256];
            bis.read(info);
            String file_name = new String(info).trim();
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
