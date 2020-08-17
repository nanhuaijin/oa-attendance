package com.ccloud.oa.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : breeze
 * @date : 2020/8/17
 * @description : 登录注册VO对象
 */
@Data
public class UserVO {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "用户账号(工号)")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "重复输入的密码")
    private String passwordAgain;
}
