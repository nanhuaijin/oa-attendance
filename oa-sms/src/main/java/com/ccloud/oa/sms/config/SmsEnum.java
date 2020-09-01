package com.ccloud.oa.sms.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author : breeze
 * @date : 2020/9/1
 * @description : 阿里云短信发送专用枚举类
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum SmsEnum {

    SMS_TEMPLATE("dysmsapi.aliyuncs.com", "2017-05-25", "SendSms",
            "cn-hangzhou", "团子", "SMS_180060107");

    private String sysDomain;
    private String sysVersion;
    private String sysAction;
    private String regionId;
    private String signName;
    private String templateCode;
}
