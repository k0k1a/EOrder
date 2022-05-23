package com.cdu.lys.graduation.admin.user.bo;

import com.cdu.lys.graduation.repository.entity.UserDO;

/**
 * @author liyinsong
 * @date 2022/3/21 20:37
 */
public interface AdminUserBO {

    UserDO getById(Integer userId);

    UserDO getByUsername(String username);
}
