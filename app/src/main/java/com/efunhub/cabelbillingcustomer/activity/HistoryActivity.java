package com.efunhub.cabelbillingcustomer.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.efunhub.cabelbillingcustomer.Interfaces.IResult;
import com.efunhub.cabelbillingcustomer.R;
import com.efunhub.cabelbillingcustomer.adapter.HistoryAdapter;
import com.efunhub.cabelbillingcustomer.adapter.PlansAdapter;
import com.efunhub.cabelbillingcustomer.model.HistoryAdditionalModel;
import com.efunhub.cabelbillingcustomer.model.HistoryBaseModel;
import com.efunhub.cabelbillingcustomer.model.HistoryModel;
import com.efunhub.cabelbillingcustomer.model.PlanBaseModel;
import com.efunhub.cabelbillingcustomer.utility.CheckConnectivity;
import com.efunhub.cabelbillingcustomer.utility.SessionManager;
import com.efunhub.cabelbillingcustomer.utility.ToastClass;
import com.efunhub.cabelbillingcustomer.utility.VolleyService;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.efunhub.cabelbillingcustomer.utility.ConstantVariables.SHOW_HISTORY;
import static com.efunhub.cabelbillingcustomer.utility.ConstantVariables.SHOW_PLANS;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_C_ID;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_MAGNET_ID;

public class HistoryActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private RecyclerView rvHistory;
    private HistoryAdapter mHistoryAdapter;
    private List<HistoryModel> historyModelList = new ArrayList<>();
    private List<HistoryAdditionalModel> historyAdditionalModelList = new ArrayList<>();

    private LinearLayout ll_NoHistory, llNoInternetHistory;
    private TextView tvRetryHistory;
    private Button btnPurchase;
    private ProgressBar progressBarHistory;

    private SessionManager sessionManager;
    private ToastClass toastClass;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;

    //volley api fetching
    private IResult mResultCallback;
    private VolleyService mVolleyService;
    private final String show_history_url = "customer-purchase-history";

    CheckConnectivity checkConnectivity;
    LinearLayout imgNoDataFound;
    ImageView imgMoreDetails;

    TextView txtSetTopBox, txtBoxModel, txtViewingCard, txtRegistrationDate, txtDueramount, txtAgreementDate;
    String settopbox, boxmodel, viewingCard, registrationDte, dueAmount, agreementnumber;
    CardView btnOk;

    Dialog alertCollectBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        init();

        setToolbar();

        getHistory();

        imgMoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogMoreInfo();
            }


        });
    }


    private void dialogMoreInfo() {

        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_more_info, null);
        dialogBuilder.setView(dialogView);

        txtSetTopBox = dialogView.findViewById(R.id.txtSetTopNumber);
        txtBoxModel = dialogView.findViewById(R.id.txtModelnumber);
        txtViewingCard = dialogView.findViewById(R.id.txtViewvingCard);
        txtRegistrationDate = dialogView.findViewById(R.id.txtRegistrationDate);
        txtDueramount = dialogView.findViewById(R.id.txtDueAmount);
        txtAgreementDate = dialogView.findViewById(R.id.txtAgreementNo);
        btnOk = dialogView.findViewById(R.id.btnOk);


        txtSetTopBox.setText(settopbox);
        txtBoxModel.setText(boxmodel);
        txtAgreementDate.setText(agreementnumber);
        txtViewingCard.setText(viewingCard);
        txtRegistrationDate.setText(registrationDte);
        txtDueramount.setText("₹" + " " + dueAmount + " /-");


/*
        imgCancle = dialogView.findViewById(R.id.ivCancel);
*/

        alertCollectBill = dialogBuilder.create();
        alertCollectBill.setCanceledOnTouchOutside(false);
        alertCollectBill.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertCollectBill.show();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertCollectBill.dismiss();
            }
        });

       /* imgCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertCollectBill.dismiss();
            }
        });*/

    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_top_to_bottom);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();

    }

    //setup toolbar
    private void setToolbar() {
        mToolbar.setTitle("Purchased History");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getHistory() {
        progressDialog = ProgressDialog.show(this, "Please Wait", null, false, true);
        initVolleyCallback();

        HashMap<String, String> user = sessionManager.getUserDetails();
        String id = user.get(KEY_C_ID);

        mVolleyService = new VolleyService(mResultCallback, this);

        Map<String, String> params = new HashMap<>();
        params.put("cust_id", id);

        Log.d("show history", params.toString());

        String url = getResources().getString(R.string.BASE_URL) + show_history_url;

        mVolleyService.postDataVolleyParameters(SHOW_HISTORY, url, params);
    }

    private void showDialog(String message) {
        alertDialog = new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                }).show();
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(int requestId, String response) {
                JSONObject jsonObj = null;

                switch (requestId) {
                    case SHOW_HISTORY:
                        try {

                            jsonObj = new JSONObject(response);
                            int status = jsonObj.getInt("status");
                            Log.d("show history", response.toString());

                            if (status == 0) {
                                Log.d("show plans error", jsonObj.getString("error"));
                                showDialog("No History available");
                                //  toastClass.makeToast(getActivity().getApplicationContext(), "Error loading sell property");
                            } else if (status == 1) {
                                // toastClass.makeToast(getActivity().getApplicationContext(), "successful sell property");
                                //   Log.d("shop sell prop",jsonObj.getString("msg"));

                                Gson gson = new Gson();
                                HistoryBaseModel historyBaseModel = gson.fromJson(
                                        response, HistoryBaseModel.class);

                                historyModelList = historyBaseModel.getMyPurchaseHistory();

                                mHistoryAdapter = new HistoryAdapter(HistoryActivity.this, historyModelList);
                                mHistoryAdapter.notifyDataSetChanged();
                                rvHistory.setAdapter(mHistoryAdapter);
runLayoutAnimation(rvHistory);

                                List<HistoryAdditionalModel> historyAdditionalModels = historyBaseModel.getAdditionalDetails();

                                for (HistoryAdditionalModel historyAdditionalModel : historyAdditionalModels) {
                                    historyAdditionalModelList.add(historyAdditionalModel);


                                    settopbox = historyAdditionalModel.getSettopBox();
                                    boxmodel = historyAdditionalModel.getBoxModel();
                                    registrationDte = historyAdditionalModel.getActivationDate();
                                    viewingCard = historyAdditionalModel.getViewingCard();
                                    dueAmount = String.valueOf(historyAdditionalModel.getDueAmount());
                                    agreementnumber = historyAdditionalModel.getAgreementNumber();

                                }


                            }

                            if (historyModelList.isEmpty()) {
                                imgNoDataFound.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception e) {
                            Log.v("Show history", e.toString());
                        }
                        progressDialog.dismiss();
                        break;
                }
            }

            @Override
            public void notifyError(int requestId, VolleyError error) {
                showDialog("Something went wrong");
                Log.v("Volley requestid", String.valueOf(requestId));
                Log.v("Volley Error", String.valueOf(error));
                progressDialog.dismiss();
            }
        };
    }

    private void init() {

        sessionManager = new SessionManager(this);
        checkConnectivity = new CheckConnectivity();

        toastClass = new ToastClass();

        mToolbar = findViewById(R.id.toolbarHistory);
        rvHistory = findViewById(R.id.rvHistory);
        ll_NoHistory = findViewById(R.id.ll_NoHistory);
        llNoInternetHistory = findViewById(R.id.llNoInternetHistory);
        tvRetryHistory = findViewById(R.id.tvRetryHistory);
        btnPurchase = findViewById(R.id.btnPurchase);
        progressBarHistory = findViewById(R.id.progressBarHistory);
        imgNoDataFound = findViewById(R.id.imgNoData);
        imgMoreDetails = findViewById(R.id.imgMoreDetails);


        rvHistory.setHasFixedSize(true);
        rvHistory.setNestedScrollingEnabled(false);
        rvHistory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvHistory.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void onResume() {
        super.onResume();

        //Check connectivity

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(checkConnectivity, intentFilter);

    }

    @Override
    public void onPause() {
        super.onPause();

        if (checkConnectivity != null) {
            this.unregisterReceiver(checkConnectivity);
        }

    }

}
