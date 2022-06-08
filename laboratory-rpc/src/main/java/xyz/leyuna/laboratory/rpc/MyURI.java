package xyz.leyuna.laboratory.rpc;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-06-08
 */
@Getter
@Setter
@ToString
public class MyURI {

    private String serverName;

    private String ipAddress;

    private String methodName;

    private Class<?>[] param;

    private Class returnParam;
}
