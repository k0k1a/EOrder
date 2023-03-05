package com.cdu.lys.graduation.admin.login.service.impl;

import com.cdu.lys.graduation.admin.login.bo.AdminBO;
import com.cdu.lys.graduation.admin.login.service.AdminLoginService;
import com.cdu.lys.graduation.domain.user.dto.UserDTO;
import com.cdu.lys.graduation.repository.entity.UserDO;
import com.cdu.lys.graduation.types.user.LoginQuery;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liyinsong
 * @date 2022/3/16 18:38
 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    private AdminBO adminBO;

    @Override
    public UserDTO login(LoginQuery query) {
        UserDO userDO = adminBO.selectAdminUserByName(query.getUsername());

        //没有该管理员
        if (userDO == null) {
            return null;
        }
        String encryptedPassword = encryptPassword(query.getPassword(), userDO.getSalt());
        //密码错误
        if (!encryptedPassword.equals(userDO.getPassword())) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userDO, userDTO);

        return userDTO;
    }

    /**
     * 密码加密
     *
     * @param password 密码
     * @return 加密后密码
     */
    private String encryptPassword(String password, String salt) {
        String saltPassword = DigestUtils.md5Hex(salt + password);

        return saltPassword;
    }
}
