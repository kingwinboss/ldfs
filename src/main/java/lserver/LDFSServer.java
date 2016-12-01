package lserver;

import lclient.ConfigConstants;

import java.io.*;
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

    public void startServer(){
        ExecutorService threadpool = Executors.newCachedThreadPool();
        try {
            ss = new ServerSocket(ConfigConstants.MASTER_SERVER_PORT);
            while(true){
                Socket s = ss.accept();
                threadpool.execute(new TrastportThread(s));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class TrastportThread implements Runnable{
        private Socket s;
        public TrastportThread(Socket s){
            this.s = s;
        }

        @Override
        public void run() {
            try {
                BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
                byte[] b1 = new byte[280];
                bis.read(b1, 0, 280);
//                System.out.println(new String(b1,0,ConfigConstants.TRANSPORT_TYPE_LENGTH));
//                System.out.println(new String(b1,1,ConfigConstants.FILE_LENGTH_LENGTH));
//                System.out.println(new String(b1,ConfigConstants.FILE_LENGTH_LENGTH,ConfigConstants.FILE_NAME_LENGTH));

                //相对路径及文件名 "fileupload" + "/news/" + ParseDate.parseShortFormat(new Date())+"/文件名";
                String file_name = new String(b1,ConfigConstants.FILE_LENGTH_LENGTH,ConfigConstants.FILE_NAME_LENGTH,"utf-8").trim();
                //项目路径
                String proLocalAddr = Thread.currentThread().getContextClassLoader().getResource("").getPath();
                proLocalAddr = proLocalAddr.substring(0,proLocalAddr.indexOf("WEB-INF"));
                //String proLocalAddr = System.getProperty("user.dir").replace("bin", "webapps")+"/upload/";
                String addr = proLocalAddr + file_name;
                File dir = new File(addr.substring(0, addr.lastIndexOf("/")));
                if(!dir.exists()){
                    dir.mkdirs();
                }
                File f = new File(addr);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = bis.read(buf)) != -1) {
                    bos.write(buf, 0, len);
                }
                bos.flush();
                bos.close();
                bis.close();
                s.close();
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
