package com.efunhub.cabelbillingcustomer.activity;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import com.android.volley.VolleyError;
import com.efunhub.cabelbillingcustomer.Interfaces.IResult;
import com.efunhub.cabelbillingcustomer.R;
import com.efunhub.cabelbillingcustomer.adapter.PlansAdapter;
import com.efunhub.cabelbillingcustomer.model.CurrentPalanBaseModel;
import com.efunhub.cabelbillingcustomer.model.CurrentPlan;
import com.efunhub.cabelbillingcustomer.model.HistoryAdditionalModel;
import com.efunhub.cabelbillingcustomer.model.PlanBaseModel;
import com.efunhub.cabelbillingcustomer.model.PlansModel;
import com.efunhub.cabelbillingcustomer.utility.CheckConnectivity;
import com.efunhub.cabelbillingcustomer.utility.SessionManager;
import com.efunhub.cabelbillingcustomer.utility.VolleyService;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.efunhub.cabelbillingcustomer.utility.ConstantVariables.CURRENT_PLAN;
import static com.efunhub.cabelbillingcustomer.utility.ConstantVariables.ENQIRY;
import static com.efunhub.cabelbillingcustomer.utility.ConstantVariables.SHOW_PLANS;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_C_ID;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_ID;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_MAGNET_ID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;

    private LinearLayout llPlans, llHistory, llRemainingTime, llMoredetails;
    private RelativeLayout rlCurrentPlanDetails;
    private CardView cardAccount, cardNoCurrentPlan;


    //change password dialog
    private AlertDialog alertDialog;
    private TextView tvRemainingDays, tvCurrentPlanValidity, tvCurrentPlanPrice, tvCurrentPlanName, tvDueBalanceAmount, tvPMode, tvexpiryDate, tvPurchaseDate;
    private ImageView ivCloseCurrentPlan,logout;

    String expiryDate, dueAmount, planName, planValidity, purchaseDate, expireDate, pMode, totalAmount;

    Button txtEnquiry;

    private List<CurrentPlan> currentPlansList = new ArrayList<>();
    private CurrentPalanBaseModel currentPalanBaseModel;

    //volley api fetching
    private IResult mResultCallback;
    private VolleyService mVolleyService;
    String currentPlanURL = "customer-current-subscription";
    String enquiryURL = "customer-current-subscription";
    SessionManager sessionManager;
    ProgressDialog progressDialog;
    CheckConnectivity checkConnectivity;

    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        checkConnectivity = new CheckConnectivity();
        sessionManager = new SessionManager(this);

        setuptoolbar();

        setCardBackground();

        getPlans();

        llPlans.setOnClickListener(this);
        llHistory.setOnClickListener(this);
        cardAccount.setOnClickListener(this);
        llRemainingTime.setOnClickListener(this);
        llMoredetails.setOnClickListener(this);


        txtEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EnqiryActivity addPhotoBottomDialogFragment =
                        EnqiryActivity.newInstance();
                addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                        "add_photo_dialog_fragment");
            }
        });


    }

    private void setuptoolbar() {

        mToolbar.setTitle("Dashboard");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));



        setSupportActionBar(mToolbar);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        getMenuInflater().inflate(R.menu.home_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.logout:

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Are you sure,you want to logout?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                  sessionManager.logoutUser();
                                // Intent i = new Intent(MainActivity.this, LoginActivity.class);
                                // startActivity(i);
                                finish();
                            }
                        });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
           /* case R.id.cart:
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }


    private void getPlans() {

        progressDialog = ProgressDialog.show(this, "Please wait", null, false, true);
        initVolleyCallback();

        HashMap<String, String> user = sessionManager.getUserDetails();
        String id = user.get(KEY_C_ID);

        mVolleyService = new VolleyService(mResultCallback, this);

        Map<String, String> params = new HashMap<>();
        params.put("cust_id", id);

        String url = getResources().getString(R.string.BASE_URL) + currentPlanURL;

        mVolleyService.postDataVolleyParameters(CURRENT_PLAN, url, params);

    }

/*
    private void sendEnquiry() {

        progressDialog = ProgressDialog.show(this, "Please Wait", null, false, true);
        initVolleyCallback();

        HashMap<String, String> user = sessionManager.getUserDetails();
        String id = user.get(KEY_C_ID);

        mVolleyService = new VolleyService(mResultCallback, this);

        Map<String, String> params = new HashMap<>();
        params.put("cust_id", id);
        params.put("cust_id", id);

        String url = getResources().getString(R.string.BASE_URL) + currentPlanURL;

        mVolleyService.postDataVolleyParameters(ENQIRY, url, params);

    }
*/



    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(int requestId, String response) {
                JSONObject jsonObj = null;

                switch (requestId) {
                    case CURRENT_PLAN:
                        try {
                            jsonObj = new JSONObject(response);
                            int status = jsonObj.getInt("status");
                            Log.d("show plans", response.toString());

                            if (status == 0) {
                                Log.d("show plans error", jsonObj.getString("error"));
                                //     showDialog("No Plans available");
                                //  toastClass.makeToast(getActivity().getApplicationContext(), "Error loading sell property");
                            } else if (status == 1) {
                                // toastClass.makeToast(getActivity().getApplicationContext(), "successful sell property");
                                //   Log.d("shop sell prop",jsonObj.getString("msg"));

                                Gson gson = new Gson();

                                CurrentPalanBaseModel currentPalanBaseModel = gson.fromJson(
                                        response, CurrentPalanBaseModel.class);

                                List<CurrentPlan> currentPlans = currentPalanBaseModel.getSubscriptionPlans();

                                CurrentPlan currentPlan = currentPlans.get(0);
                                dueAmount = String.valueOf(currentPlan.getPendingAmount());
                                expiryDate = String.valueOf(currentPlan.getExpiryDate());
                                tvDueBalanceAmount.setText("₹" + " " + dueAmount + " " + "/-");

                                planName = currentPlan.getPlanName();
                                planValidity = String.valueOf(currentPlan.getValidity());
                                purchaseDate = currentPlan.getPurchaseDate();
                                expireDate = currentPlan.getExpiryDate();
                                totalAmount = String.valueOf(currentPlan.getTotalAmount());
                                pMode = currentPlan.getPmode();

                                setCurrentPlan();

                            }
                        } catch (Exception e) {
                            Log.v("Show plans", e.toString());
                        }
                        progressDialog.dismiss();
                        break;

                 /*   case ENQIRY:
                        try {
                            jsonObj = new JSONObject(response);
                            int status = jsonObj.getInt("status");


                            if (status == 1) {
                                Toast.makeText(MainActivity.this, "Query send successfully", Toast.LENGTH_SHORT).show();
                               // alertDialogUpdatePass.dismiss();
                            }
                        } catch (Exception e) {

                            Log.v("Update_profile_dialog", e.toString());
                        }
                        break;*/

                }
            }

            @Override
            public void notifyError(int requestId, VolleyError error) {
              //  showDialog("jsdkh");
                Log.v("Volley requestid", String.valueOf(requestId));
                Log.v("Volley Error", String.valueOf(error));
                progressDialog.dismiss();
            }
        };
    }

    private void setCurrentPlan() {

        Date currentDate = Calendar.getInstance().getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date Expired_date = null;

        try {

            Expired_date = sdf.parse(expiryDate);

            long different = Expired_date.getTime() - currentDate.getTime();

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long daysDifference = different / daysInMilli;
            //  different = different % daysInMilli;


            System.out.printf("%d days", daysDifference);

            tvRemainingDays.setText(String.valueOf(daysDifference));


            Animation aniRotate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.clockwiseamimation);
            aniRotate.setRepeatCount(Animation.RESTART);
            tvRemainingDays.startAnimation(aniRotate);
/*
            ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(tvRemainingDays ,
                    "rotation", 0f, 50f);
            imageViewObjectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            imageViewObjectAnimator.setRepeatMode(ObjectAnimator.RESTART);
            imageViewObjectAnimator.setInterpolator(new AccelerateInterpolator());
            imageViewObjectAnimator.start();*/

/*

            if (daysDifference <= 4 && customerActivePlansModel.getPlan_status().equals("Active")) {

                viewHolder.imgRenewPack.setVisibility(View.VISIBLE);

            } else {

            }
*/

        } catch (ParseException e) {
            e.printStackTrace();

        }
    }

    //set background to CardView
    private void setCardBackground() {
        cardAccount.setBackgroundResource(R.drawable.card_dark_bg);
    }

    private void setCurrentPlanDetails() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_current_plans_details, null);
        dialogBuilder.setView(dialogView);

        tvCurrentPlanName = dialogView.findViewById(R.id.tvCurrentPlanName);
        tvCurrentPlanValidity = dialogView.findViewById(R.id.tvCurrentPlanValidity);
        tvCurrentPlanPrice = dialogView.findViewById(R.id.tvCurrentPlanPrice);
        ivCloseCurrentPlan = dialogView.findViewById(R.id.ivCloseCurrentPlan);
        tvPMode = dialogView.findViewById(R.id.tvPMode);
        tvexpiryDate = dialogView.findViewById(R.id.tvexpiryDate);
        tvPurchaseDate = dialogView.findViewById(R.id.tvPurchaseDate);


        tvCurrentPlanName.setText(planName);
        tvCurrentPlanValidity.setText(planValidity + " " + "days");
        tvCurrentPlanPrice.setText("₹" + " " + totalAmount + " " + "/-");
        tvPurchaseDate.setText((purchaseDate));
        tvexpiryDate.setText(expiryDate);
        tvPMode.setText(pMode);

        ivCloseCurrentPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void init() {

        mToolbar = findViewById(R.id.toolbar_main);
        llPlans = findViewById(R.id.llPlans);
        llHistory = findViewById(R.id.llHistory);
        llRemainingTime = findViewById(R.id.llRemainingTime);
        llMoredetails = findViewById(R.id.llMoredetails);
        rlCurrentPlanDetails = findViewById(R.id.rlCurrentPlanDetails);
        cardAccount = findViewById(R.id.cardAccount);
        cardNoCurrentPlan = findViewById(R.id.cardNoCurrentPlan);
        tvRemainingDays = findViewById(R.id.tvRemainingDays);
        tvDueBalanceAmount = findViewById(R.id.tvDueBalanceAmount);
        txtEnquiry = findViewById(R.id.txtEnquiry);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.llPlans:
                startActivity(new Intent(MainActivity.this, PlansActivity.class));
                break;
            case R.id.llHistory:
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                break;
            case R.id.cardAccount:
                startActivity(new Intent(MainActivity.this, AccountActivity.class));
                break;
            case R.id.llMoredetails:
                setCurrentPlanDetails();
                break;

        }
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

    @Override
    public void onBackPressed() {

        FragmentManager fm = getSupportFragmentManager();

        if (fm.getBackStackEntryCount() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Confirm Exit");
            builder.setMessage("Do you want to exit?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                            homeIntent.addCategory(Intent.CATEGORY_HOME);
                            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(homeIntent);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            super.onBackPressed();
        }
    }

}
