package com.android.mobilemodel;

import java.util.ArrayList;

import com.android.mobilemodel.database.DatabaseHelper;
import com.android.mobilemodel.entity.ApplianceEntity;
import com.android.mobilemodel.entity.CorrectionEntity;
import com.android.mobilemodel.entity.Guarantee;
import com.android.mobilemodel.entity.Model;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class ModelDetailActivity extends Activity {

	CorrectionEntity correctionEntity;
	DatabaseHelper databaseHelper;
	ArrayList<ApplianceEntity> applianceEntities;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_model_detail);
		Bundle b = getIntent().getExtras();
		correctionEntity = (CorrectionEntity) b.get("correction_item");

		if (null == correctionEntity) {
			finish();
		}

		databaseHelper = new DatabaseHelper(getApplicationContext());
		applianceEntities = new ArrayList<ApplianceEntity>();
		
		applianceEntities = (ArrayList<ApplianceEntity>) databaseHelper.getAllAppliancesByCorId(correctionEntity.getId());
		
		TextView tvMaSC = (TextView) findViewById(R.id.tv_ma_sc);
		TextView tvSC = (TextView) findViewById(R.id.tv_sc);
		TextView tvMaLK = (TextView) findViewById(R.id.tv_ma_lk);
		TextView tvLK = (TextView) findViewById(R.id.tv_lk);
		TextView tvPrice = (TextView) findViewById(R.id.tv_price);
		
		tvSC.setText(correctionEntity.getName());
		String sLK="";
		for (int i = 0; i < applianceEntities.size(); i++) {
			sLK+=applianceEntities.get(i).getName()+" "+ getResources().getString(R.string.text_price)
					+" "+applianceEntities.get(i).getPrice()+" "+getResources().getString(R.string.text_price_type);
			sLK+="</br>";
			
		}
		
		tvLK.setText(Html.fromHtml(sLK));
	}
}
