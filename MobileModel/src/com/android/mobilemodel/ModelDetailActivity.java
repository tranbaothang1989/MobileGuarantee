package com.android.mobilemodel;

import com.android.mobilemodel.entity.CorrectionEntity;
import com.android.mobilemodel.entity.Model;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ModelDetailActivity extends Activity {

	CorrectionEntity correctionEntity;
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
		
		TextView tvMaSC = (TextView) findViewById(R.id.tv_ma_sc);
		TextView tvSC = (TextView) findViewById(R.id.tv_sc);
		TextView tvMaLK = (TextView) findViewById(R.id.tv_ma_lk);
		TextView tvLK = (TextView) findViewById(R.id.tv_lk);
		TextView tvPrice = (TextView) findViewById(R.id.tv_price);
	}
}
