package com.efunhub.cabelbillingcustomer.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.android.volley.VolleyError;
import com.efunhub.cabelbillingcustomer.Interfaces.IResult;
import com.efunhub.cabelbillingcustomer.R;
import com.efunhub.cabelbillingcustomer.utility.CheckConnectivity;
import com.efunhub.cabelbillingcustomer.utility.SessionManager;
import com.efunhub.cabelbillingcustomer.utility.VolleyService;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.efunhub.cabelbillingcustomer.utility.ConstantVariables.CHANGE_PASSWORD;
import static com.efunhub.cabelbillingcustomer.utility.ConstantVariables.UPDATE_PROFILE;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_ADDRESS;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_AGENT_NAME;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_AREA;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_AREA_ID;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_CITY;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_COMMENT;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_CONTACT;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_CREATED_AT;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_C_ID;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_EMAIL;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_ID;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_MAGNET_ID;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_NAME;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_PASSWORD;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_PINCODE;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_STATE;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_STATUS;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_STREET;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_STREET_ID;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_UPDATED_AT;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_USERNAME;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY__AGENT_ID;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;

    private ImageView ivEditProfile;
    private TextView tvUserUserID, tvUserName, tvUserMail, tvUserContact, tvUserState, tvUserCity, tvUserArea,
            tvUserAddress, tvUserPincode, tvAgentName, tvAgentContact, tvUserStreet;
    private CardView cardChangePassword;

    private AlertDialog passwordAlertDialog, profileAlertDialog;
    private Dialog progressDialog;

    //change password dialog
    private TextInputEditText edtOldPassword, edtNewPassword;
    private ImageView ivCloseChnagePassword;
    private Button btnPasswordSend;

    //edit profile dialog
    private EditText edtUserID, edtName, edtEmail, edtContact;
    private ImageView ivCloseEditProfile;
    private Button btnUpdate;

    Dialog alertDialogUpdatePass, alertDialogUpdateProfile;

    //Volley service
    private IResult mResultCallback;
    private VolleyService mVolleyService;

    AlertDialog alertDialog;

    SessionManager sessionManager;

    String updatePassword = "customer-change-password";
    String updateProfile = "customer-update-profile";

    String id, cId, magnetId, agentId, agentName, name,
            email, contact, comment, address, streetId, street,
            areaId, area, city, state, pincode, user_status,
            userName, password, created_at, updated_at;

    CheckConnectivity checkConnectivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        sessionManager = new SessionManager(this);
        checkConnectivity = new CheckConnectivity();

        init();

        setToolbar();

        getData();



        ivEditProfile.setOnClickListener(this);
        cardChangePassword.setOnClickListener(this);
    }


    private void getData() {


        HashMap<String, String> userInfo = sessionManager.getUserDetails();

        id = userInfo.get(KEY_ID);
        cId = userInfo.get(KEY_C_ID);
        magnetId = userInfo.get(KEY_MAGNET_ID);
        agentId = userInfo.get(KEY__AGENT_ID);
        agentName = userInfo.get(KEY_AGENT_NAME);
        name = userInfo.get(KEY_NAME);
        email = userInfo.get(KEY_EMAIL);
        contact = userInfo.get(KEY_CONTACT);
        comment = userInfo.get(KEY_COMMENT);
        address = userInfo.get(KEY_ADDRESS);
        streetId = userInfo.get(KEY_STREET_ID);
        street = userInfo.get(KEY_STREET);
        areaId = userInfo.get(KEY_AREA_ID);
        area = userInfo.get(KEY_AREA);
        city = userInfo.get(KEY_CITY);
        state = userInfo.get(KEY_STATE);
        pincode = userInfo.get(KEY_PINCODE);
        user_status = userInfo.get(KEY_STATUS);
        userName = userInfo.get(KEY_USERNAME);
        password = userInfo.get(KEY_PASSWORD);
        created_at = userInfo.get(KEY_CREATED_AT);
        updated_at = userInfo.get(KEY_UPDATED_AT);


        tvUserUserID.setText(cId);
        tvUserName.setText(name);
        tvUserMail.setText(email);
        tvUserContact.setText(contact);
        tvUserCity.setText(city);
        tvUserArea.setText(area);
        tvUserStreet.setText(street);
        tvUserAddress.setText(address);
        tvUserPincode.setText(pincode);
        tvAgentName.setText(agentName);
        tvAgentContact.setText(userName);


    }



    //Change Password Dialog
    private void changePasswordDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.change_password_dialog, null);
        dialogBuilder.setView(dialogView);

        edtOldPassword = dialogView.findViewById(R.id.edtOldPassword);
        edtNewPassword = dialogView.findViewById(R.id.edtNewPassword);

        btnPasswordSend = dialogView.findViewById(R.id.btnPasswordSend);
        ivCloseChnagePassword = dialogView.findViewById(R.id.ivCloseChangePassword);


        btnPasswordSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPasswordValidations()) {

                    updatePassword();
                }
            }
        });

        ivCloseChnagePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordAlertDialog.dismiss();
            }
        });

        passwordAlertDialog = dialogBuilder.create();
        passwordAlertDialog.setCanceledOnTouchOutside(false);
        passwordAlertDialog.show();
    }


    private void updatePassword() {


        HashMap<String, String> userInfo = sessionManager.getUserDetails();

        id = userInfo.get(KEY_ID);
        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback, this);

        Map<String, String> params = new HashMap<>();
        params.put("oldp", edtOldPassword.getText().toString());
        params.put("newp", edtNewPassword.getText().toString());
        params.put("id", id);

        mVolleyService.postDataVolleyParameters(CHANGE_PASSWORD, this.getResources().getString(R.string.BASE_URL) + updatePassword, params);
    }

    private void updateProfile() {

        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback, this);

        Map<String, String> params = new HashMap<>();
        params.put("name", edtName.getText().toString());
        params.put("email", edtEmail.getText().toString());
        params.put("contact", edtContact.getText().toString());
        params.put("id", id);
        //params.put("send", "Send");

        mVolleyService.postDataVolleyParameters(UPDATE_PROFILE, this.getResources().getString(R.string.BASE_URL) + updateProfile, params);
    }

    private void initVolleyCallback() {

        progressDialog = ProgressDialog.show(AccountActivity.this, "Please Wait", null, false, true);
        progressDialog.show();

        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(int requestId, String response) {
                JSONObject jsonObj = null;

                switch (requestId) {

                    case UPDATE_PROFILE:
                        try {

                            progressDialog.dismiss();
                            jsonObj = new JSONObject(response);
                            int status = jsonObj.getInt("status");

                            if (status == 1) {

                                profileAlertDialog.dismiss();

                                sessionManager.removeLogin();

                                sessionManager.createLoginSession(id,cId,magnetId,agentId,agentName,edtName.getText().toString()
                                        ,edtEmail.getText().toString(),edtContact.getText().toString(),
                                        comment,address,streetId,street,
                                        areaId,area,city,state,pincode,user_status,name,password,created_at,updated_at);

                                dialogUpdateProfile();

                            }
                        } catch (Exception e) {
                            progressDialog.dismiss();
                            Log.v("Update_profile_dialog", e.toString());
                        }

                        break;

                    case CHANGE_PASSWORD:
                        try {

                            progressDialog.dismiss();

                            jsonObj = new JSONObject(response);
                            int status = jsonObj.getInt("status");


                            if (status == 1) {

                                passwordAlertDialog.dismiss();
                                dialogUpdatePassword();

                            } else if (status == 0) {
                                Toast.makeText(AccountActivity.this, "Old password does not match.", Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception e) {
                            Log.v("Update_profile_dialog", e.toString());
                        }
                        break;


                }
            }

            @Override
            public void notifyError(int requestId, VolleyError error) {
                System.out.println(error);
                //  imgNoDataFound.setVisibility(View.VISIBLE);
                Log.v("Volley requestid", String.valueOf(requestId));
                Log.v("Volley Error", String.valueOf(error));
            }
        };
    }

    private void dialogUpdateProfile() {


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Profile has been updated successfully")

                .setCancelable(true)

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        getData();
                        alertDialog.dismiss();

                    }
                });

        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));

    }


    private void dialogUpdatePassword() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Password has been updated successfully")

                .setCancelable(true)

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        finish();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(alert.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));

    }


    //Edit Profile Dialog
    private void editProfileDailog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.edit_profile_dialog, null);
        dialogBuilder.setView(dialogView);

        edtName = dialogView.findViewById(R.id.edtName);
        edtEmail = dialogView.findViewById(R.id.edtMail);
        edtContact = dialogView.findViewById(R.id.edtContact);
        ivCloseEditProfile = dialogView.findViewById(R.id.ivCloseEditProfile);
        btnUpdate = dialogView.findViewById(R.id.btnUpdate);

        edtName.setText(tvUserName.getText());
        edtEmail.setText(tvUserMail.getText());
        edtContact.setText(tvUserContact.getText());


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValid()) {

                    updateProfile();
                }
            }
        });

        ivCloseEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileAlertDialog.dismiss();
            }
        });

        profileAlertDialog = dialogBuilder.create();
        profileAlertDialog.setCanceledOnTouchOutside(false);
        profileAlertDialog.show();
    }

    //setup toolbar
    private void setToolbar() {
        mToolbar.setTitle("Account");
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

    private boolean checkPasswordValidations() {

        if (TextUtils.isEmpty(edtOldPassword.getText().toString())) {
            edtOldPassword.setError("Please enter old password");
            return false;
        } else if (edtOldPassword.getText().toString().length() < 6) {
            edtOldPassword.setError("Old Password must be atleast 6 characters");
            return false;
        } else if (TextUtils.isEmpty(edtNewPassword.getText().toString())) {
            edtNewPassword.setError("Please enter new password");
            return false;
        } else if (edtNewPassword.getText().toString().length() < 6) {
            edtNewPassword.setError(" New password must be atleast 6 characters");
            return false;
        }
        return true;
    }

    //Update Profile Validation
    public boolean checkValid() {


        final String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        String contactPattern = "^[6-9][0-9]{9}$";

        if (edtName.getText().toString().equalsIgnoreCase("")) {
            edtName.setError("Please enter name");
//            toastClass.makeToast(this, "Please enter your good name");
            return false;
        } else if (edtEmail.getText().toString().equalsIgnoreCase("")) {
            edtEmail.setError("Please enter email id");
//            toastClass.makeToast(this, "Please enter email id");
            return false;
        } else if (!edtEmail.getText().toString().matches(emailPattern)) {
            edtEmail.setError("Please enter valid email id");
//            toastClass.makeToast(this, "Please enter valid email id");
            return false;
        } else if (edtContact.getText().toString().equalsIgnoreCase("")) {
            edtContact.setError("Please enter mobile number");
//            toastClass.makeToast(this, "Please enter mobile number");
            return false;
        } else if (!edtContact.getText().toString().matches(contactPattern)) {
            edtContact.setError("Please enter valid mobile number");
//            toastClass.makeToast(this, "Please enter valid mobile number");
            return false;
        }
        return true;
    }

    private void init() {

        mToolbar = findViewById(R.id.toolbarAccount);
        // toastClass = new ToastClass();

        ivEditProfile = findViewById(R.id.ivEditProfile);
        tvUserUserID = findViewById(R.id.tvUserUserID);
        tvUserName = findViewById(R.id.tvUserName);
        tvUserMail = findViewById(R.id.tvUserMail);
        tvUserContact = findViewById(R.id.tvUserContact);
        tvUserStreet = findViewById(R.id.tvUserStreet);
        tvUserCity = findViewById(R.id.tvUserCity);
        tvUserArea = findViewById(R.id.tvUserArea);
        tvUserAddress = findViewById(R.id.tvUserAddress);
        tvUserPincode = findViewById(R.id.tvUserPincode);
        tvAgentName = findViewById(R.id.tvAgentName);
        tvAgentContact = findViewById(R.id.tvAgentContact);
        cardChangePassword = findViewById(R.id.cardChangePassword);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivEditProfile:
                editProfileDailog();
                break;
            case R.id.cardChangePassword:
                changePasswordDialog();
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

}
