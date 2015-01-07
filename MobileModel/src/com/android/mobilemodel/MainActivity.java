package com.android.mobilemodel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.mobilemodel.database.DatabaseHelper;
import com.android.mobilemodel.entity.ApplianceEntity;
import com.android.mobilemodel.entity.CorrectionEntity;
import com.android.mobilemodel.entity.MainModel;
import com.android.mobilemodel.entity.Model;
import com.csvreader.CsvReader;

public class MainActivity extends ActionBarActivity implements OnClickListener{

	public DatabaseHelper databaseHelper;
	Button btnMyModel;
	Button btnSearchModel;
    Model model;
    String sModel;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int screenHeight = metrics.heightPixels;
		int screenWidth = metrics.widthPixels;
		
		btnMyModel = (Button)findViewById(R.id.btn_my_model);
		btnSearchModel = (Button)findViewById(R.id.btn_search_model);
		btnMyModel.setOnClickListener(this);
		btnSearchModel.setOnClickListener(this);
		int btnWidth = (screenWidth*70)/100;
		int btnHeight = (screenHeight*20)/100;

		btnMyModel.setWidth(btnWidth);
		btnMyModel.setHeight(btnHeight);
        sModel        = Build.MODEL;
        btnMyModel.setText(getResources().getString(R.string.btn_my_model)+" "+sModel);

		btnSearchModel.setWidth(btnWidth);
		btnSearchModel.setHeight(btnHeight);
		
//		String sManufacturer = Build.MANUFACTURER;
//        String sBrand        = Build.BRAND;
//        String sProduct      = Build.PRODUCT;
//        String sModel        = Build.MODEL;
        databaseHelper = new DatabaseHelper(getApplicationContext());

        model = databaseHelper.getModel(sModel.toLowerCase());
        if (model.getId() ==0){
            btnMyModel.setVisibility(View.GONE);
        }
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.btn_my_model:
			Intent i = new Intent(getApplicationContext(), CorrectionListActivity.class);
			i.putExtra("model_item", model);
			startActivity(i);
			break;
		case R.id.btn_search_model:
			Intent i2 = new Intent(getApplicationContext(), ModelListActivity.class);
			startActivity(i2);
			break;

		default:
			break;
		}
	}
	
}
