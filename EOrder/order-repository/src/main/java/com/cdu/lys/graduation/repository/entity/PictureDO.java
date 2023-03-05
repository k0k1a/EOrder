package com.cdu.lys.graduation.repository.entity;

import java.util.Date;

public class PictureDO {
    private Integer id;

    private String picName;

    private String picUrl;

    private Integer type;

    private Date createTime;

    private Date updateTime;

    private String isDelete;

    private byte[] base64Data;

    public PictureDO(Integer id, String picName, String picUrl, Integer type, Date createTime, Date updateTime, String isDelete, byte[] base64Data) {
        this.id = id;
        this.picName = picName;
        this.picUrl = picUrl;
        this.type = type;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
        this.base64Data = base64Data;
    }

    public PictureDO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName == null ? null : picName.trim();
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public byte[] getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(byte[] base64Data) {
        this.base64Data = base64Data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", picName=").append(picName);
        sb.append(", picUrl=").append(picUrl);
        sb.append(", type=").append(type);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", base64Data=").append(base64Data);
        sb.append("]");
        return sb.toString();
    }
}