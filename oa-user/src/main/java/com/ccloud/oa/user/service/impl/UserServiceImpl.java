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

import java.text.ParseException;
import java.time.*;
import java.util.Date;
import java.util.List;
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
     * 获取当前登录人本月打卡记录
     * @param account
     * @return
     */
    @Override
    public List<Attendance> listCalendarDataByAccount(String account) {

        //获取当前月份
        int month = LocalDateTime.now().getMonthValue();

        QueryWrapper<Attendance> wrapper = new QueryWrapper<>();
        wrapper.eq("account", account);
        wrapper.eq("month", month);
        return this.attendanceMapper.selectList(wrapper);
    }

    /**
     * 获取当前登录人具体一天的打卡信息
     * @param account
     * @param year
     * @param month
     * @param day
     * @return
     */
    @Override
    public Attendance getCalendarDataByDay(String account, Integer year, Integer month, Integer day) {
        QueryWrapper<Attendance> wrapper = new QueryWrapper<>();
        wrapper.eq("year", year);
        wrapper.eq("month", month);
        wrapper.eq("day", day);
        wrapper.eq("account", account);
        return this.attendanceMapper.selectOne(wrapper);
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
        //数据库是按照年月日时间分开保存，只要当天的打卡记录不存在即可
        QueryWrapper<Attendance> wrapper = new QueryWrapper<>();
        LocalDateTime now = LocalDateTime.now().withNano(0);
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        wrapper.eq("year", year);
        wrapper.eq("month", month);
        wrapper.eq("day", day);
        wrapper.eq("account", account);
        Attendance attendance = this.attendanceMapper.selectOne(wrapper);
        if (attendance != null) {
            throw new ApplicationException(ResultCodeEnum.REPEAT_PUNCH_CLOCK);
        }

        //先判断是否迟到
        //获取当天的9点05 2020-08-20T09:05:00
        LocalDateTime localDateTime = LocalDateTime.now().withHour(9).withMinute(5).withSecond(0).withNano(0);

        attendance = new Attendance();
        //数据库默认状态是0，未迟到，所以只有迟到才设置状态
        if (now.isAfter(localDateTime)) {
            attendance.setStatus(1);
        }

        attendance.setAccount(account);
        attendance.setAddressStart(address);
        attendance.setYear(year);
        Instant instant = now.toInstant(ZoneOffset.of("+8"));
        attendance.setStart(Date.from(instant));
        attendance.setMonth(month);
        attendance.setDay(day);

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

        //先查询数据库中有没有当天的打卡记录，这里下班打卡只包含当天的，超过当天没打卡算漏卡
        QueryWrapper<Attendance> wrapper = new QueryWrapper<>();
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        wrapper.eq("year", year);
        wrapper.eq("month", month);
        wrapper.eq("day", day);
        wrapper.eq("account", account);
        Attendance attendance = this.attendanceMapper.selectOne(wrapper);
        if (attendance == null) {
            throw new ApplicationException(ResultCodeEnum.PUNCH_CLOCK_LOWER_ERROR);
        }

        attendance.setEnd(new Date());
        attendance.setAddressEnd(address);
        try {
            //计算当天工作时常
            double hours = DateUtil.differHours(attendance.getStart(), attendance.getEnd());
            attendance.setHours(hours);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
