package com.efunhub.cabelbillingcustomer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.efunhub.cabelbillingcustomer.R;
import com.efunhub.cabelbillingcustomer.model.HistoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 19-02-2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ItemViewHolder> {

    private Context mContext;
    private List<HistoryModel> arrayList;

    public HistoryAdapter(Context mContext, List<HistoryModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HistoryAdapter.ItemViewHolder itemViewHolder;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_item, parent, false);
        itemViewHolder = new HistoryAdapter.ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {

        final HistoryModel historyModel = arrayList.get(position);


        if (historyModel.getPlan_status().equals("Active"))
        {
            holder.tvHistoryPlanId.setText(historyModel.getPlanId());
            holder.tvHistoryPlanPurchaseDate.setText(String.format(String.valueOf(historyModel.getPurchaseDate())));
            holder.tvHistoryPlanExpiryDate.setText(String.format(String.valueOf(historyModel.getExpiryDate())));
            holder.tvHistoryPlanName.setText((historyModel.getPlanName()));
            holder.tvHistoryPlanValidity.setText(String.format( String.valueOf(historyModel.getValidity()) + " " +"days"));
            holder.tvHistoryPlanPrice.setText(("₹"+ " "+ String.valueOf(historyModel.getTotalAmount()) + " /-"));
         //   holder.tvHistoryDueAmount.setText(("₹" + " "+ String.valueOf(historyModel.getPendingAmount()) + " /-"));


            holder.tvHistoryPlanName.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.tvHistoryPlanPurchaseDate.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.tvHistoryPlanExpiryDate.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.tvHistoryPlanValidity.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.tvHistoryPlanPrice.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
          //  holder.tvHistoryDueAmount.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));

        }
        else {

            holder.tvHistoryPlanId.setText(historyModel.getPlanId());
            holder.tvHistoryPlanPurchaseDate.setText(String.format(String.valueOf(historyModel.getPurchaseDate())));
            holder.tvHistoryPlanExpiryDate.setText(String.format(String.valueOf(historyModel.getExpiryDate())));
            holder.tvHistoryPlanName.setText((historyModel.getPlanName()));
            holder.tvHistoryPlanValidity.setText(String.format( String.valueOf(historyModel.getValidity()) + " " +"days"));
            holder.tvHistoryPlanPrice.setText(("₹"+ " "+ String.valueOf(historyModel.getTotalAmount()) + " /-"));

            holder.tvHistoryPlanName.setTextColor(mContext.getResources().getColor(R.color.transparant));
            holder.tvHistoryPlanPurchaseDate.setTextColor(mContext.getResources().getColor(R.color.transparant));
            holder.tvHistoryPlanExpiryDate.setTextColor(mContext.getResources().getColor(R.color.transparant));
            holder.tvHistoryPlanValidity.setTextColor(mContext.getResources().getColor(R.color.transparant));
            holder.tvHistoryPlanPrice.setTextColor(mContext.getResources().getColor(R.color.transparant));
         //   holder.tvHistoryDueAmount.setTextColor(mContext.getResources().getColor(R.color.transparant));


            holder.tvHistoryPlanName1.setTextColor(mContext.getResources().getColor(R.color.transparant));
            holder.tvHistoryPlanPurchaseDate1.setTextColor(mContext.getResources().getColor(R.color.transparant));
            holder.tvexpirydate1.setTextColor(mContext.getResources().getColor(R.color.transparant));
            holder.planPrice1.setTextColor(mContext.getResources().getColor(R.color.transparant));
            holder.validity11.setTextColor(mContext.getResources().getColor(R.color.transparant));
        //    holder.due11.setTextColor(mContext.getResources().getColor(R.color.transparant));




        }



      /*  if (String.valueOf(historyModel.getPendingAmount()).equals("0.0")) {

            holder.tvHistoryDueAmount.setText("No Due Amount");
            holder.ivPaid.setVisibility(View.VISIBLE);
            holder.btnPayNow.setVisibility(View.GONE);
        } else {
            holder.tvHistoryDueAmount.setText(("₹" + " "+ String.valueOf(historyModel.getPendingAmount()) + " /-"));
        }*/
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

        TextView tvHistoryPlanId, tvHistoryPlanPurchaseDate, tvHistoryPlanName, tvHistoryPlanValidity,
                tvHistoryPlanPrice, tvHistoryTotalPaidPrice,tvHistoryPlanExpiryDate;

        TextView tvHistoryPlanName1,tvHistoryPlanPurchaseDate1,tvexpirydate1,planPrice1,validity11;

        ImageView ivPaid;
        CardView btnPayNow;

        ItemViewHolder(final View itemView) {
            super(itemView);

            tvHistoryPlanId = itemView.findViewById(R.id.tvHistoryPlanId);
            tvHistoryPlanPurchaseDate = itemView.findViewById(R.id.tvHistoryPlanPurchaseDate);
            tvHistoryPlanExpiryDate = itemView.findViewById(R.id.tvHistoryPlanExpiryDate);
            tvHistoryPlanName = itemView.findViewById(R.id.tvHistoryPlanName);
            tvHistoryPlanValidity = itemView.findViewById(R.id.tvHistoryPlanValidity);
            tvHistoryPlanPrice = itemView.findViewById(R.id.tvHistoryPlanPrice);
            ivPaid = itemView.findViewById(R.id.ivPaid);
            btnPayNow = itemView.findViewById(R.id.btnPayNow);


            tvHistoryPlanName1 = itemView.findViewById(R.id.tvHistoryPlanName1);
            tvHistoryPlanPurchaseDate1 = itemView.findViewById(R.id.tvHistoryPlanPurchaseDate1);
            tvexpirydate1 = itemView.findViewById(R.id.tvexpirydate1);
            planPrice1 = itemView.findViewById(R.id.planPrice1);
            validity11 = itemView.findViewById(R.id.validity11);



        }
    }
}
