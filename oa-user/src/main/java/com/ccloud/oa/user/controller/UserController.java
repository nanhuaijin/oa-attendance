package com.ccloud.oa.user.controller;


import com.ccloud.oa.common.result.BaseResponse;
import com.ccloud.oa.common.result.ResultCodeEnum;
import com.ccloud.oa.user.entity.User;
import com.ccloud.oa.user.service.UserService;
import com.ccloud.oa.user.vo.LoginVO;
import com.ccloud.oa.user.vo.RegisterVO;
import com.ccloud.oa.user.vo.UpdatePasswordVO;
import com.ccloud.oa.user.vo.UserInfo;
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

    @ApiOperation("员工用户登录")
    @PostMapping("/login")
    public BaseResponse login(
            @ApiParam(name = "loginVO", value = "登录页面VO对象", required = true)
            @RequestBody LoginVO loginVO) {

        UserInfo userInfo = this.userService.login(loginVO);

        return BaseResponse.setResult(ResultCodeEnum.ACCOUNT_LOGIN_SUCCESS).data("userInfo", userInfo);
    }

    @ApiOperation("员工用户注册")
    @PostMapping("/register")
    public BaseResponse register(
            @ApiParam(name = "user", value = "员工用户对象", required = true)
            @RequestBody RegisterVO registerVO) {
        UserInfo userInfo = this.userService.register(registerVO);
        if (userInfo == null) {
            return BaseResponse.setResult(ResultCodeEnum.ACCOUNT_REGISTER_ERROR);
        } else {
            return BaseResponse.setResult(ResultCodeEnum.ACCOUNT_REGISTER_SUCCESS).data("userInfo", userInfo);
        }
    }

    @ApiOperation("发送短信验证码")
    @GetMapping("/sms")
    public BaseResponse sendSms(
            @ApiParam(name = "phone", value = "手机号码", required = true)
            @RequestParam("phone") String phone) {
        String message = this.userService.sendSms(phone);
        return BaseResponse.success().message(message);
    }

    @ApiOperation("校验用户名是否存在")
    @GetMapping("/check/account")
    public BaseResponse checkAccountExist(
            @ApiParam(name = "account", value = "用户账号", required = true)
            @RequestParam("account") String account) {
        User user = this.userService.checkAccountExist(account);
        if (user == null) {
            return BaseResponse.setResult(ResultCodeEnum.SUCCESS);
        } else {
            return BaseResponse.setResult(ResultCodeEnum.USERNAME_ALREADY_EXISTS_ERROR);
        }
    }

    @ApiOperation("获取员工信息")
    @GetMapping("/info")
    public BaseResponse getInfo(
            @ApiParam(name = "account", value = "用户账号", required = true)
            @RequestParam("account") String account) {

        UserInfo userInfo = this.userService.getInfo(account);

        return BaseResponse.success().data("user", userInfo);
    }

    @ApiOperation("更新员工信息")
    @PostMapping("/update/user")
    public BaseResponse updateUserByAccount(
            @ApiParam(name = "user", value = "用户对象", required = true)
            @RequestBody UserInfo userInfo) {

        int count = this.userService.updateUserByAccount(userInfo);

        if (count == 1) {
            return BaseResponse.success().message("更新用户信息成功");
        } else {
            return BaseResponse.setResult(ResultCodeEnum.UPDATE_USER_INFO_ERROR);
        }
    }

    @ApiOperation("更新密码")
    @PostMapping("/update/password")
    public BaseResponse updatePasswordByAccount(
            @ApiParam(name = "passwordVO", value = "更新密码VO", required = true)
            @RequestBody UpdatePasswordVO passwordVO) {

        int count = this.userService.updatePasswordByAccount(passwordVO);

        if (count == 1) {
            return BaseResponse.success().message("更新密码成功");
        } else {
            return BaseResponse.error().message("更新密码失败");
        }
    }

    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public BaseResponse logout() {
        return BaseResponse.setResult(ResultCodeEnum.ACCOUNT_LOGOUT_SUCCESS);
    }
}

