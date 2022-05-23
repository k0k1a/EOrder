package com.cdu.lys.graduation.repository.entity;

import java.util.Date;

public class GoodsDO {
    private Integer id;

    private String goodsName;

    private String description;

    private String ingredients;

    private String weight;

    private String taste;

    private String isMeat;

    private Double price;

    private Integer stock;

    private Integer sales;

    private Double score;

    private Double cost;

    private Integer goodsType;

    private Date createTime;

    private Date updateTime;

    private String isDelete;

    public GoodsDO(Integer id, String goodsName, String description, String ingredients, String weight, String taste, String isMeat, Double price, Integer stock, Integer sales, Double score, Double cost, Integer goodsType, Date createTime, Date updateTime, String isDelete) {
        this.id = id;
        this.goodsName = goodsName;
        this.description = description;
        this.ingredients = ingredients;
        this.weight = weight;
        this.taste = taste;
        this.isMeat = isMeat;
        this.price = price;
        this.stock = stock;
        this.sales = sales;
        this.score = score;
        this.cost = cost;
        this.goodsType = goodsType;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
    }

    public GoodsDO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients == null ? null : ingredients.trim();
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste == null ? null : taste.trim();
    }

    public String getIsMeat() {
        return isMeat;
    }

    public void setIsMeat(String isMeat) {
        this.isMeat = isMeat == null ? null : isMeat.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
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
        sb.append(", goodsName=").append(goodsName);
        sb.append(", description=").append(description);
        sb.append(", ingredients=").append(ingredients);
        sb.append(", weight=").append(weight);
        sb.append(", taste=").append(taste);
        sb.append(", isMeat=").append(isMeat);
        sb.append(", price=").append(price);
        sb.append(", stock=").append(stock);
        sb.append(", sales=").append(sales);
        sb.append(", score=").append(score);
        sb.append(", cost=").append(cost);
        sb.append(", goodsType=").append(goodsType);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDelete=").append(isDelete);
        sb.append("]");
        return sb.toString();
    }
}