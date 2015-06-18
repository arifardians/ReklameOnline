package com.donbaka.reklameuser.adapter;

import com.donbaka.reklameuser.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyTableRow {
	private String strLeft; 
	private String strRight;
	
	private Context context; 
	private LayoutInflater inflater; 
	private LinearLayout container; 
	private RelativeLayout result; 
	private TextView textLeft; 
	private TextView textRight; 
	private int backgroundColor; 
	private boolean isNumericFormat;
	
	public MyTableRow(Context context, LinearLayout container) {
		this.context 	= context; 
		this.container 	= container; 
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		result = (RelativeLayout) inflater.inflate(R.layout.table_row, null);
		textLeft = (TextView) result.findViewById(R.id.table_row_left_item);
		textRight = (TextView) result.findViewById(R.id.table_row_right_item);
		
		backgroundColor = android.R.color.white; 
	}
	
	public String getStrLeft() {
		return strLeft;
	}
	
	public void setStrLeft(String strLeft) {
		this.strLeft = strLeft;
	}
	
	public String getStrRight() {
		return strRight;
	}
	
	public void setStrRight(String strRight) {
		this.strRight = strRight;
	}
	
	public int getBackgroundColor() {
		return backgroundColor;
	}
	
	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	public void setNumericFormat(boolean isNumericFormat) {
		this.isNumericFormat = isNumericFormat;
	}
	
	public boolean isNumericFormat(){
		return this.isNumericFormat;
	}
	
	public void create(){
		textLeft.setText(strLeft);
		textRight.setText(strRight);
		
		if(isNumericFormat() && !strRight.equals(" - ")){
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
													RelativeLayout.LayoutParams.WRAP_CONTENT); 
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			textRight.setLayoutParams(params);
		}
		
		result.setBackgroundColor(backgroundColor);
		container.addView(result);
	}
	
}
