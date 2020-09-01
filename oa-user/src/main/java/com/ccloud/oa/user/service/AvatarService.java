package com.ccloud.oa.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccloud.oa.user.entity.Avatar;

/**
 * @author : breeze
 * @date : 2020/9/1
 * @description : 头像Service
 */
public interface AvatarService extends IService<Avatar> {

    Avatar getAvatarByAccount(String account);
}
