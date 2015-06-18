package com.nusaraya.dbhelper;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.donbaka.reklameuser.model.Model;
import com.donbaka.reklameuser.model.SimpleReklame;

public class SimpleReklameDAO extends AbstractPatternDAO {
	
	protected static final String TAG = SimpleReklameDAO.class.getSimpleName();
	
	// Database field
	private SQLiteDatabase database; 
	private DBHelper dbHelper; 
	private static final String TABLE_NAME = DBHelper.TABLE_SIMPLE_REKLAME; 
	private static final String[] TABLE_COLUMNS = DBHelper.TABLE_SIMPLE_REKLAME_COLUMNS;
	public static final int DEFAULT_LIMIT  = 10;
	public static final int DEFAULT_OFFSET = 0;
	//private Context context;
	
	public SimpleReklameDAO(Context context) {
		dbHelper = new DBHelper(context);
		//this.context = context; 
		
		try {
			open();
		} catch (Exception e) {
			Log.e(TAG, "Sqlite on opening database : " + e.getMessage()); 
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase(); 
	}

	@Override
	public void close() {
		database.close();
		
	}
	
	/**
     * Insert Model instance of SimpleReklame
     * as paremeter to save data in database.
     *
     * @param Model
     * @return inserted id
     */
	@Override
	public long insert(Model model) {
		
		SimpleReklame reklame = (SimpleReklame) model; 
		ContentValues values = new ContentValues(); 
		
		values.put(DBHelper.KEY_ID_REKLAME_INTI, reklame.getIdReklameInti());
		values.put(DBHelper.KEY_TITLE, reklame.getTitle());
		values.put(DBHelper.KEY_ALAMAT, reklame.getAlamat());
		values.put(DBHelper.KEY_NOMOR_FORM, reklame.getNomorForm());
		values.put(DBHelper.KEY_LATITUDE, reklame.getLatitude());
		values.put(DBHelper.KEY_LONGITUDE, reklame.getLongitude());

		long insertId = database.insert(TABLE_NAME, null, values);
		Log.d(TAG, "inserted id : " + insertId + ", detail : "+reklame.toString()); 
		
		return insertId;
	}
	
	/**
     * Return Model instance of SimpleReklame that
     * has same id as paremeter.
     *
     * @param id
     * @return Model
     */
	@Override
	public Model findById(long id) {
		Cursor cursor = database.query(	TABLE_NAME, TABLE_COLUMNS, DBHelper.KEY_ID + " = ? ", 
										new String[] {String.valueOf(id)}, null, null, null); 
		if(cursor != null){
			cursor.moveToFirst();
		}
		
		Model model = cursorToModel(cursor);
		return model; 
	}
	
	public SimpleReklame findByIdInti(String idReklameInti){
		Cursor cursor = database.query(TABLE_NAME, TABLE_COLUMNS, DBHelper.KEY_ID_REKLAME_INTI + " = ? ", 
									new String[]{idReklameInti}, null, null, null);
		SimpleReklame reklame = null;
		if(cursor != null){
			cursor.moveToFirst();
			reklame = (SimpleReklame) cursorToModel(cursor);
		}
		
		return reklame;
	}
	
	public ArrayList<SimpleReklame> findByString(String search){
		ArrayList<SimpleReklame> resultData = new ArrayList<SimpleReklame>(); 
		SimpleReklame reklame = null; 
		
		String selection =  DBHelper.KEY_TITLE + " LIKE ? OR "+ 
							DBHelper.KEY_ALAMAT +" LIKE ? OR "+
							DBHelper.KEY_NOMOR_FORM + " LIKE ? ";
		String param = "%" + search + "%";
		String[] selectionArgs = {param, param, param};
		Cursor cursor = database.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
		if(cursor != null && cursor.moveToFirst()){
			do {
				reklame = (SimpleReklame) cursorToModel(cursor);
				resultData.add(reklame); 
			} while (cursor.moveToNext());
		}
		return resultData;
		
	}
	
	/**
     * Return all Model instance of SimpleReklame
     * in table form database
     *
     * @return ArrayList of model from table simple_reklame
     */
	
	@Override
	public ArrayList<Model> getAll() {
		ArrayList<Model> allData = new ArrayList<Model>(); 
		Model model = null; 
		
		Cursor cursor = database.query(TABLE_NAME, TABLE_COLUMNS, null, null, null, null, null);
		if(cursor != null && cursor.moveToFirst()){
			do {
				model = cursorToModel(cursor);
				allData.add(model);
			} while (cursor.moveToNext());
		}
		
		return allData;
	}
	
	public int getTotalRecord(){
		Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM "+ TABLE_NAME, null); 
		int total = 0; 
		if(cursor != null && cursor.moveToFirst()){
			total = cursor.getInt(0);
		}
		
		return total;
	}
	
	
	public ArrayList<Model> fetch(int limit, int offset){
		ArrayList<Model> fetchData = new ArrayList<Model>(); 
		Model model = null;
		Cursor cursor = database.rawQuery("SELECT * FROM "+DBHelper.TABLE_SIMPLE_REKLAME + " LIMIT ? OFFSET ? ", new String[] {String.valueOf(limit), String.valueOf(offset)});
		
		if(cursor != null && cursor.moveToFirst()){
			do {
				model = cursorToModel(cursor); 
				fetchData.add(model);
			} while (cursor.moveToNext());
		}
		
		return fetchData;
	}
	
	
	/**
     * Return number of affected rows of updated data
     *
     * @param Model
     * @return number of affected rows
     */
	@Override
	public int update(Model model) {
		SimpleReklame reklame = (SimpleReklame) model; 
		ContentValues values = new ContentValues(); 
		
		values.put(DBHelper.KEY_ID_REKLAME_INTI, reklame.getIdReklameInti());
		values.put(DBHelper.KEY_TITLE, reklame.getTitle());
		values.put(DBHelper.KEY_ALAMAT, reklame.getAlamat());
		values.put(DBHelper.KEY_NOMOR_FORM, reklame.getNomorForm());
		values.put(DBHelper.KEY_LATITUDE, reklame.getLatitude());
		values.put(DBHelper.KEY_LONGITUDE, reklame.getLongitude());
		
		int affectedRows = database.update(TABLE_NAME, values, DBHelper.KEY_ID +" = ? ", new String[] {String.valueOf(model.getId())});
		Log.d(TAG, "updated reklame : "+ affectedRows); 
		return affectedRows;
	}
	
	
	@Override
	public int delete(long id) {
		int affectedRows = database.delete(TABLE_NAME, DBHelper.KEY_ID + " = ?", new String[] {String.valueOf(id)});
		Log.d(TAG, "deleted reklame with id : "+id);
		Log.d(TAG, "affected rows : "+affectedRows);
		return affectedRows;
	}
	
	public int deleteAll(){
		int affectedRows = database.delete(TABLE_NAME, null, null);
		Log.d(TAG, "delete all data, affected rows : "+affectedRows);
		return affectedRows;
	}

	@Override
	protected Model cursorToModel(Cursor cursor) {
		SimpleReklame reklame = new SimpleReklame();
		reklame.setId(cursor.getLong(0));
		reklame.setIdReklameInti(cursor.getInt(1));
		reklame.setTitle(cursor.getString(2));
		reklame.setAlamat(cursor.getString(3));
		reklame.setNomorForm(cursor.getString(4));
		reklame.setLatitude(cursor.getDouble(5));
		reklame.setLongitude(cursor.getDouble(6));
		
		return reklame;
	}

}
