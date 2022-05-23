package com.cdu.lys.graduation.repository.entity;

public class SeatDO {
    private Integer id;

    private String number;

    private Integer status;

    public SeatDO(Integer id, String number, Integer status) {
        this.id = id;
        this.number = number;
        this.status = status;
    }

    public SeatDO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", number=").append(number);
        sb.append(", status=").append(status);
        sb.append("]");
        return sb.toString();
    }
}