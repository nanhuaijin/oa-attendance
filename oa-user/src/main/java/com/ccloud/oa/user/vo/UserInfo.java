package com.ccloud.oa.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : breeze
 * @date : 2020/8/19
 * @description :
 */
@Data
@ApiModel("登录成功返回前端的用户信息")
public class UserInfo {

    @ApiModelProperty(value = "用户账号")
    private String account;

    @ApiModelProperty(value = "角色名称")
    private String role;

    @ApiModelProperty(value = "权限描述")
    private String auth;

    @ApiModelProperty(value = "用户昵称")
    private String username;

    @ApiModelProperty(value = "真实姓名")
    private String name;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "用户头像地址")
    private String avatar;

    @ApiModelProperty(value = "性别 0-男 1-女")
    private Boolean sex;

    @ApiModelProperty(value = "生日")
    private String birthday;

}
