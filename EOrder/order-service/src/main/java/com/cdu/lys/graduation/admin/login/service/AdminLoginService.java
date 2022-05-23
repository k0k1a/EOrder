package com.cdu.lys.graduation.admin.login.service;

import com.cdu.lys.graduation.domain.user.dto.UserDTO;
import com.cdu.lys.graduation.types.user.LoginQuery;

/**
 * @author liyinsong
 * @date 2022/3/16 18:37
 */
public interface AdminLoginService {

    /**
     * 管理系统登录
     * @param query
     * @return 登录结果
     */
    UserDTO login(LoginQuery query);
}
