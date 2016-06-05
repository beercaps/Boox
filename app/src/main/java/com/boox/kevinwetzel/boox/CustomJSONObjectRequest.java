package com.boox.kevinwetzel.boox;

/**
 * Created by Kevinn on 04.06.2016.
 */

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

 public class CustomJSONObjectRequest extends JsonObjectRequest {
     private static final String TAG = CustomJSONObjectRequest.class.getSimpleName();
     String oAuth;

    public CustomJSONObjectRequest(int method, String url, JSONObject jsonRequest,
                                   Response.Listener<JSONObject> listener,
                                   Response.ErrorListener errorListener, String oAuth) {
        super(method, url, jsonRequest, listener, errorListener);
        this.oAuth = oAuth;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type:","application/json");
        headers.put("Content-Length:","CONTENT_LENGTH");
        headers.put("Authorization:",oAuth);
        return headers;
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        // here you can write a custom retry policy
        return super.getRetryPolicy();
    }
}
