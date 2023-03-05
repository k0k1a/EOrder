package com.cdu.lys.graduation.repository.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillDOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BillDOExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTotalOrderAmountIsNull() {
            addCriterion("total_order_amount is null");
            return (Criteria) this;
        }

        public Criteria andTotalOrderAmountIsNotNull() {
            addCriterion("total_order_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTotalOrderAmountEqualTo(Double value) {
            addCriterion("total_order_amount =", value, "totalOrderAmount");
            return (Criteria) this;
        }

        public Criteria andTotalOrderAmountNotEqualTo(Double value) {
            addCriterion("total_order_amount <>", value, "totalOrderAmount");
            return (Criteria) this;
        }

        public Criteria andTotalOrderAmountGreaterThan(Double value) {
            addCriterion("total_order_amount >", value, "totalOrderAmount");
            return (Criteria) this;
        }

        public Criteria andTotalOrderAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("total_order_amount >=", value, "totalOrderAmount");
            return (Criteria) this;
        }

        public Criteria andTotalOrderAmountLessThan(Double value) {
            addCriterion("total_order_amount <", value, "totalOrderAmount");
            return (Criteria) this;
        }

        public Criteria andTotalOrderAmountLessThanOrEqualTo(Double value) {
            addCriterion("total_order_amount <=", value, "totalOrderAmount");
            return (Criteria) this;
        }

        public Criteria andTotalOrderAmountIn(List<Double> values) {
            addCriterion("total_order_amount in", values, "totalOrderAmount");
            return (Criteria) this;
        }

        public Criteria andTotalOrderAmountNotIn(List<Double> values) {
            addCriterion("total_order_amount not in", values, "totalOrderAmount");
            return (Criteria) this;
        }

        public Criteria andTotalOrderAmountBetween(Double value1, Double value2) {
            addCriterion("total_order_amount between", value1, value2, "totalOrderAmount");
            return (Criteria) this;
        }

        public Criteria andTotalOrderAmountNotBetween(Double value1, Double value2) {
            addCriterion("total_order_amount not between", value1, value2, "totalOrderAmount");
            return (Criteria) this;
        }

        public Criteria andTotalActualAmountIsNull() {
            addCriterion("total_actual_amount is null");
            return (Criteria) this;
        }

        public Criteria andTotalActualAmountIsNotNull() {
            addCriterion("total_actual_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTotalActualAmountEqualTo(Double value) {
            addCriterion("total_actual_amount =", value, "totalActualAmount");
            return (Criteria) this;
        }

        public Criteria andTotalActualAmountNotEqualTo(Double value) {
            addCriterion("total_actual_amount <>", value, "totalActualAmount");
            return (Criteria) this;
        }

        public Criteria andTotalActualAmountGreaterThan(Double value) {
            addCriterion("total_actual_amount >", value, "totalActualAmount");
            return (Criteria) this;
        }

        public Criteria andTotalActualAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("total_actual_amount >=", value, "totalActualAmount");
            return (Criteria) this;
        }

        public Criteria andTotalActualAmountLessThan(Double value) {
            addCriterion("total_actual_amount <", value, "totalActualAmount");
            return (Criteria) this;
        }

        public Criteria andTotalActualAmountLessThanOrEqualTo(Double value) {
            addCriterion("total_actual_amount <=", value, "totalActualAmount");
            return (Criteria) this;
        }

        public Criteria andTotalActualAmountIn(List<Double> values) {
            addCriterion("total_actual_amount in", values, "totalActualAmount");
            return (Criteria) this;
        }

        public Criteria andTotalActualAmountNotIn(List<Double> values) {
            addCriterion("total_actual_amount not in", values, "totalActualAmount");
            return (Criteria) this;
        }

        public Criteria andTotalActualAmountBetween(Double value1, Double value2) {
            addCriterion("total_actual_amount between", value1, value2, "totalActualAmount");
            return (Criteria) this;
        }

        public Criteria andTotalActualAmountNotBetween(Double value1, Double value2) {
            addCriterion("total_actual_amount not between", value1, value2, "totalActualAmount");
            return (Criteria) this;
        }

        public Criteria andTotalCostIsNull() {
            addCriterion("total_cost is null");
            return (Criteria) this;
        }

        public Criteria andTotalCostIsNotNull() {
            addCriterion("total_cost is not null");
            return (Criteria) this;
        }

        public Criteria andTotalCostEqualTo(Double value) {
            addCriterion("total_cost =", value, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostNotEqualTo(Double value) {
            addCriterion("total_cost <>", value, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostGreaterThan(Double value) {
            addCriterion("total_cost >", value, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostGreaterThanOrEqualTo(Double value) {
            addCriterion("total_cost >=", value, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostLessThan(Double value) {
            addCriterion("total_cost <", value, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostLessThanOrEqualTo(Double value) {
            addCriterion("total_cost <=", value, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostIn(List<Double> values) {
            addCriterion("total_cost in", values, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostNotIn(List<Double> values) {
            addCriterion("total_cost not in", values, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostBetween(Double value1, Double value2) {
            addCriterion("total_cost between", value1, value2, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostNotBetween(Double value1, Double value2) {
            addCriterion("total_cost not between", value1, value2, "totalCost");
            return (Criteria) this;
        }

        public Criteria andNetIncomeIsNull() {
            addCriterion("net_income is null");
            return (Criteria) this;
        }

        public Criteria andNetIncomeIsNotNull() {
            addCriterion("net_income is not null");
            return (Criteria) this;
        }

        public Criteria andNetIncomeEqualTo(Double value) {
            addCriterion("net_income =", value, "netIncome");
            return (Criteria) this;
        }

        public Criteria andNetIncomeNotEqualTo(Double value) {
            addCriterion("net_income <>", value, "netIncome");
            return (Criteria) this;
        }

        public Criteria andNetIncomeGreaterThan(Double value) {
            addCriterion("net_income >", value, "netIncome");
            return (Criteria) this;
        }

        public Criteria andNetIncomeGreaterThanOrEqualTo(Double value) {
            addCriterion("net_income >=", value, "netIncome");
            return (Criteria) this;
        }

        public Criteria andNetIncomeLessThan(Double value) {
            addCriterion("net_income <", value, "netIncome");
            return (Criteria) this;
        }

        public Criteria andNetIncomeLessThanOrEqualTo(Double value) {
            addCriterion("net_income <=", value, "netIncome");
            return (Criteria) this;
        }

        public Criteria andNetIncomeIn(List<Double> values) {
            addCriterion("net_income in", values, "netIncome");
            return (Criteria) this;
        }

        public Criteria andNetIncomeNotIn(List<Double> values) {
            addCriterion("net_income not in", values, "netIncome");
            return (Criteria) this;
        }

        public Criteria andNetIncomeBetween(Double value1, Double value2) {
            addCriterion("net_income between", value1, value2, "netIncome");
            return (Criteria) this;
        }

        public Criteria andNetIncomeNotBetween(Double value1, Double value2) {
            addCriterion("net_income not between", value1, value2, "netIncome");
            return (Criteria) this;
        }

        public Criteria andTotalNumberIsNull() {
            addCriterion("total_number is null");
            return (Criteria) this;
        }

        public Criteria andTotalNumberIsNotNull() {
            addCriterion("total_number is not null");
            return (Criteria) this;
        }

        public Criteria andTotalNumberEqualTo(Integer value) {
            addCriterion("total_number =", value, "totalNumber");
            return (Criteria) this;
        }

        public Criteria andTotalNumberNotEqualTo(Integer value) {
            addCriterion("total_number <>", value, "totalNumber");
            return (Criteria) this;
        }

        public Criteria andTotalNumberGreaterThan(Integer value) {
            addCriterion("total_number >", value, "totalNumber");
            return (Criteria) this;
        }

        public Criteria andTotalNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_number >=", value, "totalNumber");
            return (Criteria) this;
        }

        public Criteria andTotalNumberLessThan(Integer value) {
            addCriterion("total_number <", value, "totalNumber");
            return (Criteria) this;
        }

        public Criteria andTotalNumberLessThanOrEqualTo(Integer value) {
            addCriterion("total_number <=", value, "totalNumber");
            return (Criteria) this;
        }

        public Criteria andTotalNumberIn(List<Integer> values) {
            addCriterion("total_number in", values, "totalNumber");
            return (Criteria) this;
        }

        public Criteria andTotalNumberNotIn(List<Integer> values) {
            addCriterion("total_number not in", values, "totalNumber");
            return (Criteria) this;
        }

        public Criteria andTotalNumberBetween(Integer value1, Integer value2) {
            addCriterion("total_number between", value1, value2, "totalNumber");
            return (Criteria) this;
        }

        public Criteria andTotalNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("total_number not between", value1, value2, "totalNumber");
            return (Criteria) this;
        }

        public Criteria andCancelNumberIsNull() {
            addCriterion("cancel_number is null");
            return (Criteria) this;
        }

        public Criteria andCancelNumberIsNotNull() {
            addCriterion("cancel_number is not null");
            return (Criteria) this;
        }

        public Criteria andCancelNumberEqualTo(Integer value) {
            addCriterion("cancel_number =", value, "cancelNumber");
            return (Criteria) this;
        }

        public Criteria andCancelNumberNotEqualTo(Integer value) {
            addCriterion("cancel_number <>", value, "cancelNumber");
            return (Criteria) this;
        }

        public Criteria andCancelNumberGreaterThan(Integer value) {
            addCriterion("cancel_number >", value, "cancelNumber");
            return (Criteria) this;
        }

        public Criteria andCancelNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("cancel_number >=", value, "cancelNumber");
            return (Criteria) this;
        }

        public Criteria andCancelNumberLessThan(Integer value) {
            addCriterion("cancel_number <", value, "cancelNumber");
            return (Criteria) this;
        }

        public Criteria andCancelNumberLessThanOrEqualTo(Integer value) {
            addCriterion("cancel_number <=", value, "cancelNumber");
            return (Criteria) this;
        }

        public Criteria andCancelNumberIn(List<Integer> values) {
            addCriterion("cancel_number in", values, "cancelNumber");
            return (Criteria) this;
        }

        public Criteria andCancelNumberNotIn(List<Integer> values) {
            addCriterion("cancel_number not in", values, "cancelNumber");
            return (Criteria) this;
        }

        public Criteria andCancelNumberBetween(Integer value1, Integer value2) {
            addCriterion("cancel_number between", value1, value2, "cancelNumber");
            return (Criteria) this;
        }

        public Criteria andCancelNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("cancel_number not between", value1, value2, "cancelNumber");
            return (Criteria) this;
        }

        public Criteria andBillDateIsNull() {
            addCriterion("bill_date is null");
            return (Criteria) this;
        }

        public Criteria andBillDateIsNotNull() {
            addCriterion("bill_date is not null");
            return (Criteria) this;
        }

        public Criteria andBillDateEqualTo(Date value) {
            addCriterion("bill_date =", value, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateNotEqualTo(Date value) {
            addCriterion("bill_date <>", value, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateGreaterThan(Date value) {
            addCriterion("bill_date >", value, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateGreaterThanOrEqualTo(Date value) {
            addCriterion("bill_date >=", value, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateLessThan(Date value) {
            addCriterion("bill_date <", value, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateLessThanOrEqualTo(Date value) {
            addCriterion("bill_date <=", value, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateIn(List<Date> values) {
            addCriterion("bill_date in", values, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateNotIn(List<Date> values) {
            addCriterion("bill_date not in", values, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateBetween(Date value1, Date value2) {
            addCriterion("bill_date between", value1, value2, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateNotBetween(Date value1, Date value2) {
            addCriterion("bill_date not between", value1, value2, "billDate");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}