package com.ccloud.oa.common.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author : breeze
 * @date : 2020/8/19
 * @description : 角色权限枚举类
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum  RoleAuthEnum {

    SUPER_ADMIN("admin", "超级管理员", "SUPER_ADMIN"),
    STAFF("staff", "员工","STAFF");

    private  String code;

    private String role;

    private String auth;
}
