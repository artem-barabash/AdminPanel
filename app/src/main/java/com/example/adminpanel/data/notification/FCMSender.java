package com.example.adminpanel.data.notification;

import android.content.Context;
import android.os.StrictMode;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FCMSender {
    private static final String BASE_URL = "https://fcm.googleapis.com/fcm/send";
    private static final String SERVER_KEY
            = "AAAAPoNvBBs:APA91bFm7SZUQ939tZG0su-V4z9OqiVmkQcHpF1Fh_-SRK0mOKQMWpCynXodN4h1zS3_OEkhltspnbX5GopeZjgKHwGAZGYaERK_7Ik-stZ-gx9KVy7py1KNJRfYPNsVD-mYDzZ0rk2S";

    public static void pushNotification(Context context, String token, double sum){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(context);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("to", token);

            JSONObject notification = new JSONObject();
            notification.put("title", "Salary");
            notification.put("body", "$" + sum);

            jsonObject.put("notification", notification);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    BASE_URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("FCM=" + response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/json");
                    params.put("Authorization", "key=" + SERVER_KEY);
                    return params;
                }
            };

            queue.add(jsonObjectRequest);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
