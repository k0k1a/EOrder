package com.cdu.lys.graduation.mini.user.bo;

import com.cdu.lys.graduation.repository.entity.UserDO;
import com.cdu.lys.graduation.types.user.UserQuery;

/**
 * @author liyinsong
 * @date 2022/3/30 21:03
 */
public interface UserBO {

    UserDO getUserById(Integer userId);

    UserDO getUserByUsername(String username);

    int updateUser(UserQuery query, Integer userId);

}
