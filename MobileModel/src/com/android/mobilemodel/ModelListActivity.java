package com.android.mobilemodel;

import java.util.ArrayList;

import com.android.mobilemodel.adapter.ModelAdapter;
import com.android.mobilemodel.database.DatabaseHelper;
import com.android.mobilemodel.entity.Model;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.widget.ListView;

public class ModelListActivity extends ActionBarActivity implements OnQueryTextListener {

ListView lvModel;
	
	ArrayList<Model> models;
	DatabaseHelper databaseHelper;
	ModelAdapter adapter;
	ArrayList<Model> modelsTemp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.fragment_model_list);
		
		models = new ArrayList<Model>();
		modelsTemp = new ArrayList<Model>();
		databaseHelper = new DatabaseHelper(getApplicationContext());
		
		models = (ArrayList<Model>) databaseHelper.getAllModels();
		lvModel = (ListView) findViewById(R.id.lv_model);
		
		ArrayList<Model> modelsTemp = (ArrayList<Model>) models.clone();
		adapter = new ModelAdapter(modelsTemp, this);
		lvModel.setAdapter(adapter);
	}

	@Override
	public boolean onQueryTextChange(String searchString) {
		// TODO Auto-generated method stub
		if (searchString.trim().equals("")) {
			modelsTemp = models;
		}
		modelsTemp = getModelListBySearch(searchString);
		adapter.notifyDataSetChanged();
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String searchString) {
		if (searchString.trim().equals("")) {
			modelsTemp = models;
		}
		modelsTemp = getModelListBySearch(searchString);
		adapter.notifyDataSetChanged();
		return false;
	}
	
	private ArrayList<Model> getModelListBySearch(String searchString){
		ArrayList<Model> list = new ArrayList<Model>();

		int modelSize = modelsTemp.size();
		for (int i = 0; i < modelSize; i++) {
			Model item = modelsTemp.get(i);
			if (item.getModelCode().toLowerCase().contains(searchString.toLowerCase())) {
				list.add(item);
			}
		}
		return list;
	}
}
