package com.cdu.lys.graduation.test;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author liyinsong
 * @date 2022/3/17 11:09
 */
public class GeneratePassword {

    public static void generatePassword(){
        String randomSalt = RandomStringUtils.randomAlphabetic(20);

        String username = RandomStringUtils.randomNumeric(11);
        String password = RandomStringUtils.randomAlphanumeric(16);
        String encryptedPassword = DigestUtils.md5Hex(randomSalt + password);

        System.out.println("username:" + username);
        System.out.println("password:" + password);
        System.out.println("encryptedPassword:" + encryptedPassword);
        System.out.println("salt:" + randomSalt);
    }

    public static void main(String[] args) {
        generatePassword();
    }
}
