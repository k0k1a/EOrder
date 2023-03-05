package com.cdu.lys.graduation.repository.entity;

public class CartDO {
    private Integer id;

    private Integer userId;

    public CartDO(Integer id, Integer userId) {
        this.id = id;
        this.userId = userId;
    }

    public CartDO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append("]");
        return sb.toString();
    }
}