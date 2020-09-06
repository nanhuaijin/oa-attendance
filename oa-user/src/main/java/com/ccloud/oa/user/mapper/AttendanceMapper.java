package com.ccloud.oa.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccloud.oa.user.entity.Attendance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Date;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author breeze
 * @since 2020-08-19
 */
@Mapper
public interface AttendanceMapper extends BaseMapper<Attendance> {

    Integer getPunchClockCount(@Param("account") String account,
                               @Param("begin") LocalDateTime begin,
                               @Param("end") LocalDateTime end);
}
