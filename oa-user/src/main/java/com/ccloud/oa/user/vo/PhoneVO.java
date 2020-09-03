package com.ccloud.oa.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : breeze
 * @date : 2020/9/3
 * @description : 更新手机号码VO
 */
@Data
@ApiModel("更新手机号码VO")
public class PhoneVO {

    @ApiModelProperty("用户账号")
    private String account;

    @ApiModelProperty("旧手机号")
    private String oldPhone;

    @ApiModelProperty("旧手机号验证码")
    private String oldCode;

    @ApiModelProperty("新手机号")
    private String newPhone;

    @ApiModelProperty("新手机号验证码")
    private String newCode;
}
