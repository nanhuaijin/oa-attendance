package com.ccloud.oa.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccloud.oa.user.entity.Role;
import com.ccloud.oa.user.mapper.RoleMapper;
import com.ccloud.oa.user.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @author : breeze
 * @date : 2020/9/1
 * @description :
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
