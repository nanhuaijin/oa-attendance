package com.ccloud.oa.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author : breeze
 * @date : 2020/9/6
 * @description : 打卡百分率VO对象
 */
@Data
@ApiModel("打卡百分率VO对象")
public class PercentagesVO {

    @ApiModelProperty("本周打卡百分率")
    private BigDecimal currWeekPer;

    @ApiModelProperty("上周打卡百分率")
    private BigDecimal preWeekPer;

    @ApiModelProperty("本月打卡百分率")
    private BigDecimal currMonthPer;

    @ApiModelProperty("本年打卡百分率")
    private BigDecimal currYearPer;

}
