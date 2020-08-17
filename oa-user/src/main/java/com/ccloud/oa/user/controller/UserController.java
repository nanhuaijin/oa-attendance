package com.ccloud.oa.user.controller;


import com.ccloud.oa.common.result.BaseResponse;
import com.ccloud.oa.common.result.ResultCodeEnum;
import com.ccloud.oa.user.entity.User;
import com.ccloud.oa.user.service.UserService;
import com.ccloud.oa.user.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("校验用户名是否存在")
    @PostMapping("/check/username")
    public BaseResponse checkUsernameExists(
            @ApiParam(name = "username", value = "员工名", required = true)
            String username) {
        User user = this.userService.checkUsernameExists(username);
        if (user == null) {
            return BaseResponse.setResult(ResultCodeEnum.USERNAME_ALREADY_EXISTS_ERROR);
        } else {
            return BaseResponse.setResult(ResultCodeEnum.SUCCESS);
        }
    }

    @ApiOperation("员工用户注册")
    @PostMapping("/register")
    public BaseResponse register(
            @ApiParam(name = "user", value = "员工用户对象", required = true)
            @RequestBody UserVO userVO) {
        User user = this.userService.register(userVO);
        if (user == null) {
            return BaseResponse.setResult(ResultCodeEnum.ACCOUNT_REGISTER_ERROR);
        } else {
            return BaseResponse.setResult(ResultCodeEnum.ACCOUNT_REGISTER_SUCCESS).data("user", user);
        }
    }

    @ApiOperation("员工用户登录")
    @PostMapping("/login")
    public BaseResponse login(
            @ApiParam(name = "loginUser", value = "员工用户对象", required = true)
            @RequestBody User loginUser) {

        User user = this.userService.login(loginUser);

        return BaseResponse.setResult(ResultCodeEnum.ACCOUNT_LOGIN_SUCCESS).data("user", user);
    }

    @ApiOperation("获取员工信息")
    @GetMapping("/info")
    public BaseResponse getInfo() {

        User user = this.userService.getInfo();

        return BaseResponse.success().data("user", user);
    }

    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public BaseResponse logout() {
        return BaseResponse.setResult(ResultCodeEnum.ACCOUNT_LOGOUT_SUCCESS);
    }
}

