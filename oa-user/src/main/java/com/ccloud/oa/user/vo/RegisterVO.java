package com.ccloud.oa.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : breeze
 * @date : 2020/8/19
 * @description :
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("注册页面VO对象")
public class RegisterVO extends LoginVO{

    @ApiModelProperty(value = "重复输入的密码")
    private String passwordAgain;

    @ApiModelProperty(value = "手机号码")
    private String phone;

}
