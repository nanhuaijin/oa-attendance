package com.ccloud.oa.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ccloud.oa.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户头像表
 * </p>
 *
 * @author breeze
 * @since 2020-08-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("oa_avatar")
@ApiModel(value="Avatar对象", description="用户头像表")
public class Avatar extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户账号")
    private String account;

    @ApiModelProperty(value = "用户头像地址")
    private String avatar;


}
