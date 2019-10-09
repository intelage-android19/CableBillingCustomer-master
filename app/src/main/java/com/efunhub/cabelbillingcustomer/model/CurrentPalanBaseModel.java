package com.efunhub.cabelbillingcustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentPalanBaseModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("subscription_plans")
    @Expose
    private List<CurrentPlan> subscriptionPlans = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<CurrentPlan> getSubscriptionPlans() {
        return subscriptionPlans;
    }

    public void setSubscriptionPlans(List<CurrentPlan> subscriptionPlans) {
        this.subscriptionPlans = subscriptionPlans;
    }

}