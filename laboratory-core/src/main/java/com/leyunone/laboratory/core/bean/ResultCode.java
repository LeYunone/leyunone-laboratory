package com.leyunone.laboratory.core.bean;


/**
 * @program: smart-home
 * @description:
 * @author: Mr.Xia
 * @create: 2020-12-28
 **/
public enum ResultCode {
    /**
     * 操作成功
     */
    SUCCESS(200, "request is success"),

    /**
     * 参数检验失败
     */
    VALIDATE_FAILED(400, "parameter is check failure"),

    /**
     * 暂未登录或token已经过期
     */
    UNAUTHORIZED(401, "token is Expired"),

    /**
     * 无权限
     */
    FORBIDDEN(403,"forbidden access"),

    /**
     * 操作失败
     */
    HANDLER_NOT_FOUND(404, "handler not found"),

    /**
     * 请求限流
     */
    TOO_MANY_REQUESTS(429,"Too Many Requests"),

    /**
     * 操作失败
     */
    FAILED(500, "Internal Server Error"),
    /**
     * 降级
     */
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),

    HOMEPARAM_IS_EMPTY(1004000000,"homeId is empty"),

    AUTHORIZED_TOKEN_LOSE(-1004000001,"authorized token lose"),

    DEVICE_IS_BIND(1002001002,"device is bind"),

    //-----------------1004 - 区域管理系统--------------------
    /**
     * 名称已存在
     */
    NAME_EXISTS(1004000001,"Name already exists"),
    /**
     * 时区不正确
     */
    INCORRECT_TIME_ZONE(1004000002,"incorrect time zone"),

    //---1004001  家庭模块
    /**
     * 该用户下创建家庭超出上限
     */
    HOME_OVER_LIMIT(1004001001,"create home over limit"),
    /**
     * 必须拥有一个家庭
     */
    MUST_HAVE_A_HOME(1004001002,"Must have a home"),
    /**
     * 没有权限操作此家庭
     */
    NO_PERMISSION_OPERATE_HOME(1004001003,"No permission to operate this home"),
    /**
     * 家庭不存在
     */
    HOME_NOT_EXIST(1004001004,"家庭不存在"),
    /**
     * 家庭名称已存在
     */
    HOME_NAME_EXISTS(1004001005,"home name is exist"),
    NO_CURRENTHOME(1004001006,"当前没有选择家庭"),
    USER_NOT_EXIST(1004001007,"用户不存在"),
    CLASS_NOT_EXIST(1004001008,"分类不存在"),
    HAS_DEFAULT(1004001009,"已存在默认家庭"),


    //---1004002  区域模块
    /**
     * 该用户下创建区域超出限制
     */
    AREA_OVER_LIMIT(1004002001,"create area over limit"),
    /**
     * 该家庭下创建区域超出限制
     */
    HOME_AREA_OVER_LIMIT(1004002002,"create area over limit in this family"),
    /**
     * 没有权限操作此区域
     */
    NO_PERMISSION_OPERATE_AREA(1004002003,"No permission to operate this area"),
    /**
     * 区域不存在
     */
    AREA_NOT_EXIST(1004002004,"area does not exist"),
    /**
     * 区域名称已存在
     */
    AREA_NAME_EXISTS(1004002005,"area name is exists"),



    //---1004003  房间模块
    /**
     * 该用户下创建房间超出限制
     */
    ROOM_OVER_LIMIT(1004003001,"create room over limit"),
    /**
     * 该家庭下创建房间超出限制
     */
    HOME_ROOM_OVER_LIMIT(1004003002,"create room over limit in this home"),
    /**
     * 没有权限操作此家庭
     */
    NO_PERMISSION_OPERATE_ROOM(1004003003,"No permission to operate this home"),
    /**
     * 房间不存在
     */
    ROOM_NOT_EXIST(1004003004,"room does not exist"),
    /**
     * 房间名称重复
     */
    ROOM_NAME_IS_EXIST(1004003005,"room name is exist"),
    NOT_FOUND_ROOM(1004003003,"not found room"),
    NOT_CURRENT_SELECT_HOME(1004003004,"非当前选中家庭"),


    //----1004004   设备模块
    /**
     * 设备不存在
     */
    DEVICE_NOT_EXIST(1004004001,"Device does not exist"),

    DEVICE_CONFIG_IS_LATEST(1004004002,"the device config be latest"),
    /**
     * 没有权限操作此设备
     */
    NO_PERMISSION_OPERATE_DEVICE(1004004003,"No permission to operate this device"),


    DEVICE_CONFIG_IS_NOT_EXIST(-1004004004,"the device config be not exist"),

    /**
     * 展示首页的设备超出最大限制
     */
    DEVICE_SHOW_OVER_LIMIT(1004004004,"device show in pageHome over limit in this home"),
    /**
     * 不存在未绑定的子设备
     */
    UNBOUND_SUBSET_NOT_EXIST(1004004005,"unbound subset does not exist"),
    /**
     * 不存在未绑定的设备
     */
    HAVE_NO_UNBOUND_DEVICE(1004004006,"have no Unbound device"),

    /**
     * 上传记录图片信息错误
     */
    UPLOAD_RECORD_PIC_PARAM_ERROR(1004004007,"upload record pic param error"),
    /**
     * 设备不在线
     */
    DEVICE_NOT_ONLINE(1004004008,"device 不在线"),
    /**
     * 设备不在使用范围内
     */
    DEVICE_OUT_OF_USE_TIME(1004004009,"设备不在使用时间范围内"),
    /**
     * 网关已被绑定
     */
    GATEWAY_IS_BIND(1004004010, "网关已被绑定"),

    /**
     * 配网token已过期
     */
    BIND_TOKEN_EXPIRE(1004004011, "配网token已过期"),

    /**
     * 网关未绑定
     */
    GATEWAY_NOT_BIND(1004004012, "网关未绑定"),

    /**
     * 存在已绑定的设备
     */
    BOUND_DEVICE_IS_EXIST(1004004013, "存在已绑定的设备"),
    //----1004005   场景模块

    /**
     * 场景不存在
     */
    SCENES_NOT_EXIST(1004005001,"scenes does not exist"),
    /**
     * 重复添加指令
     */
    REPEAT_COMMAND(1004005002,"repeat  command"),


    COMMAND_IS_EMPTY(1004005003,"command can't not empty"),
    /**
     * 场景名称已存在
     */
    SCENES_NAME_IS_EXIST(1004005004,"scenes name is exist"),
    /**
     * 该场景不是内置场景
     */
    IS_NOT_INTERNAL(1004005005,"is not internal scenes"),

    /**
     * 注册sip账号失败
     */
    REGISTER_SIP_ACCOUNT_FAILURE(1004006001,"Failed to register sip account"),

    /**
     * 删除账号失败
     */
    DELETE_SIP_ACCOUNT_FAILURE(1004006002,"Failed to delete sip account"),

    /**
     * sip账号已存在
     */
    SIP_ACCOUNT_ALREADY_EXISTS(1004006003,"SIP account already exists"),

    AVAILABILITY_ZONE_DOES_NOT_EXIST(1004006004,"No such Availability Zone"),

    //权限管理模块
    USER_HOME_NOT_PURVIEW(1004007001,"用户在当前家庭下无权限"),

    //====邀请模块
    INVITE_CODE_NOT(1004008001,"邀请码不存在或失效"),

    PURVIEW_NOT(1004007002,"权限不够"),

    INVITE_FAIL(1004008002,"审核失败"),

    INVITE_IS_TOBE_EXIST(1004008003,"已在家庭待审核列表，请勿重复提交"),
    ;

    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
