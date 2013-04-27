package hci.wlzz.android.UI;

import com.google.gson.Gson;

import hci.wlzz.android.Models.Station;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class StationInfo extends Activity {
	String stationString;
	Station station;

	Button edit = null;
	Button cancel = null;

	TextView stationname, stationaccount, stationpassword, stationaddress,
			stationphone, stationonlyone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.stationinfo);
		edit = (Button) findViewById(R.id.stationinfoedit);
		cancel = (Button) findViewById(R.id.stationinfocancel);

		stationname = (TextView) findViewById(R.id.stationname);
		stationaccount = (TextView) findViewById(R.id.stationaccount);
		stationpassword = (TextView) findViewById(R.id.stationpassword);
		stationaddress = (TextView) findViewById(R.id.stationaddress);
		stationphone = (TextView) findViewById(R.id.stationphone);
		stationonlyone = (TextView) findViewById(R.id.stationonlyone);

		Intent it = getIntent();
		Gson gson = new Gson();
		stationString = it.getStringExtra("station");
		Station station = gson.fromJson(stationString, Station.class);

		// stationname.setText(station.getId() + "station.getId");
		stationaccount.setText(station.getAccount());
		stationpassword.setText(station.getPassword());
		stationaddress.setText(station.getAddress());
		stationphone.setText(station.getPhone());
		stationonlyone.setText(station.getOnlineNum() + "");

		edit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("station", stationString);
				intent.setClass(StationInfo.this, EditStationInfo.class);
				StationInfo.this.finish();
				startActivity(intent);

			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			// TODO Auto-generated method stub
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(StationInfo.this, OrderList.class);
				StationInfo.this.finish();
				startActivity(intent);

			}
		});
	}
}