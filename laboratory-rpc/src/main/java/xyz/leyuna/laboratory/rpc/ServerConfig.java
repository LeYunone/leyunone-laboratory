package xyz.leyuna.laboratory.rpc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-06-08
 */
public class ServerConfig {

    /**
     * 服务列表
     */
    private Map<String, MyURI> server;

    public ServerConfig() {
        server = new HashMap<>();
    }

    /**
     * 注册方法
     *
     * @param uri
     */
    public void register(MyURI uri) {
        String serverName = uri.getServerName();
        if (this.server.containsKey(serverName)) {
            //刷新
        } else {
            this.server.put(serverName, uri);
        }
    }
}
