package com.cdu.lys.graduation.repository.entity;

import java.util.Date;

public class SystemRecordDO {
    private Integer id;

    private Integer visitNum;

    private Integer sales;

    private Double dailyIncome;

    private Integer dailyCommentNum;

    private Date recordDate;

    public SystemRecordDO(Integer id, Integer visitNum, Integer sales, Double dailyIncome, Integer dailyCommentNum, Date recordDate) {
        this.id = id;
        this.visitNum = visitNum;
        this.sales = sales;
        this.dailyIncome = dailyIncome;
        this.dailyCommentNum = dailyCommentNum;
        this.recordDate = recordDate;
    }

    public SystemRecordDO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVisitNum() {
        return visitNum;
    }

    public void setVisitNum(Integer visitNum) {
        this.visitNum = visitNum;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Double getDailyIncome() {
        return dailyIncome;
    }

    public void setDailyIncome(Double dailyIncome) {
        this.dailyIncome = dailyIncome;
    }

    public Integer getDailyCommentNum() {
        return dailyCommentNum;
    }

    public void setDailyCommentNum(Integer dailyCommentNum) {
        this.dailyCommentNum = dailyCommentNum;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", visitNum=").append(visitNum);
        sb.append(", sales=").append(sales);
        sb.append(", dailyIncome=").append(dailyIncome);
        sb.append(", dailyCommentNum=").append(dailyCommentNum);
        sb.append(", recordDate=").append(recordDate);
        sb.append("]");
        return sb.toString();
    }
}