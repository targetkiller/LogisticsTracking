package hci.wlzz.android.UI;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExpandAdapter extends BaseExpandableListAdapter {
	Context context;
	List<HashMap<String, Object>> group;
	List<HashMap<String, Object>> childs;

	public ExpandAdapter(Context context, List<HashMap<String, Object>> group,
			List<HashMap<String, Object>> childs) {
		this.context = context;
		this.group = group;
		this.childs = childs;
	}

	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childs.get(groupPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@SuppressWarnings("unchecked")
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// String next = ((HashMap<String, Object>) getChild(groupPosition,
		// childPosition)).get("next_station_id").toString();
		String des = ((HashMap<String, Object>) getChild(groupPosition,
				childPosition)).get("description").toString();

		LayoutInflater layoutinflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		LinearLayout linearlayout = (LinearLayout) layoutinflater.inflate(
				R.layout.child_list, null);

		// TextView nexttext = (TextView) linearlayout
		// .findViewById(R.id.child_list_next);
		TextView destext = (TextView) linearlayout
				.findViewById(R.id.child_list_des);

		// nexttext.setText(next);
		destext.setText(des);
		return linearlayout;
	}

	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return childs.get(groupPosition).size();
	}

	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return group.get(groupPosition);
	}

	public int getGroupCount() {
		// TODO Auto-generated method stub
		return group.size();
	}

	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@SuppressWarnings("unchecked")
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		String time = ((HashMap<String, Object>) getGroup(groupPosition)).get(
				"update_time").toString();
		String now = ((HashMap<String, Object>) getGroup(groupPosition)).get(
				"station_account").toString();

		LayoutInflater layoutinflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		LinearLayout linearlayout = (LinearLayout) layoutinflater.inflate(
				R.layout.group_list, null);

		TextView timetext = (TextView) linearlayout
				.findViewById(R.id.group_list_time);
		TextView nowtext = (TextView) linearlayout
				.findViewById(R.id.group_list_now);

		timetext.setText(time);
		nowtext.setText(now);
		return linearlayout;
	}

	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeAll() {
		group.clear();
		childs.clear();
	}

	public void upDate(List<HashMap<String, Object>> group,
			List<HashMap<String, Object>> childs) {
		this.group = group;
		this.childs = childs;
	}

}
