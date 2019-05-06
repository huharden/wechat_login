package com.saliai.wechat_3rdparty.service;

/**
 * @Description 授权服务
 * @author：pengkun
 * @date 2018/5/29 16:37
 * @Version 1.0
 */
public interface WechatAuthService {

    /**
     *  根据授权码获取小程序的接口调用凭据和授权信息
     * @param authCode  授权码
     * @param expiresIn
     * @return
     */
    public String queryAuth(String authCode,String expiresIn);

    /**
     * 刷新授权小程序的接口调用凭据（令牌）
     * @param authorizer_appid  授权方appid
     * @return  authorizer_access_token授权方调用凭据
     */
    public String refreshAuthorizerAccessToken(String authorizer_appid);

    /**
     * 刷新授权小程序的接口调用凭据（令牌）
     * @param authorizer_appid  授权方appid
     * @param authorizer_refresh_token  授权方的刷新令牌
     * @return  authorizer_access_token授权方调用凭据
     */
    public String refreshAuthorizerAccessToken(String authorizer_appid,String authorizer_refresh_token);

    /**
     * 获取授权方的帐号基本信息
     * @param authorizer_appid  授权方appid
     * @return
     */
    public String getAuthotizerInfo(String authorizer_appid);

    /**
     * 获取授权方接口调用凭据
     * @param authorizer_appid  授权方appid
     * @return  authorizer_access_token授权方调用凭据
     */
    public String getAuthorizerAccessToken(String authorizer_appid);


}
