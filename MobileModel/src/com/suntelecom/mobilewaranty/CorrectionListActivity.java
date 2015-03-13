package com.suntelecom.mobilewaranty;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.suntelecom.mobilewaranty.adapter.CorrectAdapter;
import com.suntelecom.mobilewaranty.database.DatabaseHelper;
import com.suntelecom.mobilewaranty.entity.CorrectionEntity;
import com.suntelecom.mobilewaranty.entity.Model;

public class CorrectionListActivity extends ActionBarActivity implements OnItemClickListener{

	ArrayList<CorrectionEntity> correctionList;
	DatabaseHelper databaseHelper;
	Model myModel;

    TextView tvNotice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_correction_list);
		
		databaseHelper = new DatabaseHelper(getApplicationContext());
		ListView lv = (ListView)findViewById(R.id.listView1);
		lv.setDivider(getResources().getDrawable(R.color.red));
		lv.setDividerHeight(2);
		
		Bundle b = getIntent().getExtras();
		myModel = (Model) b.get("model_item");
		
		if (null == myModel) {
			finish();
		}
		getSupportActionBar().setTitle(myModel.getModelName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		correctionList = (ArrayList<CorrectionEntity>) databaseHelper.getAllCorrectionsByModelId(myModel.getId());

		CorrectAdapter adapter = new CorrectAdapter(correctionList, getApplicationContext());
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);

        String sModel        = Build.MODEL;
        tvNotice = (TextView)findViewById(R.id.tv_notice);

        if (correctionList.size() == 0)
        {
            getSupportActionBar().setTitle(sModel);
            String sNotice = getResources().getString(R.string.text_data_not_found) + " "+sModel;
            tvNotice.setText(sNotice);
            tvNotice.setVisibility(View.VISIBLE);
            lv.setVisibility(View.GONE);
        }else{
            getSupportActionBar().setTitle(myModel.getModelName());
            tvNotice.setVisibility(View.GONE);
            lv.setVisibility(View.VISIBLE);
        }
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent i = new Intent(getApplicationContext(), ModelDetailActivity.class);
		i.putExtra("correction_item", correctionList.get(position));
		startActivity(i);
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
