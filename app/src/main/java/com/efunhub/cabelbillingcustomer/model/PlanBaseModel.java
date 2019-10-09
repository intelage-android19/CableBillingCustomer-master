package com.efunhub.cabelbillingcustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlanBaseModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("subscription_plans")
    @Expose
    private List<PlansModel> subscriptionPlans = null;

    public PlanBaseModel(Integer status, List<PlansModel> subscriptionPlans) {
        this.status = status;
        this.subscriptionPlans = subscriptionPlans;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<PlansModel> getSubscriptionPlans() {
        return subscriptionPlans;
    }

    public void setSubscriptionPlans(List<PlansModel> subscriptionPlans) {
        this.subscriptionPlans = subscriptionPlans;
    }
}
