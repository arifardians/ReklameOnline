package com.nusaraya.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
	
	public static final String DATABASE_NAME = "db_reklame"; 
	public static final int VERSION = 1; 
	
	/*
	 * List of Table name 
	 */
	public static final String TABLE_SIMPLE_REKLAME = "simple_reklame"; 
	
	/**
	 * Common Key column
	 */
	public static final String KEY_ID = "_id"; 
	
	/**
	 * table simple reklame
	 */
	public static final String KEY_ID_REKLAME_INTI = "id_reklame_inti"; 
	public static final String KEY_TITLE 		= "title"; 
	public static final String KEY_ALAMAT 		= "alamat"; 
	public static final String KEY_NOMOR_FORM 	= "nomor_form"; 
	public static final String KEY_LATITUDE 	= "lat";
	public static final String KEY_LONGITUDE 	= "long"; 
	
	/**
	 * columns of table reklame
	 */
	public static final String[] TABLE_SIMPLE_REKLAME_COLUMNS = {KEY_ID, KEY_ID_REKLAME_INTI, KEY_TITLE,
																 KEY_ALAMAT, KEY_NOMOR_FORM, KEY_LATITUDE, 
																 KEY_LONGITUDE};
	
	/**
	 * Create table reklame statement
	 */
	public static final String CREATE_TABLE_SIMPLE_REKLAME = "CREATE TABLE " + TABLE_SIMPLE_REKLAME 
												+ " (" + KEY_ID +" INTEGER PRIMARY KEY, "
													   + KEY_ID_REKLAME_INTI + " INTEGER, "
													   + KEY_TITLE + " TEXT, "
													   + KEY_ALAMAT + " TEXT, "
													   + KEY_NOMOR_FORM + " TEXT, "
													   + KEY_LATITUDE + " TEXT, "
													   + KEY_LONGITUDE + " TEXT)";
	
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SIMPLE_REKLAME);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXIST "+TABLE_SIMPLE_REKLAME);
		onCreate(db);
	}

}
