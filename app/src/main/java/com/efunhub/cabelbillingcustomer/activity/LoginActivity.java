package com.efunhub.cabelbillingcustomer.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.efunhub.cabelbillingcustomer.Interfaces.IResult;
import com.efunhub.cabelbillingcustomer.R;
import com.efunhub.cabelbillingcustomer.utility.CheckConnectivity;
import com.efunhub.cabelbillingcustomer.utility.SessionManager;
import com.efunhub.cabelbillingcustomer.utility.ToastClass;
import com.efunhub.cabelbillingcustomer.utility.VolleyService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.efunhub.cabelbillingcustomer.utility.ConstantVariables.USER_LOGIN;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText tiEdtUserID, tiEdtPassword;
    private Button btnLogin;
    private TextView tvForgotPassword;
    ProgressDialog progressDialog;
    private ToastClass toastClass;

    SessionManager sessionManager;
    AlertDialog alertDialog;

    //volley api fetching
    private IResult mResultCallback;
    private VolleyService mVolleyService;
    private final String login_url="customer-login";

    CheckConnectivity checkConnectivity ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        checkConnectivity = new CheckConnectivity();

        tiEdtUserID.setOnClickListener(this);
        tiEdtPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);

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
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnLogin:
                loginregister();
                break;

            case R.id.tvForgotPassword:
                Intent intentForgot = new Intent(this, ForgotPasswordActivity.class);
                startActivity(intentForgot);
                break;
        }
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(int requestId, String response) {
                JSONObject jsonObj = null;

                switch (requestId) {

                    case USER_LOGIN:
                        try {
                            jsonObj = new JSONObject(response);
                            Log.d("login response",response.toString());

                            //  Toast.makeText(Login.this,response.toString(),Toast.LENGTH_SHORT).show();
                            int status = jsonObj.getInt("status");

                            if (status == 0) {
                                showDialog(jsonObj.getString("msg"));
                            }
                            else if (status == 1) {
                                
                                JSONArray custDataArray=jsonObj.getJSONArray("customer_data");
                                JSONObject custDat=custDataArray.getJSONObject(0);

                                String id=custDat.getString("id");
                                String cid=custDat.getString("cid");
                                String magnetId=custDat.getString("magent_id");
                                String agentId=custDat.getString("agentid");
                                String agentName=custDat.getString("agent_name");
                                String name=custDat.getString("name");
                                String email=custDat.getString("email");
                                String contact=custDat.getString("contact");
                                String comment=custDat.getString("comment");
                                String address=custDat.getString("address");
                                String street_id=custDat.getString("street_id");
                                String street=custDat.getString("street");
                                String areaid=custDat.getString("area_id");
                                String area=custDat.getString("area");
                                String city=custDat.getString("city");
                                String state=custDat.getString("state");
                                String pincode=custDat.getString("pincode");
                                String user_status=custDat.getString("status");
                                String username=custDat.getString("username");
                                String password=custDat.getString("password");
                                String created_at=custDat.getString("created_at");
                                String updated_at=custDat.getString("updated_at");

                                sessionManager.createLoginSession(id,cid,magnetId,agentId,agentName,name,email,contact,comment,address,street_id,street,
                                        areaid,area,city,state,pincode,user_status,username,password,created_at,updated_at);
                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        catch (Exception e) {

                            Log.v("Register_Login", e.toString());
                        }
                        progressDialog.dismiss();
                        break;
                }
            }

            @Override
            public void notifyError(int requestId, VolleyError error) {
                progressDialog.dismiss();
                showDialog("Something went wrong. Please try again !!!");
                Log.v("Volley requestid", String.valueOf(requestId));
                Log.v("Volley Error", String.valueOf(error));
            }
        };
    }

    private void loginregister() {
        if (validations()){
            progressDialog = ProgressDialog.show(LoginActivity.this, "Please Wait", null, false, true);

            initVolleyCallback();

            mVolleyService = new VolleyService(mResultCallback, this);

            Map<String, String> params = new HashMap<>();
            params.put("contact", tiEdtUserID.getText().toString());
            params.put("password", tiEdtPassword.getText().toString());

            String url = getApplicationContext().getResources().getString(R.string.BASE_URL) + login_url;

            mVolleyService.postDataVolleyParameters(USER_LOGIN, url, params);
        }
    }

    private boolean validations(){

        String phoneRegex = "^[6-9][0-9]{9}$";

        String passwordRegex = "^.{6,16}$";

        if (tiEdtUserID.getText().toString().trim().equals("")){
            tiEdtUserID.setError("Please enter mobile no.");
            return false;
        }
        if (!tiEdtUserID.getText().toString().trim().matches(phoneRegex)){
            tiEdtUserID.setError("Please enter valid mobile no.");
            return false;

        }
        if (tiEdtPassword.getText().toString().trim().equals("")){
            tiEdtPassword.setError("Please enter password");
            return false;
        }
       else if (tiEdtPassword.getText().toString().length()<6){
            tiEdtPassword.setError("Password must be atleast 6 characters");
            return false;
        }
        return true;
    }

    private void init() {
        sessionManager=new SessionManager(this);
        toastClass = new ToastClass();

        tiEdtUserID = findViewById(R.id.tiEdtUserID);
        tiEdtPassword = findViewById(R.id.tiEdtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
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
