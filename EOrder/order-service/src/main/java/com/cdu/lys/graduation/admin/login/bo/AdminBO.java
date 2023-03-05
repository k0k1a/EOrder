package com.cdu.lys.graduation.admin.login.bo;

import com.cdu.lys.graduation.repository.entity.UserDO;

/**
 * @author liyinsong
 * @date 2022/3/16 18:58
 */
public interface AdminBO {

    /**
     * 查询管理员用户
     * @param username 用户名
     * @return 管理员用户
     */
    UserDO selectAdminUserByName(String username);
}
