package com.ccloud.oa.user.controller;

import com.ccloud.oa.common.result.BaseResponse;
import com.ccloud.oa.user.entity.Attendance;
import com.ccloud.oa.user.service.AttendanceService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : breeze
 * @date : 2020/9/1
 * @description : 日常打卡Controller
 */
@RestController
@RequestMapping("/admin/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @ApiOperation("获取当前登录人本月打卡记录")
    @GetMapping("/list/calendar/data")
    public BaseResponse listCalendarDataByAccount(
            @ApiParam(name = "account", value = "用户账号", required = true)
            @RequestParam("account") String account) {
        List<Attendance> attendanceList = this.attendanceService.listCalendarDataByAccount(account);

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
        Attendance attendance = this.attendanceService.getCalendarDataByDay(account, year, month, day);

        return BaseResponse.success().data("attendance", attendance).message("获取某天打卡信息成功");
    }

    @ApiOperation("上班打卡接口")
    @GetMapping("/punchClock/up")
    public BaseResponse punchClockUp(
            @ApiParam(name = "account", value = "用户账号", required = true)
            @RequestParam("account") String account,
            @ApiParam(name = "address", value = "打卡地点", required = true)
            @RequestParam("address") String address) {
        Attendance attendance = this.attendanceService.punchClockUp(account, address);

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

        Attendance attendance = this.attendanceService.punchClockLower(account, address);

        return BaseResponse.success().data("attendance", attendance).message("打卡成功");
    }
}
