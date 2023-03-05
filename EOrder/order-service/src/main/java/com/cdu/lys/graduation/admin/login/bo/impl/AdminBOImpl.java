package com.cdu.lys.graduation.admin.login.bo.impl;

import com.cdu.lys.graduation.admin.login.bo.AdminBO;
import com.cdu.lys.graduation.repository.dao.UserDOMapper;
import com.cdu.lys.graduation.repository.entity.UserDO;
import com.cdu.lys.graduation.repository.entity.UserDOExample;
import com.cdu.lys.graduation.types.user.UserTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/16 18:58
 */
@Service
public class AdminBOImpl implements AdminBO {

    @Autowired
    private UserDOMapper userDOMapper;

    @Override
    public UserDO selectAdminUserByName(String username) {
        UserDOExample example = new UserDOExample();
        example.createCriteria()
                .andUsernameEqualTo(username)
                .andTypeEqualTo(UserTypeEnum.ADMIN_USER.getCode())
                .andIsDeleteEqualTo("n");

        List<UserDO> userDOList = userDOMapper.selectByExample(example);

        return CollectionUtils.isEmpty(userDOList) ? null : userDOList.get(0);
    }

}
