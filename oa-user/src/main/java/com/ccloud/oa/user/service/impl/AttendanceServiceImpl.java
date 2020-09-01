package com.ccloud.oa.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccloud.oa.common.exception.ApplicationException;
import com.ccloud.oa.common.result.ResultCodeEnum;
import com.ccloud.oa.common.utils.DateUtils;
import com.ccloud.oa.user.entity.Attendance;
import com.ccloud.oa.user.mapper.AttendanceMapper;
import com.ccloud.oa.user.service.AttendanceService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

/**
 * @author : breeze
 * @date : 2020/9/1
 * @description :
 */
@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance> implements AttendanceService {

    /**
     * 获取当前登录人本月打卡记录
     * @param account
     * @return
     */
    @Override
    public List<Attendance> listCalendarDataByAccount(String account) {

        //获取当前月份
        int month = LocalDateTime.now().getMonthValue();

        QueryWrapper<Attendance> wrapper = new QueryWrapper<>();
        wrapper.eq("account", account);
        wrapper.eq("month", month);
        return this.baseMapper.selectList(wrapper);
    }

    /**
     * 获取当前登录人具体一天的打卡信息
     * @param account
     * @param year
     * @param month
     * @param day
     * @return
     */
    @Override
    public Attendance getCalendarDataByDay(String account, Integer year, Integer month, Integer day) {
        QueryWrapper<Attendance> wrapper = new QueryWrapper<>();
        wrapper.eq("year", year);
        wrapper.eq("month", month);
        wrapper.eq("day", day);
        wrapper.eq("account", account);
        return this.baseMapper.selectOne(wrapper);
    }

    /**
     * 上班打卡
     * @param account
     * @param address
     * @return
     */
    @Override
    public Attendance punchClockUp(String account, String address) {

        //先查询数据库中有没有当天的打卡记录
        //数据库是按照年月日时间分开保存，只要当天的打卡记录不存在即可
        QueryWrapper<Attendance> wrapper = new QueryWrapper<>();
        LocalDateTime now = LocalDateTime.now().withNano(0);
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        wrapper.eq("year", year);
        wrapper.eq("month", month);
        wrapper.eq("day", day);
        wrapper.eq("account", account);
        Attendance attendance = this.baseMapper.selectOne(wrapper);
        if (attendance != null) {
            throw new ApplicationException(ResultCodeEnum.REPEAT_PUNCH_CLOCK);
        }

        //先判断是否迟到
        //获取当天的9点05 2020-08-20T09:05:00
        LocalDateTime localDateTime = LocalDateTime.now().withHour(9).withMinute(5).withSecond(0).withNano(0);

        attendance = new Attendance();
        //数据库默认状态是0，未迟到，所以只有迟到才设置状态
        if (now.isAfter(localDateTime)) {
            attendance.setStatus(1);
        }

        attendance.setAccount(account);
        attendance.setAddressStart(address);
        attendance.setYear(year);
        Instant instant = now.toInstant(ZoneOffset.of("+8"));
        attendance.setStart(Date.from(instant));
        attendance.setMonth(month);
        attendance.setDay(day);

        //保存
        this.baseMapper.insert(attendance);

        return this.baseMapper.selectById(attendance.getId());
    }

    /**
     * 下班打卡
     * @param account
     * @param address
     * @return
     */
    @Override
    public Attendance punchClockLower(String account, String address) {

        //先查询数据库中有没有当天的打卡记录，这里下班打卡只包含当天的，超过当天没打卡算漏卡
        QueryWrapper<Attendance> wrapper = new QueryWrapper<>();
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        wrapper.eq("year", year);
        wrapper.eq("month", month);
        wrapper.eq("day", day);
        wrapper.eq("account", account);
        Attendance attendance = this.baseMapper.selectOne(wrapper);
        if (attendance == null) {
            throw new ApplicationException(ResultCodeEnum.PUNCH_CLOCK_LOWER_ERROR);
        }

        attendance.setEnd(new Date());
        attendance.setAddressEnd(address);
        try {
            //计算当天工作时常
            double hours = DateUtils.differHours(attendance.getStart(), attendance.getEnd());
            attendance.setHours(hours);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.baseMapper.updateById(attendance);

        return attendance;
    }
}
