package com.android.mobilemodel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
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
        TextView tvFeePrice = (TextView) findViewById(R.id.tv_fee);
		TextView tvPrice = (TextView) findViewById(R.id.tv_price);
        TextView tvPriceNotice = (TextView) findViewById(R.id.tv_price_notice);

		llContentAppliance = (LinearLayout)findViewById(R.id.ll_content_appliance);
		
		databaseHelper = new DatabaseHelper(getApplicationContext());
		applianceEntities = new ArrayList<ApplianceEntity>();
		applianceEntities = (ArrayList<ApplianceEntity>) databaseHelper.getAllAppliancesByCorId(correctionEntity.getId());
		modelEntity = databaseHelper.getModel(correctionEntity.getModelId());
        getSupportActionBar().setTitle(modelEntity.getModelName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		tvNoticeModelDetail.setText(Html.fromHtml(getResources().getString(R.string.text_notice_model_detail) + " <b>"+modelEntity.getModelName()+"</b>"));
		
		_inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();
		formatter.setMaximumFractionDigits(2);

		tvSC.setText(correctionEntity.getName());
		int sumPrice =0;
        int maxFeePrice = 0;
		for (int i = 0; i < applianceEntities.size(); i++) {
			ApplianceEntity item = applianceEntities.get(i);
		
			View applicanceView = _inflater.inflate(R.layout.item_appliance, null);
			TextView tvApplianceName = (TextView) applicanceView.findViewById(R.id.tv_appliance_name);
			TextView tvAppliancePrice = (TextView) applicanceView.findViewById(R.id.tv_appliance_price);

			
			tvApplianceName.setText(item.getName());
			
			String sPrice = formatter.format(item.getAppliancePrice());
			tvAppliancePrice.setText(sPrice);

			llContentAppliance.addView(applicanceView);
			if (item.getFee()>maxFeePrice){
                maxFeePrice = item.getFee();
            }
			sumPrice += item.getAppliancePrice();
		}
        sumPrice+=maxFeePrice;
        tvFeePrice.setText(formatter.format(maxFeePrice)+" "+getResources().getString(R.string.text_price_type));

		tvPrice.setText(formatter.format(sumPrice)+" "+getResources().getString(R.string.text_price_type));

        String sNotice = getResources().getString(R.string.text_not_price)+" ";
        final String sNoticeNumber = getResources().getString(R.string.text_not_price_mobile);
        Spannable word = new SpannableString(sNotice);

        tvPriceNotice.setText(word);
        SpannableString wordTwo = new SpannableString(sNoticeNumber);

        wordTwo.setSpan(new ForegroundColorSpan(Color.BLUE), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordTwo.setSpan(new RelativeSizeSpan(1.3f), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordTwo.setSpan(new UnderlineSpan(), 0, wordTwo.length(), 0);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+sNoticeNumber));
                startActivity(callIntent);
            }
        };

        wordTwo.setSpan(clickableSpan, 0, wordTwo.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvPriceNotice.append(wordTwo);
        tvPriceNotice.setMovementMethod(LinkMovementMethod.getInstance());

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
}
