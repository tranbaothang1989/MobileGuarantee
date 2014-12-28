package com.android.mobilemodel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.mobilemodel.database.DatabaseHelper;
import com.android.mobilemodel.entity.ApplianceEntity;
import com.android.mobilemodel.entity.CorrectionEntity;
import com.android.mobilemodel.entity.Model;
import com.csvreader.CsvReader;

public class MainActivity extends ActionBarActivity implements OnClickListener{

	public DatabaseHelper databaseHelper;
	DoImportData doImportData;
	Button btnMyModel;
	Button btnSearchModel;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnMyModel = (Button)findViewById(R.id.btn_my_model);
		btnSearchModel = (Button)findViewById(R.id.btn_search_model);
		btnMyModel.setOnClickListener(this);
		btnSearchModel.setOnClickListener(this);
		
		String sManufacturer = Build.MANUFACTURER;
        String sBrand        = Build.BRAND;
        String sProduct      = Build.PRODUCT;
        String sModel        = Build.MODEL;
        
		databaseHelper = new DatabaseHelper(getApplicationContext());
		
		ArrayList<Model> listModel = (ArrayList<Model>) databaseHelper.getAllModels();
		if (listModel.size()==0) {
			doImportData = new DoImportData();
			doImportData.execute();
		}
		
	}
	
	private class DoImportData extends AsyncTask<Object, Object, Object>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		@Override
		protected Object doInBackground(Object... params) {
			ArrayList<String> appliance = new ArrayList<String>();
			ArrayList<String> constant = new ArrayList<String>();
			try {
				InputStream is = getAssets().open("modellist.csv");
				char c = '\t';
				CsvReader products = new CsvReader(is, c,Charset.forName("UTF-8"));
			
				//products.readHeaders();

				HashMap<String, Model> mapModel = new HashMap<String, Model>();
				HashMap<String, CorrectionEntity> mapCorrection = new HashMap<String, CorrectionEntity>();
				HashMap<String, ApplianceEntity> mapComponent = new HashMap<String, ApplianceEntity>();
				ArrayList<Model> listModel = new ArrayList<Model>();
				ArrayList<CorrectionEntity> listCorrection = new ArrayList<CorrectionEntity>();
				ArrayList<ApplianceEntity> listAppliance = new ArrayList<ApplianceEntity>();

				int id=0;
				int flg =0;
				while (products.readRecord())
				{
					if (flg == 0) {
						flg =1;
						continue;
					}
					String brand = products.get(0);
					//Log.d("ThangTb", " brand = " + brand);
					String modelCode = products.get(1).trim();
					String correctionCode = products.get(2).trim();
					String correctionName = products.get(3).trim();
					String componentCode = products.get(4).trim();
					String componentName = products.get(5).trim();
					String guarantee = products.get(6).trim();
					String price = products.get(7).trim();

					if (!constant.contains(modelCode)) {
						Model model = new Model();
						model.setBrand(brand);
						model.setModelCode(modelCode);
						constant.add(modelCode);
						int modelId = (int) databaseHelper.insertModel(model);
						Log.d("ThangTB", " insert model "+modelId);
						model.setId(modelId);
						mapModel.put(modelCode, model);
						listModel.add(model);
					}
					if (!constant.contains(correctionCode)) {
						constant.add(correctionCode);
						CorrectionEntity mCorrectionEntity = new CorrectionEntity();
						mCorrectionEntity.setId(correctionCode);
						mCorrectionEntity.setModelId(mapModel.get(modelCode).getId());
						mCorrectionEntity.setName(correctionName);
						mapCorrection.put(correctionCode, mCorrectionEntity);
						listCorrection.add(mCorrectionEntity);
					}
					
					if (!constant.contains(componentCode)) {
						constant.add(componentCode);
						appliance.add(componentCode);
						ApplianceEntity mApplianceEntity = new ApplianceEntity();
						mApplianceEntity.setId(componentCode);
						mApplianceEntity.setCorrectionId(mapCorrection.get(correctionCode).getId());
						mApplianceEntity.setName(componentName);
						mApplianceEntity.setPrice(Integer.parseInt(price.trim().equals("") ? "0": price));
						mapComponent.put(componentCode, mApplianceEntity);
						listAppliance.add(mApplianceEntity);
					}
				}
				
				databaseHelper.insertCorrections(listCorrection);
				databaseHelper.insertAppliances(listAppliance);
				
				products.close();
				for (int i = 0; i < listModel.size(); i++) {
					Log.d("ThangTB", " list model : "+ listModel.get(i).getModelCode());
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			Log.d("ThangTB", "import done");
			super.onPostExecute(result);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.btn_my_model:
			Intent i = new Intent(getApplicationContext(), CorrectionListActivity.class);
			Model model = databaseHelper.getModel("M8");
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
