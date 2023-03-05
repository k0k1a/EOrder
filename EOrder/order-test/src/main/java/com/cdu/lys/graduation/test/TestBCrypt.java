package com.cdu.lys.graduation.test;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author liyinsong
 * @date 2022/4/5 13:56
 */
public class TestBCrypt {

    public static void main(String[] args) {

        String password="Aa111111";
        for (int i = 0; i < 5; i++) {
            String salt = BCrypt.gensalt();
            String encode = BCrypt.hashpw(password, salt);
            System.out.println(encode);
        }

        boolean checkpw = BCrypt.checkpw("123456", "$2a$10$GHJd.Gdy6NqhbbPI5q3Bse1r9QcWBChLvcK6cVGdDbhPAcvbU8K26");
        System.out.println(checkpw);

    }
}
