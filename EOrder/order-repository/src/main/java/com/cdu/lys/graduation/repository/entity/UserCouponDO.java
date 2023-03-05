package com.cdu.lys.graduation.repository.entity;

import java.util.Date;

public class UserCouponDO {
    private Integer id;

    private Integer userId;

    private Integer couponId;

    private String couponName;

    private Double reach;

    private Double reduce;

    private Date expireTime;

    private String isExpire;

    private Date createTime;

    private Date updateTime;

    private String isDelete;

    public UserCouponDO(Integer id, Integer userId, Integer couponId, String couponName, Double reach, Double reduce, Date expireTime, String isExpire, Date createTime, Date updateTime, String isDelete) {
        this.id = id;
        this.userId = userId;
        this.couponId = couponId;
        this.couponName = couponName;
        this.reach = reach;
        this.reduce = reduce;
        this.expireTime = expireTime;
        this.isExpire = isExpire;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
    }

    public UserCouponDO() {
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

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName == null ? null : couponName.trim();
    }

    public Double getReach() {
        return reach;
    }

    public void setReach(Double reach) {
        this.reach = reach;
    }

    public Double getReduce() {
        return reduce;
    }

    public void setReduce(Double reduce) {
        this.reduce = reduce;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getIsExpire() {
        return isExpire;
    }

    public void setIsExpire(String isExpire) {
        this.isExpire = isExpire == null ? null : isExpire.trim();
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

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", couponId=").append(couponId);
        sb.append(", couponName=").append(couponName);
        sb.append(", reach=").append(reach);
        sb.append(", reduce=").append(reduce);
        sb.append(", expireTime=").append(expireTime);
        sb.append(", isExpire=").append(isExpire);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDelete=").append(isDelete);
        sb.append("]");
        return sb.toString();
    }
}