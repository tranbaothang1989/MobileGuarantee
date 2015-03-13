package com.suntelecom.mobilewaranty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.suntelecom.mobilewaranty.adapter.ModelAdapter;
import com.suntelecom.mobilewaranty.database.DatabaseHelper;
import com.suntelecom.mobilewaranty.entity.Model;

import java.util.ArrayList;

public class ModelListActivity extends ActionBarActivity implements OnQueryTextListener, OnItemClickListener {

	ListView lvModel;

	ArrayList<Model> models;
	DatabaseHelper databaseHelper;
	ModelAdapter adapter;
	ArrayList<Model> modelsTemp;
	SearchView mSearchView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.fragment_model_list);

        Bundle b = getIntent().getExtras();
        String branch = b.getString("branch");

        getSupportActionBar().setTitle(branch.toUpperCase());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		models = new ArrayList<Model>();
		databaseHelper = new DatabaseHelper(getApplicationContext());
		
		models = (ArrayList<Model>) databaseHelper.getAllModelsByBranch(branch);
		lvModel = (ListView) findViewById(R.id.lv_model);
		
		modelsTemp = new ArrayList<Model>();
		modelsTemp = (ArrayList<Model>) models.clone();
		adapter = new ModelAdapter(modelsTemp, getApplicationContext());
		lvModel.setAdapter(adapter);
		
		lvModel.setOnItemClickListener(this);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.model_list, menu);
	    MenuItem searchItem = menu.findItem(R.id.action_search);
	    mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
	    mSearchView.setOnQueryTextListener(this);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
		switch(item.getItemId()){
        case R.id.action_search:
            mSearchView.setIconified(false);
            return true;
            case android.R.id.home:
                this.finish();
                return true;
    }
			return super.onOptionsItemSelected(item);
		}
	
	@Override
	public boolean onQueryTextChange(String searchString) {
		// TODO Auto-generated method stub
		getModelListBySearch(searchString);
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				adapter.notifyDataSetChanged();
			}
		});
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String searchString) {
		getModelListBySearch(searchString);
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				adapter.notifyDataSetChanged();
			}
		});
		
		return true;
	}
	
	private ArrayList<Model> getModelListBySearch(String searchString){
		ArrayList<Model> list = new ArrayList<Model>();

		int modelSize = models.size();
		modelsTemp.clear();
		for (int i = 0; i < modelSize; i++) {
			Model item = models.get(i);
			if (item.getModelName().toLowerCase().contains(searchString.toLowerCase())) {
				modelsTemp.add(item);
			}
		}
		return list;
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Model item = (Model) adapter.getItem(position);

        Intent i = new Intent(getApplicationContext(), CorrectionListActivity.class);
        i.putExtra("model_item", item);
        startActivity(i);
	}
}
