package com.ccloud.oa.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ccloud.oa.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author breeze
 * @since 2020-08-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("oa_attendance")
@ApiModel(value="Attendance对象", description="签到打卡表")
public class Attendance extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户账号")
    private String account;

    @ApiModelProperty(value = "年")
    private Integer year;

    @ApiModelProperty(value = "月")
    private Integer month;

    @ApiModelProperty(value = "日")
    private Integer day;

    @ApiModelProperty(value = "上班时间")
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date start;

    @ApiModelProperty(value = "下班时间")
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date end;

    @ApiModelProperty(value = "上班时常")
    private Double hours;

    @ApiModelProperty(value = "0-正常 1-迟到 2-事假 3-病假 4-休假 默认0")
    private Integer status;

    @ApiModelProperty(value = "上班打卡地点")
    private String addressStart;

    @ApiModelProperty(value = "下班打卡地点")
    private String addressEnd;
}
