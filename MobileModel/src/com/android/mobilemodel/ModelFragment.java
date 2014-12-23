package com.android.mobilemodel;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.mobilemodel.entity.CorrectionEntity;
import com.android.mobilemodel.entity.MainModel;
import com.android.mobilemodel.entity.Model;
import com.android.mobilemodel.entity.Price;

public class ModelFragment extends Fragment{

	Model mModel;
	ArrayList<CorrectionEntity> correctionList;
	MobileModelActivity activity;

	@Override
	@Nullable
	public View getView() {
		// TODO Auto-generated method stub
		return super.getView();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
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
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.view_model, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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
