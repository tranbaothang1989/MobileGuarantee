package com.android.mobilemodel.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.mobilemodel.R;
import com.android.mobilemodel.R.id;
import com.android.mobilemodel.R.layout;
import com.android.mobilemodel.entity.CorrectionEntity;
import com.android.mobilemodel.entity.MainModel;

public class CorrectAdapter extends BaseAdapter {

	ArrayList<CorrectionEntity> datas;
    private LayoutInflater l_Inflater;
    
	public CorrectAdapter(ArrayList<CorrectionEntity> datas, Context context) {
        this.datas = datas;
        this.l_Inflater = LayoutInflater.from(context);
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		View view = l_Inflater.inflate(R.layout.item_correction, null);
		ViewHolder holder;
		CorrectionEntity correctionEntity = datas.get(position);
		if(convertView == null){
			holder = new ViewHolder();
			convertView = l_Inflater.inflate(R.layout.item_correction, null);
			holder.tvCode = (TextView) convertView.findViewById(R.id.tv_correction_code);
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_correction_item);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvCode.setText(correctionEntity.getCode());
		holder.tvName.setText(correctionEntity.getName());
        return convertView;
	}

	static class ViewHolder {		
		TextView tvCode;
		TextView tvName;		
	}
	
}
