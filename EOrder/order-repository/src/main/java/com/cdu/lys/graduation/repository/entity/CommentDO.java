package com.cdu.lys.graduation.repository.entity;

import java.util.Date;

public class CommentDO {
    private Integer id;

    private Integer userId;

    private String orderNum;

    private Integer serviceScore;

    private Integer environmentScore;

    private Integer tasteScore;

    private String content;

    private String reply;

    private String isReply;

    private Date replyTime;

    private Date createTime;

    private Date updateTime;

    private String isDelete;

    public CommentDO(Integer id, Integer userId, String orderNum, Integer serviceScore, Integer environmentScore, Integer tasteScore, String content, String reply, String isReply, Date replyTime, Date createTime, Date updateTime, String isDelete) {
        this.id = id;
        this.userId = userId;
        this.orderNum = orderNum;
        this.serviceScore = serviceScore;
        this.environmentScore = environmentScore;
        this.tasteScore = tasteScore;
        this.content = content;
        this.reply = reply;
        this.isReply = isReply;
        this.replyTime = replyTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
    }

    public CommentDO() {
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

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum == null ? null : orderNum.trim();
    }

    public Integer getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(Integer serviceScore) {
        this.serviceScore = serviceScore;
    }

    public Integer getEnvironmentScore() {
        return environmentScore;
    }

    public void setEnvironmentScore(Integer environmentScore) {
        this.environmentScore = environmentScore;
    }

    public Integer getTasteScore() {
        return tasteScore;
    }

    public void setTasteScore(Integer tasteScore) {
        this.tasteScore = tasteScore;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply == null ? null : reply.trim();
    }

    public String getIsReply() {
        return isReply;
    }

    public void setIsReply(String isReply) {
        this.isReply = isReply == null ? null : isReply.trim();
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
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
        sb.append(", orderNum=").append(orderNum);
        sb.append(", serviceScore=").append(serviceScore);
        sb.append(", environmentScore=").append(environmentScore);
        sb.append(", tasteScore=").append(tasteScore);
        sb.append(", content=").append(content);
        sb.append(", reply=").append(reply);
        sb.append(", isReply=").append(isReply);
        sb.append(", replyTime=").append(replyTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDelete=").append(isDelete);
        sb.append("]");
        return sb.toString();
    }
}