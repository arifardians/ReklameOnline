package com.nusaraya.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.pedant.SweetAlert.SweetAlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.donbaka.reklameuser.R;
import com.donbaka.reklameuser.adapter.CustomAdapter;
import com.donbaka.reklameuser.app.AppConfig;
import com.donbaka.reklameuser.app.AppController;
import com.donbaka.reklameuser.helper.SessionManager;
import com.donbaka.reklameuser.model.Model;
import com.donbaka.reklameuser.model.Reklame;
import com.donbaka.reklameuser.model.SimpleReklame;
import com.nusaraya.activity.DetailReklameActivity;
import com.nusaraya.activity.HomeFragmentActivity;
import com.nusaraya.dbhelper.SimpleReklameDAO;
import com.nusaraya.helper.CustomHttpClient;

public class ReklameHomeFragment extends Fragment {
	private static final String TAG = ReklameHomeFragment.class.getSimpleName();
	
	private Context context; 
	private CustomAdapter adapter;
	private List<SimpleReklame> reklameItems;
	private ListView list;
	private Button btnNext; 
	private Button btnPrev; 
	private TextView textPage; 
	
	private int offset = SimpleReklameDAO.DEFAULT_OFFSET;
	private static final int LIMIT = SimpleReklameDAO.DEFAULT_LIMIT; 
	private int totalRecord; 
	
	private SweetAlertDialog loadingDialog;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = getHomeView(inflater, container);
		
		return rootView;
	}
	
	@Override
	public void onAttach(Activity activity) {
		this.context = activity;
		super.onAttach(activity);
	}
	
	// get view for tab home
	private View getHomeView(LayoutInflater inflater, ViewGroup container){
		View result = inflater.inflate(R.layout.fragment_home, container, false); 
		list		= (ListView) result.findViewById(R.id.fragment_home_list);
		btnNext 	= (Button) result.findViewById(R.id.fragment_home_btn_next); 
		btnPrev		= (Button) result.findViewById(R.id.fragment_home_btn_prev);
		textPage	= (TextView) result.findViewById(R.id.fragment_home_text_page);
		
		updateList(LIMIT, offset);
		
		btnNext.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(offset + LIMIT <= totalRecord){
					offset += LIMIT;
					updateList(LIMIT, offset);
				}else
					Toast.makeText(context, "This is the last page!", Toast.LENGTH_SHORT).show();
			}
		});
		
		
		btnPrev.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(offset - LIMIT >= 0){
					offset -= LIMIT;
					updateList(LIMIT, offset);
				}else
					Toast.makeText(context, "This is the first page!", Toast.LENGTH_SHORT).show();
				
			}
		});
		
		return result;
		
	}
	
	private void updateList(int limit, int offset){
		Log.d(TAG, "context : "+context);
		
		SessionManager session = new SessionManager(context);
		
		SimpleReklameDAO reklameDAO = new SimpleReklameDAO(context); 
		ArrayList<Model> listData = null;
		totalRecord = reklameDAO.getTotalRecord();
		reklameItems = new ArrayList<SimpleReklame>(); 
		SimpleReklame reklame = null; 
		
		
		if(totalRecord > 0){
			Log.d(TAG, "total records : " + totalRecord);
			listData = reklameDAO.fetch(limit, offset);
			Log.d(TAG, "list data size : "+ listData.size()+", limit : "+ limit + ", offset : "+ offset);
			for (Model model : listData) {
				reklame = (SimpleReklame) model; 
				reklameItems.add(reklame);
			}
			setListAdapter(reklameItems);
			textPage.setText((offset+1) + " - "+(offset+LIMIT) + " from "+totalRecord);
		}else{
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>(); 
			params.add(new BasicNameValuePair("key", session.getKey()));
			Thread thread = new Thread(new SyncThread(AppConfig.URL_LIST, params));
			thread.start();
		}
		reklameDAO.close();
	}
	
	private void setListAdapter(List<SimpleReklame> items){
		Log.d(TAG, "Set list adapter with items total : "+items.size());
		list.invalidate();
		adapter = new CustomAdapter(context, items);
		adapter.notifyDataSetChanged();
		
		Log.d(TAG, "list "+list.toString());
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	Intent intent = new Intent(context, DetailReklameActivity.class);
            	intent.putExtra("reklameId", ""+reklameItems.get(position).getIdReklameInti());
            	startActivity(intent);
            }
        });
	}
	 
	private List<SimpleReklame> sendRequest(final String key) {
    	final List<SimpleReklame> result = new ArrayList<SimpleReklame>();

		loadingDialog = new SweetAlertDialog(HomeFragmentActivity.ACTIVITY, SweetAlertDialog.PROGRESS_TYPE); 
		loadingDialog.setTitleText("Loading..!"); 
		loadingDialog.setContentText("please wait..!"); 
    	loadingDialog.show();
//    	Toast.makeText(context, "Loading data", Toast.LENGTH_SHORT).show();
    	
        StringRequest req = new StringRequest(
                Request.Method.POST,
                AppConfig.URL_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response);
                        try {

                            JSONArray jsonarr = (JSONArray) new JSONTokener(response).nextValue();
                            Log.d(TAG, "JSON: " + jsonarr);
                            Log.d(TAG, "lenght: "+jsonarr.length());
                            
                            SimpleReklameDAO reklameDAO = new SimpleReklameDAO(context);
                            SimpleReklame reklame = null; 
                            
                            for(int i = 0;i< jsonarr.getJSONArray(0).length();i++) {
                                JSONObject jsonobj = jsonarr.getJSONArray(0).getJSONObject(i);
                                reklame = new SimpleReklame();
                                reklame.setTitle(jsonobj.getString("title"));
                                reklame.setAlamat(jsonobj.getString("alamat"));
                                reklame.setNomorForm(jsonobj.getString("nomorForm"));
                                reklame.setIdReklameInti(jsonobj.getInt("ID_REKLAME_INTI"));
                                reklame.setLatitude(jsonobj.getDouble("LAT"));
                                reklame.setLongitude(jsonobj.getDouble("LNG"));
                                reklameDAO.insert(reklame);
                                result.add(reklame);
                            }
                            
                            reklameDAO.close();
                            loadingDialog.dismiss();
                        } catch (JSONException e) {
                            Log.d(TAG, "JSONException OnResponse: " + e.getMessage());
                            tMessage(e.getMessage());
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tMessage("Koneksi bermasalah!");
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
        // Creating volley request obj
        return result;

    }
	
	class SyncThread implements Runnable{
		private String url; 
		private ArrayList<NameValuePair> params; 
		
		public SyncThread(String url, ArrayList<NameValuePair> params) {
			this.url = url; 
			this.params = params;
		}
		@Override
		public void run() {
			((Activity) context).runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					loadingDialog = new SweetAlertDialog(HomeFragmentActivity.ACTIVITY, SweetAlertDialog.PROGRESS_TYPE); 
					loadingDialog.setTitleText("Loading..!"); 
					loadingDialog.setContentText("please wait..!"); 
			    	loadingDialog.show();
					
				}
			});
			
			processSyncThread(url, params);
		}
	}
	
	private void processSyncThread(String url, ArrayList<NameValuePair> params){
		String response = null;
		try {
			 response = CustomHttpClient.executeHttpPost(url, params);
			 Log.d(TAG, "Response: " + response);
			 
			 JSONArray jsonArr = new JSONArray(response);
			 
			 Log.d(TAG, "JSON: " + jsonArr);
             Log.d(TAG, "lenght: "+jsonArr.length());
             
             SimpleReklameDAO reklameDAO = new SimpleReklameDAO(context);
             SimpleReklame reklame = null; 
             JSONObject jsonObj = null; 
             
             for(int i = 0;i< jsonArr.getJSONArray(0).length();i++) {
                 jsonObj = jsonArr.getJSONArray(0).getJSONObject(i);
                 reklame = new SimpleReklame();
                 reklame.setTitle(jsonObj.getString("title"));
                 reklame.setAlamat(jsonObj.getString("alamat"));
                 reklame.setNomorForm(jsonObj.getString("nomorForm"));
                 reklame.setIdReklameInti(jsonObj.getInt("ID_REKLAME_INTI"));
                 reklame.setLatitude(jsonObj.getDouble("LAT"));
                 reklame.setLongitude(jsonObj.getDouble("LNG"));
                 reklameDAO.insert(reklame);
             }
			 reklameDAO.close();
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			((Activity) context).runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					loadingDialog.dismiss();
					SimpleReklameDAO reklameDAO = new SimpleReklameDAO(context); 
					int totalRecord = reklameDAO.getTotalRecord();
					reklameDAO.close();
					if(totalRecord > 0){
						updateList(SimpleReklameDAO.DEFAULT_LIMIT, SimpleReklameDAO.DEFAULT_OFFSET);
					}else{
						Toast.makeText(context, "Sinkronisasi gagal!", Toast.LENGTH_SHORT).show();
					}
					
				}
			});
		}
	}
	
	 private void tMessage(String message) {
	        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	 }
}
