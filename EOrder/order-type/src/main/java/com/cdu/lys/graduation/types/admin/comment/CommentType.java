package com.cdu.lys.graduation.types.admin.comment;

/**
 * @author liyinsong
 * @date 2022/4/20 20:40
 */
public enum CommentType {

    /**
     * 好评
     */
    GOOD_COMMENT(1),
    /**
     * 差评
     */
    BAD_COMMENT(0);

    private final Integer type;

    CommentType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    /**
     * 判断是否是评论类型
     * @param type
     * @return
     */
    public static boolean isCommentType(Integer type) {
        for (CommentType value : CommentType.values()) {
            if (value.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
