package com.efunhub.cabelbillingcustomer.utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import com.efunhub.cabelbillingcustomer.activity.LoginActivity;
import com.efunhub.cabelbillingcustomer.activity.MainActivity;

import java.util.HashMap;

/**
 * Created by Admin on 30-10-2017.
 */

public class SessionManager {

    // Shared Preferences
    private SharedPreferences pref;

    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    // Context
    private Context mContext;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "CableBillingPref";

    /*//Add to cart
    private static final String ADD_TO_CART = "AddToCart";*/

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // ID (make variable public to access from outside)
    public static final String KEY_ID = "id";
    public static final String KEY_C_ID = "c_id";
    public static final String KEY_MAGNET_ID = "magnet_id";
    public static final String KEY__AGENT_ID = "agent_id";
    public static final String KEY_AGENT_NAME = "agent_name";
    public static final String KEY_AGENT_EMAIL = "agent_gmail";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_CONTACT = "contact";
    public static final String KEY_COMMENT = "comment";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_STREET_ID = "strretid";
    public static final String KEY_STREET = "street";
    public static final String KEY_AREA_ID = "areaid";
    public static final String KEY_AREA = "area";
    public static final String KEY_CITY = "city";
    public static final String KEY_STATE = "state";
    public static final String KEY_PINCODE = "pincode";
    public static final String KEY_STATUS = "status";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_CREATED_AT = "created";
    public static final String KEY_UPDATED_AT = "updated";

    //FCM Token
    public static final String KEY_FCM_TOKEN = "token";


    // Constructor
    public SessionManager(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

//    Create login session
    public void createLoginSession(String id,String cId,String magnetId,String agentId,String agentName,String name,
                                   String email,String contact,String comment,String address,String streetId,String street,
                                   String areaId,String area,String city,String state,String pincode,String status,
                                   String userName,String password,String created_at,String updated_at) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing user data in pref
        editor.putString(KEY_ID, id);
        editor.putString(KEY_C_ID, cId);
        editor.putString(KEY_MAGNET_ID, magnetId);
        editor.putString(KEY__AGENT_ID, agentId);
        editor.putString(KEY_AGENT_NAME, agentName);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_CONTACT, contact);
        editor.putString(KEY_COMMENT, comment);
        editor.putString(KEY_ADDRESS, address);
        editor.putString(KEY_STREET_ID, streetId);
        editor.putString(KEY_STREET, street);
        editor.putString(KEY_AREA_ID, areaId);
        editor.putString(KEY_AREA, area);
        editor.putString(KEY_CITY, city);
        editor.putString(KEY_STATE, state);
        editor.putString(KEY_PINCODE, pincode);
        editor.putString(KEY_STATUS, status);
        editor.putString(KEY_USERNAME, userName);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_CREATED_AT, created_at);
        editor.putString(KEY_UPDATED_AT, updated_at);

        // commit changes
        editor.commit();
    }


    public HashMap<String, String> getUserDetails() {

        HashMap<String, String> user = new HashMap<String, String>();
        // user info
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_C_ID, pref.getString(KEY_C_ID, null));
        user.put(KEY_MAGNET_ID, pref.getString(KEY_MAGNET_ID, null));
        user.put(KEY__AGENT_ID, pref.getString(KEY__AGENT_ID, null));
        user.put(KEY_AGENT_NAME, pref.getString(KEY_AGENT_NAME, null));
        user.put(KEY_AGENT_EMAIL, pref.getString(KEY_AGENT_EMAIL, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_CONTACT, pref.getString(KEY_CONTACT, null));
        user.put(KEY_COMMENT, pref.getString(KEY_COMMENT, null));
        user.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS, null));
        user.put(KEY_STREET_ID, pref.getString(KEY_STREET_ID, null));
        user.put(KEY_STREET, pref.getString(KEY_STREET, null));
        user.put(KEY_AREA_ID, pref.getString(KEY_AREA_ID, null));
        user.put(KEY_AREA, pref.getString(KEY_AREA, null));
        user.put(KEY_CITY, pref.getString(KEY_CITY, null));
        user.put(KEY_STATE, pref.getString(KEY_STATE, null));
        user.put(KEY_PINCODE, pref.getString(KEY_PINCODE, null));
        user.put(KEY_STATUS, pref.getString(KEY_STATUS, null));
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_CREATED_AT, pref.getString(KEY_CREATED_AT, null));
        user.put(KEY_UPDATED_AT, pref.getString(KEY_UPDATED_AT, null));

        return user;
    }
    public void storeRegIdInPref(String token) {
        editor.putString(KEY_FCM_TOKEN, token);
        editor.commit();
    }

    public HashMap<String, String> getRegIdPref() {
        HashMap<String, String> token = new HashMap<String, String>();

        // token
        token.put(KEY_FCM_TOKEN, pref.getString(KEY_FCM_TOKEN, null));

        return token;
    }


    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (this.isLoggedIn()) {

            // user is logged in redirect him to Main Activity
            Intent i = new Intent(mContext, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            mContext.startActivity(i);

        } else {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(mContext, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            mContext.startActivity(i);
        }
    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getAdminDetails() {
        HashMap<String, String> user = new HashMap<String, String>();

        // user ID
        user.put(KEY_ID, pref.getString(KEY_ID, null));

        return user;
    }

    /**
     * Clear session details
     */
    public void logoutAdmin() {
        // Clearing all data from Shared Preferences


        editor.remove(KEY_ID);
        editor.remove(IS_LOGIN);
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(mContext, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        mContext.startActivity(i);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void removeLogin(){

        editor.putBoolean(IS_LOGIN, false);

        editor.remove(KEY_ID);
        editor.remove(KEY_C_ID);
        editor.remove(KEY_MAGNET_ID);
        editor.remove(KEY__AGENT_ID);
        editor.remove(KEY_AGENT_NAME);
        editor.remove(KEY_NAME);
        editor.remove(KEY_EMAIL);
        editor.remove(KEY_CONTACT);
        editor.remove(KEY_COMMENT);
        editor.remove(KEY_ADDRESS);
        editor.remove(KEY_STREET_ID);
        editor.remove(KEY_STREET);
        editor.remove(KEY_AREA_ID);
        editor.remove(KEY_AREA);
        editor.remove(KEY_CITY);
        editor.remove(KEY_STATE);
        editor.remove(KEY_PINCODE);
        editor.remove(KEY_STATUS);
        editor.remove(KEY_USERNAME);
        editor.remove(KEY_PASSWORD);
        editor.remove(KEY_CREATED_AT);
        editor.remove(KEY_UPDATED_AT);

        editor.commit();


    }

    public void logoutUser() {

        editor.putBoolean(IS_LOGIN, false);

        editor.remove(KEY_ID);
        editor.remove(KEY_C_ID);
        editor.remove(KEY_MAGNET_ID);
        editor.remove(KEY__AGENT_ID);
        editor.remove(KEY_AGENT_NAME);
        editor.remove(KEY_NAME);
        editor.remove(KEY_EMAIL);
        editor.remove(KEY_CONTACT);
        editor.remove(KEY_COMMENT);
        editor.remove(KEY_ADDRESS);
        editor.remove(KEY_STREET_ID);
        editor.remove(KEY_STREET);
        editor.remove(KEY_AREA_ID);
        editor.remove(KEY_AREA);
        editor.remove(KEY_CITY);
        editor.remove(KEY_STATE);
        editor.remove(KEY_PINCODE);
        editor.remove(KEY_STATUS);
        editor.remove(KEY_USERNAME);
        editor.remove(KEY_PASSWORD);
        editor.remove(KEY_CREATED_AT);
        editor.remove(KEY_UPDATED_AT);

        editor.commit();



        // Clearing all data from Shared Preferences
        // After logout redirect user to Login Activity
        Intent i = new Intent(mContext, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        mContext.startActivity(i);

    }
}
