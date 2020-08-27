package com.ccloud.oa.user.controller;


import com.ccloud.oa.common.result.BaseResponse;
import com.ccloud.oa.common.result.ResultCodeEnum;
import com.ccloud.oa.user.entity.Attendance;
import com.ccloud.oa.user.entity.User;
import com.ccloud.oa.user.service.UserService;
import com.ccloud.oa.user.vo.LoginVO;
import com.ccloud.oa.user.vo.RegisterVO;
import com.ccloud.oa.user.vo.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @ApiOperation("获取当前登录人本月打卡记录")
    @GetMapping("/list/calendar/data")
    public BaseResponse listCalendarDataByAccount(
            @ApiParam(name = "account", value = "用户账号", required = true)
            @RequestParam("account") String account) {
        List<Attendance> attendanceList = this.userService.listCalendarDataByAccount(account);

        return BaseResponse.success().data("list", attendanceList).message("获取当前登录人本月打卡记录成功");
    }

    @ApiOperation("获取当前登录人具体一天的打卡信息")
    @GetMapping("/get/calendar/data/day")
    public BaseResponse getCalendarDataByDay(
            @ApiParam(name = "account", value = "用户账号", required = true)
            @RequestParam("account") String account,
            @ApiParam(name = "year", value = "年", required = true)
            @RequestParam("year") Integer year,
            @ApiParam(name = "month", value = "月", required = true)
            @RequestParam("month") Integer month,
            @ApiParam(name = "day", value = "日", required = true)
            @RequestParam("day") Integer day) {
        Attendance attendance = this.userService.getCalendarDataByDay(account, year, month, day);

        return BaseResponse.success().data("attendance", attendance).message("获取某天打卡信息成功");
    }

    @ApiOperation("上班打卡接口")
    @GetMapping("/punchClock/up")
    public BaseResponse punchClockUp(
            @ApiParam(name = "account", value = "用户账号", required = true)
            @RequestParam("account") String account,
            @ApiParam(name = "address", value = "打卡地点", required = true)
            @RequestParam("address") String address) {
        Attendance attendance = this.userService.punchClockUp(account, address);

        return BaseResponse.success().data("attendance", attendance).message("打卡成功");
    }

    @ApiOperation("下班打卡接口")
    @GetMapping("/punchClock/lower")
    public BaseResponse punchClockLower(
            @ApiParam(name = "account", value = "用户账号", required = true)
            @RequestParam("account") String account,
            @ApiParam(name = "address", value = "打卡地点", required = true)
            @RequestParam("address") String address,
            @ApiParam(name = "type", value = "0-正常打卡 1-提前打卡", required = true)
            @RequestParam("type") Integer type) {

        if (type == 0) {
            //先判断是否是下班时间
            //获取当天的17点30 2020-08-20T17:30:00
            LocalDateTime localDateTime = LocalDateTime.now().withHour(17).withMinute(30).withSecond(0).withNano(0);
            LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
            if (now.isBefore(localDateTime)) {
                return BaseResponse.success();
            }
        }

        Attendance attendance = this.userService.punchClockLower(account, address);

        return BaseResponse.success().data("attendance", attendance).message("打卡成功");
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

