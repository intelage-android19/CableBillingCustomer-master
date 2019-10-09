package com.efunhub.cabelbillingcustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentPlan {

    @Expose
    private Integer id;
    @SerializedName("cid")
    @Expose
    private String cid;
    @SerializedName("magent_id")
    @Expose
    private String magentId;
    @SerializedName("agentid")
    @Expose
    private String agentid;
    @SerializedName("plan_id")
    @Expose
    private String planId;
    @SerializedName("plan_name")
    @Expose
    private String planName;
    @SerializedName("validity")
    @Expose
    private Integer validity;
    @SerializedName("purchase_date")
    @Expose
    private String purchaseDate;
    @SerializedName("expiry_date")
    @Expose
    private String expiryDate;
    @SerializedName("plan_amount")
    @Expose
    private Integer planAmount;
    @SerializedName("cgst")
    @Expose
    private Integer cgst;
    @SerializedName("sgst")
    @Expose
    private Integer sgst;
    @SerializedName("cgstprice")
    @Expose
    private Integer cgstprice;
    @SerializedName("sgstprice")
    @Expose
    private Integer sgstprice;
    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;
    @SerializedName("paid_amount")
    @Expose
    private Integer paidAmount;
    @SerializedName("pending_amount")
    @Expose
    private Integer pendingAmount;
    @SerializedName("pmode")
    @Expose
    private String pmode;
    @SerializedName("transaction_date")
    @Expose
    private String transactionDate;
    @SerializedName("plan_status")
    @Expose
    private String planStatus;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getMagentId() {
        return magentId;
    }

    public void setMagentId(String magentId) {
        this.magentId = magentId;
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
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

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
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

    public Integer getCgstprice() {
        return cgstprice;
    }

    public void setCgstprice(Integer cgstprice) {
        this.cgstprice = cgstprice;
    }

    public Integer getSgstprice() {
        return sgstprice;
    }

    public void setSgstprice(Integer sgstprice) {
        this.sgstprice = sgstprice;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Integer paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Integer getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(Integer pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    public String getPmode() {
        return pmode;
    }

    public void setPmode(String pmode) {
        this.pmode = pmode;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}