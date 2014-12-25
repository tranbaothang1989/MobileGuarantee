package com.android.mobilemodel.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.mobilemodel.R;
import com.android.mobilemodel.entity.Model;

public class ModelAdapter extends BaseAdapter {

	ArrayList<Model> datas;
    LayoutInflater l_Inflater;
    
	public ModelAdapter(ArrayList<Model> datas, Context context) {
        this.datas = datas;
        this.l_Inflater = LayoutInflater.from(context);
    }
	
	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Model model = datas.get(position);
		if(convertView == null){
			holder = new ViewHolder();
			convertView = l_Inflater.inflate(R.layout.item_model, null);
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_model_name);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvName.setText(model.getModelCode());
//		convertView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Log.d("ThangTB", "click------------");
//			}
//		});
        return convertView;
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}
	
	static class ViewHolder {		
		TextView tvCode;
		TextView tvName;		
	}
	
	
	public interface OnClickModelItem{
		public void onClick(Model model);
	}
}
