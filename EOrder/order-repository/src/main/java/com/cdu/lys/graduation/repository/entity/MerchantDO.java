package com.cdu.lys.graduation.repository.entity;

import java.util.Date;

public class MerchantDO {
    private Integer id;

    private String storeName;

    private String headerUrl;

    private String description;

    private String businessTime;

    private String province;

    private String city;

    private String district;

    private String address;

    private String phone;

    private String announcement;

    private Double score;

    private String isAutoReply;

    private String autoReplyContent;

    private Date createTime;

    private Date updateTime;

    private String isDelete;

    public MerchantDO(Integer id, String storeName, String headerUrl, String description, String businessTime, String province, String city, String district, String address, String phone, String announcement, Double score, String isAutoReply, String autoReplyContent, Date createTime, Date updateTime, String isDelete) {
        this.id = id;
        this.storeName = storeName;
        this.headerUrl = headerUrl;
        this.description = description;
        this.businessTime = businessTime;
        this.province = province;
        this.city = city;
        this.district = district;
        this.address = address;
        this.phone = phone;
        this.announcement = announcement;
        this.score = score;
        this.isAutoReply = isAutoReply;
        this.autoReplyContent = autoReplyContent;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
    }

    public MerchantDO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName == null ? null : storeName.trim();
    }

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl == null ? null : headerUrl.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(String businessTime) {
        this.businessTime = businessTime == null ? null : businessTime.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement == null ? null : announcement.trim();
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getIsAutoReply() {
        return isAutoReply;
    }

    public void setIsAutoReply(String isAutoReply) {
        this.isAutoReply = isAutoReply == null ? null : isAutoReply.trim();
    }

    public String getAutoReplyContent() {
        return autoReplyContent;
    }

    public void setAutoReplyContent(String autoReplyContent) {
        this.autoReplyContent = autoReplyContent == null ? null : autoReplyContent.trim();
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
        sb.append(", storeName=").append(storeName);
        sb.append(", headerUrl=").append(headerUrl);
        sb.append(", description=").append(description);
        sb.append(", businessTime=").append(businessTime);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", district=").append(district);
        sb.append(", address=").append(address);
        sb.append(", phone=").append(phone);
        sb.append(", announcement=").append(announcement);
        sb.append(", score=").append(score);
        sb.append(", isAutoReply=").append(isAutoReply);
        sb.append(", autoReplyContent=").append(autoReplyContent);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDelete=").append(isDelete);
        sb.append("]");
        return sb.toString();
    }
}