package com.efunhub.cabelbillingcustomer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.efunhub.cabelbillingcustomer.R;
import com.efunhub.cabelbillingcustomer.model.PlansModel;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 19-02-2018.
 */

public class PlansAdapter extends RecyclerView.Adapter<PlansAdapter.ItemViewHolder> {

    private Context mContext;
    private List<PlansModel> arrayList;

    private AlertDialog alertDialog;

    public PlansAdapter(Context mContext, List<PlansModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PlansAdapter.ItemViewHolder itemViewHolder;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plans_list_item, parent, false);
        itemViewHolder = new PlansAdapter.ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        final PlansModel plansModel = arrayList.get(position);

        holder.tvPlanName.setText(plansModel.getPlanName());
        holder.tvPlanPrice.setText( "₹" + " " +String.valueOf(plansModel.getTotalAmount()) + "  /-");
        holder.tvPlanValidity.setText(String.format("%s days", String.valueOf(plansModel.getValidity())));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {


        TextView tvPlanName, tvPlanPrice, tvPlanValidity, tvPlanDescription, tvDialogPlanDescription;
        ImageView ivClosePlanDescription;

        ItemViewHolder(final View itemView) {
            super(itemView);

            tvPlanName = itemView.findViewById(R.id.tvPlanName);
            tvPlanPrice = itemView.findViewById(R.id.tvPlanPrice);
            tvPlanValidity = itemView.findViewById(R.id.tvPlanValidity);
            tvPlanDescription = itemView.findViewById(R.id.tvPlanDescription);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(itemView.getContext());
                    final View dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_plans_discription, null);
                    dialogBuilder.setView(dialogView);

                    tvDialogPlanDescription = dialogView.findViewById(R.id.tvDialogPlanDescription);
                    ivClosePlanDescription = dialogView.findViewById(R.id.ivClosePlanDescription);

                    final PlansModel plansModel = arrayList.get(getAdapterPosition());

                    tvDialogPlanDescription.setText(plansModel.getDescription());

                    ivClosePlanDescription.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });

                    alertDialog = dialogBuilder.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                }
            });
        }
    }
}
