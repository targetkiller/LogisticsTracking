package hci.wlzz.android.UI;

import hci.wlzz.android.Models.Station;
import hci.wlzz.android.Utils.GlobalVariable;
import hci.wlzz.android.sql.DAOFactory;
import hci.wlzz.android.sql.StationHelper;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

public class EditStationInfo extends Activity {
	Button save = null;
	Button cancel = null;

	TextView stationname, stationaccount, stationpassword, stationaddress,
			stationphone, stationonlyone;
	
	StationHelper stationHelper=new DAOFactory().getStationInstance(EditStationInfo.this);
	SQLiteDatabase db=stationHelper.getWritableDatabase();
	Station station =null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.stationinfo_edit);
		save = (Button) findViewById(R.id.stationinfoedit);
		cancel = (Button) findViewById(R.id.stationinfocancel);

		stationname = (TextView) findViewById(R.id.stationname);
		stationaccount = (TextView) findViewById(R.id.stationaccount);
		stationpassword = (TextView) findViewById(R.id.stationpassword);
		stationaddress = (TextView) findViewById(R.id.stationaddress);
		stationphone = (TextView) findViewById(R.id.stationphone);
		stationonlyone = (TextView) findViewById(R.id.stationonlyone);

		/*Intent it = getIntent();
		Gson gson = new Gson();
		String stationString = it.getStringExtra("station");
		Station station = gson.fromJson(stationString, Station.class);

		System.out.println(stationString);*/
		
		//从本地数据库获取工作站信息并显示
		int id=GlobalVariable.station.getId();
		
		try {
			station=stationHelper.queryById(db, id);
			stationname.setText(station.getId() + "");
			stationaccount.setText(station.getAccount());
			stationpassword.setText(station.getPassword());
			stationaddress.setText(station.getAddress());
			stationphone.setText(station.getPhone());
			stationonlyone.setText(station.getOnlineNum() + "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		save.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Station tmp = new Station();
				tmp.setAccount(stationaccount.getText().toString());
				tmp.setAddress(stationaddress.getText().toString());
				tmp.setOnlineNum(Integer.parseInt(stationonlyone.getText()
						.toString()));
				tmp.setPassword(stationpassword.getText().toString());
				tmp.setPhone(stationphone.getText().toString());
				tmp.setId(station.getId());
//			发送请求保存，代码待写
				
				
				Intent intent = new Intent();
				intent.setClass(EditStationInfo.this, StationList.class);
				EditStationInfo.this.finish();
				startActivity(intent);

			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			// TODO Auto-generated method stub
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(EditStationInfo.this, OrderList.class);
				EditStationInfo.this.finish();
				startActivity(intent);

			}
		});
	}
}