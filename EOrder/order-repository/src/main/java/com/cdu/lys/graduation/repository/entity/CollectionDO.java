package com.cdu.lys.graduation.repository.entity;

public class CollectionDO {
    private Integer id;

    private Integer userId;

    private Integer goodsId;

    public CollectionDO(Integer id, Integer userId, Integer goodsId) {
        this.id = id;
        this.userId = userId;
        this.goodsId = goodsId;
    }

    public CollectionDO() {
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

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", goodsId=").append(goodsId);
        sb.append("]");
        return sb.toString();
    }
}