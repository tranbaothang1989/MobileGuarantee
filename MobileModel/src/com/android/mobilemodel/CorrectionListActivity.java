package com.android.mobilemodel;

import java.util.ArrayList;

import com.android.mobilemodel.adapter.CorrectAdapter;
import com.android.mobilemodel.database.DatabaseHelper;
import com.android.mobilemodel.entity.CorrectionEntity;
import com.android.mobilemodel.entity.Model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CorrectionListActivity extends Activity implements OnItemClickListener{

	ArrayList<CorrectionEntity> correctionList;
	DatabaseHelper databaseHelper;
	Model myModel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_correction_list);
		databaseHelper = new DatabaseHelper(getApplicationContext());
		ListView lv = (ListView)findViewById(R.id.listView1);
		
		Bundle b = getIntent().getExtras();
		myModel = (Model) b.get("model_item");
		
		if (null == myModel) {
			finish();
		}
		
		correctionList = (ArrayList<CorrectionEntity>) databaseHelper.getAllCorrectionsByModelId(myModel.getId());

		CorrectAdapter adapter = new CorrectAdapter(correctionList, getApplicationContext());
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent i = new Intent(getApplicationContext(), ModelDetailActivity.class);
		i.putExtra("correction_item", correctionList.get(position));
		startActivity(i);
	}
}
