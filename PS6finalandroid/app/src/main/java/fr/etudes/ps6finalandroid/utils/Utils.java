package fr.etudes.ps6finalandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public final class Utils {
    private static String URL = "http://192.168.0.10:3000/api/clients/";
    private Utils() {
    }

    public static ArrayList<Integer> getList(int idList, final Context context){
        final ArrayList res = new ArrayList();
        if (idList<1 || idList>4) throw new RuntimeException("l'id de liste demandé est erroné");
        JsonArrayRequest arr=new JsonArrayRequest(
                Request.Method.GET,
                URL + idList,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i=0;i<response.length();i++){
                                JSONObject client = response.getJSONObject(i);
                                Integer phoneId = client.getInt("phoneId");
                                res.add(phoneId);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                    }
                }
        );
        RequestQueue rq= Volley.newRequestQueue(context);
        rq.add(arr);
        return res;
    }

    public static int getPosInList(int idList, final Context context){
        if (idList<1 || idList>4) throw new RuntimeException("l'id de liste demandé est erroné");
        final ArrayList<Integer> res = new ArrayList();
        res.add(-1);
        JsonArrayRequest arr=new JsonArrayRequest(
                Request.Method.GET,
                URL + idList,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int id = Integer.parseInt(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
                        try{
                            for(int i=0;i<response.length();i++){
                                JSONObject client = response.getJSONObject(i);
                                if (id == client.getInt("phoneId")){
                                    res.add(i+1);
                                    break;
                                }
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                    }
                }
        );
        RequestQueue rq= Volley.newRequestQueue(context);
        rq.add(arr);
        return res.get(res.size()-1);
    }
}
