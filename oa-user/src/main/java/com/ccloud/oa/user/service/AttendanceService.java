package com.ccloud.oa.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccloud.oa.user.entity.Attendance;
import com.ccloud.oa.user.vo.PercentagesVO;

import java.util.List;

/**
 * @author : breeze
 * @date : 2020/9/1
 * @description : 打卡信息Service
 */
public interface AttendanceService extends IService<Attendance> {

    Attendance punchClockUp(String account, String address);

    Attendance punchClockLower(String account, String address);

    List<Attendance> listCalendarDataByAccount(String account);

    Attendance getCalendarDataByDay(String account, Integer year, Integer month, Integer day);

    PercentagesVO getPercentages(String account);
}
