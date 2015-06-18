package com.nusaraya.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.pedant.SweetAlert.SweetAlertDialog;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.donbaka.reklameuser.R;
import com.donbaka.reklameuser.adapter.MyTableRow;
import com.donbaka.reklameuser.adapter.PanelTable;
import com.donbaka.reklameuser.app.AppConfig;
import com.donbaka.reklameuser.app.AppController;
import com.donbaka.reklameuser.helper.SessionManager;
import com.donbaka.reklameuser.model.Reklame;
import com.nusaraya.helper.StringHelper;

public class DetailReklameActivity extends Activity {
	
	private static final String TAG = DetailReklameActivity.class.getSimpleName();
	
	private LinearLayout container; 
	private LayoutInflater inflater; 
	private SweetAlertDialog loadingDialog; 
	private RelativeLayout actionBarView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setActionBar();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_reklame);
		
		container = (LinearLayout) findViewById(R.id.detail_reklame_container); 
		inflater  = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		loadingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE); 
		loadingDialog.setTitleText("Loading");
		loadingDialog.setContentText("please wait...!");
		
		// send requeset to get detail reklame 
		SessionManager session = new SessionManager(this); 
		String reklameId = getIntent().getStringExtra("reklameId");
		
		getDetailReklameRequest(session.getKey(), reklameId);
	}
	
	private void setActionBar(){
		LayoutInflater inflater = getLayoutInflater();
        actionBarView = (RelativeLayout) inflater.inflate(R.layout.action_bar_material, null);

        TextView title = (TextView) actionBarView.findViewById(R.id.action_bar_mat_title);
        ImageButton iconBack = (ImageButton) actionBarView.findViewById(R.id.action_bar_mat_btn_back);
        
        title.setText("Detail Reklame");
        iconBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getActionBar().setCustomView(actionBarView);
        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.blue_primary));
	}
	
	private void getDetailReklameRequest(final String key, final String id){
		Log.d(TAG, "send detail reklame request. paramas{key: "+key+", id : "+id+"}"); 
		Log.d(TAG, "url request : "+AppConfig.URL_DETAIL_REKLAME);
		loadingDialog.show();
		
		// Post request : 
		StringRequest request = new StringRequest(
				Method.POST, 
				AppConfig.URL_DETAIL_REKLAME, 
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d(TAG, "response : "+response); 
						JSONArray jsonArr = null; 
						Reklame reklame = null; 
						try {
							jsonArr = new JSONArray(response);
							JSONObject jsonObj = jsonArr.getJSONObject(0); 
							
							reklame = new Reklame(); 
							reklame.setIdInti(jsonObj.getString("ID_INTI_REKLAME"));
							reklame.setTanggalVerifikasi(jsonObj.getString("tgl_verifikasi"));
							reklame.setNama(jsonObj.getString("nama"));
							reklame.setJenisBillboard(jsonObj.getString("jenis_billboard"));
							reklame.setJenisProduk(jsonObj.getString("jenis_produk"));
							reklame.setLetak(jsonObj.getString("letak"));
							reklame.setStatusTanah(jsonObj.getString("status_tanah"));
							reklame.setLokasi(jsonObj.getString("lokasi"));
							reklame.setBatasIjin(jsonObj.getString("batas_ijin"));
							reklame.setAlamat(jsonObj.getString("alamat"));
							reklame.setKecamatan(jsonObj.getString("kecamatan"));
							reklame.setNipr(jsonObj.getString("nipr"));
							reklame.setSudutPandang(jsonObj.getString("sudut_pandang"));
							reklame.setUkuranReklame(jsonObj.getInt("UKURAN_REKLAME"));
							
							// setting ketinggian 
							String[] ketinggians = new String[4];
							for (int i = 0; i < ketinggians.length; i++) {
								ketinggians[i] = jsonObj.getString("ketinggian"+(i+1));
							}
							
							// setting lebars 
							String[] lebars = new String[4]; 
							for (int i = 0; i < lebars.length; i++) {
								lebars[i] = jsonObj.getString("lebar"+(i+1));
							}
							
							// setting panjang 
							String[] panjangs = new String[4];
							for (int i = 0; i < panjangs.length; i++) {
								panjangs[i] = jsonObj.getString("panjang"+(i+1)); 
							}
							
							// setting luas 
							String[] luas = new String[4]; 
							for (int i = 0; i < luas.length; i++) {
								luas[i] = jsonObj.getString("luas"+(i+1));
							}
							
							// setting text
							String[] texts = new String[4]; 
							for (int i = 0; i < texts.length; i++) {
								texts[i] = jsonObj.getString("text"+(i+1)); 
							}
							
							reklame.setKetinggian(ketinggians);
							reklame.setLebar(lebars);
							reklame.setPanjang(panjangs);
							reklame.setLuas(luas);
							reklame.setText(texts);
							
							// set table with data 
							setTableData(reklame);
							
							loadingDialog.dismiss();
							
						} catch (JSONException e) {
							Log.e(TAG, "error json response : "+e.getMessage());
							e.printStackTrace();
						}
						
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						loadingDialog.dismiss();
						Toast.makeText(getApplicationContext(), "connection error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
						Log.d(TAG, "connection error :"+ error.getMessage());
						error.printStackTrace();
					}
				}){
					protected Map<String, String> getParams() {
						Map<String, String> params = new HashMap<String, String>(); 
						params.put("key", key); 
						params.put("id", id);
						return params;
					}
				};
				
			AppController.getInstance().addToRequestQueue(request);
	}
	
	private void setTableData(Reklame reklame){
		PanelTable tableInfoUmum = new PanelTable(this, container);
		tableInfoUmum.setTitle("Informasi Umum");
		LinearLayout containerInfoUmum = tableInfoUmum.getRowContainer(); 
		
		final String[] INFO_UMUM_COLUMNS = {Reklame.ID_REKLAME, Reklame.TGL_VERIFIKASI, Reklame.NAMA_PEMOHON, 
											Reklame.JENIS_BILLBOARD, Reklame.JENIS_PRODUK, Reklame.LETAK_REKLAME, 
											Reklame.STATUS_TANAH, Reklame.LOKASI_PENEMPATAN, Reklame.BATAS_IJIN, 
											Reklame.ALAMAT, Reklame.KECAMATAN, Reklame.NIPR, Reklame.SUDUT_PANDANG, 
											Reklame.UKURAN_REKLAME};
		MyTableRow row = null;
		String value = "-"; 
		for (int i = 0; i < INFO_UMUM_COLUMNS.length; i++) {
			row = new MyTableRow(this, containerInfoUmum); 
			row.setStrLeft(INFO_UMUM_COLUMNS[i]);
			if(INFO_UMUM_COLUMNS[i].equals(Reklame.ID_REKLAME)){
				value = reklame.getIdInti(); 
			}else if(INFO_UMUM_COLUMNS[i].equals(Reklame.TGL_VERIFIKASI)){
				if(reklame.getTanggalVerifikasi().length() >= 10){
					value = StringHelper.dateFormatIndonesia(reklame.getTanggalVerifikasi()); 
				}else{
					value = reklame.getTanggalVerifikasi();
				}
			}else if(INFO_UMUM_COLUMNS[i].equals(Reklame.NAMA_PEMOHON)){
				value = StringHelper.toCapitalize(reklame.getNama()); 
			}else if(INFO_UMUM_COLUMNS[i].equals(Reklame.JENIS_BILLBOARD)){
				value = reklame.getJenisBillboard(); 
			}else if(INFO_UMUM_COLUMNS[i].equals(Reklame.JENIS_PRODUK)){
				value = reklame.getJenisProduk(); 
			}else if(INFO_UMUM_COLUMNS[i].equals(Reklame.LETAK_REKLAME)){
				value = reklame.getLetak(); 
			}else if(INFO_UMUM_COLUMNS[i].equals(Reklame.STATUS_TANAH)){
				value = reklame.getStatusTanah();
			}else if(INFO_UMUM_COLUMNS[i].equals(Reklame.LOKASI_PENEMPATAN)){
				value = reklame.getLokasi(); 
			}else if(INFO_UMUM_COLUMNS[i].equals(Reklame.BATAS_IJIN)){
				String interval = reklame.getBatasIjin(); 
				if(interval.length()>=25){
					String[] splitDate = interval.split(" s/d ");
					value = StringHelper.dateFormatIndonesia(splitDate[0]) + " s/d "
							+ StringHelper.dateFormatIndonesia(splitDate[1]); 
				}else{
					value = reklame.getBatasIjin(); 
				}
			}else if(INFO_UMUM_COLUMNS[i].equals(Reklame.ALAMAT)){
				value = StringHelper.toCapitalize(reklame.getAlamat()); 
			}else if(INFO_UMUM_COLUMNS[i].equals(Reklame.KECAMATAN)){
				value = reklame.getKecamatan(); 
			}else if(INFO_UMUM_COLUMNS[i].equals(Reklame.NIPR)){
				value = reklame.getNipr(); 
			}else if(INFO_UMUM_COLUMNS[i].equals(Reklame.SUDUT_PANDANG)){
				value = reklame.getSudutPandang();
			}else if(INFO_UMUM_COLUMNS[i].equals(Reklame.UKURAN_REKLAME)){
				value = String.valueOf(reklame.getUkuranReklame());
			}
			
			if(value.equals("null") || value.equals("")){
				value = " - ";
			}
			row.setStrRight(value);
			
			if(i % 2 == 0){
				row.setBackgroundColor(Color.WHITE);
			}else{
				row.setBackgroundColor(Color.parseColor("#E3F2FD"));
			}
			row.create();
		}
		tableInfoUmum.create();
		
		// create table ukuran
		
		final int JUMLAH_UKURAN = 4;
		final String[] UKURAN_COLUMNS = {Reklame.KETINGGIAN, Reklame.LEBAR, Reklame.PANJANG, Reklame.LUAS, Reklame.TEXT};
		PanelTable tableUkuran = null; 
		row = null;
		value = "-";
		
		for (int i = 0; i < JUMLAH_UKURAN; i++) {
			tableUkuran = new PanelTable(this, container); 
			tableUkuran.setTitle("Ukuran "+(i+1));
			for (int j = 0; j < UKURAN_COLUMNS.length; j++) {
				row = new MyTableRow(this, tableUkuran.getRowContainer());
				
				row.setStrLeft(UKURAN_COLUMNS[j]+" "+(i+1)); // ketinggian 1/2/3/4, lebar 1/2/3/4 dst
				
				if(UKURAN_COLUMNS[j].equals(Reklame.KETINGGIAN)){
					value = reklame.getKetinggian()[i];
				}else if (UKURAN_COLUMNS[j].equals(Reklame.LEBAR)) {
					value = reklame.getLebar()[i];
				}else if(UKURAN_COLUMNS[j].equals(Reklame.PANJANG)){
					value = reklame.getPanjang()[i];
				}else if(UKURAN_COLUMNS[j].equals(Reklame.LUAS)){
					value = reklame.getLuas()[i];
				}else if(UKURAN_COLUMNS[j].equals(Reklame.TEXT)){
					if(reklame.getText()[i].equals("") || reklame.getText()[i].equals("null"))
						value = " - ";
					else
						value = StringHelper.toCapitalize(reklame.getText()[i]);
				}
				
				
				if(j < UKURAN_COLUMNS.length -1){ // not text index
					row.setNumericFormat(true);
					// set decimal format
					if(value.equals("null")){
						value = " - ";
					}else{
						value = String.format("%.2f", Double.parseDouble(value));
					}	
				}
				
				row.setStrRight(value);
				if(j % 2 == 0){
					row.setBackgroundColor(Color.WHITE);
				}else{
					row.setBackgroundColor(Color.parseColor("#E3F2FD"));
				}
				row.create();
			}
			tableUkuran.create();
		}
		
	}

	/*TextView tableTitle = (TextView) tableTest.findViewById(R.id.panel_table_title);
	LinearLayout tableContainer = (LinearLayout) tableTest.findViewById(R.id.panel_table_container_row); 
	
	String[] columns = {"ID Reklame", "Tanggal Verifikasi", "Nama Perusahaan", "Jenis Billboard"};
	String[] values  = {"5523", "20 Juni 2015", "PT. Genthong Surabaya", "Billboard"};
	
	MyTableRow row = null; 
	for (int i = 0; i < columns.length; i++) {
		row = new MyTableRow(this, tableContainer);
		row.setStrLeft(columns[i]);
		row.setStrRight(values[i]);
		if(i % 2 == 0){
			row.setBackgroundColor(color.white);
		}else{
			row.setBackgroundColor(Color.parseColor("#F5F5F5"));
		}
		row.create();
	}
	container.addView(tableTest);*/
}
