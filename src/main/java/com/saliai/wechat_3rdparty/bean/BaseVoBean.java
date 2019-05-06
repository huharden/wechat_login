package com.saliai.wechat_3rdparty.bean;

import lombok.Data;

/**
 * @Author: chenyiwu
 * @Describtion: 微信返回消息基类，包含errcode errmsg
 * @Create Time:2018/6/7
 */
@Data
public class BaseVoBean {
    /**返回错误码*/
    private Integer errcode;
    /**返回错误信息*/
    private String errmsg;
}
