package com.nusaraya.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.pedant.SweetAlert.SweetAlertDialog;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.donbaka.reklameuser.R;
import com.donbaka.reklameuser.app.AppConfig;
import com.donbaka.reklameuser.app.AppController;
import com.donbaka.reklameuser.helper.SessionManager;
import com.nusaraya.services.SynchronizerReceiver;

public class LoginNewActivity extends Activity {
	private final static String TAG = LoginNewActivity.class.getSimpleName();
	private EditText inputUsername; 
	private EditText inputPassword; 
	private Button btnLogin; 
	
	private SweetAlertDialog loadingDialog;
	private SessionManager session; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_new);
		
		session = new SessionManager(this);
		
		inputUsername	= (EditText) findViewById(R.id.login_new_input_username);
		inputPassword	= (EditText) findViewById(R.id.login_new_input_password); 
		btnLogin		= (Button) findViewById(R.id.login_new_button_login);
		
		btnLogin.setOnClickListener(actionLogin());
		loadingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE); 
		loadingDialog.setTitleText("Loading..!"); 
		loadingDialog.setContentText("please wait..!"); 
		
		// Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginNewActivity.this, HomeFragmentActivity.class);
            startActivity(intent);
            finish();
        }
		
	
	}
	
	private OnClickListener actionLogin(){
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String username = inputUsername.getText().toString(); 
				String password = inputPassword.getText().toString();
				
				if(username.equals("") || password.equals("")){
					SweetAlertDialog alert = new SweetAlertDialog(LoginNewActivity.this, SweetAlertDialog.ERROR_TYPE); 
					alert.setTitleText("Oops..!"); 
					alert.setContentText("username dan password tidak boleh kosong!"); 
					alert.show();
				}else{
					loginProcess(username, password);
				}
				
			}
		};
	}

	private void loginProcess(final String username, final String password){
		Log.d(TAG, "username : "+username+", password: "+password); 
		loadingDialog.show();
		Log.d(TAG, "url: " + AppConfig.URL_LOGIN);
		
		// POST REQUEST 
		StringRequest request = new StringRequest(
				Method.POST, 
				AppConfig.URL_LOGIN, 
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d(TAG, "respose : "+ response); 
						String key = null; 
						JSONArray jsonArr;
						try {
							jsonArr = new JSONArray(response);
							JSONObject jsonObj = jsonArr.getJSONObject(0);
							key = jsonObj.getString("PIN"); 
							if(key.equals("null")){
								loadingDialog.hide();
								SweetAlertDialog alertWrong = new SweetAlertDialog(LoginNewActivity.this, SweetAlertDialog.ERROR_TYPE); 
								alertWrong.setTitleText("Oops..!!"); 
								alertWrong.setContentText("username or password wrong!!"); 
								alertWrong.show();
							}else{
								loadingDialog.dismiss();;
								session.setLogin(true, key);
								
								SynchronizerReceiver syncAlarm = new SynchronizerReceiver(); 
								syncAlarm.setAlarm(getApplicationContext());
								
								Intent intent = new Intent(LoginNewActivity.this, HomeFragmentActivity.class);
								startActivity(intent);
								finish();
							}
							
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							Toast.makeText(getApplicationContext(), "json error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						} 
						
						
					}
				}, 
				new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						loadingDialog.hide();
						Toast.makeText(getApplicationContext(), "erro connection : "+error, Toast.LENGTH_SHORT).show();
						Log.d(TAG, "error connection : "+error);
						
					}
				}){
					protected Map<String, String> getParams() {
						Map<String, String> params = new HashMap<String, String>();
		                params.put("username", username);
		                params.put("password", password);
		                return params;
					}
				};
				
				AppController.getInstance().addToRequestQueue(request);
	}
}
