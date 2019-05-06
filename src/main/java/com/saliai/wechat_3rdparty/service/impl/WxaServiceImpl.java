package com.saliai.wechat_3rdparty.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.saliai.wechat_3rdparty.bean.ItemListBean;
import com.saliai.wechat_3rdparty.bean.PropertiesBean;
import com.saliai.wechat_3rdparty.service.CommonService;
import com.saliai.wechat_3rdparty.service.WechatAuthService;
import com.saliai.wechat_3rdparty.service.WxaService;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import org.codehaus.xfire.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhangzhk
 * @Description:
 * @Date: 2018/5/29 16:24
 * @Modify By:
 */

@Service
public class WxaServiceImpl implements WxaService {
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
     * 设置小程序服务器域名
     *
     * @param authorizer_appid 授权方appid
     * @param action           add添加, delete删除, set覆盖, get获取。当参数是get时不需要填四个域名字段
     * @param requestdomain    request合法域名，当action参数是get时不需要此字段
     * @param wsrequestdomain  socket合法域名，当action参数是get时不需要此字段
     * @param uploaddomain     uploadFile合法域名，当action参数是get时不需要此字段
     * @param downloaddomain   downloadFile合法域名，当action参数是get时不需要此字段
     * @return
     */
    @Override
    public String modifyDomain(String authorizer_appid, String action, String requestdomain, String wsrequestdomain, String uploaddomain, String downloaddomain) {
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
        JSONObject paramJson = new JSONObject();
        paramJson.put("action", action);
        if (StringUtils.isNotBlank(requestdomain)) {
            String[] str =  requestdomain.split(",");
            paramJson.put("requestdomain", str);
        }
        if (StringUtils.isNotBlank(wsrequestdomain)) {
            String[] str =  wsrequestdomain.split(",");
            paramJson.put("wsrequestdomain", str);
        }
        if (StringUtils.isNotBlank(uploaddomain)) {
            String[] str =  uploaddomain.split(",");
            paramJson.put("uploaddomain", str);
        }
        if (StringUtils.isNotBlank(downloaddomain)) {
            String[] str =  downloaddomain.split(",");
            paramJson.put("downloaddomain", str);
        }
        logger.info("——————》"+paramJson.toString());
        JSONObject result_json = restTemplate.postForEntity(propertiesBean.getModify_domain_url() + authorizer_access_token,
                paramJson, JSONObject.class).getBody();
        logger.info(">>>>>>  设置小程序服务器域名返回结果： " + result_json + " <<<<<< ");

        /**
         {
         "errcode":0,
         "errmsg":"ok",
         //以下字段仅在get时返回
         "requestdomain":["https://www.qq.com","https://www.qq.com"],
         "wsrequestdomain":["wss://www.qq.com","wss://www.qq.com"],
         "uploaddomain":["https://www.qq.com","https://www.qq.com"],
         "downloaddomain":["https://www.qq.com","https://www.qq.com"],
         }
         85015	该账号不是小程序账号
         85016	域名数量超过限制
         85017	没有新增域名，请确认小程序已经添加了域名或该域名是否没有在第三方平台添加
         85018	域名没有在第三方平台设置
         */
        return result_json.toString();
    }

    /**
     * 设置小程序业务域名
     *
     * @param authorizer_appid 授权方appid
     * @param action           add添加, delete删除, set覆盖, get获取。当参数是get时不需要填webviewdomain字段。如果没有action字段参数，则默认见开放平台第三方登记的小程序业务域名全部添加到授权的小程序中
     * @param webviewdomain    小程序业务域名，当action参数是get时不需要此字段
     * @return
     */
    @Override
    public String setWebviewDomain(String authorizer_appid, String action, String webviewdomain) {
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
        JSONObject paramJson = new JSONObject();
        if (StringUtils.isNotBlank(action)) {
            paramJson.put("action", action);
        }
        if (StringUtils.isNotBlank(webviewdomain)) {
            paramJson.put("webviewdomain", webviewdomain);
        }
        JSONObject result_json = restTemplate.postForEntity(propertiesBean.getSet_webview_domain_url() + authorizer_access_token,
                paramJson, JSONObject.class).getBody();
        logger.info(">>>>>> 设置小程序业务域名返回结果： " + result_json + " <<<<<< ");

        /**
         {
         "errcode":0,
         "errmsg":"ok",
         }
         89019	业务域名无更改，无需重复设置
         89020	尚未设置小程序业务域名，请先在第三方平台中设置小程序业务域名后在调用本接口
         89021	请求保存的域名不是第三方平台中已设置的小程序业务域名或子域名
         89029	业务域名数量超过限制
         89231	个人小程序不支持调用setwebviewdomain 接口
         */
        return result_json.toString();
    }

    /**
     * 使用登录凭证 code 以及第三方平台的component_access_token 获取 session_key 和 openid
     *
     * @param authorizer_appid 授权方AppID
     * @param code             登录凭证 code
     * @return
     */
    @Override
    public String getSessionKey(String authorizer_appid, String code) {
        String component_appid = propertiesBean.getAppId();
        String component_access_token = commonService.getComponentAccessToken();
        String url = "https://api.weixin.qq.com/sns/component/jscode2session?appid=" + authorizer_appid + "&js_code=" + code + "&grant_type=authorization_code" +
                "&component_appid=" + component_appid + "&component_access_token=" + component_access_token;
        /**
         * 由于返回的数据content type [text/plain]   与文档中不一致，用fastJson直接接收会报错，所以先用String类型接收，在进行转换
         */
//        JSONObject result_json = restTemplate.postForEntity(url,null,JSONObject.class).getBody();
        String result_json = restTemplate.postForEntity(url, null, String.class).getBody();
        JSONObject return_json = JSONObject.parseObject(result_json);
        logger.info(" >>>>>>获取 session_key 和 openid返回结果: " + return_json + " <<<<<< ");
        return return_json.toString();
    }

    /**
     * 绑定微信用户为小程序体验者
     *
     * @param authorizer_appid 授权方appid
     * @param wechatid         微信号
     * @return
     */
    @Override
    public String bindTester(String authorizer_appid, String wechatid) {
        JSONObject jsonObject = JSONObject.parseObject(redisTemplate.opsForValue().get(authorizer_appid).toString());
        logger.info(" >>>>>> 缓存中的小程序: " + jsonObject.toJSONString() + " <<<<<< ");
        JSONObject param_json = new JSONObject();
        param_json.put("wechatid", wechatid);
        //authorizer_access_token 获取修改
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
        logger.info(" >>>>>> 请求的url: " + propertiesBean.getBind_tester_url() + authorizer_access_token + "<<<<<<< ");
        JSONObject result_json = restTemplate.postForEntity(propertiesBean.getBind_tester_url() + authorizer_access_token,
                param_json, JSONObject.class).getBody();
        logger.info(" >>>>>> 绑定微信用户为小程序体验者返回结果: " + result_json.toJSONString() + " <<<<<< ");
        return result_json.toString();
    }

    /**
     * 为授权的小程序帐号上传小程序代码
     *
     * @param extAppid    授权方Appid
     * @param template_id 小程序模版id
     * @return
     */
    @Override
    public String commit(String extAppid, String template_id) {
        JSONObject post_json = new JSONObject();
        StringBuffer stringBuffer = new StringBuffer();
        try {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("/soft/wechat_miniprogram/template/template_3.txt"), "UTF-8"));
            String s = null;
            while ((s = bufferedReader.readLine()) != null) {
                stringBuffer.append(s.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        post_json.put("template_id", 3);
        post_json.put("ext_json", stringBuffer.toString());
        post_json.put("user_version", "V1.0");
        post_json.put("user_desc", "test");
        logger.info(" >>>>>> 代码管理接口post_json: " + post_json + " <<<<<< ");
        //authorizer_access_token 获取修改
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(extAppid);
        String url = propertiesBean.getCommit_url() + authorizer_access_token;
        logger.info(" >>>>>> 代码管理接口url: " + url + " <<<<<< ");
        JSONObject callback_json = restTemplate.postForEntity(url, post_json, JSONObject.class).getBody();

        return callback_json.toString();
    }

    /**
     * 获取体验小程序的体验二维码
     *
     * @param authorizer_access_token
     * @param response
     */
    @Override
    public void getQrcode(String authorizer_access_token, HttpServletResponse response) {
        logger.info(" >>>>> 开始调用获取体验小程序的体验二维码接口 <<<<<< ");
        String url = "https://api.weixin.qq.com/wxa/get_qrcode?access_token=" + authorizer_access_token;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        ResponseEntity<byte[]> entity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<byte[]>(headers), byte[].class);
        byte[] result = entity.getBody();
        logger.info(" >>>>>> 返回结果: " + result + " <<<<<< ");
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            inputStream = new ByteArrayInputStream(result);
            int content = 0;
            byte[] buffer = new byte[1024 * 8];
            if ((content = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, content);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 功能描述:发布小程序代码
     * @auther: Martin、chen
     * @date: 2018/5/30 15:39
     * @param: [authorizer_appid] 授权方appid
     * @return: java.lang.String
     */
    /**
     * 返回码	        说明
     * -1	           系统繁忙
     * 85019	       没有审核版本
     * 85020	       审核状态未满足发布
     */
    @Override
    public String release(String authorizer_appid) {
        String url = "https://api.weixin.qq.com/wxa/release?access_token=";
        JSONObject param_json = new JSONObject();
        //authorizer_access_token 获取修改
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
        logger.info(" >>>>>> 请求的url: " + url + authorizer_access_token + "<<<<<<< ");
        JSONObject result_json = restTemplate.postForEntity(url + authorizer_access_token,
                param_json, JSONObject.class).getBody();
        logger.info(" >>>>>> 发布已通过审核的小程序返回结果: " + result_json.toJSONString() + " <<<<<< ");
        return result_json.toString();
    }

    /**
     * 功能描述: 修改线上小程序代码是否可见
     *
     * @auther: Martin、chen
     * @date: 2018/5/30 16:11
     * @param: [authorizer_appid, action]
     * @return: java.lang.String
     * 返回码	说明
     * -1	   系统繁忙
     * 85021   状态不可变
     * 85022   action非法
     */
    @Override
    public String changeVisitStatus(String authorizer_appid, String action) {
        String url = "https://api.weixin.qq.com/wxa/change_visitstatus?access_token=";
        //从redis缓存中先回去授权时存放的调用凭证以及授权信息，以authorizer_appid 授权方appid进行缓存
        //JSONObject jsonObject = JSONObject.parseObject(redisTemplate.opsForValue().get(authorizer_appid).toString());
       // logger.info(" >>>>>> 缓存中的小程序的authorizer_access_token为: " + jsonObject.toJSONString() + " <<<<<< ");
        JSONObject param_json = new JSONObject();
        param_json.put("action", action);
        //authorizer_access_token 获取修改
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
        logger.info(" >>>>>> 请求的url: " + url + authorizer_access_token + "<<<<<<< ");
        JSONObject result_json = restTemplate.postForEntity(url + authorizer_access_token,
                param_json, JSONObject.class).getBody();
        logger.info(" >>>>>> 修改小程序线上代码的可见状态返回结果: " + result_json.toJSONString() + " <<<<<< ");
        return result_json.toString();
    }

    /**
     * 功能描述: 小程序版本回退
     *
     * @auther: Martin、chen
     * @date: 2018/5/30 16:45
     * @param: [authorizer_appid]
     * @return: java.lang.String
     */
    @Override
    public String revertcodeRelease(String authorizer_appid) {
        String url = "https://api.weixin.qq.com/wxa/revertcoderelease?access_token=";
        //获取缓存中的值
        //JSONObject jsonObject = JSONObject.parseObject(redisTemplate.opsForValue().get(authorizer_appid).toString());
       // logger.info(" >>>>>> 缓存中的小程序的authorizer_access_token为: " + jsonObject.toJSONString() + " <<<<<< ");
        //authorizer_access_token 获取修改
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
        logger.info(" >>>>>> 请求的url: " + url + authorizer_access_token + "<<<<<<< ");
        JSONObject result_json = restTemplate.getForEntity(url + authorizer_access_token, JSONObject.class).getBody();
        logger.info(" >>>>>> 小程序回退结果: " + result_json.toJSONString() + " <<<<<< ");
        return result_json.toString();
    }

    /**
     * 功能描述: 获取草稿箱中的小程序的模板/草稿箱中的代码
     *
     * @auther: Martin、chen
     * @date: 2018/5/30 17:03
     * @param: [type] 0：获取草稿箱中的模板代码，1：获取模板代码
     * @return: java.lang.String
     */
    @Override
    public String getTemplateList(Integer type) {
        String url = "";
        String componentAccessToken = commonService.getComponentAccessToken();
        if (type == 0) {
            url = "https://api.weixin.qq.com/wxa/gettemplatedraftlist?access_token=";
        } else {
            url = "https://api.weixin.qq.com/wxa/gettemplatelist?access_token=";
        }

        logger.info(" >>>>>> component_access_token为: " + componentAccessToken + " <<<<<< ");
        logger.info(" >>>>>> 请求的url: " + url + componentAccessToken + "<<<<<<< ");
        JSONObject result_json = restTemplate.getForEntity(url + componentAccessToken, JSONObject.class).getBody();
        logger.info(" >>>>>> 返回小程序模板集: " + result_json.toJSONString() + " <<<<<< ");

        return result_json.toString();
    }

    /**
     * 功能描述:将草稿箱中的模板代码设置为正式模板
     *
     * @auther: Martin、chen
     * @date: 2018/5/30 17:20
     * @param: [draft_id] 草稿ID
     * @return: java.lang.String
     */
    @Override
    public String addtoTemplate(Integer draft_id) {
        String url = "https://api.weixin.qq.com/wxa/addtotemplate?access_token=";
        //获取第三方平台的component_access_token
        String componentAccessToken = commonService.getComponentAccessToken();
        logger.info(" >>>>>> component_access_token为: " + componentAccessToken + " <<<<<< ");
        logger.info(" >>>>>> 请求的url: " + url + componentAccessToken + "<<<<<<< ");
        JSONObject param = new JSONObject();
        param.put("draft_id", draft_id);
        JSONObject result_json = restTemplate.postForEntity(url + componentAccessToken, param, JSONObject.class).getBody();
        logger.info(" >>>>>> 将草稿箱中的模板设置为正式的小程序模板返回结果: " + result_json.toJSONString() + " <<<<<< ");
        return result_json.toString();

    }

    /**
     * 功能描述: 删除小程序模板
     *
     * @auther: Martin、chen
     * @date: 2018/5/30 17:22
     * @param: [template_id]
     * @return: java.lang.String
     */
    @Override
    public String deleteTemplate(Integer template_id) {
        String url = "https://api.weixin.qq.com/wxa/deletetemplate?access_token=";
        String componentAccessToken = commonService.getComponentAccessToken();
        logger.info(" >>>>>> component_access_token为: " + componentAccessToken + " <<<<<< ");
        logger.info(" >>>>>> 请求的url: " + url + componentAccessToken + "<<<<<<< ");
        JSONObject param = new JSONObject();
        param.put("template_id", template_id);
        JSONObject result_json = restTemplate.postForEntity(url + componentAccessToken, param, JSONObject.class).getBody();
        logger.info(" >>>>>> 小程序模板删除返回结果: " + result_json.toJSONString() + " <<<<<< ");
        return result_json.toString();
    }

    /**
     * 微信小程序解密用户敏感数据获取用户信息
     *
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param sessionKey    数据进行加密签名的密钥
     * @param iv            加密算法的初始向量
     * @return 用户信息数据
     */
    @Override
    public String getUserInfo(String encryptedData, String sessionKey, String iv) {
        String result = "";
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base
                        + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters
                    .getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                result = new String(resultByte, "UTF-8");
                logger.info(" >>>>>> 解密用户敏感数据获取用户信息返回结果：" + result + " <<<<<< ");
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        } catch (NoSuchPaddingException e) {
            logger.error(e.getMessage(), e);
        } catch (InvalidParameterSpecException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalBlockSizeException e) {
            logger.error(e.getMessage(), e);
        } catch (BadPaddingException e) {
            logger.error(e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        } catch (InvalidKeyException e) {
            logger.error(e.getMessage(), e);
        } catch (InvalidAlgorithmParameterException e) {
            logger.error(e.getMessage(), e);
        } catch (NoSuchProviderException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 功能描述:将第三方提交的代码包提交审核
     *
     * @auther: Martin、chen
     * @date: 2018/5/31 9:57
     * @param: [authorizer_appid]
     * @return: java.lang.String
     */
    @Override
    public String submitAudit(String authorizer_appid, ItemListBean[] item_list) {
        logger.info(" >>>>>> 请求的item_list: " + item_list + " <<<<<<< ");
        logger.info(" >>>>>> 请求的authorizer_appid: " + authorizer_appid + " <<<<<<< ");
        String url = "https://api.weixin.qq.com/wxa/submit_audit?access_token=";
        JSONObject params = new JSONObject();
        params.put("item_list", item_list);
        System.out.println(item_list);
        System.out.println(params.get("item_list"));
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
        logger.info(" >>>>>> 请求的url: " + url + authorizer_access_token + " <<<<<<< ");
        //JSONArray arrayList = new JSONArray();
        JSONObject result_json = restTemplate.postForEntity(url + authorizer_access_token, params, JSONObject.class).getBody();
        logger.info(" >>>>>> 提交审核结果: " + result_json.toJSONString() + " <<<<<< ");
        return result_json.toString();
    }

    /**
     * 功能描述: 获取授权小程序帐号的可选类目
     *
     * @auther: Martin、chen
     * @date: 2018/5/31 13:59
     * @param: [authorizer_appid]
     * @return: java.lang.String
     */
    @Override
    public String getCategory(String authorizer_appid) {
        logger.info(" >>>>>> 小程序AppId: " + authorizer_appid + " <<<<<<<< ");
        String url = "https://api.weixin.qq.com/wxa/get_category?access_token=";
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
        logger.info(" >>>>>> 请求的url: " + url + authorizer_access_token + "<<<<<<< ");
        JSONObject result_json = restTemplate.getForEntity(url + authorizer_access_token, JSONObject.class).getBody();
        logger.info(" >>>>>> 小程序类目结果集: " + result_json.toJSONString() + " <<<<<< ");
        return result_json.toString();
    }

    /**
     * 功能描述:获取小程序的第三方提交代码的页面配置
     *
     * @auther: Martin、chen
     * @date: 2018/5/31 15:12
     * @param: [authorizer_appid]
     * @return: java.lang.String
     */
    @Override
    public String getPage(String authorizer_appid) {
        logger.info(" >>>>>> 小程序AppId: " + authorizer_appid + " <<<<<<<< ");
        String url = "https://api.weixin.qq.com/wxa/get_page?access_token=";
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
        logger.info(" >>>>>> 请求的url: " + url + authorizer_access_token + "<<<<<<< ");
        JSONObject result_json = restTemplate.getForEntity(url + authorizer_access_token, JSONObject.class).getBody();
        logger.info(" >>>>>> 小程序页面配置列表: " + result_json.toJSONString() + " <<<<<< ");
        return result_json.toString();
    }

    /**
     * 查询最新一次提交的审核状态
     *
     * @param authorizer_appid 授权方AppID
     * @return
     */
    @Override
    public String getLatestAuditstatus(String authorizer_appid) {
        String url = "https://api.weixin.qq.com/wxa/get_latest_auditstatus?access_token=";
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
        JSONObject result_json = restTemplate.getForEntity(url + authorizer_access_token, JSONObject.class).getBody();
        logger.info(" >>>>>> 查询最新一次提交的审核状态: " + result_json.toJSONString() + " <<<<<< ");
        return result_json.toString();
    }

    /**
     * 功能描述: 查询某个指定版本的审核状态
     * auditid 提交审核时获得的审核id
     * @auther: Martin、chen
     * @date: 2018/6/4 11:41
     * @param: [authorizer_appid, auditid]
     * @return: java.lang.String
     */
    @Override
    public String getAuditstatus(String authorizer_appid, String auditid) {

        String url = "https://api.weixin.qq.com/wxa/get_auditstatus?access_token=";
        //根据authorizer_appid从redis中获取authorizer_access_token
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
        JSONObject param = new JSONObject();
        param.put("auditid", auditid);
        JSONObject result_json = restTemplate.postForEntity(url + authorizer_access_token, param, JSONObject.class).getBody();
        logger.info(" >>>>>> 查询某个指定版本的审核状态返回信息: " + result_json.toJSONString() + " <<<<<< ");
        return result_json.toString();
    }

    /**
     * 创建开放平台帐号并绑定小程序
     *
     * @param authorizer_appid  授权小程序的 appid
     * @return
     */
    @Override
    public String creat(String authorizer_appid) {
        //根据authorizer_appid从redis中获取authorizer_access_token
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
        JSONObject param = new JSONObject();
        param.put("appid", authorizer_appid);
        JSONObject result_json = restTemplate.postForEntity(propertiesBean.getCreat_url() + authorizer_access_token, param, JSONObject.class).getBody();
        logger.info(" >>>>>> 创建 开放平台帐号并绑定小程序返回信息: " + result_json.toJSONString() + " <<<<<< ");
        return result_json.toString();
    }

    /**
     * 获取小程序所绑定的开放平台帐号
     *
     * @param authorizer_appid  授权小程序的 appid
     * @return
     */
    @Override
    public String getBindInfo(String authorizer_appid) {
        //根据authorizer_appid从redis中获取authorizer_access_token
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
        JSONObject param = new JSONObject();
        param.put("appid", authorizer_appid);
        JSONObject result_json = restTemplate.postForEntity(propertiesBean.getBind_info_url() + authorizer_access_token, param, JSONObject.class).getBody();
        logger.info(" >>>>>> 获取小程序所绑定的开放平台帐号返回信息: " + result_json.toJSONString() + " <<<<<< ");
        return result_json.toString();
    }

    /**
     * 将小程序绑定到开放平台帐号下
     *
     * @param authorizer_appid 授权小程序的appid
     * @param open_appid       开放平台帐号appid
     * @return
     */
    @Override
    public String bind(String authorizer_appid, String open_appid) {
        //根据authorizer_appid从redis中获取authorizer_access_token
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
        JSONObject param = new JSONObject();
        param.put("appid", authorizer_appid);
        param.put("open_appid",open_appid);
        JSONObject result_json = restTemplate.postForEntity(propertiesBean.getBind_url() + authorizer_access_token, param, JSONObject.class).getBody();
        logger.info(" >>>>>> 将小程序绑定到开放平台帐号下返回信息: " + result_json.toJSONString() + " <<<<<< ");
        return result_json.toString();
    }

    /**
     * 将小程序从开放平台帐号下解绑
     *
     * @param authorizer_appid 授权小程序的appid
     * @param open_appid       开放平台帐号appid
     * @return
     */
    @Override
    public String unbind(String authorizer_appid, String open_appid) {
        //根据authorizer_appid从redis中获取authorizer_access_token
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
        JSONObject param = new JSONObject();
        param.put("appid", authorizer_appid);
        param.put("open_appid",open_appid);
        JSONObject result_json = restTemplate.postForEntity(propertiesBean.getUnbind_url() + authorizer_access_token, param, JSONObject.class).getBody();
        logger.info(" >>>>>> 将小程序从开放平台帐号下解绑返回信息: " + result_json.toJSONString() + " <<<<<< ");
        return result_json.toString();
    }

    /**
     * 小程序插件管理
     *
     * @param authorizer_appid 授权小程序的appid
     * @param action           申请填写apply   查询填写list    删除填写unbind
     * @param plugin_appid     插件appid
     * @return
     */
    @Override
    public String pluginManage(String authorizer_appid, String action, String plugin_appid) {
        //根据authorizer_appid从redis中获取authorizer_access_token
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
        JSONObject param = new JSONObject();
        param.put("action", action);
        if(StringUtils.isNotBlank(plugin_appid)){
            param.put("plugin_appid", plugin_appid);
        }
        JSONObject result_json = restTemplate.postForEntity(propertiesBean.getPlugin_url() + authorizer_access_token, param, JSONObject.class).getBody();
        if("apply".equals(action)){
            logger.info(" >>>>>> 小程序插件管理申请使用插件返回信息: " + result_json.toJSONString() + " <<<<<< ");
        }else if("list".equals(action)){
            logger.info(" >>>>>> 小程序插件管理查询已添加插件返回信息: " + result_json.toJSONString() + " <<<<<< ");
        }else if("unbind".equals(action)){
            logger.info(" >>>>>> 小程序插件管理删除已添加插件返回信息: " + result_json.toJSONString() + " <<<<<< ");
        }
        return result_json.toString();
    }

    /**
     * 设置小程序隐私设置（是否可被搜索）
     *
     * @param authorizer_appid 授权小程序的appid
     * @param status           1表示不可搜索，0表示可搜索
     * @return
     */
    @Override
    public String changeWxaSearchStatus(String authorizer_appid, String status) {
        //根据authorizer_appid从redis中获取authorizer_access_token
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
        JSONObject param = new JSONObject();
        param.put("status",status);
        JSONObject result_json = restTemplate.postForEntity(propertiesBean.getChange_wxa_search_status_url()+authorizer_access_token, param, JSONObject.class).getBody();
        logger.info(" >>>>>> 设置小程序隐私设置（是否可被搜索）返回信息: " + result_json.toJSONString() + " <<<<<< ");
        return result_json.toString();
    }

    /**
     * 查询小程序当前隐私设置（是否可被搜索）
     *
     * @param authorizer_appid 授权小程序的appid
     * @return
     */
    @Override
    public String getWxaSearchStatus(String authorizer_appid) {
        //根据authorizer_appid从redis中获取authorizer_access_token
        String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
        JSONObject result_json = restTemplate.exchange(propertiesBean.getGet_wxa_search_status_url()+authorizer_access_token,HttpMethod.GET,null,JSONObject.class).getBody();
        logger.info(" >>>>>> 查询小程序当前隐私设置（是否可被搜索）返回信息: " + result_json.toJSONString() + " <<<<<< ");
        return result_json.toString();
    }

    /**
     * 生成小程序码
     *
     * @param authorizer_appid 授权小程序的appid
     * @param path  用户扫描该码进入小程序后，将直接进入 path 对应的页面
     */
    @Override
    public void getwxacode(String authorizer_appid,String path) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File file = null;
        try {
            String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
            String url = "https://api.weixin.qq.com/wxa/getwxacode?access_token="+authorizer_access_token;
            JSONObject param = new JSONObject();
            param.put("path",path);
            param.put("width",430); //二维码的宽度
            param.put("auto_color",false);  //自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调
            param.put("is_hyaline",false);  //是否需要透明底色， is_hyaline 为true时，生成透明底色的小程序码
            Map<String, Object> line_color = new HashMap<>();
            line_color.put("r", 0);
            line_color.put("g", 0);
            line_color.put("b", 0);
            param.put("line_color",line_color);
            logger.info(">>>>>> 调用生成微信小程序码URL接口入参:"+param.toString()+"  <<<<<< ");
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity requestEntity = new HttpEntity(param, headers);
            ResponseEntity<byte[]> entity = restTemplate.exchange(url,HttpMethod.POST,requestEntity,byte[].class);
            logger.info(">>>>>> 调用小程序生成微信永久小程序码URL接口回参: " + entity);
            byte[] result = entity.getBody();
            inputStream = new ByteArrayInputStream(result);
            File filePath = new File(propertiesBean.getQrcode_path());
            if (!filePath.mkdir())
                filePath.mkdirs();

            file = new File(filePath+File.separator+authorizer_appid+".jpeg");
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            inputStream = new ByteArrayInputStream(result);
            int content = 0;
            byte[] buffer = new byte[1024 * 8];
            while  ((content = inputStream.read(buffer,0,1024)) != -1) {
                outputStream.write(buffer, 0, content);
            }
            outputStream.flush();
        }catch (Exception e){
            logger.error("调用小程序生成微信永久小程序码URL接口异常: "+e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 生成小程序码
     * @param authorizer_appid  授权小程序的appid
     * @param scene 页面可接受参数 最大32个可见字符，只支持数字，大小写英文以及部分特殊字符：!#$&'()*+,/:;=?@-._~，其它字符请自行编码为合法字符（因不支持%，中文无法使用 urlencode 处理，请使用其他编码方式）
     * @param page 已经发布的小程序存在的页面(pages/index/index) 如果不填写这个字段，默认跳主页面
     */
    @Override
    public void getwxacodeunlimit(String authorizer_appid,String scene,String page) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File file = null;
        try {
            String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+authorizer_access_token;
            JSONObject param = new JSONObject();
            param.put("scene",scene);   //页面可接受参数
            param.put("page",page);
            param.put("width",430); //二维码的宽度
            param.put("auto_color",false);  //自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调
            param.put("is_hyaline",false);  //是否需要透明底色， is_hyaline 为true时，生成透明底色的小程序码
            Map<String, Object> line_color = new HashMap<>();
            line_color.put("r", 0);
            line_color.put("g", 0);
            line_color.put("b", 0);
            param.put("line_color",line_color);
            logger.info(">>>>>> 调用生成微信小程序码URL接口入参:"+param.toString()+"  <<<<<< ");
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity requestEntity = new HttpEntity(param, headers);
            ResponseEntity<byte[]> entity = restTemplate.exchange(url,HttpMethod.POST,requestEntity,byte[].class);
            logger.info(">>>>>> 调用小程序生成微信永久小程序码URL接口回参: " + entity.getBody());
            byte[] result = entity.getBody();
            inputStream = new ByteArrayInputStream(result);
            File filePath = new File(propertiesBean.getQrcode_path());
            if (!filePath.mkdir())
                filePath.mkdirs();

            file = new File(filePath+File.separator+authorizer_appid+".jpeg");
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            inputStream = new ByteArrayInputStream(result);
            int content = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((content = inputStream.read(buffer,0,1024)) != -1) {
                outputStream.write(buffer, 0, content);
            }
            outputStream.flush();
        }catch (Exception e){
            logger.error("调用小程序生成微信永久小程序码URL接口异常: "+e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取小程序二维码
     *
     * @param authorizer_appid 授权小程序的appid
     * @param path   用户扫描该码进入小程序后，将直接进入 path 对应的页面
     */
    @Override
    public void createwxaqrcode(String authorizer_appid,String path) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File file = null;
        try {
            String authorizer_access_token = wechatAuthService.getAuthorizerAccessToken(authorizer_appid);
            String url = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token="+authorizer_access_token;
            JSONObject param = new JSONObject();
            param.put("path",path);  //必须是已经发布的小程序存在的页面（否则报错），如果不填写这个字段，默认跳主页面
            logger.info(">>>>>> 调用生成微信小程序码URL接口入参:"+param.toString()+"  <<<<<< ");
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity requestEntity = new HttpEntity(param, headers);
            ResponseEntity<byte[]> entity = restTemplate.exchange(url,HttpMethod.POST,requestEntity,byte[].class);
            logger.info(">>>>>> 调用小程序生成微信永久小程序码URL接口回参: " + entity);
            byte[] result = entity.getBody();
            inputStream = new ByteArrayInputStream(result);
            File filePath = new File(propertiesBean.getQrcode_path());
            if (!filePath.mkdir())
                filePath.mkdirs();

            file = new File(filePath+File.separator+authorizer_appid+".jpeg");
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            inputStream = new ByteArrayInputStream(result);
            int content = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((content = inputStream.read(buffer,0,1024)) != -1) {
                outputStream.write(buffer, 0, content);
            }
            outputStream.flush();
        }catch (Exception e){
            logger.error("调用小程序生成微信永久小程序码URL接口异常: "+e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
