package com.ccloud.oa.common.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : breeze
 * @date : 2020/8/14
 * @description :
 */
@Data
@ApiModel("全局统一返回结果")
public class BaseResponse {

    @ApiModelProperty("返回状态码")
    private Integer code;

    @ApiModelProperty("返回消息")
    private String message;

    @ApiModelProperty("返回数据")
    private Map<String, Object> data;

    public static BaseResponse success() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(ResultCodeEnum.SUCCESS.getCode());
        baseResponse.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return baseResponse;
    }

    public static BaseResponse error() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(ResultCodeEnum.UNKNOWN_REASON.getCode());
        baseResponse.setMessage(ResultCodeEnum.UNKNOWN_REASON.getMessage());
        return baseResponse;
    }

    public static BaseResponse setResult(ResultCodeEnum codeEnum) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(codeEnum.getCode());
        baseResponse.setMessage(codeEnum.getMessage());
        return baseResponse;
    }

    public BaseResponse message(String message) {
        this.setMessage(message);
        return this;
    }

    public BaseResponse code(Integer code) {
        this.setCode(code);
        return this;
    }

    public BaseResponse data(String key, Object value) {
        Map<String, Object> data = new HashMap<>();
        data.put(key, value);
        this.setData(data);
        return this;
    }

    public BaseResponse data(Map<String, Object> data) {
        this.setData(data);
        return this;
    }

}
