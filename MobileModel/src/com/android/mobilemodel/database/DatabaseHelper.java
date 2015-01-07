package com.android.mobilemodel.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.android.mobilemodel.Contants;
import com.android.mobilemodel.entity.ApplianceEntity;
import com.android.mobilemodel.entity.CorrectionEntity;
import com.android.mobilemodel.entity.Model;

public class DatabaseHelper extends SQLiteOpenHelper {

	// Logcat tag
	private static final String LOG = "DatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "contactsManager";

	// Table Names
	private static final String TABLE_MODEL = "model";
	private static final String TABLE_CORRECTION = "correction";
	private static final String TABLE_APPLIANCE = "appliance";

	// Common column names
	private static final String COL_ID = "id";
	private static final String COL_CREATED_AT = "created_at";

	// MODEL Table - column nmaes
	private static final String COL_MODEL_BRAND = "brand";
	private static final String COL_MODEL_CODE = "model_code";

	// CORRECTION Table - column names
	private static final String COL_COR_MODEL_ID = "model_id";
	private static final String COL_COR_CODE = "code";
	private static final String COL_COR_NAME = "name";
	
	// APPLIANCE Table - column names
	private static final String COL_APP_COR_ID = "correction_id";
	private static final String COL_APP_CODE = "code";
	private static final String COL_APP_NAME = "name";
	private static final String COL_APP_PRICE = "applicance_price";
	private static final String COL_APP_FEE = "fee";

	// Table Create Statements
	// model table create statement
	private static final String CREATE_TABLE_MODEL = "CREATE TABLE "
			+ TABLE_MODEL + "(" + COL_ID + " INTEGER PRIMARY KEY," 
								+ COL_MODEL_BRAND+ " TEXT," 
								+ COL_MODEL_CODE + " TEXT UNIQUE," 
								+ COL_CREATED_AT + " DATETIME" + ")";

	// correction table create statement
	private static final String CREATE_TABLE_CORRECTION = "CREATE TABLE " + TABLE_CORRECTION
								+ "(" 	+ COL_ID + " INTEGER PRIMARY KEY," 
										+ COL_COR_MODEL_ID + " INTEGER,"
										+ COL_COR_CODE + " TEXT,"
										+ COL_COR_NAME + " TEXT,"
										+ COL_CREATED_AT + " DATETIME" + ")";

	// correction table create statement
		private static final String CREATE_TABLE_APPLIANCE = "CREATE TABLE " + TABLE_APPLIANCE
									+ "(" 	+ COL_ID + " INTEGER PRIMARY KEY," 
											+ COL_APP_COR_ID + " INTEGER,"
											+ COL_APP_CODE + " TEXT,"
											+ COL_APP_NAME + " TEXT,"
											+ COL_APP_PRICE + " INTEGER,"
											+ COL_APP_FEE + " INTEGER,"
											+ COL_CREATED_AT + " DATETIME" + ")";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// creating required tables
		db.execSQL(CREATE_TABLE_MODEL);
		db.execSQL(CREATE_TABLE_CORRECTION);
		db.execSQL(CREATE_TABLE_APPLIANCE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_MODEL);
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_CORRECTION);
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_APPLIANCE);

		// create new tables
		onCreate(db);
	}

	// ------------------------ "todos" table methods ----------------//

	/*
	 * Inserting a model
	 */
	public long insertModel(Model model) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COL_MODEL_BRAND, model.getBrand());
		values.put(COL_MODEL_CODE, model.getModelName());
		values.put(COL_CREATED_AT, getDateTime());

		Log.d("ThangTB", " insert model "+model.getModelName());
		// insert row
//		long model_id = db.insert(TABLE_MODEL, null, values);
		long model_id = db.insertWithOnConflict(TABLE_MODEL, null, values, SQLiteDatabase.CONFLICT_REPLACE);

		return model_id;
	}

	/*
	 * Inserting a model
	 */
	public int insertModelList(ArrayList<Model> listModels) {
		//Log.d("ThangTB", "====> insertModelList "+ listModels.size());
		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransaction();
		try {
			//insert lots of stuff...
			for (int i = 0; i < listModels.size(); i++) {
				Model model = listModels.get(i);
				ContentValues values = new ContentValues();
				values.put(COL_MODEL_BRAND, model.getBrand());
				values.put(COL_MODEL_CODE, model.getModelName());
				values.put(COL_CREATED_AT, getDateTime());
				long model_id = db.insertWithOnConflict(TABLE_MODEL, null, values, SQLiteDatabase.CONFLICT_REPLACE);
			}
			db.setTransactionSuccessful();
			db.endTransaction();
		} catch (Exception e) {
			db.endTransaction();
			return Contants.INSERT_FAIL;
		}
		return Contants.INSERT_OK;
	}
	
	/*
	 * get single model
	 */
	public Model getModel(long model_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_MODEL + " WHERE "
				+ COL_ID + " = " + model_id;

		//Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		Model model = new Model();
		model.setId(c.getInt(c.getColumnIndex(COL_ID)));
		model.setBrand((c.getString(c.getColumnIndex(COL_MODEL_BRAND))));
		model.setModelName((c.getString(c.getColumnIndex(COL_MODEL_CODE))));
		model.setCreatedAt(c.getString(c.getColumnIndex(COL_CREATED_AT)));

		return model;
	}

	/*
	 * get single model
	 */
	public Model getModel(String modelName) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_MODEL + " WHERE "
				+ COL_MODEL_CODE + " like '%"+modelName+"%'";
		Log.e(LOG, selectQuery);
		Cursor c = db.rawQuery(selectQuery, null);
		
		Model model = new Model();
		if (c.moveToFirst()) {
			do {
				model.setId(c.getInt(c.getColumnIndex(COL_ID)));
				model.setBrand((c.getString(c.getColumnIndex(COL_MODEL_BRAND))));
				model.setModelName((c.getString(c.getColumnIndex(COL_MODEL_CODE))));
				model.setCreatedAt(c.getString(c.getColumnIndex(COL_CREATED_AT)));
			}while (c.moveToNext());

		}
		//Log.d("ThangTB", model.getModelCode());
		return model;
	}
	
	/**
	 * getting all model
	 * */
	public List<Model> getAllModels() {
		List<Model> models = new ArrayList<Model>();
		String selectQuery = "SELECT  * FROM " + TABLE_MODEL;

		//Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Model model = new Model();
				model.setId(c.getInt(c.getColumnIndex(COL_ID)));
				model.setBrand((c.getString(c.getColumnIndex(COL_MODEL_BRAND))));
				model.setModelName((c.getString(c.getColumnIndex(COL_MODEL_CODE))));
				model.setCreatedAt(c.getString(c.getColumnIndex(COL_CREATED_AT)));

				// adding to todo list
				models.add(model);
			} while (c.moveToNext());
		}

		return models;
	}

	/**
	 * getting all model
	 * */
	public List<Model> getAllModelsByName(String name) {
		List<Model> models = new ArrayList<Model>();
		String selectQuery = "SELECT  * FROM " + TABLE_MODEL + " WHERE "
				+ COL_MODEL_CODE + " like '%"+name+"%'";

		//Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Model model = new Model();
				model.setId(c.getInt(c.getColumnIndex(COL_ID)));
				model.setBrand((c.getString(c.getColumnIndex(COL_MODEL_BRAND))));
				model.setModelName((c.getString(c.getColumnIndex(COL_MODEL_CODE))));
				model.setCreatedAt(c.getString(c.getColumnIndex(COL_CREATED_AT)));

				// adding to todo list
				models.add(model);
			} while (c.moveToNext());
		}

		return models;
	}
	
	/*
	 * Updating a todo
	 */
	public int updateModel(Model model) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COL_MODEL_BRAND, model.getBrand());
		values.put(COL_MODEL_CODE, model.getModelName());

		// updating row
		return db.update(TABLE_MODEL, values, COL_ID + " = ?",
				new String[] { String.valueOf(model.getId()) });
	}
	

	/*
	 * Inserting a model
	 */
	public int insertCorrections(ArrayList<CorrectionEntity> listCorrections) {
		Log.d("ThangTB", "====> insertAppliances "+ listCorrections.size());
		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransaction();
		try {
			//insert lots of stuff...
			for (int i = 0; i < listCorrections.size(); i++) {
				CorrectionEntity entity = listCorrections.get(i);
				ContentValues values = new ContentValues();
				//values.put(COL_ID, entity.getId());
				values.put(COL_COR_MODEL_ID, entity.getModelId());
				values.put(COL_COR_CODE, entity.getCode());
				values.put(COL_COR_NAME, entity.getName());
				values.put(COL_CREATED_AT, getDateTime());
//				long model_id = db.insert(TABLE_CORRECTION, null, values);
				long model_id = db.insertWithOnConflict(TABLE_CORRECTION, null, values, SQLiteDatabase.CONFLICT_REPLACE);
				//Log.d("ThangTB", "====> insertCorrections "+ entity.getId()+ " is "+model_id);
			}
			db.setTransactionSuccessful();
			db.endTransaction();
		} catch (Exception e) {
			db.endTransaction();
			return Contants.INSERT_FAIL;
		}
		return Contants.INSERT_OK;
	}
	
	/*
	 * Inserting a model
	 */
	public int insertAppliances(ArrayList<ApplianceEntity> listAppliances) {
		Log.d("ThangTB", "====> insertAppliances "+ listAppliances.size());
		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransaction();
		try {
			//insert lots of stuff...
			for (int i = 0; i < listAppliances.size(); i++) {
				ApplianceEntity entity = listAppliances.get(i);
				ContentValues values = new ContentValues();
				//values.put(COL_ID, entity.getId());
				values.put(COL_APP_COR_ID, entity.getCorrectionId());
				values.put(COL_APP_CODE, entity.getCode());
				values.put(COL_APP_NAME, entity.getName());
				values.put(COL_APP_PRICE, entity.getAppliancePrice());
				values.put(COL_APP_FEE, entity.getFee());
				values.put(COL_CREATED_AT, getDateTime());
				
//				long model_id = db.insert(TABLE_APPLIANCE, null, values);
				long model_id = db.insertWithOnConflict(TABLE_APPLIANCE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
				//Log.d("ThangTB", "====> insertAppliances "+ entity.getId()+ " is "+model_id);
			}
			db.setTransactionSuccessful();
			db.endTransaction();
		} catch (Exception e) {
			db.endTransaction();
			return Contants.INSERT_FAIL;
		}
		return Contants.INSERT_OK;
	}

	/**
	 * getting all corrections under model id
	 * */
	public List<CorrectionEntity> getAllCorrectionsByModelId(int model_id) {
		List<CorrectionEntity> Corrections = new ArrayList<CorrectionEntity>();

		String selectQuery = "SELECT  * FROM " + TABLE_CORRECTION 
				+ " WHERE "+ COL_COR_MODEL_ID + " = '" + model_id + "'";

		//Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				CorrectionEntity correctionEntity = new CorrectionEntity();
				correctionEntity.setId(c.getInt((c.getColumnIndex(COL_ID))));
				correctionEntity.setModelId(c.getInt(c.getColumnIndex(COL_COR_MODEL_ID)));
				correctionEntity.setCode(c.getString((c.getColumnIndex(COL_COR_CODE))));
				correctionEntity.setName(c.getString((c.getColumnIndex(COL_COR_NAME))));

				// adding to correction list
				Corrections.add(correctionEntity);
			} while (c.moveToNext());
		}

		return Corrections;
	}

	/**
	 * getting all appliance under correction id
	 * */
	public List<ApplianceEntity> getAllAppliancesByCorId(int cor_id) {
		List<ApplianceEntity> appliances = new ArrayList<ApplianceEntity>();

		String selectQuery = "SELECT  * FROM " + TABLE_APPLIANCE
				+ " WHERE "+ COL_APP_COR_ID + " like '" + cor_id + "'";

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				ApplianceEntity correctionEntity = new ApplianceEntity();
				correctionEntity.setId(c.getInt((c.getColumnIndex(COL_ID))));
				correctionEntity.setCorrectionId(c.getInt(c.getColumnIndex(COL_APP_COR_ID)));
				correctionEntity.setCode(c.getString((c.getColumnIndex(COL_APP_CODE))));
				correctionEntity.setName(c.getString((c.getColumnIndex(COL_APP_NAME))));
				correctionEntity.setAppliancePrice(c.getInt((c.getColumnIndex(COL_APP_PRICE))));
				correctionEntity.setFee(c.getInt((c.getColumnIndex(COL_APP_FEE))));

				// adding to correction list
				appliances.add(correctionEntity);
			} while (c.moveToNext());
		}

		return appliances;
	}


	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

	/**
	 * get datetime
	 * */
	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
}
