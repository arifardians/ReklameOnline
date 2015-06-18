package com.nusaraya.services;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.donbaka.reklameuser.app.AppConfig;
import com.donbaka.reklameuser.app.AppController;
import com.donbaka.reklameuser.helper.SessionManager;
import com.donbaka.reklameuser.model.SimpleReklame;
import com.nusaraya.dbhelper.SimpleReklameDAO;

public class SynchronizerService extends IntentService{
	
	private static final String TAG = SynchronizerService.class.getSimpleName();
	private String key; 
	
	public SynchronizerService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		SessionManager session = new SessionManager(getApplicationContext());
		key = session.getKey(); 
		Log.d(TAG, "updated data reklame");
		sendUpdateRequest();
		SynchronizerReceiver.completeWakefulIntent(intent);
	}
	
	private void sendUpdateRequest(){
		StringRequest req = new StringRequest(
                Request.Method.POST,
                AppConfig.URL_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response);
                        try {

                            JSONArray jsonArr = (JSONArray) new JSONTokener(response).nextValue();
                            Log.d(TAG, "JSON: " + jsonArr);
                            Log.d(TAG, "lenght: "+jsonArr.length());
                            
                            SimpleReklame reklame = null; 
                            JSONObject jsonObj = null; 
                            
                            SimpleReklameDAO reklameDAO = new SimpleReklameDAO(getApplicationContext());
                            reklameDAO.deleteAll();
                            
                            for(int i = 0;i< jsonArr.getJSONArray(0).length();i++) {
                                jsonObj = jsonArr.getJSONArray(0).getJSONObject(i);
                                reklame = new SimpleReklame(); 
                                reklame.setIdReklameInti(jsonObj.getInt("ID_REKLAME_INTI"));
                                reklame.setTitle(jsonObj.getString("title"));
                                reklame.setAlamat(jsonObj.getString("alamat"));
                                reklame.setNomorForm(jsonObj.getString("nomorForm"));
                                reklame.setLatitude(jsonObj.getDouble("LAT"));
                                reklame.setLongitude(jsonObj.getDouble("LNG"));
                                
                                reklameDAO.insert(reklame);
                            }
                            
                            reklameDAO.close();
                            
                        } catch (JSONException e) {
                            Log.d(TAG, "JSONException OnResponse: " + e.getMessage());
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error.getMessage());
                        error.printStackTrace();
                    }
                }
        ) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", key);
                return params;
            }
        };

        // add the request object to the queue to be executed
        AppController.getInstance().addToRequestQueue(req);
	}

}
