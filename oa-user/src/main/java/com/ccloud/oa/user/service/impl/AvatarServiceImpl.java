package com.ccloud.oa.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccloud.oa.user.entity.Avatar;
import com.ccloud.oa.user.mapper.AvatarMapper;
import com.ccloud.oa.user.service.AvatarService;
import org.springframework.stereotype.Service;

/**
 * @author : breeze
 * @date : 2020/9/1
 * @description :
 */
@Service
public class AvatarServiceImpl extends ServiceImpl<AvatarMapper, Avatar> implements AvatarService {

    /**
     * 根据用户名获取头像地址
     * @param account
     * @return
     */
    @Override
    public Avatar getAvatarByAccount(String account) {
        QueryWrapper<Avatar> wrapper = new QueryWrapper<>();
        wrapper.eq("account", account);
        return this.baseMapper.selectOne(wrapper);
    }
}
