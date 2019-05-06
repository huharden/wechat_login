package com.saliai.wechat_3rdparty.controller;

import com.alibaba.fastjson.JSONArray;
import com.saliai.wechat_3rdparty.bean.ItemListBean;
import com.saliai.wechat_3rdparty.service.WxaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: zhangzhk
 * @Description: 代小程序实现业务统一入口
 * @Date: 2018/5/29 15:57
 * @Modify By:
 */

@RestController
@RequestMapping(value = "/wxa")
public class WxaController {

    @Autowired
    private WxaService wxaService;

    /**
     * 设置小程序服务器域名
     *
     * @param authorizer_appid 授权方AppID
     * @param action           add添加, delete删除, set覆盖, get获取。当参数是get时不需要填四个域名字段
     * @param requestdomain    request合法域名，当action参数是get时不需要此字段    "requestdomain":["https://www.qq.com","https://www.qq.com"]
     * @param wsrequestdomain  socket合法域名，当action参数是get时不需要此字段
     * @param uploaddomain     uploadFile合法域名，当action参数是get时不需要此字段
     * @param downloaddomain   downloadFile合法域名，当action参数是get时不需要此字段
     * @return
     */
    @RequestMapping(value = "/modifyDomain")
    @ResponseBody
    public String modifyDomain(@RequestParam(name = "authorizer_appid") String authorizer_appid, @RequestParam(name = "action") String action,
                               @RequestParam(name = "requestdomain", required = false) String requestdomain, @RequestParam(name = "wsrequestdomain", required = false) String wsrequestdomain,
                               @RequestParam(name = "uploaddomain", required = false) String uploaddomain, @RequestParam(name = "downloaddomain", required = false) String downloaddomain) {
        return wxaService.modifyDomain(authorizer_appid, action, requestdomain, wsrequestdomain, uploaddomain, downloaddomain);
    }

    /**
     * 设置小程序业务域名
     *
     * @param authorizer_appid 授权方AppID
     * @param action           add添加, delete删除, set覆盖, get获取。当参数是get时不需要填webviewdomain字段。如果没有action字段参数，则默认见开放平台第三方登记的小程序业务域名全部添加到授权的小程序中
     * @param webviewdomain    小程序业务域名，当action参数是get时不需要此字段    "webviewdomain":["https://www.qq.com","https://m.qq.com"]
     * @return
     */
    @RequestMapping(value = "/setWebviewDomain")
    @ResponseBody
    public String setWebviewDomain(@RequestParam(name = "authorizer_appid") String authorizer_appid, @RequestParam(name = "action", required = false) String action,
                                   @RequestParam(name = "webviewdomain", required = false) String webviewdomain) {
        return wxaService.setWebviewDomain(authorizer_appid, action, webviewdomain);
    }

    /**
     * 为授权的小程序帐号上传小程序代码
     *
     * @param extAppid    授权方Appid
     * @param template_id 小程序模版id
     * @return
     */
    @RequestMapping(value = "/commit")
    public String commit(String extAppid, String template_id) {
        return wxaService.commit(extAppid, template_id);
    }

    /**
     * 获取体验小程序的体验二维码
     *
     * @param authorizer_access_token
     * @param response
     */
    @RequestMapping(value = "/getQrcode/{authorizer_access_token}")
    public void getQrcode(@PathVariable String authorizer_access_token, HttpServletResponse response) {
        wxaService.getQrcode(authorizer_access_token, response);
    }

    /**
     * 使用登录凭证 code 以及第三方平台的component_access_token 获取 session_key 和 openid
     *
     * @param authorizer_appid 授权方AppID
     * @param code             登录凭证 code
     * @return
     */
    @RequestMapping(value = "/getSessionKey")
    @ResponseBody
    public String getSessionKey(@RequestParam(name = "authorizer_appid") String authorizer_appid, @RequestParam(name = "code") String code) {
        return wxaService.getSessionKey(authorizer_appid, code);
    }

    /**
     * 功能描述:获取小程序类目
     *
     * @auther: Martin、chen
     * @date: 2018/5/31 14:02
     * @param: [authorizer_appid]
     * @return: java.lang.String
     */
    @RequestMapping(value = "/getCategory")
    @ResponseBody
    public String getCategory(@RequestParam(name = "authorizer_appid") String authorizer_appid) {

        return wxaService.getCategory(authorizer_appid);
    }

    /**
     * 功能描述: 获取页面列表
     *
     * @auther: Martin、chen
     * @date: 2018/5/31 15:14
     * @param: [authorizer_appid]
     * @return: java.lang.String
     */
    @RequestMapping(value = "/getPage")
    @ResponseBody
    public String getPage(@RequestParam(name = "authorizer_appid") String authorizer_appid) {
        return wxaService.getPage(authorizer_appid);
    }

    /**
     * 功能描述:将第三方提交的代码包提交审核
     *
     * @auther: Martin、chen
     * @date: 2018/5/31 16:28
     * @param: [authorizer_appid, item_list]
     * @return: java.lang.String
     */
    @RequestMapping(value = "/submitAudit")
    @ResponseBody
    public String submitAudit(@RequestParam(name = "authorizer_appid") String authorizer_appid, @RequestBody ItemListBean[] params) {
        System.out.println("参数为：" + params);
        return wxaService.submitAudit(authorizer_appid, params);
    }

    /**
     * 查询最新一次提交的审核状态
     *
     * @param authorizer_appid 授权方AppID
     * @return
     */
    @RequestMapping(value = "/getLatestAuditstatus")
    @ResponseBody
    public String getLatestAuditstatus(@RequestParam(name = "authorizer_appid") String authorizer_appid) {
        return wxaService.getLatestAuditstatus(authorizer_appid);
    }

    /**
     * 功能描述:获取草稿箱或者模板箱中的模板代码
     *
     * @auther: Martin、chen
     * @date: 2018/6/4 9:49
     * @param: [type] 0：是草稿箱 1：是模板箱
     * @return: java.lang.String
     */
    @RequestMapping(value = "/getTemplateList")
    @ResponseBody
    public String getTemplateList(@RequestParam(name = "type") Integer type) {

        return wxaService.getTemplateList(type);
    }

    /**
     * 功能描述:将草稿箱中的草稿代码设置为模板
     *
     * @auther: Martin、chen
     * @date: 2018/6/4 10:03
     * @param: [draft_id]
     * @return: java.lang.String
     */
    @RequestMapping(value = "/addtoTemplate")
    @ResponseBody
    public String addtoTemplate(@RequestParam(name = "draft_id") Integer draft_id) {

        return wxaService.addtoTemplate(draft_id);
    }

    /**
     * 功能描述:删除模板库中的模板代码
     *
     * @auther: Martin、chen
     * @date: 2018/6/4 10:06
     * @param: [template_id]
     * @return: java.lang.String
     */
    @RequestMapping(value = "/deleteTemplate")
    @ResponseBody
    public String deleteTemplate(@RequestParam(name = "template_id") Integer template_id) {

        return wxaService.deleteTemplate(template_id);
    }

    /**
     * 发布已通过审核的小程序（仅供第三方代小程序调用）
     * @param authorizer_appid  授权的appid
     * @return
     */
    @RequestMapping(value = "/release")
    @ResponseBody
    public String release(@RequestParam(name = "authorizer_appid") String authorizer_appid){
        return wxaService.release(authorizer_appid);
    }

    /**
     * 功能描述:回退小程序版本
     *
     * @auther: Martin、chen
     * @date: 2018/6/4 10:15
     * @param: [authorizer_appid]
     * @return: java.lang.String
     */
    @RequestMapping(value = "/revertCodeRelease")
    @ResponseBody
    public String revertCodeRelease(@RequestParam(name = "authorizer_appid") String authorizer_appid) {

        return wxaService.revertcodeRelease(authorizer_appid);
    }

    /**
     * 功能描述:修改发布小程序的代码是否可见
     * 设置可访问状态，发布后默认可访问，close为不可见，open为可见
     *
     * @auther: Martin、chen
     * @date: 2018/6/4 10:48
     * @param: [authorizer_appid]
     * @return: java.lang.String
     */
    @RequestMapping(value = "/changeVisitStatus")
    @ResponseBody
    public String changeVisitStatus(@RequestParam(name = "authorizer_appid") String authorizer_appid, @RequestParam(name = "action") String action) {

        return wxaService.changeVisitStatus(authorizer_appid, action);
    }

    /**
     * 功能描述: 查询某个指定版本的审核状态
     *
     * @auther: Martin、chen
     * @date: 2018/6/4 13:52
     * @param: [authorizer_appid, auditid]
     * @return: java.lang.String
     */
    @RequestMapping(value = "/getAuditstatus")
    @ResponseBody
    public String getAuditstatus(String authorizer_appid, String auditid) {

        return wxaService.getAuditstatus(authorizer_appid, auditid);
    }

    /**
     *创建开放平台帐号并绑定小程序
     * @param authorizer_appid  授权小程序的appid
     * @return
     */
    @RequestMapping(value = "/creat")
    @ResponseBody
    public String creat(@RequestParam(name = "authorizer_appid")String authorizer_appid) {
        return wxaService.creat(authorizer_appid);
    }

    /**
     *  获取小程序所绑定的开放平台帐号
     * @param authorizer_appid  授权小程序的appid
     * @return
     */
    @RequestMapping(value = "/getBindInfo")
    @ResponseBody
    public String getBindInfo(@RequestParam(name = "authorizer_appid")String authorizer_appid) {
        return wxaService.getBindInfo(authorizer_appid);
    }

    /**
     * 将小程序绑定到开放平台帐号下
     * @param authorizer_appid  授权小程序的appid
     * @param open_appid    开放平台帐号appid
     * @return
     */
    @RequestMapping(value = "/bind")
    @ResponseBody
    public String bind(@RequestParam(name = "authorizer_appid")String authorizer_appid,@RequestParam(name = "open_appid")String open_appid){
        return wxaService.bind(authorizer_appid,open_appid);
    }

    /**
     *   将小程序绑定到开放平台帐号下
     * @param authorizer_appid  授权小程序的appid
     * @param open_appid    开放平台帐号appid
     * @return
     */
    @RequestMapping(value = "/unbind")
    @ResponseBody
    public String unbind(@RequestParam(name = "authorizer_appid")String authorizer_appid,@RequestParam(name = "open_appid")String open_appid){
        return wxaService.unbind(authorizer_appid,open_appid);
    }

    /**
     *  小程序插件管理
     * @param authorizer_appid  授权小程序的appid
     * @param action    申请填写apply   查询填写list    删除填写unbind
     * @param plugin_appid  申请和删除操作填写   查询操作不填写
     * @return
     */
    @RequestMapping(value = "/pluginManage")
    @ResponseBody
    public String pluginManage(@RequestParam(name = "authorizer_appid")String authorizer_appid,@RequestParam(name = "action")String action,@RequestParam(name = "plugin_appid",required = false)String plugin_appid){
        return wxaService.pluginManage(authorizer_appid,action,plugin_appid);
    }

    /**
     *  设置小程序隐私设置（是否可被搜索）
     * @param authorizer_appid  授权小程序的appid
     * @param status    1表示不可搜索，0表示可搜索
     * @return
     */
    @RequestMapping(value = "/changeWxaSearchStatus")
    @ResponseBody
    public String changeWxaSearchStatus(@RequestParam(name = "authorizer_appid")String authorizer_appid,@RequestParam(name = "status")String status){
        return wxaService.changeWxaSearchStatus(authorizer_appid,status);
    }

    /**
     *  查询小程序当前隐私设置（是否可被搜索）
     * @param authorizer_appid  授权小程序的appid
     * @return
     */
    @RequestMapping(value = "/getWxaSearchStatus")
    @ResponseBody
    public String getWxaSearchStatus(@RequestParam(name = "authorizer_appid")String authorizer_appid){
        return wxaService.getWxaSearchStatus(authorizer_appid);
    }

    /**
     * 生成小程序码(适用于需要的码数量较少的业务场景) 总次数有限制  接口A
     * @param authorizer_appid  授权小程序的appid
     * @param path  用户扫描该码进入小程序后，将直接进入 path 对应的页面
     */
    @RequestMapping(value = "/getwxacode")
    @ResponseBody
    public void getwxacode(@RequestParam(name = "authorizer_appid")String authorizer_appid,@RequestParam(name = "path")String path){
        wxaService.getwxacode(authorizer_appid,path);
    }

    /**
     * 生成小程序码 (适用于需要的码数量极多的业务场景)    目前5000次/分钟  小程序必须已经发布   接口B
     * @param authorizer_appid  授权小程序的appid
     * @param scene 自定义参数
     * @param page 已经发布的小程序存在的页面(pages/index/index) 如果不填写这个字段，默认跳主页面
     */
    @RequestMapping(value = "/getwxacodeunlimit")
    @ResponseBody
    public void getwxacodeunlimit(@RequestParam(name = "authorizer_appid")String authorizer_appid,@RequestParam(name = "scene",required = false)String scene,@RequestParam(name = "page",required = false)String page){
        wxaService.getwxacodeunlimit(authorizer_appid,scene,page);
    }

    /**
     * 生成小程序二维码 (适用于需要的码数量较少的业务场景)
     * @param authorizer_appid  授权小程序的appid
     * @param path   用户扫描该码进入小程序后，将直接进入 path 对应的页面
     */
    @RequestMapping(value = "/createwxaqrcode")
    @ResponseBody
    public void createwxaqrcode(@RequestParam(name = "authorizer_appid")String authorizer_appid,@RequestParam(name = "path")String path){
        wxaService.createwxaqrcode(authorizer_appid,path);
    }
}
