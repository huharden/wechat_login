package com.saliai.wechat_3rdparty.message;

/**
 * @Author: chenyiwu
 * @Describtion: 请求返回码统一处理
 * @Create Time:2018/6/7
 */
public enum ResponseType {
    SUCCESS(0, "ok"),

    /**
     * 参数验证
     */
    PARAMETER_ERROR(1000, "业务参数错误"),

    /**
     * 通用错误
     */
    COMMOEM_ERROR(-1, "程序内部错误"),
    /**
     * 通用错误
     */
    TIME_ERROR(1, "请校验本地时间"),
    /**
     * 通用错误
     */
    SIGN_ERROR(2, "签名错误"),
    /**
     * 通用错误
     */
    PARSE_ERROR(3, "解密参数错误"),
    /**
     * 通用错误
     */
    DER_ERROR(4, "解密参数错误"),
    /**
     * 通用错误
     */
    UNKNOWN(5, "未知错误"),

    /**
     * 业务错误 从1001开始
     */
    SORE_ERROR(1001, ""),

    UPDATE_ERROR(1002, "更新失败"),

    UPDATE_SUCCESS(1111, "数据更新成功"),

    EXIST_ERROR(1003, "数据已存在"),

    NOTEXIST_ERROR(1004, "数据不存在"),

    LOGIN_ERROR(1005, "帐号或密码错误, 登录失败!"),

    INSERT_ERROR(1006, "插入数据失败"),

    SELECT_ERROR(1101, "获取数据失败!"),

    ACCOUNT_ERROR(1102, "账户不允许登录!"),

    ACCOUNT_REFUND_ERROR(1502, "退款失败!");

    private int code;

    private String message;

    ResponseType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public static ResponseType parse(int code) {
        ResponseType[] types = ResponseType.values();
        for (ResponseType type : types) {
            if (type.code() == code) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
