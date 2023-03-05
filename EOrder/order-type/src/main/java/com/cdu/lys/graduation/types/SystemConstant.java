package com.cdu.lys.graduation.types;

/**
 * 系统常量
 *
 * @author liyinsong
 * @date 2022/1/8 15:17
 */
public class SystemConstant {

    /**
     * 登录token key，后面加上token值
     */
    public static final String LOGIN_TOKEN_KEY = "LOGIN:TOKEN:";

    /**
     * 管理员登录token key
     */
    public static final String ADMIN_LOGIN_TOKEN_KEY = "ADMIN:LOGIN:";

    /**
     * 系统登录token时效
     */
    public static final long SYSTEM_LOGIN_TIMEOUT=1;

    /**
     * 系统api根路径
     */
    public static final String SYSTEM_API_ROOT = "/eorder/app/**";

    /**
     * 静态资源请求路径
     */
    public static final String SYSTEM_STATIC_PATH = "/eorder/static/**";

    /**
     * 管理系统请求路径
     */
    public static final String SYSTEM_MANAGE_PATH = "/eorder/admin/**";

    /**
     * 系统上传文件地址
     */
    public static final String SYSTEM_FILE_UPLOAD_PATH = "/eorder/file/upload";
    /**
     * 系统下载图片地址
     */
    public static final String SYSTEM_DOWNLOAD_PATH = "/eorder/file/download/";

    /**
     * 微信登录授权接口
     */
    public static final String WX_LOGIN_AUTH_URL = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={js_code}&grant_type=authorization_code";

    /**
     * 获取小程序全局唯一后台接口调用凭据（access_token）请求地址
     */
    public static final String WX_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={secret}";

    /**
     * 获取微信用户手机号接口
     */
    public static final String WX_PHONE_NUMBER_URL = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token={ACCESS_TOKEN}";

    /**
     * 热搜列表redis key
     */
    public static final String HOT_SEARCH_LIST_KEY="HOT:SEARCH";

    /**
     * 日销量
     */
    public static final String DAILY_SALES_KEY = "DAILY:SALE";

    /**
     * 日访问量
     */
    public static final String DAILY_VISIT_NUM_KEY = "DAILY:VISIT";

    /**
     * 日收入
     */
    public static final String DAILY_INCOME_KEY = "DAILY:INCOME";

    /**
     * 日评论数
     */
    public static final String DAILY_COMMENT_NUM_KEY = "DAILY:COMMENT";
}
