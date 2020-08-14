package com.ccloud.oa.user.controller;


import com.ccloud.oa.common.result.BaseResponse;
import com.ccloud.oa.common.result.ResultCodeEnum;
import com.ccloud.oa.user.entity.User;
import com.ccloud.oa.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 员工表 前端控制器
 * </p>
 *
 * @author breeze
 * @since 2020-08-14
 */
@RestController
@RequestMapping("/admin/user")
@Api(tags = "用户操作Controller")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("员工用户注册")
    @PostMapping("/register")
    public BaseResponse register(
            @ApiParam(name = "user", value = "员工用户对象", required = true)
            User user) {
        int count = this.userService.register(user);
        if (count == 1) {
            return BaseResponse.setResult(ResultCodeEnum.ACCOUNT_REGISTER_SUCCESS);
        } else {
            return BaseResponse.setResult(ResultCodeEnum.UNKNOWN_REASON);
        }
    }

    @ApiOperation("员工用户登录")
    @PostMapping("/login")
    public BaseResponse login(
            @ApiParam(name = "loginUser", value = "员工用户对象", required = true)
            User loginUser) {

        User user = this.userService.login(loginUser);

        return BaseResponse.setResult(ResultCodeEnum.ACCOUNT_LOGIN_SUCCESS).data("user", user);
    }
}

