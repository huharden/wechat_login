package com.saliai.wechat_3rdparty.message;

/**
 * @Author:chenyiwu
 * @Describtion:
 * @Create Time:2018/6/7
 */
public enum WxResponseCode {
    SUCCESS(0, "ok"),
    RESPONSE_SUCCESS(200, "请求成功"),

    BUSY_SYSTEM(-1, "系统繁忙或者系统错误"),
    /**
     * 通用错误
     */
    UNKNOWN_CODE(5, "未知错误"),
    /**小程序相关错误码*/
    /**
     * 修改服务器模块
     */
    NOT_PROGRAM_ACCOUNT(85015, "该账号不是小程序账号"),

    DOMAIN_NAME_LIMIT(85016, "域名数量超过限制"),

    DOMAIN_NAME_UNBIND(85017, "没有新增域名，请确认小程序已经添加了域名或该域名是否没有在第三方平台添加"),

    DOMAIN_NAME_UNSET(85018, "域名没有在第三方平台设置"),

    BUSINESS_DOMAIN_NAME_SETAGAIN(89019, "业务域名无更改，无需重复设置"),

    UNSET_DOMAIN_NAME(89020, "尚未设置小程序业务域名，请先在第三方平台中设置小程序业务域名后在调用本接口"),

    DOMAIN_NAME_UNVALUE(89021, "请求保存的域名不是第三方平台中已设置的小程序业务域名或子域名"),

    BUSINESS_DOMAIN_NAME_LIMIT(89029, "业务域名数量超过限制"),

    PERSON_APP_UNSUPPORT(89031, "个人小程序不支持调用setwebviewdomain 接口"),
    /**
     * 成员管理模块
     */
    WECHAT_ACCOUNT_UNEXISTENT(85001, "微信号不存在或微信号设置为不可搜索"),

    APP_BIND_LIMIT(85002, "小程序绑定的体验者数量达到上限"),

    WECHATACCOUNT_BIND_LIMIT(85003, "微信号绑定的小程序体验者达到上限"),

    WECAHT_ACCOUNT_ISBINDED(85004, "微信号已经绑定"),

    /**
     * 代码管理模块
     */
    INVALID_CUSTOM_CONFIGURATION(85013, "无效的自定义配置"),

    CODE_INVALID_TEMPLATE_ID(85014, "无效的模版编号"),

    TEMPLATE_ERROR(85043, "模版错误"),

    CODE_PACKET_LIMIT(85044, "代码包超过大小限制"),

    EXTJSON_HAVE_UNEXIST_PATH(85045, "ext_json有不存在的路径"),

    TABBAR_LACK_PATH(85046, "tabBar中缺少path"),

    PAGES_FIELD_EMPTY(85047, "pages字段为空"),

    EXTJSON_PARSE_FAILURE(85048, "ext_json解析失败"),

    THIRD_PARTY_SUBROUTINE(86000, "不是由第三方代小程序进行调用"),

    THIRD_PARTY_UNSUBMIT(86001, "不存在第三方的已经提交的代码"),

    LABEL_FORMAT_ERROR(85006, "标签格式错误"),

    PAGE_PATH_ERROR(85007, "页面路径错误"),

    CLASS_ENTRY_ERROR(85008, "类目填写错误"),

    VERSION_ALREADY_REVIEW(85009, "已经有正在审核的版本"),

    ITEMLIST_HAVE_EMPTY(85010, "item_list有项目为空"),

    TITLE_ERROR(85011, "标题填写错误"),

    REVIEWLIST_OUT_OF_ANGE(85023, "审核列表填写的项目数不在1-5以内"),

    CATEGORY_INFORMATION_INVALID(85077, "小程序类目信息失效（类目中含有官方下架的类目，请重新选择类目）"),

    INCOMPLETE_INFORMATION(86002, "小程序还未设置昵称、头像、简介。请先设置完后再重新提交。"),

    AUDIT_OVER_SUBMISSION(85085, "近7天提交审核的小程序数量过多，请耐心等待审核完毕后再次提交"),

    INVALID_AUDIT_ID(85012, "无效的审核id"),

    NO_APPROVED_VERSION(85019, "没有审核版本"),

    NOT_SATISFY_RELEASE(85020, "审核状态未满足发布"),

    VERSION_IN_GRAY(87011, "现网已经在灰度发布，不能进行版本回退"),

    VERSION_CANNOT_RETURNED(87012, "该版本不能回退，可能的原因：1:无上一个线上版用于回退 2:此版本为已回退版本，不能回退 3:此版本为回退功能上线之前的版本，不能回退"),

    RECALL_REACH_LIMIT(87013, "撤回次数达到上限（每天一次，每个月10次）"),

    VERSION_INPUT_ERROR(85015, "版本输入错误"),

    /**
     * 小程序代码模版库管理
     */
    DRAFT_OR_TEMPLATE_UNFIND(85064, "找不到草稿或模板"),

    TEMPLATE_FULL(85065, "模版库已满"),

    /**
     * 小程序模板消息
     */
    //TEMPLATE_ID_FALSE(40037, "template_id不正确"),

    FORMID_FALSE(41028, "form_id不正确，或者过期"),

    FORMID_ALREADY_USED(41029, "form_id已被使用"),

    PAHE_PATH_ERROR(41030, "page不正确"),

    INTERFACE_CALL_OVER(45009, "接口调用超过限额（目前默认每个帐号日调用限额为100万）"),

    INVALID_APPID(40013, "invalid appid，appid或open_appid无效。"),

    ACCOUNT_HAS_BOUND(89000, "account has bound open，该公众号/小程序已经绑定了开放平台帐号"),

    NOT_SAME_CONTRACTOR(89001, "not same contractor，Authorizer与开放平台帐号主体不相同"),

    OPEN_NOT_EXISTS(89002, "open not exists，该公众号/小程序未绑定微信开放平台帐号。"),

    ILLEGAL_CREATE(89003, "该开放平台帐号并非通过api创建，不允许操作"),

    APP_BINDING_LIMIT(89004, "该开放平台帐号所绑定的公众号/小程序已达上限（100个）"),

    SEARCH_FORBID_ERROR(85083, "搜索标记位被封禁，无法修改"),

    STATUS_VALUE_INVALID(85084, "非法的status值，只能填0或者1"),

    /**
     * 插件模块
     */
    PLUG_CANNOT_APPLIED(89236, "该插件不能申请"),

    PLUG_HAS_ADD(89237, "已经添加该插件"),

    PLUGS_NUMBER_LIMIT(89238, "申请或使用的插件已经达到上限"),

    PLUG_EXISTS(89239, "该插件不存在"),

    PLUG_DELETE_ERROR(89243, "该申请为“待确认”状态，不可删除"),

    PLUG_APPID_EXISTS(89244, "不存在该插件appid"),

    /**
     * 公共返回状态码
     */
    INVALID_CREDENTIAL(40001, "不合法的调用凭证"),

    INVALID_GRANT_TYPE(40002, "不合法的grant_type"),

    INVALID_OPENID(40003, "不合法的openid"),

    INVALID_MEDIA_TYPE(40004, "不合法的媒体文件类型"),

    INVALID_MEDIA_ID(40007, "不合法的media_id"),

    INVALID_MESSAGE_TYPE(40008, "不合法的message_type"),

    INVALID_IMAGE_SIZE(40009, "不合法的图片大小"),

    INVALID_VOICE_SIZE(40010, "不合法的语音大小"),

    INVALID_VIDEO_SIZE(40011, "不合法的视频大小"),

    INVALID_THUMB_SIZE(40012, "不合法的缩略图大小"),

    INVALID_ACCESS_TOKEN(40014, "不合法的access_token"),

    INVALID_MENU_TYPE(40015, "不合法的菜单类型"),

    INVALID_BUTTON_SIZE(40016, "不合法的菜单按钮个数"),

    INVALID_BUTTON_TYPE(40017, "不合法的按钮类型"),

    INVALID_BUTTON_NAME_SIZE(40018, "不合法的按钮名称长度"),

    INVALID_BUTTON_KEY_SIZE(40019, "不合法的按钮KEY长度"),

    INVALID_BUTTON_URL_SIZE(40020, "不合法的url长度"),

    INVALID_SUB_BUTTON_SIZE(40023, "不合法的子菜单按钮个数"),

    INVALID_SUB_BUTTON_TYPE(40024, "不合法的子菜单类型"),

    INVALID_SUB_BUTTON_NAME_SIZE(40025, "不合法的子菜单按钮名称长度"),

    INVALID_SUB_BUTTON_KEY_SIZE(40026, "不合法的子菜单按钮KEY长度"),

    INVALID_SUB_BUTTON_URL_SIZE(40027, "不合法的子菜单按钮url长度"),

    INVALID_CODE(40029, "不合法或已过期的code"),

    INVALID_REFRESH_TOKEN(40030, "不合法的refresh_token"),

    INVALID_TEMPLATE_ID_SIZE(40036, "不合法的template_id长度"),

    INVALID_TEMPLATE_ID(40037, "不合法的template_id"),

    INVALID_URL_SIZE(40039, "不合法的url长度"),

    INVALID_URL_DOMAIN(40048, "不合法的url域名"),

    NVALID_SUB_BUTTON_URL_DOMAIN(40054, "不合法的子菜单按钮url域名"),

    INVALID_BUTTON_URL_DOMAIN(40055, "不合法的菜单按钮url域名"),

    INVALID_URL(40066, "不合法的url"),

    ACCESS_TOKEN_MISSING(41001, "缺失access_token参数"),

    APPID_MISSING(41002, "缺失appid参数"),

    REFRESH_TOKEN_MISSING(41003, "缺失refresh_token参数"),

    APPSECRET_MISSING(41004, "缺失secret参数"),

    MEDIA_DATA_MISSING(41005, "缺失二进制媒体文件"),

    MEDIA_ID_MISSING(41006, "缺失media_id参数"),

    SUB_MENU_DATA_MISSING(41007, "缺失子菜单数据"),

    MISSING_CODE(41008, "缺失code参数"),

    MISSING_OPENID(41009, "缺失openid参数"),

    MISSING_URL(41010, "缺失url参数"),

    ACCESS_TOKEN_EXPIRED(42001, "access_token超时"),

    REFRESH_TOKEN_EXPIRED(42002, "refresh_token超时"),

    CODE_EXPIRED(42003, "code超时"),

    REQUIRE_GET_METHOD(43001, "需要使用GET方法请求"),

    REQUIRE_POST_METHOD(43002, "需要使用POST方法请求"),

    REQUIRE_HTTPS(43003, "需要使用HTTPS"),

    REQUIRE_SUBSCRIBE(43004, "需要订阅关系"),

    EMPTY_MEDIA_DATA(44001, "空白的二进制数据"),

    EMPTY_POST_DATA(44002, "空白的POST数据"),

    EMPTY_NEWS_DATA(44003, "空白的news数据"),

    EMPTY_CONTENT(44004, "空白的内容"),

    EMPTY_LIST_SIZE(44005, "空白的列表"),

    MEDIA_SIZE_OUT_OF_LIMIT(45001, "二进制文件超过限制"),

    CONTENT_SIZE_OUT_OF_LIMIT(45002, "content参数超过限制"),

    TITLE_SIZE_OUT_OF_LIMIT(45003, "title参数超过限制"),

    DESCRIPTION_SIZE_OUT_OF_LIMIT(45004, "description参数超过限制"),

    URL_SIZE_OUT_OF_LIMIT(45005, "url参数长度超过限制"),

    PICURL_SIZE_OUT_OF_LIMIT(45006, "picurl参数超过限制"),

    PLAYTIME_OUT_OF_LIMIT(45007, "播放时间超过限制（语音为60s最大）"),

    ARTICLE_SIZE_OUT_OF_LIMIT(45008, "article参数超过限制"),

    API_FREQ_OUT_OF_LIMIT(45009, "接口调动频率超过限制"),

    ACREATE_MENU_LIMIT(45010, "建立菜单被限制"),

    API_LIMIT(45011, "频率限制"),

    TEMPLATE_SIZE_OUT_OF_LIMIT(45012, "模板大小超过限制"),

    MODIFY_SYS_GROUP_ERROR(45016, "不能修改默认组"),

    GROUP_NAME_LONG(45017, "修改组名过长"),

    GROUP_TOOMANY(45018, "组数量过多"),

    API_UNAUTHORIZED(50001, "接口未授权"),

    ACCOUNT_REFUND_ERROR(1502, "退款失败!");


    private int code;

    private String message;

    WxResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    //根据code（返回码）返回枚举信息
    public static WxResponseCode parseMessage(int code) {
        WxResponseCode[] types = WxResponseCode.values();
        for (WxResponseCode type : types) {
            if (type.code() == code) {
                return type;
            }
        }
        return UNKNOWN_CODE;
    }
}
