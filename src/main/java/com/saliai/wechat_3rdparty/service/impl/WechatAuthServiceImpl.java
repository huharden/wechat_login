package com.saliai.wechat_3rdparty.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.saliai.wechat_3rdparty.bean.PropertiesBean;
import com.saliai.wechat_3rdparty.service.CommonService;
import com.saliai.wechat_3rdparty.service.WechatAuthService;
import com.saliai.wechat_3rdparty.service.WxaService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @Description 授权相关的Service
 * @author：chenyiwu
 * @date 2018/5/29 16:41
 * @Version 1.0
 */
@Service
public class WechatAuthServiceImpl implements WechatAuthService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PropertiesBean propertiesBean;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CommonService commonService;

    @Autowired
    private WxaService wxaService;

    /**
     * 根据授权码获取小程序的接口调用凭据和授权信息
     *
     * @param authCode  授权码
     * @param expiresIn
     * @return  authorizer_access_token授权方调用凭据
     */
    @Override
    public String queryAuth(String authCode, String expiresIn) {
        //判断Redis中component_access_token是否存在
        String component_access_token = "";
        if(redisTemplate.hasKey("component_access_token")){   //存在
            component_access_token = redisTemplate.opsForValue().get("component_access_token").toString();
        }else{
            component_access_token = commonService.getComponentAccessToken();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("component_appid", propertiesBean.getAppId());
        jsonObject.put("authorization_code",authCode);
        JSONObject result_json = restTemplate.postForEntity(propertiesBean.getAuthorizer_access_token_url() + component_access_token,
                jsonObject,JSONObject.class).getBody();
        logger.info(" >>>>>> 获取小程序调用凭据和授权信息返回结果："+result_json+" <<<<<< ");
        JSONObject infoJson = result_json.getJSONObject("authorization_info");
        String authorizer_appid = infoJson.getString("authorizer_appid");
        String authorizer_access_token = infoJson.getString("authorizer_access_token");
        String expires_in = infoJson.getString("expires_in");
        redisTemplate.opsForValue().set(authorizer_appid,infoJson);
        //将authorizer_access_token进行缓存
        redisTemplate.opsForValue().set(authorizer_appid+"_access_token",authorizer_access_token,Integer.valueOf(expires_in),TimeUnit.SECONDS);

        //  设置模版
        String result = wxaService.commit(authorizer_appid, "3");
        logger.info(" >>>>>>> 设置模版结果: " + result + " <<<<<< ");

        return authorizer_access_token;
    }

    /**
     * 刷新授权小程序的接口调用凭据（令牌）
     *
     * @param authorizer_appid 授权方appid
     * @return authorizer_access_token授权方调用凭据
     */
    @Override
    public String refreshAuthorizerAccessToken(String authorizer_appid) {
        return this.refreshAuthorizerAccessToken(authorizer_appid,null);
    }

    /**
     * 刷新授权小程序的接口调用凭据（令牌）
     *
     * @param authorizer_appid         授权方appid
     * @param authorizer_refresh_token 授权方的刷新令牌
     * @return authorizer_access_token授权方调用凭据
     */
    @Override
    public String refreshAuthorizerAccessToken(String authorizer_appid, String authorizer_refresh_token) {
        //根据授权方appid获取授权信息
        String authInfo = redisTemplate.opsForValue().get(authorizer_appid).toString();
        JSONObject authorizationInfo = JSONObject.parseObject(authInfo);

        if(StringUtils.isBlank(authorizer_refresh_token)){  //如果刷新令牌为空则从redis缓存中获取
            authorizer_refresh_token =  authorizationInfo.getString("authorizer_refresh_token");
        }

        //获取component_access_token
        String component_access_token = commonService.getComponentAccessToken();

        //刷新授权小程序的接口调用凭据
        JSONObject paramJson = new JSONObject();
        paramJson.put("component_appid", propertiesBean.getAppId());
        paramJson.put("authorizer_appid",authorizer_appid);
        paramJson.put("authorizer_refresh_token",authorizer_refresh_token);
        JSONObject result_json = restTemplate.postForEntity(propertiesBean.getRefresh_authorizer_token_url() + component_access_token,
                paramJson,JSONObject.class).getBody();
        logger.info(">>>>>> 刷新授权小程序的接口调用凭据返回结果： "+result_json+" <<<<<< ");
        String authorizer_access_token = result_json.getString("authorizer_access_token");
        String expires_in = result_json.getString("expires_in");
        String new_authorizer_refresh_token = result_json.getString("authorizer_refresh_token");

        //将新的凭据信息放到Redis中进行缓存
        authorizationInfo.put("authorizer_access_token",authorizer_access_token);
        authorizationInfo.put("expires_in",expires_in);
        authorizationInfo.put("authorizer_refresh_token",new_authorizer_refresh_token);

        redisTemplate.opsForValue().set(authorizer_appid,authorizationInfo);
        redisTemplate.opsForValue().set(authorizer_appid+"_access_token",authorizer_access_token,Integer.valueOf(expires_in),TimeUnit.SECONDS);
        return authorizer_access_token;
    }

    /**
     * 获取授权方的帐号基本信息
     *
     * @param authorizer_appid 授权方appid
     * @return
     */
    @Override
    public String getAuthotizerInfo(String authorizer_appid) {
        //判断Redis中component_access_token是否存在
        String component_access_token = "";
        if(redisTemplate.hasKey("component_access_token")){   //存在
            component_access_token = redisTemplate.opsForValue().get("component_access_token").toString();
        }else{
            component_access_token = commonService.getComponentAccessToken();
        }
        JSONObject paramJson = new JSONObject();
        paramJson.put("component_appid", propertiesBean.getAppId());
        paramJson.put("authorizer_appid",authorizer_appid);
        JSONObject result_json = restTemplate.postForEntity(propertiesBean.getGet_authorizer_info_url() + component_access_token,
                paramJson,JSONObject.class).getBody();
        logger.info(">>>>>>  获取授权方的帐号基本信息返回结果： "+result_json+" <<<<<< ");

        return result_json.toString();
    }

    /**
     * 获取授权方接口调用凭据
     *
     * @param authorizer_appid 授权方appid
     * @return authorizer_access_token授权方调用凭据
     */
    @Override
    public String getAuthorizerAccessToken(String authorizer_appid) {
        String authorizer_access_token = "";
        logger.info(" >>>>>> 获取appid为【"+authorizer_appid+"】的authorizer_access_token <<<<<< ");
        if(redisTemplate.hasKey(authorizer_appid+"_access_token")){
            if(redisTemplate.getExpire(authorizer_appid+"_access_token",TimeUnit.SECONDS) > 30){
                logger.info(" >>>>>> 从缓存中获取 authorizer_access_token <<<<<< ");
                authorizer_access_token = redisTemplate.opsForValue().get(authorizer_appid+"_access_token").toString();
            }else{
                logger.info(" >>>>>> authorizer_access_token即将过期，执行重新获取操作 <<<<<< ");
                authorizer_access_token = this.refreshAuthorizerAccessToken(authorizer_appid);
            }
        }else{
            logger.info(" >>>>>> 缓存中不存在 authorizer_access_token <<<<<< ");
            authorizer_access_token = this.refreshAuthorizerAccessToken(authorizer_appid);
        }
        return authorizer_access_token;
    }
}
