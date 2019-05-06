package com.saliai.wechat_3rdparty.controller;

import com.saliai.wechat_3rdparty.bean.WxMessageBean;
import com.saliai.wechat_3rdparty.service.WxopenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: chenyiwu
 * @Describtion: 代小程序业务开发，小程序模板管理
 * @Create Time:2018/6/4
 */
@RestController
@RequestMapping(value = "/wxopen")
public class WxopenController {
    @Autowired
    private WxopenService wxopenService;
    /**
     * 功能描述:获取小程序小的模板列表
     *
     * @param authorizer_appid 授权小程序的appid
     * @param offset           分页获取的开始下标
     * @param count            返回的数据条数 最大支持20
     * @auther: Martin、chen
     * @date: 2018/6/5 11:00
     * @return: java.lang.String
     */
    @RequestMapping(value = "/getPersonLibrary")
    @ResponseBody
    public String getPersonLibrary(@RequestParam(name = "authorizer_appid") String authorizer_appid, @RequestParam(name = "offset") Integer offset, @RequestParam(name = "count") Integer count) {
        return wxopenService.getPersonLibrary(authorizer_appid, offset, count);
    }

    /**
     * 功能描述:获取模板库某个模板标题下关键词库
     *
     * @param titleId  模板标题id，可通过接口获取，也可登录小程序后台查看获取
     * @param authorizer_appid 小程序appid
     * @auther: Martin、chen
     * @date: 2018/6/5 11:11
     * @return: java.lang.String
     */
    @RequestMapping(value = "/getLibraryTitle")
    @ResponseBody
    public String getLibraryTitle(@RequestParam(name = "authorizer_appid") String authorizer_appid, @RequestParam(name = "titleId") String titleId) {
        return wxopenService.getLibraryTitle(authorizer_appid, titleId);
    }

    /**
     * 功能描述:获取帐号下已存在的模板列表
     *
     * @param offset 开始标志未
     * @param count 获取的条数（最大20）
     * @auther: Martin、chen
     * @date: 2018/6/5 11:16
     * @return: java.lang.String
     */
    @RequestMapping(value = "/getTemplateList")
    @ResponseBody
    public String getTemplateList(@RequestParam(name = "authorizer_appid") String authorizer_appid, @RequestParam(name = "offset") Integer offset, @RequestParam(name = "count") Integer count) {

        return wxopenService.getTemplateList(authorizer_appid, offset, count);
    }

    /**
     * 功能描述: 删除账号下的消息模板
     *
     * @param templateId 模板id
     * @auther: Martin、chen
     * @date: 2018/6/5 11:22
     * @return: java.lang.String
     */
    @RequestMapping(value = "/deletePlatFormTemplate")
    @ResponseBody
    public String deletePlatFormTemplate(@RequestParam(name = "authorizer_appid") String authorizer_appid, @RequestParam(name = "templateId") String templateId) {

        return wxopenService.deletePlatFormTemplate(authorizer_appid, templateId);
    }

    /**
     * 功能描述: 组合模板并添加至帐号下的个人模板库
     *
     * @param authorizer_appid 小程序appid
     * @param titleId 模板标题id
     * @param keyword_id_list 开发者自行组合好的模板关键词列表，关键词顺序可以自由搭配（例如[3,5,4]或[4,5,3]），最多支持10个关键词组合
     * @auther: Martin、chen
     * @date: 2018/6/5 11:30
     * @return: java.lang.String
     */
    @RequestMapping(value = "/addTemplateLibrary")
    @ResponseBody
    public String addTemplateLibrary(@RequestParam(name = "authorizer_appid") String authorizer_appid, @RequestParam(name = "titleId") String titleId, @RequestParam(name = "keyword_id_list") int[] keyword_id_list) {

        return wxopenService.addTemplateLibrary(authorizer_appid, titleId, keyword_id_list);
    }

    /**
     * 功能描述:发送模板消息
     *
     * @param authorizer_appid 小程序appid
     * @param code d登录时获取的openid是需要，前台页面也可直接传入openid
     * @param wxMessageBean 发送模板消息的主体
     * @auther: Martin、chen
     * @date: 2018/6/6 13:38
     * @return: java.lang.String
     */
    @RequestMapping(value = "/sendTemplateMessage")
    @ResponseBody
    public String sendTemplateMessage(@RequestParam(name = "authorizer_appid") String authorizer_appid, @RequestParam(name = "code") String code, @RequestBody WxMessageBean wxMessageBean) {

        return wxopenService.sendTemplateMessage(authorizer_appid, code, wxMessageBean);
    }
}
