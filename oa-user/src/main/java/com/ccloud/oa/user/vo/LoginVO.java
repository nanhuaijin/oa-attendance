package com.ccloud.oa.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : breeze
 * @date : 2020/8/19
 * @description : 登录VO对象
 */
@Data
@ApiModel("登录页面VO对象")
public class LoginVO {

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("密码")
    private String password;
}
