package com.ccloud.oa.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author : breeze
 * @date : 2020/8/14
 * @description : 全局统一返回常量结果 - 枚举类
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum ResultCodeEnum {

    SUCCESS(20000, "成功"),
    UNKNOWN_REASON(40001, "未知错误"),

    ACCOUNT_LOGIN_SUCCESS(20000, "登录成功"),
    ACCOUNT_REGISTER_SUCCESS(20000, "员工账号注册成功"),
    ACCOUNT_LOGOUT_SUCCESS(20000, "退出登录成功"),
    SEND_SMS_CODE_SUCCESS(20000, "验证码发送成功"),

    ACCOUNT_NOT_EXISTS_ERROR(41000, "该用户不存在"),
    ACCOUNT_OR_PASSWORD_ERROR(41001, "账号或密码错误"),
    PASSWORD_CHECK_SAME_ERROR(41002, "两次密码不一致"),
    USERNAME_ALREADY_EXISTS_ERROR(41003, "用户名已存在"),
    ACCOUNT_REGISTER_ERROR(41004, "账号注册失败，请稍后尝试"),
    SMS_CODE_COUNT_ERROR(41005, "验证码获取次数超限"),
    REPEAT_GET_CODE_ERROR(41005, "请不要频繁获取验证码"),
    PHONE_ALREADY_EXISTS_ERROR(41006, "手机号码已被占用"),
    PHONE_FORMAT_ERROR(41007, "手机号码格式不正确"),
    CODE_HAS_EXPIRED_ERROR(41008, "验证码已过期，请重新发送"),
    CODE_UNEQUAL_ERROR(41009, "您输入的验证码不正确"),
    UPDATE_USER_INFO_ERROR(41010, "更新用户信息失败"),
    EMAIL_FORMAT_ERROR(41011, "邮箱格式不正确"),
    UPDATE_TOKEN_EQUALS_ERROR(41012, "表单Token校验失败，请刷新重试"),


    REPEAT_PUNCH_CLOCK(42000, "请不要重复打卡"),
    PUNCH_CLOCK_LOWER_ERROR(42001, "请先进行上班打卡");

    private Integer code;

    private String message;
}
