package com.cdu.lys.graduation.mini.user.bo.impl;

import com.cdu.lys.graduation.mini.user.bo.UserBO;
import com.cdu.lys.graduation.repository.dao.UserDOMapper;
import com.cdu.lys.graduation.repository.entity.UserDO;
import com.cdu.lys.graduation.repository.entity.UserDOExample;
import com.cdu.lys.graduation.types.user.UserQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/30 21:04
 */
@Service
public class UserBOImpl implements UserBO {

    @Autowired
    private UserDOMapper userDOMapper;

    @Override
    public UserDO getUserById(Integer userId) {
        return userDOMapper.selectByPrimaryKey(userId);
    }

    @Override
    public UserDO getUserByUsername(String username) {
        UserDOExample example = new UserDOExample();
        example.createCriteria()
                .andUsernameEqualTo(username);
        List<UserDO> userDOS = userDOMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(userDOS)) {
            return null;
        }
        return userDOS.get(0);
    }

    @Override
    public int updateUser(UserQuery query, Integer userId) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(query, userDO);
        userDO.setId(userId);

        return userDOMapper.updateByPrimaryKeySelective(userDO);
    }
}
