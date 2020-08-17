package com.ccloud.oa.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccloud.oa.common.exception.ApplicationException;
import com.ccloud.oa.common.result.ResultCodeEnum;
import com.ccloud.oa.user.entity.User;
import com.ccloud.oa.user.mapper.UserMapper;
import com.ccloud.oa.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccloud.oa.user.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;

/**
 * <p>
 * 员工表 服务实现类
 * </p>
 *
 * @author breeze
 * @since 2020-08-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 校验用户名是否重复
     * @param username
     * @return
     */
    @Override
    public User checkUsernameExists(String username) {
        //校验用户名是否重复
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return this.baseMapper.selectOne(wrapper);
    }

    @Override
    public User login(User loginUser) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", loginUser.getUsername());

        User user = this.baseMapper.selectOne(wrapper);

        if (user != null && user.getPassword().equals(loginUser.getPassword())) {
            return user;
        } else {
            throw new ApplicationException(ResultCodeEnum.ACCOUNT_OR_PASSWORD_ERROR);
        }

    }

    @Override
    public User register(UserVO userVO) {
        //校验两次密码是否相等
        String password = userVO.getPassword();
        String passwordAgain = userVO.getPasswordAgain();
        if (!password.equals(passwordAgain)) {
            throw new ApplicationException(ResultCodeEnum.PASSWORD_CHECK_SAME_ERROR);
        }

        //校验用户名是否重复
        String username = userVO.getUsername();
        User user = this.checkUsernameExists(username);
        if (user != null) {
            throw new ApplicationException(ResultCodeEnum.USERNAME_ALREADY_EXISTS_ERROR);
        }

        //如果为null，直接实例化
        user = new User();
        BeanUtils.copyProperties(userVO, user);
        user.setName(username);
        this.baseMapper.insert(user);

        return this.checkUsernameExists(username);
    }

    @Override
    public User getInfo() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", 1);

        User user = this.baseMapper.selectOne(wrapper);

        if (user != null) {
            return user;
        } else {
            throw new ApplicationException(ResultCodeEnum.ACCOUNT_OR_PASSWORD_ERROR);
        }
    }
}
