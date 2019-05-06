package com.saliai.wechat_3rdparty.controller;

import com.saliai.wechat_3rdparty.service.WechatAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description 授权Controller
 * @author：pengkun
 * @date 2018/5/29 16:31
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "/auth")
public class WechatAuthController {

    @Autowired
    private WechatAuthService wechatAuthService;

    /**
     * 根据auth_code查询授权信息（授权回调服务）
     *
     * @param authCode  授权成功时获得的授权码
     * @param expiresIn 存活时间
     * @return
     */
    @RequestMapping(value = "/queryAuth")
//    @ResponseBody
    public String queryAuth(@RequestParam("auth_code") String authCode, @RequestParam("expires_in") String expiresIn) {
        String authorizer_access_token = wechatAuthService.queryAuth(authCode, expiresIn);
        return "redirect:/wxa/getQrcode/" + authorizer_access_token;
    }

    /**
     * 刷新授权小程序的接口调用凭据（令牌）
     *
     * @param authorizer_appid 授权方appId
     * @return
     */
    @RequestMapping(value = "/refreshAccessToken")
    @ResponseBody
    public String refreshAuthorizerAccessToken(@RequestParam("authorizer_appid") String authorizer_appid) {
        return wechatAuthService.refreshAuthorizerAccessToken(authorizer_appid);
    }

    /**
     * 获取授权方的帐号基本信息
     *
     * @param authorizer_appid
     * @return
     */
    @RequestMapping(value = "/getAuthotizerInfo")
    @ResponseBody
    public String getAuthotizerInfo(@RequestParam("authorizer_appid") String authorizer_appid) {
        return wechatAuthService.getAuthotizerInfo(authorizer_appid);
    }

    /**
     * 获取授权方的authorizer_access_token
     *
     * @param authorizer_appid 授权方AppID
     * @return
     */
    @RequestMapping(value = "/getAccessToken")
    @ResponseBody
    public String getAccessToken(@RequestParam("authorizer_appid") String authorizer_appid) {
        return wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
    }

}
