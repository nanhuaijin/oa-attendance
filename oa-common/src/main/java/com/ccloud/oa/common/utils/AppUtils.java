package com.ccloud.oa.common.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : breeze
 * @date : 2020/9/1
 * @description : 工具类
 */
public class AppUtils {

    /**
     * 验证手机格式的方法
     * @param phone
     * @return
     */
    public static boolean isMobilePhone(String phone) {
        boolean flag;
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            flag = false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            flag = m.matches();
        }

        return flag;
    }


    /**
     * 校验EMAIL格式，真为正确
     * @param email
     * @return true 为格式正确 false 为格式错误
     */
    public static boolean isEmail(String email) {
        boolean falg = true;
        String emailRegex = "^[a-zA-Z0-9_-]+@([a-zA-Z0-9]+\\.)+(com|cn|net|org)$";
        if (!email.matches(emailRegex)) {
            falg = false;
        }
        return falg;
    }

    /**
     * 随机生成i位数字字符串
     * @param i
     * @return
     */
    public static String generateRandomInt(int i) {
        int[] intArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        StringBuilder code = new StringBuilder();
        for (int j = 0; j < i; j++) {
            int index = new Random().nextInt(10);
            code.append(intArr[index]);
        }
        return code.toString();
    }
}
