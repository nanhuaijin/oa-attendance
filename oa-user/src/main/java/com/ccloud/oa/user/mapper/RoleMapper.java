package com.ccloud.oa.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccloud.oa.user.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户角色权限表 Mapper 接口
 * </p>
 *
 * @author breeze
 * @since 2020-08-19
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}
