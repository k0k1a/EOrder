package com.cdu.lys.graduation.repository.entity;

import java.util.Date;

public class CartGoodsDO {
    private Integer id;

    private Integer goodsId;

    private Integer cartId;

    private Date addTime;

    public CartGoodsDO(Integer id, Integer goodsId, Integer cartId, Date addTime) {
        this.id = id;
        this.goodsId = goodsId;
        this.cartId = cartId;
        this.addTime = addTime;
    }

    public CartGoodsDO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", cartId=").append(cartId);
        sb.append(", addTime=").append(addTime);
        sb.append("]");
        return sb.toString();
    }
}