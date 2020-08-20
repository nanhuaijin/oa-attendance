package com.ccloud.oa.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccloud.oa.common.consts.RoleAuthEnum;
import com.ccloud.oa.common.exception.ApplicationException;
import com.ccloud.oa.common.result.ResultCodeEnum;
import com.ccloud.oa.common.utils.DateUtil;
import com.ccloud.oa.user.entity.Attendance;
import com.ccloud.oa.user.entity.Avatar;
import com.ccloud.oa.user.entity.Role;
import com.ccloud.oa.user.entity.User;
import com.ccloud.oa.user.mapper.AttendanceMapper;
import com.ccloud.oa.user.mapper.AvatarMapper;
import com.ccloud.oa.user.mapper.RoleMapper;
import com.ccloud.oa.user.mapper.UserMapper;
import com.ccloud.oa.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccloud.oa.user.vo.LoginVO;
import com.ccloud.oa.user.vo.RegisterVO;
import com.ccloud.oa.user.vo.UserInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

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
    private AvatarMapper avatarMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AttendanceMapper attendanceMapper;
    @Autowired
    private ThreadPoolExecutor threadPool;

    /**
     * 登录
     * @param loginVO
     * @return
     */
    @Override
    public UserInfo login(LoginVO loginVO) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account", loginVO.getAccount());

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
                QueryWrapper<Avatar> AvatarWrapper = new QueryWrapper<>();
                AvatarWrapper.eq("account", loginVO.getAccount());
                Avatar avatar = this.avatarMapper.selectOne(AvatarWrapper);
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
     * 注册
     * @param registerVO
     * @return
     */
    @Override
    public UserInfo register(RegisterVO registerVO) {
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
        avatar.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        this.avatarMapper.insert(avatar);

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user, userInfo);
        userInfo.setRole(RoleAuthEnum.STAFF.getRole());
        userInfo.setAuth(RoleAuthEnum.STAFF.getAuth());

        return userInfo;
    }

    /**
     * 上班打卡
     * @param account
     * @param address
     * @return
     */
    @Override
    public Attendance punchClockUp(String account, String address) {

        //先查询数据库中有没有当天的打卡记录
        QueryWrapper<Attendance> wrapper = new QueryWrapper<>();
        String startTime = DateUtil.getTodayStartTime();
        String endTime = DateUtil.getTodayEndTime();
        wrapper.ge("up_time", startTime); //大于等于
        wrapper.le("up_time", endTime); //小于等于
        wrapper.eq("account", account);
        Attendance attendance = this.attendanceMapper.selectOne(wrapper);
        if (attendance != null) {
            throw new ApplicationException(ResultCodeEnum.REPEAT_PUNCH_CLOCK);
        }

        //先判断是否迟到
        //获取当天的9点05 2020-08-20T09:05:00
        LocalDateTime localDateTime = LocalDateTime.now().withHour(9).withMinute(5).withSecond(0).withNano(0);
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);

        attendance = new Attendance();
        //数据库默认状态是0，未迟到，所以只有迟到才设置状态
        if (now.isAfter(localDateTime)) {
            attendance.setStatus(1);
        }

        attendance.setAccount(account);
        attendance.setAddressUp(address);
        attendance.setUpTime(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
        //保存
        this.attendanceMapper.insert(attendance);

        return this.attendanceMapper.selectById(attendance.getId());
    }

    /**
     * 下班打卡
     * @param account
     * @param address
     * @return
     */
    @Override
    public Attendance punchClockLower(String account, String address) {

        //先查询数据库中有没有当天的打卡记录
        QueryWrapper<Attendance> wrapper = new QueryWrapper<>();
        String startTime = DateUtil.getTodayStartTime();
        String endTime = DateUtil.getTodayEndTime();
        wrapper.ge("up_time", startTime); //大于等于
        wrapper.le("up_time", endTime); //小于等于
        wrapper.eq("account", account);
        Attendance attendance = this.attendanceMapper.selectOne(wrapper);
        if (attendance == null) {
            throw new ApplicationException(ResultCodeEnum.PUNCH_CLOCK_LOWER_ERROR);
        }

        attendance.setLowerTime(new Date());
        attendance.setAddressLower(address);
        this.attendanceMapper.updateById(attendance);

        return attendance;
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
