package com.ccloud.oa.user.service;

import com.ccloud.oa.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 员工表 服务类
 * </p>
 *
 * @author breeze
 * @since 2020-08-14
 */
public interface UserService extends IService<User> {

    User login(User user);

    int register(User user);
}
