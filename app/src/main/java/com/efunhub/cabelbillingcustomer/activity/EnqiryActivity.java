package com.efunhub.cabelbillingcustomer.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.efunhub.cabelbillingcustomer.Interfaces.IResult;
import com.efunhub.cabelbillingcustomer.R;
import com.efunhub.cabelbillingcustomer.model.CurrentPalanBaseModel;
import com.efunhub.cabelbillingcustomer.model.CurrentPlan;
import com.efunhub.cabelbillingcustomer.utility.CheckConnectivity;
import com.efunhub.cabelbillingcustomer.utility.SessionManager;
import com.efunhub.cabelbillingcustomer.utility.VolleyService;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.efunhub.cabelbillingcustomer.utility.ConstantVariables.CURRENT_PLAN;
import static com.efunhub.cabelbillingcustomer.utility.ConstantVariables.ENQIRY;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_C_ID;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY_MAGNET_ID;
import static com.efunhub.cabelbillingcustomer.utility.SessionManager.KEY__AGENT_ID;

public class EnqiryActivity extends BottomSheetDialogFragment {

    View view;

    //volley api fetching
    private IResult mResultCallback;
    private VolleyService mVolleyService;
    String enquiryURL = "customer-enquiry";
    SessionManager sessionManager;
    ProgressDialog progressDialog;
    CheckConnectivity checkConnectivity;

    EditText edtMessage;
    Button btnSendMessage;
    LinearLayout linearLayout ;

    AlertDialog  alertDialog ;

    public static EnqiryActivity newInstance() {
        return new EnqiryActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.enquiry_dialog, container, false);

        edtMessage = view.findViewById(R.id.edtMessage);
        btnSendMessage = view.findViewById(R.id.btnSendMessage);
        linearLayout = view.findViewById(R.id.linearLayout);


        sessionManager = new SessionManager(getActivity());

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validations()) {
                    sendEnquiry();
                }
            }
        });
        return view;

    }

    private void sendEnquiry() {

        progressDialog = ProgressDialog.show(getActivity(), "Please Wait", null, false, true);
        initVolleyCallback();

        HashMap<String, String> user = sessionManager.getUserDetails();

        String id = user.get(KEY_C_ID);
        String m_agent_id = user.get(KEY_MAGNET_ID);
        String agent_id = user.get(KEY__AGENT_ID);

        mVolleyService = new VolleyService(mResultCallback, getActivity());

        Map<String, String> params = new HashMap<>();

        params.put("cid", id);
        params.put("magent_id", m_agent_id);
        params.put("agent_id", agent_id);
        params.put("message", edtMessage.getText().toString());

        String url = getResources().getString(R.string.BASE_URL) + enquiryURL;

        mVolleyService.postDataVolleyParameters(ENQIRY, url, params);

    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(int requestId, String response) {
                JSONObject jsonObj = null;

                switch (requestId) {
                    case ENQIRY:
                        try {
                            jsonObj = new JSONObject(response);
                            int status = jsonObj.getInt("status");


                            if (status == 1) {
                                linearLayout.setVisibility(View.GONE);
                                dialogMessageSend();
                            }
                            progressDialog.dismiss();
                        } catch (Exception e) {
                            progressDialog.dismiss();
                            Log.v("Update_profile_dialog", e.toString());
                        }
                        break;

                }
            }

            @Override
            public void notifyError(int requestId, VolleyError error) {
                //  showDialog(error.toString());
                Log.v("Volley requestid", String.valueOf(requestId));
                Log.v("Volley Error", String.valueOf(error));
                progressDialog.dismiss();
            }
        };
    }

    private void dialogMessageSend() {


        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());

        builder.setMessage("Message has been send successfully ")

                .setCancelable(true)

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        alertDialog.dismiss();
                        startActivity(new Intent(getActivity(),MainActivity.class));

                    }
                });

        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));

    }

    private boolean validations() {

        if (edtMessage.getText().toString().trim().equals("")) {
            edtMessage.setError("Please enter message.");
            return false;
        }
        return true;
    }


}
