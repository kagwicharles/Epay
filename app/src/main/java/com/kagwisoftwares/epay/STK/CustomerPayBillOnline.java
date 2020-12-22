package com.kagwisoftwares.epay.STK;

import android.content.ContentUris;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class CustomerPayBillOnline {
    private Context mcontext;

    public CustomerPayBillOnline(Context context) {
        this.mcontext = context;
    }

    private JsonObjectRequest request;
    private final String url = "https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest";

    public void stkPush(String currentToken, String amount) {
        RequestQueue requestQueue = SingletonRestQueue.getInstance(mcontext).getRequestQueue();
        Log.d("CURRENT_TOKEN", currentToken);
        try {
            JSONObject object = new JSONObject();
            object.put("BusinessShortCode", "174379");
            object.put("Password", "MTc0Mzc5YmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMTgwNDA5MDkzMDAy");
            object.put("Timestamp", "20180409093002");
            object.put("TransactionType", "CustomerPayBillOnline");
            object.put("Amount", amount);
            object.put("PartyA", "254712464436");
            object.put("PartyB", "174379");
            object.put("PhoneNumber", "254712464436");
            object.put("CallBackURL", "https://sandbox.safaricom.co.ke/mpesa");
            object.put("AccountReference", "KagwiTechnologies");
            object.put("TransactionDesc", "Pay matatu fare");

            request = new JsonObjectRequest(
                    POST,
                    url,
                    object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("STK_RESPONSE", response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                Log.d("STK_PUSH_RESPONSE", new String(error.networkResponse.data, "UTF-8"));
                            } catch (UnsupportedEncodingException e) {}
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("Authorization", "Bearer "+currentToken);
                    return params;
                }
            };
        } catch (JSONException e) {
            e.printStackTrace();
        }
          requestQueue.add(request);
    }
}
