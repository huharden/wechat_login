package com.saliai.wechat_3rdparty.controller;

import com.saliai.wechat_3rdparty.service.CommonService;
import com.saliai.wechat_3rdparty.utils.AesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: zhangzhk
 * @Description:
 * @Date: 2018/5/30 10:42
 * @Modify By:
 */

@Controller
public class CommonController {

    @Autowired
    private CommonService commonService;

    /**
     * 微信公众号开发域名、小程序业务域名校验
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/1294769665.txt")
    public void wechatVerification(HttpServletRequest request, HttpServletResponse response) {
        commonService.wechatVerification(request, response);
    }

    /**
     * 获取第三方平台接口调用凭据component_verify_ticket
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param encrypt_type
     * @param msg_signature
     * @param postData
     */
    @RequestMapping(value = "/auth")
    @ResponseBody
    public String auth(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce, @RequestParam("encrypt_type") String encrypt_type,
                       @RequestParam("msg_signature") String msg_signature, @RequestBody String postData) throws AesException {
        return commonService.auth(signature, timestamp, nonce, encrypt_type, msg_signature, postData);
    }

    /**
     * 获取预授权码pre_auth_code
     *
     * @return
     */
    @RequestMapping(value = "/preAuthCode")
    @ResponseBody
    public String getPreAutoCode() {
        return commonService.getPreAuthCode();
    }


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
    @RequestMapping(value = "/{appid}/callback")
    @ResponseBody
    public String callback(@PathVariable String appid,@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
                           @RequestParam("nonce") String nonce, @RequestParam("encrypt_type") String encrypt_type,
                           @RequestParam("msg_signature") String msg_signature, @RequestBody String postData){
        return commonService.callback(appid,signature,timestamp,nonce,encrypt_type,msg_signature,postData);
    }
}
