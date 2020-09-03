package com.ccloud.oa.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : breeze
 * @date : 2020/9/3
 * @description : 更新密码VO对象
 */
@Data
@ApiModel("更新密码VO对象")
public class UpdatePasswordVO {

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("新密码")
    private String newPassword;

    @ApiModelProperty("重复密码")
    private String passwordAgain;
}
