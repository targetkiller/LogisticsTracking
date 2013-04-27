package hci.wlzz.android.UI;

import hci.wlzz.android.HttpRequest.HttpClient;
import hci.wlzz.android.Models.OrderDetail;
import hci.wlzz.android.Models.Station;
import hci.wlzz.android.sql.DAOFactory;
import hci.wlzz.android.sql.StationHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

public class Search extends Activity {
	private ImageButton seekbtn = null;
	private ImageView loginiv = null;
	private EditText inputext = null;
	private ExpandableListView orderelistlv = null;
	private ExpandAdapter orderlistea = null;
	public List<HashMap<String, Object>> mParentList = null;
	public List<HashMap<String, Object>> mChildList = null;
	private VerifyCodeView vcv = null;
	private RelativeLayout rl = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		Init();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem del = menu.add(Menu.NONE, 1, 1, "清楚数据");
		MenuItem disexpand = menu.add(Menu.NONE, 2, 2, "收起全部");

		del.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				if (orderelistlv != null) {
					orderlistea.removeAll();
					orderlistea.notifyDataSetChanged();
				}
				setHeight();
				rl.scrollTo(0, 0);
				inputext.setText("");
				return false;
			}
		});

		disexpand.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				if (orderelistlv != null && orderlistea != null) {
					for (int i = 0; i < orderlistea.getGroupCount(); i++) {
						orderelistlv.collapseGroup(i);
					}
				}
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
	}

	@SuppressLint("ParserError")
	public void Init() {
		rl = (RelativeLayout) findViewById(R.id.search_rl_backgrund);
		rl.scrollTo(0, 0);

		ButtonListener clicked = new ButtonListener();
		seekbtn = (ImageButton) findViewById(R.id.search_imgBtn_search_button);
		seekbtn.setOnClickListener(clicked);

		loginiv = (ImageView) findViewById(R.id.search_imgView_login_button);
		loginiv.setOnClickListener(clicked);

		inputext = (EditText) findViewById(R.id.search_ext_input);
		inputext.setText("");

		orderelistlv = (ExpandableListView) findViewById(R.id.search_elitView_orderlist);
		orderelistlv.setDivider(null);

		mParentList = new ArrayList<HashMap<String, Object>>();
		mChildList = new ArrayList<HashMap<String, Object>>();
	}

	// 获得数据的list
	public boolean getList(String Code) {

		HashMap<String, Object> tempParentMap = null;
		HashMap<String, Object> tempChildMap = null;

		boolean flag = false;

		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("code", inputext.getText().toString()));
		String response = new HttpClient().sendHttpPost("/order/search", param);
		tempParentMap = new HashMap<String, Object>();
		tempChildMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		try {
			JSONArray ja = new JSONArray(response);
			OrderDetail orderDetail = null;
			Station station = null;
			StationHelper stationHelper = DAOFactory
					.getStationInstance(Search.this);
			SQLiteDatabase db = stationHelper.getReadableDatabase();
			for (int i = 0; i < ja.length(); i++) {
				orderDetail = gson.fromJson(ja.get(i).toString(),
						OrderDetail.class);
				tempParentMap.put("update_time", orderDetail.getUpdateTime());
				try {
					station = stationHelper.queryById(db,
							orderDetail.getStationId());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tempParentMap.put("station_account", station.getAccount());
				tempChildMap.put("description", orderDetail.getDescription());

				mParentList.add(tempParentMap);
				mChildList.add(tempChildMap);

			}
			flag = true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return flag;
	}

	// 收起键盘
	public void hideKeyboard(EditText input) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
	}

	// 設置高度
	public void setHeight() {
		ViewGroup.LayoutParams orderlist_h = null, rl_h = null;
		DisplayMetrics dm = new DisplayMetrics();

		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int height = dm.heightPixels;

		if (orderelistlv == null)
			return;

		orderlist_h = orderelistlv.getLayoutParams();
		rl_h = rl.getLayoutParams();

		rl_h.height = height
				+ ((ImageView) findViewById(R.id.search_imgView_title))
						.getHeight();

		orderlist_h.height = height - 4 * inputext.getHeight();

		rl.setLayoutParams(rl_h);
		orderelistlv.setLayoutParams(orderlist_h);
	}

	public class ButtonListener implements OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			orderlistea.removeAll();
			orderlistea.notifyDataSetChanged();

			hideKeyboard(inputext);

			int id = v.getId();

			switch (id) {
			case R.id.search_imgView_login_button:
				clickButton1();
				break;
			case R.id.search_imgBtn_search_button:
				clickButton2();
				break;
			default:
				break;
			}
		}

		// 后台登陆
		public void clickButton1() {
			Intent intent = new Intent(Search.this, Login.class);
			startActivity(intent);
		}

		// 查詢
		public void clickButton2() {
			if (getList(inputext.getText().toString())) {
				vcv = new VerifyCodeView(Search.this);
				new AlertDialog.Builder(Search.this).setView(vcv)
						.setPositiveButton("确定", new PostiveButtonListener())
						.setNegativeButton("取消", null).show();

			} else {
				orderlistea.removeAll();
				orderlistea.notifyDataSetChanged();
				Toast.makeText(Search.this, "找不到该订单", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	public class PostiveButtonListener implements
			DialogInterface.OnClickListener {

		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			if (vcv.checkVerifyCode()) {
				setHeight();

				orderlistea.upDate(mParentList, mChildList);
				orderlistea.notifyDataSetChanged();

				rl.scrollTo(
						0,
						((ImageView) findViewById(R.id.search_imgView_title))
								.getHeight() + 20 + 2 * loginiv.getHeight());
				orderlistea = new ExpandAdapter(Search.this, mParentList,
						mChildList);
				orderelistlv.setAdapter(orderlistea);
			} else {
				Toast.makeText(Search.this, "验证码错误", Toast.LENGTH_SHORT).show();
				seekbtn.performClick();
			}
		}
	}
}
