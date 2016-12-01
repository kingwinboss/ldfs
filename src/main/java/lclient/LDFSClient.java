package lclient;

import util.ParseDate;

import java.io.*;
import java.net.Socket;
import java.util.Date;

/**
 * Class desc：客户端
 * <li>传输类型定义：byte[0:传输类型,1-270:文件名,271-279:文件大小KB,280-:文件内容]</li>
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
        this(serverHost, ConfigConstants.MASTER_SERVER_PORT);

    }

    /**
     * 创建文件
     * @param path
     * @return 文件路径及名称 ; null：失败
     */
    public String createFile(String path){
        SequenceInputStream sis = null;
        BufferedOutputStream bos = null;
        FileInputStream fs = null;
        ByteArrayInputStream bais = null;
        try {
            Socket s = new Socket(serverHost,serverPort);
            File file = new File(path);
            if(!file.exists() || !file.isFile()){
                return null;
            }
            fs = new FileInputStream(file);
            //定义一个280字节的区域来保存文件信息。
            byte[] info = new byte[ConfigConstants.HEAD_INFO_LENGTH];
            byte[] transType = ConfigConstants.FILE_TRANSPORT.getBytes();
            //文件路径
            String absAddr = "fileupload" + "/news/" + ParseDate.parseShortFormat(new Date())+"/";
            byte[] fileName = (absAddr+file.getName()).getBytes("utf-8");
            byte[] fileLength = new Long(file.length()/1024).toString().getBytes();
            System.arraycopy(transType,0,info,0,transType.length);
            System.arraycopy(fileLength,0,info,1,fileLength.length);
            System.arraycopy(fileName,0,info, ConfigConstants.FILE_LENGTH_LENGTH,fileName.length);
            bais = new ByteArrayInputStream(info);

            //合并流
            sis = new SequenceInputStream(bais,fs);
            bos = new BufferedOutputStream(s.getOutputStream());
            byte[] buf = new byte[1024];
            int len = 0;
            while((len = sis.read(buf))!=-1){
                bos.write(buf,0,len);
            }
            bos.flush();
            if(s.isConnected() && !s.isClosed()){
                s.close();
            }
            return absAddr+file.getName();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeOutputStream(bos);
            closeInputStream(sis);
            closeInputStream(fs);
            closeInputStream(bais);
        }
    }

    public void sendMsg(String msg){

    }

    private void closeInputStream(InputStream is){
        if(is != null){
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeOutputStream(OutputStream os){
        if(os != null){
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        LDFSClient client = new LDFSClient("127.0.0.1");
        System.out.println(client.createFile("D:/apache-maven-3.3.3-bin.rar"));
        //byte[] info = new byte[280];
        //byte[] b = new String("1").getBytes();
        //byte[] fileName = new String("地方大幅度发等等").getBytes();
        //byte[] fileLength = new Long(1000202002/1024).toString().getBytes();
        //System.arraycopy(b,0,info,0,b.length);
        //System.arraycopy(fileName,0,info,1,fileName.length);
        //System.arraycopy(fileLength,0,info,272,fileLength.length);
        //System.out.println(new String(info));
        //System.out.println(new String(info,272,fileLength.length));
    }
}
