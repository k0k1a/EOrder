package com.cdu.lys.graduation.admin.user.bo.impl;

import com.cdu.lys.graduation.admin.user.bo.AdminUserBO;
import com.cdu.lys.graduation.repository.dao.UserDOMapper;
import com.cdu.lys.graduation.repository.entity.UserDO;
import com.cdu.lys.graduation.repository.entity.UserDOExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/21 20:37
 */
@Service
public class AdminUserBOImpl implements AdminUserBO {

    @Autowired
    private UserDOMapper userDOMapper;

    @Override
    public UserDO getById(Integer userId) {
        UserDOExample example = new UserDOExample();
        example.createCriteria()
                .andIdEqualTo(userId)
                .andIsDeleteEqualTo("n");

        List<UserDO> userDOS = userDOMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(userDOS)) {
            return null;
        }
        return userDOS.get(0);
    }

    @Override
    public UserDO getByUsername(String username) {
        UserDOExample example = new UserDOExample();
        example.createCriteria()
                .andUsernameEqualTo(username)
                .andIsDeleteEqualTo("n");

        List<UserDO> userDOS = userDOMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(userDOS)) {
            return null;
        }
        return userDOS.get(0);
    }

}
