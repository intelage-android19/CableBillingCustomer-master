package com.efunhub.cabelbillingcustomer.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.efunhub.cabelbillingcustomer.Interfaces.IResult;
import com.efunhub.cabelbillingcustomer.R;
import com.efunhub.cabelbillingcustomer.adapter.PlansAdapter;
import com.efunhub.cabelbillingcustomer.model.PlanBaseModel;
import com.efunhub.cabelbillingcustomer.model.PlansModel;
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

import static com.efunhub.cabelbillingcustomer.utility.ConstantVariables.SHOW_PLANS;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_ID;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_MAGNET_ID;

public class PlansActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private RecyclerView rvPlans;
    private PlansAdapter mPlansAdapter;
    private List<PlansModel> plansModelList=new ArrayList<>();

    private LinearLayout ll_NoPlans, llNoInternetPlans;
    private TextView tvRetryPlan;
    private ProgressBar progressBarPlan;

    private SessionManager sessionManager;
    private ToastClass toastClass;

    ProgressDialog progressDialog;
    AlertDialog alertDialog;

    //volley api fetching
    private IResult mResultCallback;
    private VolleyService mVolleyService;
    private final String show_plan_url="customer-show-suscription-plans";

    CheckConnectivity checkConnectivity ;
    LinearLayout imgNoDataFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);

        init();
        checkConnectivity =  new CheckConnectivity();
        setToolbar();
        getPlans();
    }

    private void showDialog(String message){
        alertDialog = new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                }).show(); }

    //setup toolbar
    private void setToolbar() {
        mToolbar.setTitle("Plans");
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

    private void getPlans(){
        progressDialog = ProgressDialog.show(this, "Please Wait", null, false, true);
        initVolleyCallback();

        HashMap<String,String> user=sessionManager.getUserDetails();
        String id=user.get(KEY_MAGNET_ID);

        mVolleyService = new VolleyService(mResultCallback, this);

        Map<String, String> params = new HashMap<>();
        params.put("operator_id",id );

        Log.d("show plans",params.toString());

        String url = getResources().getString(R.string.BASE_URL) + show_plan_url;

        mVolleyService.postDataVolleyParameters(SHOW_PLANS, url, params);

    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(int requestId, String response) {
                JSONObject jsonObj = null;

                switch (requestId) {
                    case SHOW_PLANS:
                        try {
                            jsonObj = new JSONObject(response);
                            int status = jsonObj.getInt("status");
                            Log.d("show plans",response.toString());

                            if (status == 0) {
                                Log.d("show plans error",jsonObj.getString("error"));
                                showDialog("No Plans available");
                                //  toastClass.makeToast(getActivity().getApplicationContext(), "Error loading sell property");
                            }
                            else if (status == 1) {
                                // toastClass.makeToast(getActivity().getApplicationContext(), "successful sell property");
                                //   Log.d("shop sell prop",jsonObj.getString("msg"));

                                Gson gson = new Gson();
                                PlanBaseModel planBaseModel = gson.fromJson(
                                        response, PlanBaseModel.class);

                                plansModelList=planBaseModel.getSubscriptionPlans();

                                mPlansAdapter=new PlansAdapter(PlansActivity.this,plansModelList);
                                mPlansAdapter.notifyDataSetChanged();
                                rvPlans.setAdapter(mPlansAdapter);
                                runLayoutAnimation(rvPlans);
                            }
                            if (plansModelList.isEmpty()) {
                                imgNoDataFound.setVisibility(View.VISIBLE);
                            }

                        }
                        catch (Exception e) {
                            Log.v("Show plans", e.toString());
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

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_top_to_bottom);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();

    }

    private void init() {

        toastClass = new ToastClass();
        sessionManager = new SessionManager(this);

        mToolbar = findViewById(R.id.toolbarPlans);
        rvPlans = findViewById(R.id.rvPlans);
        rvPlans.setHasFixedSize(true);
        rvPlans.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvPlans.setItemAnimator(new DefaultItemAnimator());

        ll_NoPlans = findViewById(R.id.ll_NoPlans);
        llNoInternetPlans = findViewById(R.id.llNoInternetPlans);
        tvRetryPlan = findViewById(R.id.tvRetryPlan);
        progressBarPlan = findViewById(R.id.progressBarPlan);

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

        if(checkConnectivity!=null){
            this.unregisterReceiver(checkConnectivity);
        }

    }

}
