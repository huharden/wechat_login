package com.saliai.wechat_3rdparty.service;

import com.saliai.wechat_3rdparty.bean.WxMessageBean;

/**
 * @Author:chenyiwu
 * @Describtion: 小程序模板管理
 * @Create Time:2018/6/4
 */
public interface WxopenService {
    //public String getPlatFormLibrary(Integer offset,Integer count);
    //获取小程序小的模板列表
    public String getPersonLibrary(String authorizer_appid, Integer offset, Integer count);

    //获取小程序模板标题
    public String getLibraryTitle(String authorizer_appid, String titleId);

    //组合模板添加至小程序下
    public String addTemplateLibrary(String authorizer_appid, String titleId, int[] keyword_id_list);

    //获取账号下的模板列表
    public String getTemplateList(String authorizer_appid, Integer offset, Integer count);

    //删除帐号下的某个模板
    public String deletePlatFormTemplate(String authorizer_appid, String templateId);

    //发送模板信息
    public String sendTemplateMessage(String authorizer_appid, String code, WxMessageBean wxMessageBean);
}
