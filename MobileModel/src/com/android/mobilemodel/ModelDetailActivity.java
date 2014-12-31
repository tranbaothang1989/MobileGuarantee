package com.android.mobilemodel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.mobilemodel.database.DatabaseHelper;
import com.android.mobilemodel.entity.ApplianceEntity;
import com.android.mobilemodel.entity.CorrectionEntity;
import com.android.mobilemodel.entity.Model;

public class ModelDetailActivity extends ActionBarActivity {

	CorrectionEntity correctionEntity;
	Model modelEntity;
	DatabaseHelper databaseHelper;
	ArrayList<ApplianceEntity> applianceEntities;
	LayoutInflater _inflater;
	LinearLayout llContentAppliance;
	
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

		TextView tvSC = (TextView) findViewById(R.id.tv_sc);
		TextView tvNoticeModelDetail = (TextView) findViewById(R.id.tv_notice_model_detail);
		TextView tvPrice = (TextView) findViewById(R.id.tv_price);
		llContentAppliance = (LinearLayout)findViewById(R.id.ll_content_appliance);
		
		databaseHelper = new DatabaseHelper(getApplicationContext());
		applianceEntities = new ArrayList<ApplianceEntity>();
		applianceEntities = (ArrayList<ApplianceEntity>) databaseHelper.getAllAppliancesByCorId(correctionEntity.getId());
		modelEntity = databaseHelper.getModel(correctionEntity.getModelId());
		
		tvNoticeModelDetail.setText(Html.fromHtml(getResources().getString(R.string.text_notice_model_detail) + " <b>"+modelEntity.getModelName()+"</b>"));
		
		_inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();
		formatter.setMaximumFractionDigits(2);

		tvSC.setText(correctionEntity.getName());
		int sumPrice =0;
		for (int i = 0; i < applianceEntities.size(); i++) {
			ApplianceEntity item = applianceEntities.get(i);
		
			View applicanceView = _inflater.inflate(R.layout.item_appliance, null);
			TextView tvApplianceName = (TextView) applicanceView.findViewById(R.id.tv_appliance_name);
			TextView tvAppliancePrice = (TextView) applicanceView.findViewById(R.id.tv_appliance_price);
			
			tvApplianceName.setText(item.getName());
			
			String sPrice = formatter.format(item.getAppliancePrice());
			tvAppliancePrice.setText(sPrice);

			llContentAppliance.addView(applicanceView);
			
			sumPrice += item.getAppliancePrice();
		}
		
		tvPrice.setText(formatter.format(sumPrice)+" "+getResources().getString(R.string.text_price_type));
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
}
