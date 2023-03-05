package com.cdu.lys.graduation.repository.dao.ext;

import com.cdu.lys.graduation.repository.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserDOMapper扩展类
 *
 * @author liyinsong
 * @date 2022/1/8 16:04
 */
@Mapper
public interface UserDOMapperExt {

    UserDO selectByUsername(String username);

    UserDO selectByUsernameAndPassword(String username,String password);

    UserDO selectByOpenId(String openId);
}
