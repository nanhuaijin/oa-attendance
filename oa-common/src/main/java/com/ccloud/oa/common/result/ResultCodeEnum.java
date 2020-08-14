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

    ACCOUNT_LOGIN_SUCCESS(20001, "登录成功"),
    ACCOUNT_REGISTER_SUCCESS(20002, "员工账号注册成功"),
    ACCOUNT_OR_PASSWORD_ERROR(41000, "账号或密码错误"),

    SAVE_ORDER_ERROR(40002, "保存订单信息失败"),
    ORDER_PAY_ERROR(40003, "订单支付失败");

    private Integer code;

    private String message;
}
