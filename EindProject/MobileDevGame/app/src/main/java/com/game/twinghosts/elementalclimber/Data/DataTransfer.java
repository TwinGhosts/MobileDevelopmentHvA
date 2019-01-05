package com.game.twinghosts.elementalclimber.Data;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.game.twinghosts.elementalclimber.Activities.HighScoreActivity;
import com.game.twinghosts.elementalclimber.R;

import java.util.HashMap;
import java.util.Map;

public class DataTransfer {

    /**
     * Method where data is transferred from the app to the google sheet by using HTTP Rest API calls
     */
    public static void addScoreToSheet(final Context context, final HiScore hiScore) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HighScoreActivity.WEB_APP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, R.string.toast_added_hi_score, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                //here we pass params
                params.put("name", hiScore.getName());
                params.put("score", "" + hiScore.getScore());

                return params;
            }
        };

        int socketTimeOut = 10000;

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }
}