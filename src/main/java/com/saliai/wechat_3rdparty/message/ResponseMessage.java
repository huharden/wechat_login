package com.saliai.wechat_3rdparty.message;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author:chenyiwu
 * @Describtion:
 * @Create Time:2018/6/7
 */
@Data
public class ResponseMessage implements Serializable {
    private static final long serialVersionUID = 1636572609235632727L;
    /**
     * 状态码
     */
    private int code = 200;
    /**
     * 消息
     */
    private String message = "";

    /**
     * 是否成功
     */
    private boolean success = true;
    /**
     * 结果集
     */
    private Object result;

    public ResponseMessage(int code, String message, Object result,
                           boolean success) {
        this.code = code;
        this.message = message;
        this.result = result;
        this.success = success;
    }

    public ResponseMessage(int code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public ResponseMessage() {

    }

    public ResponseMessage(Object result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

}
