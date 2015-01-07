package com.android.mobilemodel;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.mobilemodel.adapter.CorrectAdapter;
import com.android.mobilemodel.database.DatabaseHelper;
import com.android.mobilemodel.entity.CorrectionEntity;
import com.android.mobilemodel.entity.Model;

public class CorrectionListActivity extends ActionBarActivity implements OnItemClickListener{

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
