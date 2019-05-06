package com.saliai.wechat_3rdparty;

import com.saliai.wechat_3rdparty.bean.PropertiesBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(PropertiesBean.class)
public class Wechat3rdpartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(Wechat3rdpartyApplication.class, args);
    }
}
