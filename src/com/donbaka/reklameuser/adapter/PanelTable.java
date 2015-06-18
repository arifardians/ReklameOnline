package com.donbaka.reklameuser.adapter;

import com.donbaka.reklameuser.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PanelTable {
	private Context context; 
	private LayoutInflater inflater; 
	private String title; 
	private LinearLayout rowContainer; 
	private LinearLayout container; 
	private TextView textTitle;
	private LinearLayout result; 
	
	public PanelTable(Context context, LinearLayout container) {
		this.context 	= context; 
		this.container 	= container; 
		
		inflater 	= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		result 	 	= (LinearLayout) inflater.inflate(R.layout.panel_table, null); 
		
		textTitle 		= (TextView) result.findViewById(R.id.panel_table_title);
		rowContainer 	= (LinearLayout) result.findViewById(R.id.panel_table_container_row);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public LinearLayout getRowContainer() {
		return rowContainer;
	}
	
	public void create(){
		textTitle.setText(title);
		textTitle.setTextColor(Color.WHITE);
		container.addView(result);
	}
}
