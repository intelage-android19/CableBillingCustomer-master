package com.efunhub.cabelbillingcustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlansModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("magent_id")
    @Expose
    private String magentId;
    @SerializedName("plan_id")
    @Expose
    private String planId;
    @SerializedName("plan_name")
    @Expose
    private String planName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("validity")
    @Expose
    private Integer validity;
    @SerializedName("plan_amount")
    @Expose
    private Integer planAmount;
    @SerializedName("cgst")
    @Expose
    private Integer cgst;
    @SerializedName("sgst")
    @Expose
    private Integer sgst;
    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    public PlansModel(Integer id, String magentId, String planId, String planName, String description, Integer validity, Integer planAmount, Integer cgst, Integer sgst, Integer totalAmount, String status, Object createdAt, Object updatedAt) {
        this.id = id;
        this.magentId = magentId;
        this.planId = planId;
        this.planName = planName;
        this.description = description;
        this.validity = validity;
        this.planAmount = planAmount;
        this.cgst = cgst;
        this.sgst = sgst;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMagentId() {
        return magentId;
    }

    public void setMagentId(String magentId) {
        this.magentId = magentId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public Integer getPlanAmount() {
        return planAmount;
    }

    public void setPlanAmount(Integer planAmount) {
        this.planAmount = planAmount;
    }

    public Integer getCgst() {
        return cgst;
    }

    public void setCgst(Integer cgst) {
        this.cgst = cgst;
    }

    public Integer getSgst() {
        return sgst;
    }

    public void setSgst(Integer sgst) {
        this.sgst = sgst;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }
}
