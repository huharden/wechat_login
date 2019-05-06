package com.saliai.wechat_3rdparty.message;

/**
 * @Author: chenyiwu
 * @Describtion: 返回请求信息基类
 * @Create Time:2018/6/7
 */
public class BaseMessage {

    protected ResponseMessage SUCCESS() {
        return new ResponseMessage();
    }

    protected ResponseMessage SUCCESS(Object result) {
        return new ResponseMessage(result);
    }

    protected ResponseMessage SUCCESS(int code, String message, Object result, boolean success) {
        return new ResponseMessage(code, message, result, success);
    }

    protected ResponseMessage SUCCESS(WxResponseCode wrc) {
        return new ResponseMessage(wrc.code(), wrc.message(), null, false);
    }

    protected ResponseMessage SUCCESS(ResponseType rt) {
        return new ResponseMessage(rt.code(), rt.message(), null, false);
    }

    protected ResponseMessage FAIL(ResponseType rt) {
        return new ResponseMessage(rt.code(), rt.message(), null, false);
    }

    protected ResponseMessage FAIL(WxResponseCode wrc) {
        return new ResponseMessage(wrc.code(), wrc.message(), null, false);
    }

    protected ResponseMessage FAIL(int code, String message) {
        return new ResponseMessage(code, message, null, false);
    }

}
