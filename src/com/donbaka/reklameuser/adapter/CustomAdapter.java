package com.donbaka.reklameuser.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.donbaka.reklameuser.R;
import com.donbaka.reklameuser.model.SimpleReklame;
import com.nusaraya.helper.StringHelper;

public class CustomAdapter extends BaseAdapter{
	
	private static final String TAG = CustomAdapter.class.getSimpleName();
	private Context context; 
	private LayoutInflater inflater; 
	private List<SimpleReklame> reklameItems; 
	private List<SimpleReklame> reklamePlacholder;
	
	public CustomAdapter(Context context, List<SimpleReklame> reklameItems) {
		this.context = context;
		this.reklameItems = reklameItems; 
		
		this.reklamePlacholder = new ArrayList<SimpleReklame>(); 
		reklamePlacholder.addAll(reklameItems);
	}
	
	
	@Override
	public int getCount() {
		return reklameItems.size();
	}

	@Override
	public Object getItem(int position) {
		return reklameItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(inflater == null){
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.list_row, null);
		}

		TextView textTitle = (TextView) convertView.findViewById(R.id.title); 
		TextView textAddress = (TextView) convertView.findViewById(R.id.alamat); 
		TextView textNoForm = (TextView) convertView.findViewById(R.id.no_form); 
		
		SimpleReklame reklame = reklameItems.get(position); 
		textTitle.setText(StringHelper.toCapitalize(reklame.getTitle()));
		textAddress.setText(StringHelper.toCapitalize(reklame.getAlamat()));
		textNoForm.setText(reklame.getNomorForm());
		
		return convertView;
	}
	
	// filter method 
	public void filter(String text){
		text = text.toLowerCase(Locale.getDefault());
		Log.d(TAG, "filter list with params "+text);
		reklameItems.clear();
		if(text.length()==0){
			reklameItems.addAll(reklamePlacholder); 
		}else{
			for (SimpleReklame reklame : reklamePlacholder) {
				boolean isSelected = reklame.getTitle().toLowerCase(Locale.getDefault()).contains(text) || 
									 reklame.getAlamat().toLowerCase(Locale.getDefault()).contains(text) ||
									 reklame.getNomorForm().toLowerCase(Locale.getDefault()).contains(text);
				if(isSelected){
					reklameItems.add(reklame);
				}
			}
		}
	}

}
