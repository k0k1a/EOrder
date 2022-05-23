package com.cdu.lys.graduation.repository.entity;

import java.util.Date;

public class OrderFormDO {
    private Integer id;

    private Integer userId;

    private Integer userCouponId;

    private String orderNum;

    private Integer tradeStatus;

    private Integer payStatus;

    private Date createTime;

    private Date expireTime;

    private Date closeTime;

    private String remark;

    private Double orderAmount;

    private Double payAmount;

    private Double couponAmount;

    private Date payTime;

    private String outerTradeNo;

    private String queueNum;

    private Integer deliveryStatus;

    private String isComment;

    private Date commentTime;

    public OrderFormDO(Integer id, Integer userId, Integer userCouponId, String orderNum, Integer tradeStatus, Integer payStatus, Date createTime, Date expireTime, Date closeTime, String remark, Double orderAmount, Double payAmount, Double couponAmount, Date payTime, String outerTradeNo, String queueNum, Integer deliveryStatus, String isComment, Date commentTime) {
        this.id = id;
        this.userId = userId;
        this.userCouponId = userCouponId;
        this.orderNum = orderNum;
        this.tradeStatus = tradeStatus;
        this.payStatus = payStatus;
        this.createTime = createTime;
        this.expireTime = expireTime;
        this.closeTime = closeTime;
        this.remark = remark;
        this.orderAmount = orderAmount;
        this.payAmount = payAmount;
        this.couponAmount = couponAmount;
        this.payTime = payTime;
        this.outerTradeNo = outerTradeNo;
        this.queueNum = queueNum;
        this.deliveryStatus = deliveryStatus;
        this.isComment = isComment;
        this.commentTime = commentTime;
    }

    public OrderFormDO() {
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

    public Integer getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(Integer userCouponId) {
        this.userCouponId = userCouponId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum == null ? null : orderNum.trim();
    }

    public Integer getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public Double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getOuterTradeNo() {
        return outerTradeNo;
    }

    public void setOuterTradeNo(String outerTradeNo) {
        this.outerTradeNo = outerTradeNo == null ? null : outerTradeNo.trim();
    }

    public String getQueueNum() {
        return queueNum;
    }

    public void setQueueNum(String queueNum) {
        this.queueNum = queueNum == null ? null : queueNum.trim();
    }

    public Integer getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getIsComment() {
        return isComment;
    }

    public void setIsComment(String isComment) {
        this.isComment = isComment == null ? null : isComment.trim();
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", userCouponId=").append(userCouponId);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", tradeStatus=").append(tradeStatus);
        sb.append(", payStatus=").append(payStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", expireTime=").append(expireTime);
        sb.append(", closeTime=").append(closeTime);
        sb.append(", remark=").append(remark);
        sb.append(", orderAmount=").append(orderAmount);
        sb.append(", payAmount=").append(payAmount);
        sb.append(", couponAmount=").append(couponAmount);
        sb.append(", payTime=").append(payTime);
        sb.append(", outerTradeNo=").append(outerTradeNo);
        sb.append(", queueNum=").append(queueNum);
        sb.append(", deliveryStatus=").append(deliveryStatus);
        sb.append(", isComment=").append(isComment);
        sb.append(", commentTime=").append(commentTime);
        sb.append("]");
        return sb.toString();
    }
}