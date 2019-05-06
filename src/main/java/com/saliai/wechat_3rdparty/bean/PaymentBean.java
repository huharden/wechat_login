package com.saliai.wechat_3rdparty.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: chenyiwu
 * @Describtion: 微信支付统一下单实体
 * @Create Time:2018/6/14
 */
@Data
public class PaymentBean implements Serializable {
    private static final long serialVersionUID = 5143093628031525448L;
    /**
     * 服务商ID
     * 微信分配的公众账号ID
     */
    private String appid;
    /**
     * 商户号
     * 微信支付分配的商户号
     */
    private String mch_id;
    /**
     * 小程序的APPID
     * 当前调起支付的小程序APPID
     */
    private String sub_appid;
    /**
     * 子商户号
     * 微信支付分配的子商户号
     */
    private String sub_mch_id;
    /**
     * 设备号
     * 终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
     */
    private String device_info;
    /**
     * 随机字符串
     * 随机字符串，不长于32位
     */
    private String nonce_str;
    /**
     * 签名
     */
    private String sign;
    /**
     * 签名类型
     * 签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
     */
    private String sign_type;
    /**
     * 商品描述
     */
    private String body;
    /**
     * 商品详情
     */
    private String detail;
    /**
     * 附加数据
     */
    private String attach;
    /**
     * 商户订单号
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一
     */
    private String out_trade_no;
    /**
     * 货币类型
     * 符合ISO 4217标准的三位字母代码，默认人民币：CNY
     */
    private String fee_type;
    /**
     * 总金额
     * 订单总金额，只能为整数单位为分
     */
    private Integer total_fee;
    /**
     * 终端IP
     * APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
     */
    private String spbill_create_ip;
    /**
     * 交易起始时间
     * 订单生成时间，格式为yyyyMMddHHmmss，
     */
    private String time_start;
    /**
     * 交易结束时间
     * 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。
     * 订单失效时间是针对订单号而言的，由于在请求支付的时候有一个必传参数prepay_id只有两小时的有效期，
     * 所以在重入时间超过2小时的时候需要重新请求下单接口获取新的prepay_id
     */
    private String time_expire;
    /**
     * 订单优惠标记
     * 订单优惠标记，代金券或立减优惠功能的参数
     */
    private String goods_tag;
    /**
     * 通知地址
     * 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
     */
    private String notify_url;
    /**
     * 交易类型
     * 小程序取值如下：JSAPI
     */
    private String trade_type;
    /**
     * 指定支付方式
     * no_credit--指定不能使用信用卡支付
     */
    private String limit_pay;
    /**
     * 用户标识
     * trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。
     */
    private String openid;
    /**
     * 用户子标识
     * trade_type=JSAPI，此参数必传，用户在子商户appid下的唯一标识。
     * openid和sub_openid可以选传其中之一，如果选择传sub_openid,则必须传sub_appid
     */
    private String sub_openid;
}
