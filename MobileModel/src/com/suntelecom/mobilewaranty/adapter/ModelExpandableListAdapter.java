package com.suntelecom.mobilewaranty.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suntelecom.mobilewaranty.R;
import com.suntelecom.mobilewaranty.entity.Model;
import com.suntelecom.mobilewaranty.view.AnimatedExpandableListView;

import java.util.HashMap;
import java.util.List;

public class ModelExpandableListAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {

	private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Model>> _listDataChild;
 
    public ModelExpandableListAdapter(Context context, List<String> listDataHeader,
            HashMap<String, List<Model>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }
    
	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_group_model, null);
        }
 
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
 
        return convertView;
	}

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Model model = (Model) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_model, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.tv_model_name);
        txtListChild.setText(model.getModelName());
        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}
}
