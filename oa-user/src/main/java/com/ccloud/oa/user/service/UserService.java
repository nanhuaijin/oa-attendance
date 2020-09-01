package com.ccloud.oa.user.service;

import com.ccloud.oa.user.entity.Attendance;
import com.ccloud.oa.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccloud.oa.user.vo.LoginVO;
import com.ccloud.oa.user.vo.RegisterVO;
import com.ccloud.oa.user.vo.UserInfo;

import java.util.List;

/**
 * <p>
 * 员工表 服务类
 * </p>
 *
 * @author breeze
 * @since 2020-08-14
 */
public interface UserService extends IService<User> {

    UserInfo login(LoginVO loginVO);

    UserInfo register(RegisterVO registerVO);

    User getInfo();

    User checkAccountExist(String username);

    String sendSms(String phone);
}
