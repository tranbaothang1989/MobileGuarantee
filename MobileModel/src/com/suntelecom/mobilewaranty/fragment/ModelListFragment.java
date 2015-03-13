package com.suntelecom.mobilewaranty.fragment;

import java.util.ArrayList;

import com.suntelecom.mobilewaranty.R;
import com.suntelecom.mobilewaranty.adapter.ModelAdapter;
import com.suntelecom.mobilewaranty.database.DatabaseHelper;
import com.suntelecom.mobilewaranty.entity.Model;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ModelListFragment extends Fragment {

	ListView lvModel;
	
	ArrayList<Model> models;
	DatabaseHelper databaseHelper;
	ModelAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		models = new ArrayList<Model>();
		databaseHelper = new DatabaseHelper(getActivity());
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_model_list, container, false);
		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		models = (ArrayList<Model>) databaseHelper.getAllModels();
		lvModel = (ListView) getView().findViewById(R.id.lv_model);
		
		adapter = new ModelAdapter(models, getActivity());
		lvModel.setAdapter(adapter);
		super.onViewCreated(view, savedInstanceState);
	}
}
