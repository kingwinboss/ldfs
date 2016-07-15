package lclient;

import lserver.LDFSClientPool;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

/**
 * Class desc：
 * Created by ll
 * 2016/7/14 17:18
 */
public class LDFSClient {

    private String serverHost;
    private int serverPort;

    public LDFSClient(String serverHost,int serverPort){
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public LDFSClient(String serverHost){
        this(serverHost,ConfigConstants.SERVER_PORT);
    }

    public void createFile(String path){

    }

    public static void main(String[] args) {
        try {
            Socket s = LDFSClientPool.getSocketFromPool("192.168.1.12",58888);
            File file = new File("D:/apache-maven-3.3.3-bin - 副本.rar");
            FileInputStream fs = new FileInputStream(file);
            //定义一个256字节的区域来保存文件信息。
            byte[] b = file.getName().getBytes();
            byte[] info = Arrays.copyOf(b,256);
            ByteArrayInputStream bais = new ByteArrayInputStream(info);
            //合并流
            SequenceInputStream sis = new SequenceInputStream(bais,fs);
            BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream());
            byte[] buf = new byte[1024];
            int len = 0;
            while((len = sis.read(buf))!=-1){
                bos.write(buf,0,len);
            }
            bos.close();
            sis.close();
            fs.close();
            bais.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
