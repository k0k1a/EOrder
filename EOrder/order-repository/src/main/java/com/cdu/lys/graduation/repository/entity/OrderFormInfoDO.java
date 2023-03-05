package com.cdu.lys.graduation.repository.entity;

import java.util.Date;

public class OrderFormInfoDO {
    private Integer id;

    private Integer orderId;

    private String orderNum;

    private Integer goodsId;

    private String goodsPic;

    private String goodsName;

    private Double goodsPrice;

    private Integer goodsQuantity;

    private Double extraPrice;

    private String extraOptions;

    private Integer deliveryStatus;

    private Date createTime;

    private Date updateTime;

    public OrderFormInfoDO(Integer id, Integer orderId, String orderNum, Integer goodsId, String goodsPic, String goodsName, Double goodsPrice, Integer goodsQuantity, Double extraPrice, String extraOptions, Integer deliveryStatus, Date createTime, Date updateTime) {
        this.id = id;
        this.orderId = orderId;
        this.orderNum = orderNum;
        this.goodsId = goodsId;
        this.goodsPic = goodsPic;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsQuantity = goodsQuantity;
        this.extraPrice = extraPrice;
        this.extraOptions = extraOptions;
        this.deliveryStatus = deliveryStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public OrderFormInfoDO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum == null ? null : orderNum.trim();
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic == null ? null : goodsPic.trim();
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsQuantity() {
        return goodsQuantity;
    }

    public void setGoodsQuantity(Integer goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }

    public Double getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(Double extraPrice) {
        this.extraPrice = extraPrice;
    }

    public String getExtraOptions() {
        return extraOptions;
    }

    public void setExtraOptions(String extraOptions) {
        this.extraOptions = extraOptions == null ? null : extraOptions.trim();
    }

    public Integer getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderId=").append(orderId);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", goodsPic=").append(goodsPic);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", goodsPrice=").append(goodsPrice);
        sb.append(", goodsQuantity=").append(goodsQuantity);
        sb.append(", extraPrice=").append(extraPrice);
        sb.append(", extraOptions=").append(extraOptions);
        sb.append(", deliveryStatus=").append(deliveryStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}