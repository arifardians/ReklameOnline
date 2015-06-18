package com.nusaraya.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.donbaka.reklameuser.R;
import com.donbaka.reklameuser.helper.SessionManager;
import com.donbaka.reklameuser.model.Model;
import com.donbaka.reklameuser.model.SimpleReklame;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nusaraya.activity.DetailReklameActivity;
import com.nusaraya.dbhelper.SimpleReklameDAO;
import com.nusaraya.helper.StringHelper;

public class ReklameMapFragment extends Fragment{
	private static final String TAG = ReklameMapFragment.class.getSimpleName();
	
	private Context context;
	private MapView mapView;
	private GoogleMap googleMap;
	private SessionManager session;
    private View rootView; 
    private Marker lastMarker;
    
    private PopupWindow popupWindow; 
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// inflate and return the layout
		rootView = inflater.inflate(R.layout.fragment_map, container, false);
		mapView = (MapView) rootView.findViewById(R.id.mapView); 
		mapView.onCreate(savedInstanceState);
		
		mapView.onResume(); // needed to get map immediately
		try {
			MapsInitializer.initialize(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		googleMap = mapView.getMap();
		// set latitude and longitude
		SimpleReklameDAO reklameDAO = new SimpleReklameDAO(context); 
		ArrayList<Model> allData = reklameDAO.getAll(); 
		reklameDAO.close();
		SimpleReklame reklame = null; 
		MarkerOptions marker = null;
		
		
		for (Model model : allData) {
			reklame = (SimpleReklame) model;
			if(reklame.getLatitude() != 0 && reklame.getLongitude() != 0){
				marker = new MarkerOptions().position(new LatLng(reklame.getLatitude(), reklame.getLongitude()));
				marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
				marker.title(StringHelper.toCapitalize(reklame.getAlamat()));
				marker.snippet(""+reklame.getIdReklameInti());
				googleMap.addMarker(marker);
			}
		}
		
		// set position : 
		CameraPosition cameraPosition = new CameraPosition.Builder().target(marker.getPosition()).zoom(12).build();
		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		
				
		googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				if(lastMarker != null){
					lastMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
				}
				marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
				marker.showInfoWindow();
				CameraPosition cameraPosition = new CameraPosition.Builder().target(marker.getPosition()).zoom(12).build();
				googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
				showDescWindow(marker.getSnippet());
				lastMarker = marker;
				return true;
			}
		});
		
		
		// perform any camera update here. 
		return rootView;
	}
	
	private void showDescWindow(final String strReklameId){
		if(popupWindow != null){
			popupWindow.dismiss();
		}
		
		popupWindow = new PopupWindow(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT); 
		popupWindow.setAnimationStyle(R.style.PopupAnimaitonBottom);
		popupWindow.setOutsideTouchable(false);
		//popupWindow.setBackgroundDrawable(new BitmapDrawable());
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		View contentView = inflater.inflate(R.layout.popup_detail_marker, null); 
		popupWindow.setContentView(contentView);
		
		SimpleReklameDAO reklameDAO = new SimpleReklameDAO(context); 
		SimpleReklame reklame = reklameDAO.findByIdInti(strReklameId);
		reklameDAO.close();
		
		TextView textCompany = (TextView) contentView.findViewById(R.id.popup_detail_text_company_name); 
		TextView textAddress = (TextView) contentView.findViewById(R.id.popup_detail_text_address); 
		TextView textNoForm  = (TextView) contentView.findViewById(R.id.popup_detail_text_no_form); 
		
		Button btnClose  = (Button) contentView.findViewById(R.id.popup_detail_button_close); 
		Button btnDetail = (Button) contentView.findViewById(R.id.popup_detail_button_detail);
		
		textCompany.setText(StringHelper.toCapitalize(reklame.getTitle()));
		textAddress.setText(StringHelper.toCapitalize(reklame.getAlamat()));
		textNoForm.setText(reklame.getNomorForm());
		
		btnDetail.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, DetailReklameActivity.class);
				intent.putExtra("reklameId", ""+strReklameId);
				startActivity(intent);
				
			}
		});
		
		btnClose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
				
			}
		});
		
		View view = new View(context); 
		popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mapView.onPause();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}
	
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}
	
	@Override
	public void onAttach(Activity activity) {
		this.context = activity;
		super.onAttach(activity);
	}
	
	 private void tMessage(String message) {
	        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	    }
	
}
