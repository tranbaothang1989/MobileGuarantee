/**
 * 
 */
package com.android.mobilemodel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author ThangTB
 *
 */
public class DataHeper {
	Context mContext;
	
	public String databasePath = "";
	public String databaseName = "data.sqlite";
	public SQLiteDatabase database=null;	
	public File databaseFile;
	
	/**
	 * @param context
	 */
	public DataHeper(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		databasePath = "data/data/"+context.getPackageName()+"/data.sqlite";
		databaseFile = new File(databasePath);
		if(!databaseFile.exists())
			try {
				deployDataBase(databaseName, databasePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * 
	 * copy database to devices
	 * @param databaseName
	 * @param tagertPath
	 * @throws IOException
	 */
	private void deployDataBase(String databaseName, String tagertPath) throws IOException    {  
    	InputStream myInput = mContext.getAssets().open(databaseName);  
    	String outFileName = tagertPath;  	
    	OutputStream myOutput = new FileOutputStream(outFileName);  

    	byte[] buffer = new byte[1024]; 
    	int length; 
    	while ((length = myInput.read(buffer))>0)  
    	{  
    		myOutput.write(buffer, 0, length);   
    	}  
    	
    	myOutput.flush();  
    	myOutput.close();  
    	myInput.close();   
    }
	
	/**
	 * @param QuoteID
	 * @return
	 */
	public boolean AddFavourites(String QuoteID){
		String query="";
		query = "UPDATE quotes SET is_favourist = 1 WHERE  _id = "+QuoteID+";";
		
		try {
			database = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
			database.execSQL(query);
			database.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	/**
	 * @param QuoteID
	 * @return
	 */
	public boolean DeleteFavourites(String QuoteID){
		String query="";
		query = "UPDATE quotes SET is_favourist = 0 WHERE  _id = "+QuoteID+";";
		
		try {
			database = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
			database.execSQL(query);
			database.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	/**
	 * @param QuoteID
	 * @return
	 */
	public boolean DeleteAllFavourites(){
		String query="";
		query = "UPDATE quotes SET is_favourist = 0 WHERE  is_favourist = 1 ;";
		
		try {
			database = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
			database.execSQL(query);
			database.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public boolean SaveQuoteOfDay(int quoteId, String body){
		Date d = new Date();
		String query="";
		query = "select * from qod LIMIT 1;";
		database = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
		Cursor  cursor =  database.rawQuery(query, null);	 
        if (cursor==null || cursor.getCount()==0) {
        	query = "INSERT INTO qod (quote_id,changed,body) VALUES ("+quoteId+","+d.getTime()+",\""+body+"\") ;";
        	database.execSQL(query);
		}else {
			query = "UPDATE qod SET quote_id = "+quoteId+",changed = "+d.getTime()+",body = \"\""+body+"\"\" ;";
			StringBuilder stringBuilder = new StringBuilder();
			
			stringBuilder.append("UPDATE qod SET ")
			.append(" quote_id = ").append(quoteId).append(",changed = ").append(d.getTime())
			.append(",body = '").append(body.replaceAll("'", "''")).append("' ;");
			database.execSQL(stringBuilder.toString());
		}
		try {
			cursor.close();
			database.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			cursor.close();
			database.close();
			return false;
		}finally{
			cursor.close();
			database.close();
		}
	}
	
}
