package com.saliai.wechat_3rdparty.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.saliai.wechat_3rdparty.bean.PropertiesBean;
import com.saliai.wechat_3rdparty.service.CommonService;
import com.saliai.wechat_3rdparty.service.WechatAuthService;
import com.saliai.wechat_3rdparty.utils.WXBizMsgCrypt;
import com.saliai.wechat_3rdparty.utils.XMLParse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhangzhk
 * @Description:
 * @Date: 2018/5/30 10:46
 * @Modify By:
 */

@Service
public class CommonServiceImpl implements CommonService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PropertiesBean propertiesBean;

    @Autowired
    private WechatAuthService wechatAuthService;

    /**
     * 微信公众号开发域名、小程序业务域名校验
     *
     * @param request
     * @param response
     */
    @Override
    public void wechatVerification(HttpServletRequest request, HttpServletResponse response) {
        FileInputStream fileInputStream = null;
        OutputStream outputStream = null;
        response.setContentType("multipart/form-data");
        try {
            fileInputStream = new FileInputStream(new File("/soft/wechat_files/1294769665.txt"));
//            fileInputStream = new FileInputStream(new File("D:/1294769665.txt"));
            outputStream = response.getOutputStream();
            int content = 0;
            byte[] buffer = new byte[1024 * 8];
            if ((content = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, content);
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
     * @return
     */
    @Override
    public String auth(String signature, String timestamp, String nonce, String encrypt_type, String msg_signature, String postData) {
        String postData_Str = postData.toString().replace("AppId", "ToUserName");
        WXBizMsgCrypt pc = null;
        try {
            pc = new WXBizMsgCrypt(propertiesBean.getToken(), propertiesBean.getEncodingAesKey(), propertiesBean.getAppId());
            String result = pc.decryptMsg(msg_signature, timestamp, nonce, postData_Str);
            logger.info(" >>>>>> 解密后PostData数据: " + result + " <<<<<< ");
            Map<String, String> map = XMLParse.xmlToMap(result);
            //
            String InfoType = map.get("InfoType");
            if ("component_verify_ticket".equals(InfoType)) { //推送component_verify_ticket协议
                String component_verify_ticket = map.get("ComponentVerifyTicket");
                redisTemplate.opsForValue().set("component_verify_ticket", component_verify_ticket);
            } else if ("unauthorized".equals(InfoType)) {  //unauthorized是取消授权
                //移除Redis中保存的信息
                String AuthorizerAppid = map.get("AuthorizerAppid");
                if(redisTemplate.hasKey(AuthorizerAppid+"_access_token")){
                    //移除
                    logger.info(" >>>>>> 删除key值为【"+AuthorizerAppid+"_access_token"+"】数据 <<<<<< ");
                    redisTemplate.delete(AuthorizerAppid+"_access_token");
                }
                logger.info(" >>>>>> 删除key值为【"+AuthorizerAppid+"】数据 <<<<<< ");
                redisTemplate.delete(AuthorizerAppid);
            } else if ("updateauthorized".equals(InfoType)) {  //updateauthorized是更新授权
                //TODO

            } else if ("authorized".equals(InfoType)) {  //authorized是授权成功通知
                //TODO

            } else {  //其他类型
                //TODO

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * Https请求获取第三方component_access_token
     *
     * @return
     */
    public JSONObject getComponentAccessTokenByHttps() {
        String component_verify_ticket = redisTemplate.opsForValue().get("component_verify_ticket").toString();
        JSONObject token_json = new JSONObject();
        token_json.put("component_appid", propertiesBean.getAppId());
        token_json.put("component_appsecret", propertiesBean.getAppSecret());
        token_json.put("component_verify_ticket", component_verify_ticket);
        JSONObject token_result_json = restTemplate.postForEntity(propertiesBean.getComponent_access_token_url(), token_json, JSONObject.class).getBody();
        logger.info(" >>>>>> 获取第三方平台component_access_token返回结果: " + token_result_json.toJSONString() + " <<<<<< ");
        return token_result_json;
    }

    /**
     * 获取component_access_token
     *
     * @return
     */
    public String getComponentAccessToken() {
        String component_access_token = "";
        String expires_in = "";
        if (redisTemplate.hasKey("component_access_token")) {
            logger.info(" >>>>>> component_access_token存在，执行下一步 <<<<<< ");
            logger.info(" >>>>>> component_access_token剩余时间: " + redisTemplate.getExpire("component_access_token", TimeUnit.SECONDS) + " <<<<<< ");
            if (redisTemplate.getExpire("component_access_token", TimeUnit.SECONDS) < 30) {
                logger.info(" >>>>>> component_access_token即将过期，执行重新获取操作 <<<<<< ");
                JSONObject jsonObject = getComponentAccessTokenByHttps();
                component_access_token = jsonObject.getString("component_access_token");
                expires_in = jsonObject.getString("expires_in");
                logger.info(" >>>>>> component_access_token放入redis缓存 <<<<<< ");
                redisTemplate.opsForValue().set("component_access_token", component_access_token, Integer.parseInt(expires_in), TimeUnit.SECONDS);
            } else {
                logger.info(" >>>>>> component_access_token尚未过期，从缓存中读取 <<<<<<");
                return redisTemplate.opsForValue().get("component_access_token").toString();
            }
        } else {
            logger.info(" >>>>>> component_access_token不存在，执行获取操作 <<<<<< ");
            JSONObject jsonObject = getComponentAccessTokenByHttps();
            component_access_token = jsonObject.getString("component_access_token");
            expires_in = jsonObject.getString("expires_in");
            logger.info(" >>>>>> component_access_token放入redis缓存 <<<<<< ");
            redisTemplate.opsForValue().set("component_access_token", component_access_token, Integer.parseInt(expires_in), TimeUnit.SECONDS);
        }
        return component_access_token;
    }

    /**
     * 获取预授权码pre_auth_code
     *
     * @return
     */
    @Override
    public String getPreAuthCode() {
        String component_access_token = getComponentAccessToken();
        JSONObject code_json = new JSONObject();
        code_json.put("component_appid", propertiesBean.getAppId());
        JSONObject code_result_json = restTemplate.postForEntity(propertiesBean.getPre_auth_code_url() + component_access_token, code_json, JSONObject.class).getBody();
        logger.info(" >>>>>> 获取预授权码pre_auth_code返回结果: " + code_result_json + " <<<<<< ");
        String pre_auth_code = code_result_json.getString("pre_auth_code"); //  预授权码
        String expires_in = code_result_json.getString("expires_in");
        return pre_auth_code;
    }

    /**
     * 消息与事件接收回调
     *
     * @param appid
     * @param signature
     * @param timestamp
     * @param nonce
     * @param encrypt_type
     * @param msg_signature
     * @param postData
     * @return
     */
    @Override
    public String callback(String appid, String signature, String timestamp, String nonce, String encrypt_type, String msg_signature, String postData) {
        logger.info(">>>>>> 消息与事件接收回调请求： "+postData+" <<<<<< ");
        WXBizMsgCrypt pc = null;
        try {
            pc = new WXBizMsgCrypt(propertiesBean.getToken(), propertiesBean.getEncodingAesKey(), propertiesBean.getAppId());
            String result = pc.decryptMsg(msg_signature, timestamp, nonce, postData);
            logger.info(" >>>>>> 解密后PostData数据: " + result + " <<<<<< ");
            Map<String, String> map = XMLParse.xmlToMap(result);

            String MsgType = map.get("MsgType");
            if("event".equals(MsgType)){    //事件
                logger.info(">>>>>> 事件回调  <<<<<<");
                String Event = map.get("Event");
                if("weapp_audit_success".equals(Event)){
                    //TODO  审核通过

                }else if("weapp_audit_fail".equals(Event)){
                    //TODO 审核不通过
                    String Reason = map.get("Reason");
                    logger.info(">>>>>> 审核失败原因："+Reason+"  <<<<<<");

                }else{
                    //其他事件类型


                }

            }else if("text".equals(MsgType) && "gh_8dad206e9538".equals(map.get("ToUserName"))){  //全网发布测试回复客服消息
                logger.info(">>>>>> 文本消息回调  <<<<<<");
                //获取授权码
                String codeStr = map.get("Content");
                String query_auth_code = codeStr.substring(codeStr.indexOf(":")+1);
                String authorizer_access_token = wechatAuthService.queryAuth(query_auth_code,"1800");

                // 回复客服信息(调用发送客服消息API)
                logger.info(">>>>>> 回复客服信息 <<<<<<");
                String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+authorizer_access_token;

                JSONObject text = new JSONObject();
                text.put("content",query_auth_code+"_from_api");
                JSONObject json = new JSONObject();
                json.put("touser",map.get("FromUserName")); //发送者的openid
                json.put("msgtype","text");
                json.put("text",text);
                restTemplate.postForEntity(url,json,JSONObject.class);

            }else{
                logger.info(">>>>>> 消息回调  <<<<<<");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
}
