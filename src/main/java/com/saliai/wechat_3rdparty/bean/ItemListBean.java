package com.saliai.wechat_3rdparty.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author:chenyiwu
 * @Describtion: 将第三方提交的代码包提交审核实体
 * @Create Time:2018/5/31
 */
@Data
public class ItemListBean implements Serializable{

    private static final long serialVersionUID = -6050812715416750062L;

    /**小程序的页面，可通过“获取小程序的第三方提交代码的页面配置”接口获得*/
    private String address;
    /**小程序的标签，多个标签用空格分隔，标签不能多于10个，标签长度不超过20*/
    private String tag;
    /**一级类目名称，可通过“获取授权小程序帐号的可选类目”接口获得*/
    private String first_class;
    /**二级类目(同上)*/
    private String second_class;
    /**三级类目(同上)*/
    private  String third_class;
    /**一级类目的ID，可通过“获取授权小程序帐号的可选类目”接口获得*/
    private Integer first_id;
    /**二级类目的ID(同上)*/
    private Integer second_id;
    /**三级类目的ID(同上)*/
    private  Integer third_id;
    /**小程序页面的标题,标题长度不超过32*/
    private  String title;
}
