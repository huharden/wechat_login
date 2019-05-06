package com.saliai.wechat_3rdparty.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: zhangzhk
 * @Description:
 * @Date: 2018/5/30 10:45
 * @Modify By:
 */
public interface CommonService {

    /**
     * 微信公众号开发域名、小程序业务域名校验
     *
     * @param request
     * @param response
     */
    public void wechatVerification(HttpServletRequest request, HttpServletResponse response);

    /**
     * 获取第三方平台接口调用凭据component_verify_ticket
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param encrypt_type
     * @param msg_signature
     * @param postData
     * @return
     */
    public String auth(String signature, String timestamp, String nonce, String encrypt_type, String msg_signature, String postData);

    /**
     * 获取预授权码pre_auth_code
     *
     * @return
     */
    public String getPreAuthCode();

    /**
     * 获取component_access_token
     *
     * @return
     */
    public String getComponentAccessToken();

    /**
     * 消息与事件接收回调
     * @param appid
     * @param signature
     * @param timestamp
     * @param nonce
     * @param encrypt_type
     * @param msg_signature
     * @param postData
     * @return
     */
    public String callback(String appid,String signature, String timestamp, String nonce, String encrypt_type, String msg_signature, String postData);
}
