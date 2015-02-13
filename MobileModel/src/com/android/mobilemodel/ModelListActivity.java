package com.android.mobilemodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.mobilemodel.adapter.ModelAdapter;
import com.android.mobilemodel.adapter.ModelExpandableListAdapter;
import com.android.mobilemodel.database.DatabaseHelper;
import com.android.mobilemodel.entity.Model;
import com.android.mobilemodel.view.AnimatedExpandableListView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;

public class ModelListActivity extends ActionBarActivity implements OnQueryTextListener, OnItemClickListener {

	ListView lvModel;
	//AnimatedExpandableListView expandableListViewModel;
	
	
	ArrayList<Model> models;
	DatabaseHelper databaseHelper;
	ModelAdapter adapter;
	ArrayList<Model> modelsTemp;
	SearchView mSearchView;
	
	//ArrayList<String> listDataHeader;
	//HashMap<String, List<Model>> listDataChild;
	//ModelExpandableListAdapter modelExpandableListAdapter;
	
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
		/*expandableListViewModel = (AnimatedExpandableListView)findViewById(R.id.expandable_lv_model);
		expandableListViewModel.setGroupIndicator(getResources().getDrawable(R.drawable.select_vertical));
		expandableListViewModel.setChildIndicator(null);
		expandableListViewModel.setChildDivider(getResources().getDrawable(R.color.red));
		expandableListViewModel.setDivider(getResources().getDrawable(R.color.white));
		expandableListViewModel.setDividerHeight(2);*/


		/*listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<Model>>();*/
		
		modelsTemp = new ArrayList<Model>();
		modelsTemp = (ArrayList<Model>) models.clone();
		adapter = new ModelAdapter(modelsTemp, getApplicationContext());
		lvModel.setAdapter(adapter);
		
		lvModel.setOnItemClickListener(this);
		
		//prepareListData(modelsTemp);
		/*modelExpandableListAdapter = new ModelExpandableListAdapter(getApplicationContext(), listDataHeader, listDataChild);
		expandableListViewModel.setAdapter(modelExpandableListAdapter);
		expandableListViewModel.setOnChildClickListener(this);*/
		//expandList();
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
		//prepareListData(modelsTemp);
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				adapter.notifyDataSetChanged();
				//modelExpandableListAdapter.notifyDataSetChanged();
				//expandList();
			}
		});
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String searchString) {
		getModelListBySearch(searchString);
		//prepareListData(modelsTemp);
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				adapter.notifyDataSetChanged();
				//modelExpandableListAdapter.notifyDataSetChanged();
				//expandList();
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
		
		//Log.d("ThangTB", "click on :"+item.getModelName());
        Intent i = new Intent(getApplicationContext(), CorrectionListActivity.class);
        i.putExtra("model_item", item);
        startActivity(i);
	}
	
	/*private void prepareListData(ArrayList<Model> modelList) {
		listDataHeader.clear();
		listDataChild.clear();
		int modelSize = modelList.size() ;
		for (int i = 0; i < modelSize; i++) {
			Model item = modelList.get(i);
			if (!listDataHeader.contains(item.getBrand())) {
				listDataHeader.add(item.getBrand());
			}
			if (!listDataChild.containsKey(item.getBrand())) {
				List<Model> models = new ArrayList<Model>();
				models.add(item);
				listDataChild.put(item.getBrand(), models);
			}else{
				listDataChild.get(item.getBrand()).add(item);
			}
		}
	}*/

	/*private void expandList(){
		for(int i=0; i < modelExpandableListAdapter.getGroupCount(); i++){
			expandableListViewModel.expandGroup(i);
		}
	}*/

	/*@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		String sGroup = listDataHeader.get(groupPosition);
		
		Model item = (Model) listDataChild.get(sGroup).get(childPosition);
		
		Intent i = new Intent(getApplicationContext(), CorrectionListActivity.class);
		i.putExtra("model_item", item);
		startActivity(i);
		Log.d("ThangTB", "click on :"+item.getModelName());
		return false;
	}*/
}
