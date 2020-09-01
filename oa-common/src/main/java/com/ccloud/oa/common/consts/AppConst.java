package com.ccloud.oa.common.consts;

/**
 * @author : breeze
 * @date : 2020/9/1
 * @description : 项目常量
 */
public interface AppConst {
    /**
     * redis中存储验证码的前缀
     */
    String SMS_CODE_PREFIX = "sms:code:";
    /**
     * redis中存储验证码的次数
     */
    String SMS_CODE_COUNT_PREFIX = "sms:code:count:";
    /**
     * 每个手机每天最多发送3条短信
     */
    Integer SMS_CODE_MAX_COUNT = 3;
    /**
     * 注册用户默认头像地址
     */
    String DEFAULT_AVATAR_URL = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";
}
