package com.kagwisoftwares.epay.STK;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingletonRestQueue {

    private volatile static SingletonRestQueue instance;
    private RequestQueue requestQueue;
    private static Context mcontext;
    private SingletonRestQueue (Context context) {
        mcontext = context;
    }

    public static SingletonRestQueue getInstance(Context context) {
        if (instance == null) {
            Log.d("SINGLETON INSTANCE", "Check if is null");
            synchronized (SingletonRestQueue.class) {
                if (instance == null) {
                    instance = new SingletonRestQueue(context);
                }
            }
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mcontext);
        }
        return requestQueue;
    }
}
