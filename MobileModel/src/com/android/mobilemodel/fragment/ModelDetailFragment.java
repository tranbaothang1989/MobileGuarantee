package com.android.mobilemodel.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.mobilemodel.R;
import com.android.mobilemodel.entity.MainModel;
import com.android.mobilemodel.entity.Price;

import java.util.ArrayList;

public class ModelDetailFragment extends Fragment{

	MainModel mainModel;
	ArrayList<Price> prices;
	
	public ModelDetailFragment(MainModel mainModel, ArrayList<Price> prices) {
		// TODO Auto-generated constructor stub
		this.mainModel = mainModel;
		this.prices = prices;
	}
	
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
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.view_model_detail, container, false);
		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = getView();
		
		TextView tvSC = (TextView) v.findViewById(R.id.tv_sc);
		TextView tvLK = (TextView) v.findViewById(R.id.tv_lk);
		TextView tvPrice = (TextView) v.findViewById(R.id.tv_price);
		
		tvSC.setText(mainModel.getCorrectionName());

		tvLK.setText(mainModel.getApplianceName());
		ArrayList<String> listPrice = new ArrayList<String>();
		for (int i = 0; i < prices.size(); i++) {
			Price p = prices.get(i);
		}
		super.onViewCreated(view, savedInstanceState);
	}
}
