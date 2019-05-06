package com.saliai.wechat_3rdparty.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: zhangzhk
 * @Description:
 * @Date: 2018/5/25 9:50
 * @Modify By:
 */

@Configuration
@ConfigurationProperties(prefix = "config")
@PropertySource("classpath:properties/config.properties")
@Data
public class PropertiesBean {
    private String appId;

    private String appSecret;

    private String token;

    private String encodingAesKey;

    private String qrcode_path;

    private String component_access_token_url;

    private String pre_auth_code_url;

    private String authorizer_access_token_url;

    private String refresh_authorizer_token_url;

    private String get_authorizer_info_url;

    private String bind_tester_url;

    private String modify_domain_url;

    private String set_webview_domain_url;

    private String commit_url;

    private String creat_url;

    private String bind_info_url;

    private String bind_url;

    private String unbind_url;
    //发送模板消息请求url
    private String send_template_message_url;

    private String plugin_url;

    private String change_wxa_search_status_url;

    private String get_wxa_search_status_url;

}
