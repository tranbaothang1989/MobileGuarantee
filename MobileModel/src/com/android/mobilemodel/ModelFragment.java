package com.android.mobilemodel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.mobilemodel.entity.ApplianceEntity;
import com.android.mobilemodel.entity.CorrectionEntity;
import com.android.mobilemodel.entity.MainModel;
import com.android.mobilemodel.entity.Model;
import com.android.mobilemodel.entity.Price;
import com.csvreader.CsvReader;

public class ModelFragment extends Fragment{

	ArrayList<MainModel> mainModels;
	ArrayList<Price> prices;
	
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
		ArrayList<String> appliance = new ArrayList<String>();
		ArrayList<String> constant = new ArrayList<String>();
		mainModels = new ArrayList<MainModel>();
		prices = new ArrayList<Price>();
		
		try {
			InputStream is = getActivity().getAssets().open("book1.txt");
			char c = '\t';
			CsvReader products = new CsvReader(is, c,Charset.forName("UCS-2"));
		
			//products.readHeaders();

			HashMap<String, Model> mapModel = new HashMap<String, Model>();
			HashMap<String, CorrectionEntity> mapCorrection = new HashMap<String, CorrectionEntity>();
			HashMap<String, ApplianceEntity> mapComponent = new HashMap<String, ApplianceEntity>();
			ArrayList<Model> listModel = new ArrayList<Model>();
			ArrayList<CorrectionEntity> listCorrection = new ArrayList<CorrectionEntity>();
			ArrayList<ApplianceEntity> listAppliance = new ArrayList<ApplianceEntity>();

			int id=0;
			while (products.readRecord())
			{
				String brand = products.get(0);
				//Log.d("ThangTb", " brand = " + brand);
				String modelCode = products.get(1).trim();
				String correctionCode = products.get(2).trim();
				String correctionName = products.get(3).trim();
				String componentCode = products.get(4).trim();
				String componentName = products.get(5).trim();
				String guarantee = products.get(6).trim();
				String price = products.get(7).trim();

				if (!constant.contains(modelCode)) {
					Model model = new Model();
					model.setId(mapModel.size()+1);
					model.setBrand(brand);
					model.setModelCode(modelCode);
					constant.add(modelCode);
					int modelId = (int) activity.databaseHelper.insertModel(model);
					model.setId(modelId);
					mapModel.put(modelCode, model);
					listModel.add(model);
				}
				if (!constant.contains(correctionCode)) {
					constant.add(correctionCode);
					CorrectionEntity mCorrectionEntity = new CorrectionEntity();
					mCorrectionEntity.setId(correctionCode);
					mCorrectionEntity.setModelId(mapModel.get(modelCode).getId());
					mCorrectionEntity.setName(correctionName);
					mapCorrection.put(correctionCode, mCorrectionEntity);
					listCorrection.add(mCorrectionEntity);
				}
				
				if (!constant.contains(componentCode)) {
					constant.add(componentCode);
					appliance.add(componentCode);
					ApplianceEntity mApplianceEntity = new ApplianceEntity();
					mApplianceEntity.setId(componentCode);
					mApplianceEntity.setCorrectionId(mapCorrection.get(correctionCode).getId());
					mApplianceEntity.setName(componentName);
					mApplianceEntity.setPrice(Integer.parseInt(price));
					mapComponent.put(componentCode, mApplianceEntity);
					listAppliance.add(mApplianceEntity);
				}
			}
			
			
			activity.databaseHelper.insertCorrections(listCorrection);
			activity.databaseHelper.insertAppliances(listAppliance);
			
			products.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		CorrectAdapter adapter = new CorrectAdapter(mainModels, getActivity().getApplicationContext());
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				ModelDetailFragment modelDetailFragment = new ModelDetailFragment(mainModels.get(position), prices);
//				getChildFragmentManager().beginTransaction().replace(R.id.container, modelDetailFragment).commit();
				if (null!=onClickItemList) {
					onClickItemList.onClick(mainModels.get(position), prices);
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
