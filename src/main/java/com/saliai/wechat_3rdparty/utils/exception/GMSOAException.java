package com.saliai.wechat_3rdparty.utils.exception;

/**
 * @Author:chenyiwu
 * @Describtion:
 * @Create Time:2018/6/8
 */
public class GMSOAException extends RuntimeException {
    private static final long serialVersionUID = -2396344394815479161L;

    public static final String ISP = "ISP";//系统内部
    public static final String ISV = "ISV";//用户导致错误

    private String errorCode;
    private String subCode;
    /**
     * 异常类型
     **/
    private GMSOAExceptionType type;
    /**
     * 异常代码
     **/
    private GMSOAExceptionCode code;
    /**
     * 异常数据
     **/
    private Object errorData;

    public GMSOAException() {
    }

    public GMSOAException(String subCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = ISV;
        this.subCode = subCode;
    }

    public GMSOAException(String subCode, Throwable cause) {
        super(cause);
        this.errorCode = ISV;
        this.subCode = subCode;
    }

    public GMSOAException(GMSOAExceptionCode code, GMSOAExceptionType type, String message, Object errorData) {
        super(message);

        this.type = type;
        this.code = code;
        this.errorData = errorData;
    }

    public GMSOAException(GMSOAExceptionCode code, GMSOAExceptionType type, String message) {
        super(message);

        this.type = type;
        this.code = code;
    }

    public GMSOAException(String subCode, String message) {
        super(message);
        this.errorCode = ISV;
        this.subCode = subCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public GMSOAException setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public GMSOAExceptionType getType() {
        return type;
    }

    public GMSOAExceptionCode getCode() {
        return code;
    }

    public Object getErrorData() {
        return errorData;
    }

}
