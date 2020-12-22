package com.kagwisoftwares.epay.STK;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.inspector.StaticInspectionCompanionProvider;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.GET;


public class AccessToken {

    private static Context mcontext;
    public AccessToken(Context context){
        mcontext = context;
    }

    private final String url = "https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials";
    private String auth;
    private String token;

    public void requestToken(String amount) {
        CustomerPayBillOnline stkPush = new CustomerPayBillOnline(mcontext);
        RequestQueue requestQueue = SingletonRestQueue.getInstance(mcontext).getRequestQueue();
        String app_key = "ctgLDdDa4GCll2EbLh991pFAckaHGnun";
        String app_secret = "nx6ATbwbZzz7dI0e";
        String appKeySecret = app_key + ":" + app_secret;
        byte[] bytes = appKeySecret.getBytes(StandardCharsets.ISO_8859_1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            auth = new String(Base64.getEncoder().encode(bytes));
        }

        JsonObjectRequest request = new JsonObjectRequest(
                GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String res = response.toString();
                        Log.d("ACCESS_TOKEN_RESPONSE", res);
                        try {
                            JSONObject object = new JSONObject(res);
                            token = object.getString("access_token");
                            Log.d("ACCESS TOKEN: ", token);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        stkPush.stkPush(token, amount);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", "Basic "+auth);
                return params;
            }
        };
        requestQueue.add(request);
    }
}
