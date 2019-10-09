package com.efunhub.cabelbillingcustomer.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.VolleyError;
import com.efunhub.cabelbillingcustomer.Interfaces.IResult;
import com.efunhub.cabelbillingcustomer.R;
import com.efunhub.cabelbillingcustomer.utility.CheckConnectivity;
import com.efunhub.cabelbillingcustomer.utility.SessionManager;
import com.efunhub.cabelbillingcustomer.utility.ToastClass;
import com.efunhub.cabelbillingcustomer.utility.VolleyService;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.efunhub.cabelbillingcustomer.utility.ConstantVariables.FORGOT_PASS;


public class ForgotPasswordActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextInputEditText tiEdtForgotUserID;
    private Button btnSend;
    private ToastClass toastClass;

    String ForgotPassword = "customer-forgot-password";

    //Volley service
    private IResult mResultCallback;
    private VolleyService mVolleyService;

    SessionManager sessionManager;

    ProgressDialog pdForgotPassword;

    CheckConnectivity checkConnectivity ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_forgot_password);

        checkConnectivity = new CheckConnectivity();
        init();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValid()) {
                    forgotPassword();
                }
            }
        });
    }


    private void forgotPassword() {

        initVolleyCallback();

        btnSend.setVisibility(View.GONE);

        mVolleyService = new VolleyService(mResultCallback, this);

        Map<String, String> params = new HashMap<>();
        params.put("email", tiEdtForgotUserID.getText().toString());

        mVolleyService.postDataVolleyParameters(FORGOT_PASS,
                getApplicationContext().getResources().getString(R.string.BASE_URL) + ForgotPassword,
                params);

    }


    private void initVolleyCallback() {
        pdForgotPassword = ProgressDialog.show(ForgotPasswordActivity.this, "Please Wait", null, false, true);

        pdForgotPassword.show();

        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(int requestId, String response) {
                JSONObject jsonObj = null;

                switch (requestId) {

                    case FORGOT_PASS:
                        try {
                            jsonObj = new JSONObject(response);
                            int status = jsonObj.getInt("status");

                            if (status == 0) {
                                Toast.makeText(ForgotPasswordActivity.this, "Sorry, an account not exist with this email", Toast.LENGTH_SHORT).show();
                            }
                            else if (status == 1) {

                                Toast.makeText(ForgotPasswordActivity.this, "Success, Please check your email", Toast.LENGTH_SHORT).show();
startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));                            }
                        } catch (Exception e) {

                            Log.v("ForgotPasswordActivity", e.toString());
                        }
                        pdForgotPassword.dismiss();
                        btnSend.setVisibility(View.VISIBLE);
                        break;

                }
            }

            @Override
            public void notifyError(int requestId, VolleyError error) {

                //    imgNoDataFound.setVisibility(View.VISIBLE);
                Log.v("Volley requestid", String.valueOf(requestId));
                Log.v("Volley Error", String.valueOf(error));
                //   pbForgotPass.setVisibility(View.GONE);
                btnSend.setVisibility(View.VISIBLE);
            }
        };
    }


    private boolean checkValid() {

        final String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (tiEdtForgotUserID.getText().toString().equalsIgnoreCase("")) {

            tiEdtForgotUserID.setError("Please enter email id");
            return false;
        } else if (!tiEdtForgotUserID.getText().toString().matches(emailPattern)) {
            tiEdtForgotUserID.setError("Please enter valid email id");
            return false;
        }
        return true;
    }

    private void init() {
        toastClass = new ToastClass();
        tiEdtForgotUserID = findViewById(R.id.tiEdtForgotUserID);
        btnSend = findViewById(R.id.btnSend);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    finish();
    }
}
