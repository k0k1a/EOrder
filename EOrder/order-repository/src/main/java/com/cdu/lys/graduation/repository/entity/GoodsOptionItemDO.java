package com.cdu.lys.graduation.repository.entity;

import java.util.Date;

public class GoodsOptionItemDO {
    private Integer id;

    private String optionItem;

    private Integer optionId;

    private Double extraPrice;

    private String isDelete;

    private Date createTime;

    private Date updateTime;

    public GoodsOptionItemDO(Integer id, String optionItem, Integer optionId, Double extraPrice, String isDelete, Date createTime, Date updateTime) {
        this.id = id;
        this.optionItem = optionItem;
        this.optionId = optionId;
        this.extraPrice = extraPrice;
        this.isDelete = isDelete;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public GoodsOptionItemDO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOptionItem() {
        return optionItem;
    }

    public void setOptionItem(String optionItem) {
        this.optionItem = optionItem == null ? null : optionItem.trim();
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public Double getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(Double extraPrice) {
        this.extraPrice = extraPrice;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
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
        sb.append(", optionItem=").append(optionItem);
        sb.append(", optionId=").append(optionId);
        sb.append(", extraPrice=").append(extraPrice);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}