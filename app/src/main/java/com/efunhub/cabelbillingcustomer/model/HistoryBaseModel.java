package com.efunhub.cabelbillingcustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryBaseModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("additional_details")
    @Expose
    private List<HistoryAdditionalModel> additionalDetails = null;
    @SerializedName("my_purchase_history")
    @Expose
    private List<HistoryModel> myPurchaseHistory = null;

    public HistoryBaseModel(Integer status, List<HistoryAdditionalModel> additionalDetails, List<HistoryModel> myPurchaseHistory) {
        this.status = status;
        this.additionalDetails = additionalDetails;
        this.myPurchaseHistory = myPurchaseHistory;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<HistoryAdditionalModel> getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(List<HistoryAdditionalModel> additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

    public List<HistoryModel> getMyPurchaseHistory() {
        return myPurchaseHistory;
    }

    public void setMyPurchaseHistory(List<HistoryModel> myPurchaseHistory) {
        this.myPurchaseHistory = myPurchaseHistory;
    }
}
