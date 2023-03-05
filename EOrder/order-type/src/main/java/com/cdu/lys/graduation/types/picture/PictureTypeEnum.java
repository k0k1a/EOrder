package com.cdu.lys.graduation.types.picture;

/**
 * @author liyinsong
 * @date 2022/3/20 15:01
 */
public enum PictureTypeEnum {

    USER_PIC(1,"用户图片"),
    GOODS_PIC(2,"商品图片"),
    OTHER_PIC(3,"其他图片");


    private Integer code;
    private String msg;

    PictureTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
