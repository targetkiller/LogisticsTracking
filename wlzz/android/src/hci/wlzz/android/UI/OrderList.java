package hci.wlzz.android.UI;

import hci.wlzz.android.HttpRequest.HttpClient;
import hci.wlzz.android.Models.OrderDetail;
import hci.wlzz.android.sql.DAOFactory;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class OrderList extends Activity {
	private int[] armTypes;
	private String[][] arms;;
	private int orders_num;

	ImageView stationinfo = null;
	ImageView refresh = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.orderexpandlist);
		stationinfo = (ImageView) findViewById(R.id.stationinfo);
		refresh = (ImageView) findViewById(R.id.refresh);
		stationinfo.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(OrderList.this, StationInfo.class);
				OrderList.this.finish();
				OrderList.this.startActivity(intent);
			}

		});

		// 向服务器发送请求并获得ExpandableListView 每条list的内容，每條内容为订单号
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("queryWhat", "all"));
		String response = new HttpClient().sendHttpPost(HttpClient.BASE_URL
				+ "/orderList", param);
		try {
			JSONArray ja = new JSONArray(response);
			orders_num = ja.length();
			armTypes = new int[orders_num];
			arms = new String[orders_num][3];
			for (int i = 0; i < orders_num; i++) {
				JSONObject jo = (JSONObject) ja.get(i);
				Gson gson = new Gson();
				OrderDetail orderdetail = gson.fromJson(jo.toString(),
						OrderDetail.class);
				armTypes[i] = orderdetail.getOrderFormId();
				// 二级目录显示的订单内容
				arms[i][0] = "订单的Descripetion:" + orderdetail.getDescription();
				arms[i][1] = "当前所在工作站的id:" + orderdetail.getStationId();
				arms[i][2] = "下一工作站的id:" + orderdetail.getNextStationId();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ExpandableListAdapter adapter = new BaseExpandableListAdapter() {

			public boolean isChildSelectable(int groupPosition,
					int childPosition) {
				// TODO Auto-generated method stub
				return true;
			}

			public boolean hasStableIds() {

				return true;
			}

			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				LinearLayout l1 = new LinearLayout(OrderList.this);
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
				return arms[groupPosition].length;
			}

			private TextView getTextView() {

				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
						ViewGroup.LayoutParams.FILL_PARENT, 64);
				TextView textView = new TextView(OrderList.this);
				textView.setLayoutParams(lp);
				textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
				textView.setPadding(36, 0, 0, 0);
				textView.setTextSize(20);
				return textView;
			}

			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent) {
				LinearLayout l1 = new LinearLayout(OrderList.this);
				l1.setOrientation(0);
				ImageView logo = new ImageView(OrderList.this);
				logo.setImageResource(R.drawable.ordericon);
				l1.addView(logo);
				TextView textView = getTextView();
				textView.setText(getChild(groupPosition, childPosition)
						.toString());
				l1.addView(textView);
				return l1;
			}

			public long getChildId(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return childPosition;
			}

			public Object getChild(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return arms[groupPosition][childPosition];
			}
		};
		ExpandableListView expandListView = (ExpandableListView) findViewById(R.id.OrderListView);
		expandListView.setAdapter(adapter);

		expandListView
				.setOnItemLongClickListener(new OnItemLongClickListener() {
					// TODO Auto-generated method stub
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						AlertDialog.Builder adb = new Builder(OrderList.this);
						adb.setTitle("选项");
						final int arg = arg2;
						adb.setItems(new String[] { "查看或修改订单信息", "添加订单到站信息",
								"删除该订单" },
								new DialogInterface.OnClickListener() {
									// TODO Auto-generated method stub
									public void onClick(DialogInterface dialog,
											int which) {
										switch (which) {
										case 0:
											Intent intent = new Intent();
											intent.putExtra("orderFormId",
													armTypes[arg]);
											intent.setClass(OrderList.this,
													OrderInfo.class);
											OrderList.this
													.startActivity(intent);

											break;

										case 1:
											/*
											 * Intent intent2 = new Intent();
											 * intent2.putExtra("ItemText",
											 * map.get("ItemText") .toString());
											 * intent2
											 * .setClass(ExpandableListViewList
											 * .this, AddManager.class);
											 * startActivity(intent2);
											 */
											break;
										case 2:

											Toast.makeText(OrderList.this,
													"删除成功", Toast.LENGTH_SHORT)
													.show();
											break;
										}
									}
								});
						adb.show();
						return false;
					}
				});

		expandListView.setOnChildClickListener(new OnChildClickListener() {
			// TODO Auto-generated method stub
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				AlertDialog.Builder adb = new Builder(OrderList.this);
				adb.setTitle("选项");
				adb.setItems(new String[] { "修改该登录点", "删除该登录点" },
						new DialogInterface.OnClickListener() {
							// TODO Auto-generated method stub
							public void onClick(DialogInterface dialog,
									int which) {
								switch (which) {
								case 0:
									Intent intent = new Intent();
									intent.setClass(OrderList.this,
											OrderInfo.class);

									OrderList.this.finish();
									startActivity(intent);
									break;
								case 1:

									Toast.makeText(OrderList.this, "删除成功",
											Toast.LENGTH_SHORT).show();
									break;
								}
							}
						});
				adb.show();
				return false;

			}
		});
	}
}
