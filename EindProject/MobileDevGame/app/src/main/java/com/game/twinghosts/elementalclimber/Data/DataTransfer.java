package com.game.twinghosts.elementalclimber.Data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataTransfer {

    public static final String BASE_URL = "https://spreadsheets.google.com/tq?tq="; // Use at the start of the url
    public static final String BASE_KEY = "&key=19J040b8Nv_vfoy5z5sU6Im9brNgYqT0-ZYQc-IVtikc"; // Use at the end of the url

    public static final String ALL_SCORES_DESC = "select*order+by+B+desc"; // Tried other sorting methods, didn't work for Day and Month

    public static final String WEB_APP_URL = "https://script.google.com/macros/s/AKfycbxH2Z-Njk4YGn1XmXQQxIekdkvncPVpxtZZOgLWZ-mFKLf0aAo/exec"; // Google AppScript url

    public static final int SORTING_ALL = 0;
    public static final int SORTING_MONTH = 1;
    public static final int SORTING_TODAY = 2;

    /**
     * Method where data is transferred from the app to the google sheet by using HTTP Rest API calls
     */
    public static void addScoreToSheet(final Context context, final HiScore hiScore) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WEB_APP_URL,
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

    public static ArrayList<HiScore> sortHiScore(ArrayList<HiScore> scores, int sortMode){
        // Just return everything as is
        if(sortMode == SORTING_ALL) return scores;

        // Sort by current month
        CustomDate currentDate = new CustomDate();
        if(sortMode == SORTING_MONTH){
            for(int i = scores.size()-1; i >= 0; i--){
                HiScore score = scores.get(i);
                CustomDate scoreDate = score.getDate();
                if(currentDate.getMonth() == scoreDate.getMonth() && currentDate.getYear() == scoreDate.getYear()){
                } else scores.remove(score);
            }
        }

        // Sort by today
        if(sortMode == SORTING_TODAY){
            for(int i = scores.size()-1; i >= 0; i--){
                HiScore score = scores.get(i);
                CustomDate scoreDate = score.getDate();
                if(!currentDate.sameAs(scoreDate)){
                    scores.remove(score);
                }
            }
        }

        return scores;
    }

    public static boolean isConnectedToInternet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are connected to a network
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }
}