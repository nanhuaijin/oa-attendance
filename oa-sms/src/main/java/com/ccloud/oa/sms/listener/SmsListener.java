package com.ccloud.oa.sms.listener;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.ccloud.oa.sms.config.SmsEnum;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : breeze
 * @date : 2020/9/1
 * @description : 短信服务监听器
 */
@Component
public class SmsListener {

    @Value("${sms.accessKeyId}")
    private String accessKeyId;
    @Value("${sms.accessSecret}")
    private String accessSecret;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "OA-MESSAGE-QUEUE", durable = "true"),
            exchange = @Exchange(value = "oa.message.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"message.*"}
    ))
    public void listenerCode(Map<String, String> map){
        //手机号
        String phone = map.get("phone");
        String code = map.get("code");

        Map<String, String> templateParamMap = new HashMap<>(2);
        templateParamMap.put("code", code);

        DefaultProfile profile = DefaultProfile.getProfile(SmsEnum.SMS_TEMPLATE.getRegionId(), accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(SmsEnum.SMS_TEMPLATE.getSysDomain());
        request.setSysVersion(SmsEnum.SMS_TEMPLATE.getSysVersion());
        request.setSysAction(SmsEnum.SMS_TEMPLATE.getSysAction());
        request.putQueryParameter("RegionId", SmsEnum.SMS_TEMPLATE.getRegionId());
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", SmsEnum.SMS_TEMPLATE.getSignName());
        request.putQueryParameter("TemplateCode", SmsEnum.SMS_TEMPLATE.getTemplateCode());
        request.putQueryParameter("TemplateParam", JSON.toJSONString(templateParamMap));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }
}
