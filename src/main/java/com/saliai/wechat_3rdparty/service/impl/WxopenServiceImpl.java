package com.saliai.wechat_3rdparty.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.saliai.wechat_3rdparty.bean.PropertiesBean;
import com.saliai.wechat_3rdparty.bean.WxMessageBean;
import com.saliai.wechat_3rdparty.service.CommonService;
import com.saliai.wechat_3rdparty.service.WechatAuthService;
import com.saliai.wechat_3rdparty.service.WxopenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author:chenyiwu
 * @Describtion: 小程序模板消息实现层
 * @Create Time:2018/6/4
 */
@Service
public class WxopenServiceImpl implements WxopenService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PropertiesBean propertiesBean;

    @Autowired
    private CommonService commonService;

    @Autowired
    private WechatAuthService wechatAuthService;

    /**
     * 功能描述: 获取小程序模板库标题列表
     *
     * @auther: Martin、chen
     * @date: 2018/6/4 16:43
     * @param: [authorizer_appid, offset, count]
     * @return: java.lang.String
     */
    @Override
    public String getPersonLibrary(String authorizer_appid, Integer offset, Integer count) {

        String url = "https://api.weixin.qq.com/cgi-bin/wxopen/template/library/list?access_token=";
        //authorizer_access_token 获取
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);

        logger.info(" >>>>>> authorizer_access_token: " + authorizer_access_token + " <<<<<< ");
        logger.info(" >>>>>> 请求的url: " + url + authorizer_access_token + "<<<<<<< ");
        //设置请求获取的开始位置offset以及获取的条数count
        JSONObject params = new JSONObject();
        if (offset < 0) {
            offset = 0;
        }
        if (count > 20 || count < 0) {
            count = 10;
        }
        params.put("offset", offset);
        params.put("count", count);

        JSONObject result_json = restTemplate.postForEntity(url + authorizer_access_token, params, JSONObject.class).getBody();

        logger.info(" >>>>>> 返回获取小程序模板库标题列表: " + result_json.toJSONString() + " <<<<<< ");

        return result_json.toString();
    }

    /**
     * 功能描述:获取模板库某个模板标题下关键词库
     *
     * @auther: Martin、chen
     * @date: 2018/6/4 17:02
     * @param: [authorizer_appid, titleId]
     * @return: java.lang.String
     */
    @Override
    public String getLibraryTitle(String authorizer_appid, String titleId) {
        String url = "https://api.weixin.qq.com/cgi-bin/wxopen/template/library/get?access_token=";
        //authorizer_access_token 获取
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);

        logger.info(" >>>>>> authorizer_access_token: " + authorizer_access_token + " <<<<<< ");
        logger.info(" >>>>>> 请求的url: " + url + authorizer_access_token + "<<<<<<< ");

        JSONObject param = new JSONObject();
        param.put("id", titleId);
        JSONObject result_json = restTemplate.postForEntity(url + authorizer_access_token, param, JSONObject.class).getBody();

        return result_json.toString();
    }

    /**
     * 功能描述: 组合模板并添加至帐号下的个人模板库
     *
     * @auther: Martin、chen
     * @date: 2018/6/4 17:13
     * @param: [authorizer_appid, titleId, keyword_id_list]
     * @return: java.lang.String
     */
    @Override
    public String addTemplateLibrary(String authorizer_appid, String titleId, int[] keyword_id_list) {
        String url = "https://api.weixin.qq.com/cgi-bin/wxopen/template/add?access_token";
        //authorizer_access_token 获取
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);

        logger.info(" >>>>>> authorizer_access_token: " + authorizer_access_token + " <<<<<< ");
        logger.info(" >>>>>> 请求的url: " + url + authorizer_access_token + "<<<<<<< ");

        JSONObject params = new JSONObject();
        params.put("id", titleId);
        params.put("keyword_id_list", keyword_id_list);

        JSONObject result_json = restTemplate.postForEntity(url + authorizer_access_token, params, JSONObject.class).getBody();

        return result_json.toString();
    }

    /**
     * 功能描述:获取帐号下已存在的模板列表
     *
     * @param offset 开始标志
     * @param count  返回的条数（最大20）
     * @auther: Martin、chen
     * @date: 2018/6/5 10:03
     * @return: java.lang.String
     */
    @Override
    public String getTemplateList(String authorizer_appid, Integer offset, Integer count) {
        //authorizer_access_token 获取
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
        // String componentAccessToken = commonService.getComponentAccessToken();
        logger.info(" >>>>>> authorizer_access_token: " + authorizer_access_token + " <<<<<< ");
        String url = "https://api.weixin.qq.com/cgi-bin/wxopen/template/list?access_token=";
        logger.info(" >>>>>> 请求的url: " + url + authorizer_access_token + "<<<<<<< ");

        JSONObject params = new JSONObject();
        params.put("offset", offset);
        params.put("count", count);

        JSONObject result_json = restTemplate.postForEntity(url + authorizer_access_token, params, JSONObject.class).getBody();
        logger.info(" >>>>>>获取帐号下已存在的模板列表返回结果: " + result_json.toString() + "<<<<<<< ");

        return result_json.toString();
    }

    /**
     * 功能描述:删除帐号下的某个模板
     *
     * @param templateId 模板id
     * @auther: Martin、chen
     * @date: 2018/6/5 10:17
     * @return: java.lang.String
     */
    @Override
    public String deletePlatFormTemplate(String authorizer_appid, String templateId) {

        //获取调用凭证
        //String componentAccessToken = commonService.getComponentAccessToken();
        //authorizer_access_token 获取
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
        logger.info(" >>>>>> authorizer_access_token: " + authorizer_access_token + " <<<<<< ");
        String url = "https://api.weixin.qq.com/cgi-bin/wxopen/template/del?access_token=";
        logger.info(" >>>>>> 请求的url: " + url + authorizer_access_token + "<<<<<<< ");

        JSONObject param = new JSONObject();
        param.put("templateId", templateId);

        JSONObject result_json = restTemplate.postForEntity(url + authorizer_access_token, param, JSONObject.class).getBody();
        logger.info(" >>>>>>删除帐号下的某个模板返回结果: " + result_json.toString() + "<<<<<<< ");
        return result_json.toString();
    }

    /**
     * 功能描述:发送模板消息
     *
     * @param authorizer_appid 小程序appid
     * @param code 登录成功后获得的code，用于获取openid
     * @param wxMessageBean 发送模板消息请求的实体
     * @auther: Martin、chen
     * @date: 2018/6/5 17:53
     * @return: java.lang.String
     */
    @Override
    public String sendTemplateMessage(String authorizer_appid, String code, WxMessageBean wxMessageBean) {
        logger.info(" >>>>>>请求发送的模板消息主体: " + wxMessageBean.toString() + "<<<<<<< ");
        //authorizer_access_token 获取
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
        logger.info(" >>>>>> authorizer_access_token: " + authorizer_access_token + " <<<<<< ");
        logger.info(" >>>>>> 请求的url: " + propertiesBean.getSend_template_message_url() + authorizer_access_token + "<<<<<<< ");

        JSONObject result_json = restTemplate.postForEntity(propertiesBean.getSend_template_message_url() + authorizer_access_token,wxMessageBean, JSONObject.class).getBody();
        logger.info(" >>>>>>发送模板消息返回结果: " + result_json.toString() + "<<<<<<< ");

        return result_json.toString();
    }
}
