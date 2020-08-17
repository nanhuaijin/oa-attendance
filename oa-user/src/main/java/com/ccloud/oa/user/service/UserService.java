package com.ccloud.oa.user.service;

import com.ccloud.oa.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccloud.oa.user.vo.UserVO;

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

    User register(UserVO userVO);

    User getInfo();

    User checkUsernameExists(String username);
}
