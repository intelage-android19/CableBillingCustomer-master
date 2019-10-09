package com.efunhub.cabelbillingcustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryAdditionalModel {
    @SerializedName("agreement_number")
    @Expose
    private String agreementNumber;
    @SerializedName("due_amount")
    @Expose
    private Integer dueAmount;
    @SerializedName("activation_date")
    @Expose
    private String activationDate;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("settop_box")
    @Expose
    private String settopBox;
    @SerializedName("box_model")
    @Expose
    private String boxModel;
    @SerializedName("viewing_card")
    @Expose
    private String viewingCard;

    public String getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    public Integer getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(Integer dueAmount) {
        this.dueAmount = dueAmount;
    }

    public String getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(String activationDate) {
        this.activationDate = activationDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSettopBox() {
        return settopBox;
    }

    public void setSettopBox(String settopBox) {
        this.settopBox = settopBox;
    }

    public String getBoxModel() {
        return boxModel;
    }

    public void setBoxModel(String boxModel) {
        this.boxModel = boxModel;
    }

    public String getViewingCard() {
        return viewingCard;
    }

    public void setViewingCard(String viewingCard) {
        this.viewingCard = viewingCard;
    }
}
