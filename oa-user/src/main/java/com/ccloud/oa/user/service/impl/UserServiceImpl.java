package com.ccloud.oa.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccloud.oa.common.consts.AppConst;
import com.ccloud.oa.common.consts.RoleAuthEnum;
import com.ccloud.oa.common.exception.ApplicationException;
import com.ccloud.oa.common.result.ResultCodeEnum;
import com.ccloud.oa.common.utils.AppUtils;
import com.ccloud.oa.user.entity.Avatar;
import com.ccloud.oa.user.entity.Role;
import com.ccloud.oa.user.entity.User;
import com.ccloud.oa.user.mapper.RoleMapper;
import com.ccloud.oa.user.mapper.UserMapper;
import com.ccloud.oa.user.service.AvatarService;
import com.ccloud.oa.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccloud.oa.user.vo.LoginVO;
import com.ccloud.oa.user.vo.RegisterVO;
import com.ccloud.oa.user.vo.UserInfo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private AvatarService avatarService;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private ThreadPoolExecutor threadPool;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 登录
     * @param loginVO
     * @return
     */
    @Override
    public UserInfo login(LoginVO loginVO) {
        //根据用户名或者手机号查询用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account", loginVO.getAccount());
        wrapper.or().eq("phone", loginVO.getAccount());
        User user = this.baseMapper.selectOne(wrapper);

        //该用户不存在
        if (user == null) {
            throw new ApplicationException(ResultCodeEnum.ACCOUNT_NOT_EXISTS_ERROR);
        }

        //获取盐
        String salt = user.getSalt();
        String password = user.getPassword();

        String loginPassword = loginVO.getPassword();
        String md5Password = new String(DigestUtils.md5Digest(loginPassword.getBytes()));
        String passwordSalt = DigestUtils.appendMd5DigestAsHex(salt.getBytes(), new StringBuilder(md5Password)).toString();

        //如果密码正确，返回用户信息
        if (password.equals(passwordSalt)) {

            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(user, userInfo);

            //使用异步编排 - 查询用户角色权限和用户头像
            CompletableFuture<Void> avatarFuture = CompletableFuture.runAsync(() -> {
                Avatar avatar = this.avatarService.getAvatarByAccount(user.getAccount());
                userInfo.setAvatar(avatar.getAvatar());
            }, threadPool);

            CompletableFuture<Void> roleFuture = CompletableFuture.runAsync(() -> {
                Role role = this.roleMapper.selectById(user.getRoleId());
                userInfo.setRole(role.getRole());
                userInfo.setAuth(role.getAuth());
            }, threadPool);

            //等待异步结果返回
            CompletableFuture.allOf(avatarFuture, roleFuture).join();

            return userInfo;
        } else {
            throw new ApplicationException(ResultCodeEnum.ACCOUNT_OR_PASSWORD_ERROR);
        }

    }

    /**
     * 校验用户名是否重复
     * @param account
     * @return
     */
    @Override
    public User checkAccountExist(String account) {
        //校验用户名是否重复
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account", account);
        return this.baseMapper.selectOne(wrapper);
    }

    /**
     * 发送短信验证码
     * @param phone
     */
    @Override
    public String sendSms(String phone) {

        //判断是否是手机号码
        boolean mobilePhone = AppUtils.isMobilePhone(phone);
        if (!mobilePhone) {
            throw new ApplicationException(ResultCodeEnum.PHONE_FORMAT_ERROR);
        }

        //判断手机号码是否被占用
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        User user = this.baseMapper.selectOne(wrapper);
        if (user != null) {
            throw new ApplicationException(ResultCodeEnum.PHONE_ALREADY_EXISTS_ERROR);
        }

        //验证redis中存储的当前手机号获取验证码的次数
        //没有超过指定次数可以继续获取验证码
        //一个手机号码一天内最多获取3次验证码
        String countString = this.redisTemplate.opsForValue().get(AppConst.SMS_CODE_COUNT_PREFIX + phone);
        int count = 0;
        if (!StringUtils.isEmpty(countString)) {
            count = Integer.parseInt(countString);
        }
        if (count >= AppConst.SMS_CODE_MAX_COUNT) {
            throw new ApplicationException(ResultCodeEnum.SMS_CODE_COUNT_ERROR);
        }

        //验证redis中是否有未过期的验证码
        Boolean hasKey = this.redisTemplate.hasKey(AppConst.SMS_CODE_PREFIX + phone);
        if (hasKey) {
            throw new ApplicationException(ResultCodeEnum.REPEAT_GET_CODE_ERROR);
        }

        //随机生成6位验证码
        String code = AppUtils.generateRandomInt(6);
        Map<String, String> map = new HashMap<>(2);
        map.put("phone", phone);
        map.put("code", code);

        //发送rabbitMq
        this.amqpTemplate.convertAndSend("oa.message.exchange", "message.code", map);

        //发送之后将验证码存入redis，并设置过期时间
        this.redisTemplate.opsForValue().set(AppConst.SMS_CODE_PREFIX + phone, code, 5, TimeUnit.MINUTES);
        //修改获取验证码的次数
        Long expire = this.redisTemplate.getExpire(AppConst.SMS_CODE_COUNT_PREFIX + phone, TimeUnit.MINUTES);
        if (expire == null || expire <= 0) {
            expire = (long) (24 * 60);
        }
        count++;
        this.redisTemplate.opsForValue().set(AppConst.SMS_CODE_COUNT_PREFIX + phone, String.valueOf(count), expire, TimeUnit.MINUTES);
        return ResultCodeEnum.SEND_SMS_CODE_SUCCESS.getMessage();
    }

    /**
     * 注册
     * @param registerVO
     * @return
     */
    @Override
    public UserInfo register(RegisterVO registerVO) {

        //校验验证码是否正确
        String code = this.redisTemplate.opsForValue().get(AppConst.SMS_CODE_PREFIX + registerVO.getPhone());
        if (StringUtils.isEmpty(code)) {
            throw new ApplicationException(ResultCodeEnum.CODE_HAS_EXPIRED_ERROR);
        }
        if (!code.equalsIgnoreCase(registerVO.getCode())) {
            throw new ApplicationException(ResultCodeEnum.CODE_UNEQUAL_ERROR);
        }

        //校验两次密码是否相等
        String password = registerVO.getPassword();
        String passwordAgain = registerVO.getPasswordAgain();
        if (!password.equals(passwordAgain)) {
            throw new ApplicationException(ResultCodeEnum.PASSWORD_CHECK_SAME_ERROR);
        }

        //校验用户名是否重复
        String account = registerVO.getAccount();
        User user = this.checkAccountExist(account);
        if (user != null) {
            throw new ApplicationException(ResultCodeEnum.USERNAME_ALREADY_EXISTS_ERROR);
        }

        //如果为null，直接实例化
        user = new User();
        BeanUtils.copyProperties(registerVO, user);
        user.setUsername(account);

        //生成随机盐
        String salt = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        //先给密码加密
        String md5Password = new String(DigestUtils.md5Digest(password.getBytes()));
        StringBuilder passwordMd5 = DigestUtils.appendMd5DigestAsHex(salt.getBytes(), new StringBuilder(md5Password));
        user.setPassword(passwordMd5.toString());
        user.setSalt(salt);
        user.setRoleId(2);

        this.baseMapper.insert(user);

        //保存头像
        Avatar avatar = new Avatar();
        avatar.setAccount(user.getAccount());
        avatar.setAvatar(AppConst.DEFAULT_AVATAR_URL);
        this.avatarService.save(avatar);

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user, userInfo);
        userInfo.setRole(RoleAuthEnum.STAFF.getRole());
        userInfo.setAuth(RoleAuthEnum.STAFF.getAuth());
        userInfo.setAvatar(AppConst.DEFAULT_AVATAR_URL);

        return userInfo;
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
