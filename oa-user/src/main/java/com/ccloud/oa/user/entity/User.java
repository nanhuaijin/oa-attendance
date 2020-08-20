package com.ccloud.oa.user.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.ccloud.oa.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 员工表
 * </p>
 *
 * @author breeze
 * @since 2020-08-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("oa_user")
@ApiModel(value="User对象", description="员工表")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关联角色表id")
    private Integer roleId;

    @ApiModelProperty(value = "用户账号")
    private String account;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "加密盐")
    private String salt;

    @ApiModelProperty(value = "用户昵称")
    private String username;

    @ApiModelProperty(value = "真实姓名")
    private String name;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "性别 0-男 1-女")
    private Boolean sex;

    @ApiModelProperty(value = "生日")
    private String birthday;

    @ApiModelProperty(value = "状态 0-正常 1-删除")
    @TableLogic
    private Boolean del;
}
