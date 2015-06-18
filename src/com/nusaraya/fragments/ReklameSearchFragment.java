package com.nusaraya.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.donbaka.reklameuser.R;
import com.donbaka.reklameuser.adapter.CustomAdapter;
import com.donbaka.reklameuser.model.Model;
import com.donbaka.reklameuser.model.SimpleReklame;
import com.nusaraya.activity.DetailReklameActivity;
import com.nusaraya.dbhelper.SimpleReklameDAO;

public class ReklameSearchFragment extends Fragment {
	
	private static final String TAG = ReklameSearchFragment.class.getSimpleName();
	private EditText inputText; 
	private Button btnSearch; 
	private ListView listResult; 
	private TextView textResult;
	
	private Context context; 
	private CustomAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View result = inflater.inflate(R.layout.fragment_search, container, false); 
		inputText  = (EditText) result.findViewById(R.id.fragment_search_input_text);
		btnSearch  = (Button) result.findViewById(R.id.fragment_search_btn_search); 
		textResult = (TextView) result.findViewById(R.id.fragment_search_text_result);
		listResult = (ListView) result.findViewById(R.id.fragment_search_list_result);
		
		textResult.setText("Pencarian berdasarkan nama perusahaan, alamat atau nomor form.");
		inputText.setHintTextColor(getResources().getColor(R.color.blue_text_placeholder));
		inputText.setTextColor(getResources().getColor(R.color.blue_primary));
		
		btnSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// do search process
				String str = inputText.getText().toString();
				processSearchUsingQuery(str);
				hideKeyboard();
			}
		});
		
		
		// perform on enter key board when fill edit text
		inputText.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// if the event is key-down event on the "Enter" button 
				if(event.getAction() == KeyEvent.ACTION_DOWN &&
				  (keyCode == KeyEvent.KEYCODE_ENTER)){
					// perform action enter 
					String str = inputText.getText().toString(); 
					processSearchUsingQuery(str);
					hideKeyboard();
					return true;
				}
				return false;
			}
		});
		
		//updateList();
		
		return result;
	}
	
	private void updateList(){
		/** initiate data **/
		SimpleReklameDAO reklameDAO = new SimpleReklameDAO(context);
		ArrayList<Model> allData = reklameDAO.getAll(); 
		reklameDAO.close();
		Log.d(TAG, "total record : "+ allData.size());
		List<SimpleReklame> reklameItems = new ArrayList<SimpleReklame>(); 
		SimpleReklame reklame = null; 
		for (Model model : allData) {
			reklame = (SimpleReklame) model; 
			reklameItems.add(reklame);
		}
		
		listResult.invalidate();
		adapter = new CustomAdapter(context, reklameItems);
		listResult.setAdapter(adapter);
		
		listResult.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(context, DetailReklameActivity.class);
				SimpleReklame selectedReklame = (SimpleReklame) adapter.getItem(position);
				intent.putExtra("reklameId", String.valueOf(selectedReklame.getIdReklameInti()));
				startActivity(intent);
				
			}
		});
		
	}
	
	@Override
	public void onResume() {
		//updateList();
		super.onResume();
	}
	
	private void hideKeyboard(){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE); 
		imm.hideSoftInputFromWindow(inputText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	private void processSearchUsingQuery(String text){
		listResult.invalidate();
		SimpleReklameDAO reklameDAO = new SimpleReklameDAO(context); 
		List<SimpleReklame> result = reklameDAO.findByString(text); 
		reklameDAO.close();
		
		if(result.size() > 0){
			adapter = new CustomAdapter(context, result);
			adapter.notifyDataSetChanged();
			listResult.setAdapter(adapter);
			listResult.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent intent = new Intent(context, DetailReklameActivity.class);
					SimpleReklame selectedReklame = (SimpleReklame) adapter.getItem(position);
					intent.putExtra("reklameId", String.valueOf(selectedReklame.getIdReklameInti()));
					startActivity(intent);
					
				}
			});
			textResult.setText(adapter.getCount()+" item sesuai berhasil ditemukan!");
		}else{
			textResult.setText("Tidak ditemukan item yang sesuai");
			listResult.setAdapter(null);
		}
		
	}
	
	private void processSearch(String text){
		adapter.filter(text);
		adapter.notifyDataSetChanged();
		
		if(adapter.getCount() > 0){
			textResult.setText(adapter.getCount()+" item sesuai berhasil ditemukan!");
		}else{
			textResult.setText("Tidak ditemukan item yang sesuai!");
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		this.context = activity;
		super.onAttach(activity);
	}
	
	
	
}
