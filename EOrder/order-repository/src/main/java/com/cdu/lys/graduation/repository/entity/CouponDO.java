package com.cdu.lys.graduation.repository.entity;

import java.util.Date;

public class CouponDO {
    private Integer id;

    private String couponName;

    private Double reach;

    private Double reduce;

    private Integer userType;

    private Integer effectiveTime;

    private Date deadline;

    private Integer number;

    private Date createTime;

    private Date updateTime;

    private String isDelete;

    public CouponDO(Integer id, String couponName, Double reach, Double reduce, Integer userType, Integer effectiveTime, Date deadline, Integer number, Date createTime, Date updateTime, String isDelete) {
        this.id = id;
        this.couponName = couponName;
        this.reach = reach;
        this.reduce = reduce;
        this.userType = userType;
        this.effectiveTime = effectiveTime;
        this.deadline = deadline;
        this.number = number;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
    }

    public CouponDO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Integer effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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
        sb.append(", couponName=").append(couponName);
        sb.append(", reach=").append(reach);
        sb.append(", reduce=").append(reduce);
        sb.append(", userType=").append(userType);
        sb.append(", effectiveTime=").append(effectiveTime);
        sb.append(", deadline=").append(deadline);
        sb.append(", number=").append(number);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDelete=").append(isDelete);
        sb.append("]");
        return sb.toString();
    }
}