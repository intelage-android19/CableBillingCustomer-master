package com.efunhub.cabelbillingcustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryModel {

    @SerializedName("cust_id")
    @Expose
    private String custId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("address")
    @Expose
    private String address;
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

    public String getPlan_status() {
        return plan_status;
    }

    public void setPlan_status(String plan_status) {
        this.plan_status = plan_status;
    }

    @SerializedName("plan_status")
    @Expose
    private String plan_status;


    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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


}
