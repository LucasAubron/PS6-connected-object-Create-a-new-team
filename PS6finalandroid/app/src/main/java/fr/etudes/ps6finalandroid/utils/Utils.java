package fr.etudes.ps6finalandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Utils {


    private static String URL = "http://192.168.0.10:3000/api/clients/";
    private static RequestQueue rq = null;
    private static int res;

    private Utils() {
    }

    public static void get(final int idList, final Context context, final ServerCallBack cb){
        JsonArrayRequest request=new JsonArrayRequest(
                Request.Method.GET,
                URL + "l" + idList,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        cb.onSuccess(response, null);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        error.printStackTrace();
                    }
                }
        );
        if (rq==null) {
            rq = Volley.newRequestQueue(context);
        }
        rq.add(request);
    }

    public static void post(final int idList, Context context, String data) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL + "l" + idList,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        if (rq == null) {
            rq = Volley.newRequestQueue(context);
        }
        rq.add(request);
    }
}
