package hci.wlzz.android.UI;

import hci.wlzz.android.HttpRequest.HttpClient;
import hci.wlzz.android.Models.Station;
import hci.wlzz.android.Models.StationManager;
import hci.wlzz.android.Utils.GlobalVariable;
import hci.wlzz.android.Utils.JsonUtils;
import hci.wlzz.android.sql.DAOFactory;
import hci.wlzz.android.sql.StationHelper;
import hci.wlzz.android.sql.StationManagerHelper;
import hci.wlzz.android.sql.UserInfoHelper;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class StationList extends Activity {

	public static Station[] stationIntent;
	Gson gson;
	Station station;
	StationManager stationManager;
	private String[] armTypes;
	private List<List<String>> arms;
	private int stationLength;
	String response;

	ImageView userinfo = null;
	ImageView refresh = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.stationexpandlist);
		userinfo = (ImageView) findViewById(R.id.userinfo);
		refresh = (ImageView) findViewById(R.id.refresh);

		userinfo.setOnClickListener(new OnClickListener() {

			// TODO Auto-generated method stub
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(StationList.this, PersonInfo.class);
				StationList.this.startActivity(intent);

			}
		});

		refresh.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				getArmTypes();
				getArms();
			}

		});

		getArmTypes();
		getArms();
		// 重写BaseExpandableListAdapter
		ExpandableListAdapter adapter = new MyExpandableListAdapter();
		ExpandableListView expandListView = (ExpandableListView) findViewById(R.id.stationListView);
		expandListView.setAdapter(adapter);
		expandListView.setOnItemLongClickListener(new MyLongClickListener());
		expandListView.setOnChildClickListener(new MyShortClickListener());
	}

	// 从数据库取得managers的信息并将信息赋值给ExpandableListView的arms
	private void getArms() {
		UserInfoHelper uiHelper = DAOFactory
				.getUserInfoInstance(StationList.this);
		StationManagerHelper smHelper = DAOFactory
				.getStationManagerInstance(StationList.this);
		SQLiteDatabase uiDB;
		SQLiteDatabase smDB;
		arms = new ArrayList<List<String>>();

		for (int i = 0; i < stationLength; i++) {
			List<String> tempList = new ArrayList<String>();
			smDB = smHelper.getReadableDatabase();
			Cursor smCursor = smDB.rawQuery(
					"select * from station_manager where stationId =?",
					new String[] { stationIntent[i].getId() + "" });
			smCursor.moveToFirst();
			for (int j = 0; j < smCursor.getCount(); j++) {
				uiDB = uiHelper.getReadableDatabase();
				Cursor uiCursor = uiDB.rawQuery(
						"select * from user_info where id =?",
						new String[] { smCursor.getInt(smCursor
								.getColumnIndex("userId")) + "" });
				uiCursor.moveToFirst();
				if (uiCursor.getCount() != 0) {
					tempList.add(uiCursor.getString(uiCursor
							.getColumnIndex("name")));
				}
				smCursor.moveToNext();
				uiCursor.close();
				uiDB.close();

			}
			smCursor.close();
			smDB.close();
			arms.add(tempList);
		}
		smHelper.close();
	}

	private void getArmTypes() {

		StationHelper stationHelper = DAOFactory
				.getStationInstance(StationList.this);
		SQLiteDatabase stationDB = null;
		List<Station> stationList = null;

		stationDB = stationHelper.getReadableDatabase();
		try {
			stationList = stationHelper.queryAll(stationDB);
			stationLength = stationList.size();
			stationIntent = new Station[stationList.size()];
			armTypes = new String[stationLength];

			for (int i = 0; i < stationList.size(); i++) {
				stationIntent[i] = stationList.get(i);
				armTypes[i] = stationIntent[i].getAccount();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR");
			e.printStackTrace();
		}
		stationDB.close();
		stationHelper.close();

	}

	class MyExpandableListAdapter extends BaseExpandableListAdapter {
		// armTypes和 arms 是全局变量，已经在前面赋值了

		public boolean isChildSelectable(int groupPosition, int childPosition) {

			return true;
		}

		public boolean hasStableIds() {

			return true;
		}

		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {

			LinearLayout l1 = new LinearLayout(StationList.this);
			l1.setOrientation(0);

			// ImageView logo = new ImageView(ExpandableListViewList.this);
			// logo.setImageResource(R.drawable.stationcon);
			// l1.addView(logo);
			TextView textView = getTextView();
			textView.setText("    " + getGroup(groupPosition).toString());
			l1.addView(textView);
			return l1;
		}

		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		public int getGroupCount() {
			// TODO Auto-generated method stub
			return armTypes.length;
		}

		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return armTypes[groupPosition];
		}

		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return arms.get(groupPosition).size();
		}

		private TextView getTextView() {

			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT, 64);
			TextView textView = new TextView(StationList.this);
			textView.setLayoutParams(lp);
			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			textView.setPadding(36, 0, 0, 0);
			textView.setTextSize(20);
			return textView;
		}

		// TODO Auto-generated method stub
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			LinearLayout l1 = new LinearLayout(StationList.this);
			l1.setOrientation(0);
			ImageView logo = new ImageView(StationList.this);
			logo.setImageResource(R.drawable.managericon);
			l1.addView(logo);
			TextView textView = getTextView();
			textView.setText(getChild(groupPosition, childPosition).toString());
			l1.addView(textView);
			return l1;
		}

		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return arms.get(groupPosition).get(childPosition);
		}
	}

	class MyLongClickListener implements OnItemLongClickListener {
		// TODO Auto-generated method stub
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			AlertDialog.Builder adb = new Builder(StationList.this);
			adb.setTitle("选项");
			final int arg = arg2;
			adb.setItems(
					new String[] { "进入工作站", "查看工作站信息", "添加工作站管理员", "删除工作站" },
					new DialogInterface.OnClickListener() {
						// TODO Auto-generated method stub
						public void onClick(DialogInterface dialog, int which) {
							switch (which) {
							case 0:
								Intent intent0 = new Intent();
								intent0.setClass(StationList.this,
										StationLogin.class);
								StationList.this.finish();
								startActivity(intent0);
								break;

							case 1:
								Intent intent1 = new Intent();
								intent1.putExtra("station",
										gson.toJson(stationIntent[arg]));
								intent1.setClass(StationList.this,
										StationInfo.class);
								StationList.this.finish();
								startActivity(intent1);
								break;
							case 2:
								Intent intent2 = new Intent();
								intent2.setClass(StationList.this,
										StationManagerList.class);
								StationList.this.finish();
								startActivity(intent2);

								break;
							case 3:
								Toast.makeText(StationList.this, "删除工作站成功",
										Toast.LENGTH_SHORT).show();
								break;
							}
						}
					});
			adb.show();
			return false;
		}
	}

	class MyShortClickListener implements OnChildClickListener {

		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			// TODO Auto-generated method stub

			AlertDialog.Builder adb = new Builder(StationList.this);
			adb.setTitle("选项");
			final int groupPosition0 = groupPosition;
			final int childPosition0 = childPosition;
			adb.setItems(new String[] { "查看个人信息", "删除该管理员" },
					new DialogInterface.OnClickListener() {
						// TODO Auto-generated method stub
						public void onClick(DialogInterface dialog, int which) {
							switch (which) {
							case 0:
								Intent intent = new Intent();
								intent.putExtra("name", arms
										.get(groupPosition0)
										.get(childPosition0));
								intent.setClass(StationList.this,
										PersonInfo.class);
								StationList.this.finish();
								startActivity(intent);
								break;
							case 1:
								Toast.makeText(StationList.this, "删除该管理员成功",
										Toast.LENGTH_SHORT).show();
								break;
							}
						}
					});
			adb.show();
			return false;
		}
		// TODO Auto-generated method stub
	}

}
