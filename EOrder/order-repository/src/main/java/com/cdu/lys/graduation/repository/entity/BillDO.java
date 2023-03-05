package com.cdu.lys.graduation.repository.entity;

import java.util.Date;

public class BillDO {
    private Integer id;

    private Double totalOrderAmount;

    private Double totalActualAmount;

    private Double totalCost;

    private Double netIncome;

    private Integer totalNumber;

    private Integer cancelNumber;

    private Date billDate;

    public BillDO(Integer id, Double totalOrderAmount, Double totalActualAmount, Double totalCost, Double netIncome, Integer totalNumber, Integer cancelNumber, Date billDate) {
        this.id = id;
        this.totalOrderAmount = totalOrderAmount;
        this.totalActualAmount = totalActualAmount;
        this.totalCost = totalCost;
        this.netIncome = netIncome;
        this.totalNumber = totalNumber;
        this.cancelNumber = cancelNumber;
        this.billDate = billDate;
    }

    public BillDO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(Double totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    public Double getTotalActualAmount() {
        return totalActualAmount;
    }

    public void setTotalActualAmount(Double totalActualAmount) {
        this.totalActualAmount = totalActualAmount;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Double getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(Double netIncome) {
        this.netIncome = netIncome;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getCancelNumber() {
        return cancelNumber;
    }

    public void setCancelNumber(Integer cancelNumber) {
        this.cancelNumber = cancelNumber;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", totalOrderAmount=").append(totalOrderAmount);
        sb.append(", totalActualAmount=").append(totalActualAmount);
        sb.append(", totalCost=").append(totalCost);
        sb.append(", netIncome=").append(netIncome);
        sb.append(", totalNumber=").append(totalNumber);
        sb.append(", cancelNumber=").append(cancelNumber);
        sb.append(", billDate=").append(billDate);
        sb.append("]");
        return sb.toString();
    }
}