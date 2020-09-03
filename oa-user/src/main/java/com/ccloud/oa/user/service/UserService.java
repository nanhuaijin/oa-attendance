package com.ccloud.oa.user.service;

import com.ccloud.oa.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccloud.oa.user.vo.*;

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

    UserInfo getInfo(String account);

    User checkAccountExist(String username);

    String sendSms(String phone, Integer type);

    int updateUserByAccount(UserInfo userInfo);

    int updatePasswordByAccount(PasswordVO passwordVO);

    int bingingPhoneByAccount(PhoneVO phoneVO);

    int updatePhoneByAccount(PhoneVO phoneVO);
}
