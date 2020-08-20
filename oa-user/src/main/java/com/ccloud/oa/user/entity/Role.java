package com.ccloud.oa.user.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ccloud.oa.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户角色权限表
 * </p>
 *
 * @author breeze
 * @since 2020-08-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("oa_role")
@ApiModel(value="Role对象", description="用户角色权限表")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色名称")
    private String role;

    @ApiModelProperty(value = "权限描述")
    private String auth;

    @ApiModelProperty(value = "0-正常 1-删除")
    @TableLogic
    private Boolean del;

    @ApiModelProperty(value = "创建人姓名")
    private String creator;

    @ApiModelProperty(value = "创建人账号")
    private String createAccount;


}
