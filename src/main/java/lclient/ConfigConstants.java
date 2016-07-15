package lclient;

/**
 * Class desc：配置项常量
 * Created by ll
 * 2016/7/14 18:41
 */
public interface ConfigConstants {
    /**主控机监听端口*/
    int MASTER_SERVER_PORT = 41735;
    /**主控机与数据服务器连接端口*/
    int MASTER_DATASERVER_CONNECT_PORT = 41736;
    /**文件传输*/
    String FILE_TRANSPORT = "1";
    /**文本传输*/
    String TEXT_TRANSPORT = "2";
    /**心跳*/
    String HEART_BEAT = "3";
    /**头信息长度*/
    int HEAD_INFO_LENGTH = 280;
    /**传输类型长度*/
    int TRANSPORT_TYPE_LENGTH = 1;
    /**文件名称长度*/
    int FILE_NAME_LENGTH = 270;
    /**文件长度长度*/
    int FILE_LENGTH_LENGTH = 9;


}
