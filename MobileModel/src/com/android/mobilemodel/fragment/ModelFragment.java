package com.android.mobilemodel.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.mobilemodel.MobileModelActivity;
import com.android.mobilemodel.R;
import com.android.mobilemodel.R.id;
import com.android.mobilemodel.R.layout;
import com.android.mobilemodel.adapter.CorrectAdapter;
import com.android.mobilemodel.entity.CorrectionEntity;
import com.android.mobilemodel.entity.MainModel;
import com.android.mobilemodel.entity.Model;
import com.android.mobilemodel.entity.Price;

public class ModelFragment extends Fragment{

	//public Model mModel;
	ArrayList<CorrectionEntity> correctionList;
	MobileModelActivity activity;

	@Override
	public View getView() {
		// TODO Auto-generated method stub
		return super.getView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		activity = (MobileModelActivity) getActivity();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.view_correction_list, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = getView();
		ListView lv = (ListView) v.findViewById(R.id.listView1);
		correctionList = (ArrayList<CorrectionEntity>) activity.databaseHelper.getAllCorrectionsByModelId(activity.mModel.getId());

		CorrectAdapter adapter = new CorrectAdapter(correctionList, getActivity().getApplicationContext());
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				ModelDetailFragment modelDetailFragment = new ModelDetailFragment(mainModels.get(position), prices);
//				getChildFragmentManager().beginTransaction().replace(R.id.container, modelDetailFragment).commit();
				if (null!=onClickItemList) {
					//onClickItemList.onClick(mainModels.get(position), prices);
				}
			}
		});
		
		super.onViewCreated(view, savedInstanceState);
	}

	OnClickItemList onClickItemList;
	public interface OnClickItemList{
		public void onClick(MainModel mainModel, ArrayList<Price> prices);
	}
	
	public void setOnClickItemList(OnClickItemList onClickItemList) {
		this.onClickItemList = onClickItemList;
	}
	
}
