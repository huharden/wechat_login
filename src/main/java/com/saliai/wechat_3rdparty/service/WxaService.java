package com.saliai.wechat_3rdparty.service;

import com.saliai.wechat_3rdparty.bean.ItemListBean;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: zhangzhk
 * @Description:
 * @Date: 2018/5/29 16:23
 * @Modify By:
 */
public interface WxaService {

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
    public String modifyDomain(String authorizer_appid, String action, String requestdomain, String wsrequestdomain, String uploaddomain, String downloaddomain);

    /**
     * 设置小程序业务域名
     *
     * @param authorizer_appid 授权方appid
     * @param action           add添加, delete删除, set覆盖, get获取。当参数是get时不需要填webviewdomain字段。如果没有action字段参数，则默认见开放平台第三方登记的小程序业务域名全部添加到授权的小程序中
     * @param webviewdomain    小程序业务域名，当action参数是get时不需要此字段
     * @return
     */
    public String setWebviewDomain(String authorizer_appid, String action, String webviewdomain);

    /**
     * 使用登录凭证 code 以及第三方平台的component_access_token 获取 session_key 和 openid
     *
     * @param authorizer_appid 授权方AppID
     * @param code             登录凭证 code
     * @return
     */
    public String getSessionKey(String authorizer_appid, String code);

    /**
     * 绑定微信用户为小程序体验者
     *
     * @param authorizer_appid 授权方appid
     * @param wechatid         微信号
     * @return
     */
    public String bindTester(String authorizer_appid, String wechatid);

    /**
     * 为授权的小程序帐号上传小程序代码
     *
     * @param extAppid    授权方Appid
     * @param template_id 小程序模版id
     * @return
     */
    public String commit(String extAppid, String template_id);

    /**
     * 获取体验小程序的体验二维码
     *
     * @param authorizer_access_token
     * @param response
     */
    public void getQrcode(String authorizer_access_token, HttpServletResponse response);

    /**
     * @param: authorizer_appid //授权方appid
     */
    public String release(String authorizer_appid);

    /**
     * @param authorizer_appid 授权方appid
     * @param action           代码是否显示 close为不可见，open为可见
     * @return
     */
    public String changeVisitStatus(String authorizer_appid, String action);

    /**
     * 小程序代码回退
     *
     * @param authorizer_appid
     * @return
     */
    public String revertcodeRelease(String authorizer_appid);

    /**
     * 功能描述: 获取草稿箱内的所有临时代码草稿/模板代码
     *
     * @auther: Martin、chen
     * @date: 2018/5/30 16:55
     * @param: type
     * @return:
     */
    public String getTemplateList(Integer type);

    /**
     * 功能描述: 将草稿箱中的模板代码设置为正式的模板代码
     *
     * @auther: Martin、chen
     * @date: 2018/5/30 17:12
     * @param:
     * @return:
     */
    public String addtoTemplate(Integer draft_id);

    /**
     * 功能描述: 删除模板
     *
     * @auther: Martin、chen
     * @date: 2018/5/30 17:15
     * @param:
     * @return:
     */
    public String deleteTemplate(Integer template_id);

    /**
     * 微信小程序解密用户敏感数据获取用户信息
     *
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param sessionKey    数据进行加密签名的密钥
     * @param iv            加密算法的初始向量
     * @return 用户信息数据
     */
    public String getUserInfo(String encryptedData, String sessionKey, String iv);

    /**
     * 功能描述: 将第三方提交的代码包提交审核
     *
     * @auther: Martin、chen
     * @date: 2018/5/31 9:55
     * @param: authorizer_appid
     * @return:
     */
    public String submitAudit(String authorizer_appid, ItemListBean[] item_list);

    /**
     * 功能描述: 获取小程序类目信息
     *
     * @auther: Martin、chen
     * @date: 2018/5/31 13:55
     * @param: authorizer_appid
     * @return:
     */
    public String getCategory(String authorizer_appid);

    /**
     * 功能描述:获取小程序的第三方提交代码的页面配置
     *
     * @auther: Martin、chen
     * @date: 2018/5/31 15:09
     * @param:
     * @return:
     */
    public String getPage(String authorizer_appid);

    /**
     * 查询最新一次提交的审核状态
     *
     * @param authorizer_appid 授权方AppID
     * @return
     */
    public String getLatestAuditstatus(String authorizer_appid);

    /**
     * 功能描述:查询某个指定版本的审核状态
     *
     * @auther: Martin、chen
     * @date: 2018/6/4 11:29
     * @param: auditid	提交审核时获得的审核id
     * @return:
     */
    public String getAuditstatus(String authorizer_appid, String auditid);

    /**
     * 创建开放平台帐号并绑定小程序
     * @param authorizer_appid  授权小程序的 appid
     * @return
     */
    public String creat(String authorizer_appid);

    /**
     *  获取小程序所绑定的开放平台帐号
     * @param authorizer_appid  授权小程序的 appid
     * @return
     */
    public String getBindInfo(String authorizer_appid);

    /**
     *  将小程序绑定到开放平台帐号下
     * @param authorizer_appid  授权小程序的appid
     * @param open_appid    开放平台帐号appid
     * @return
     */
    public String bind(String authorizer_appid,String open_appid);

    /**
     *  将小程序从开放平台帐号下解绑
     * @param authorizer_appid  授权小程序的appid
     * @param open_appid    开放平台帐号appid
     * @return
     */
    public String unbind(String authorizer_appid,String open_appid);

    /**
     * 小程序插件管理
     * @param authorizer_appid  授权小程序的appid
     * @param action    申请填写apply   查询填写list    删除填写unbind
     * @param plugin_appid  插件appid
     * @return
     */
    public String pluginManage(String authorizer_appid,String action,String plugin_appid);

    /**
     * 设置小程序隐私设置（是否可被搜索）
     * @param authorizer_appid  授权小程序的appid
     * @param status    1表示不可搜索，0表示可搜索
     * @return
     */
    public String changeWxaSearchStatus(String authorizer_appid,String status);

    /**
     * 查询小程序当前隐私设置（是否可被搜索）
     * @param authorizer_appid  授权小程序的appid
     * @return
     */
    public String getWxaSearchStatus(String authorizer_appid);

    /**
     * 生成小程序码
     * @param authorizer_appid  授权小程序的appid
     * @param path   用户扫描该码进入小程序后，将直接进入 path 对应的页面
     */
    public void getwxacode(String authorizer_appid,String path);

    /**
     * 生成小程序码
     * @param authorizer_appid  授权小程序的appid
     * @param scene 最大32个可见字符，只支持数字，大小写英文以及部分特殊字符：!#$&'()*+,/:;=?@-._~，其它字符请自行编码为合法字符（因不支持%，中文无法使用 urlencode 处理，请使用其他编码方式）
     * @param page 已经发布的小程序存在的页面(pages/index/index) 如果不填写这个字段，默认跳主页面
     */
    public void getwxacodeunlimit(String authorizer_appid,String scene,String page);

    /**
     * 生成小程序二维码
     * @param authorizer_appid  授权小程序的appid
     * @param path   用户扫描该码进入小程序后，将直接进入 path 对应的页面
     */
    public void createwxaqrcode(String authorizer_appid,String path);
}
