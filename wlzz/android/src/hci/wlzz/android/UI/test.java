package hci.wlzz.android.UI;

import hci.wlzz.android.HttpRequest.HttpClient;
import hci.wlzz.android.Utils.JsonUtils;
import hci.wlzz.android.sql.DAOFactory;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;

public class test extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		String jsonArray = new HttpClient().sendHttpGet("/stations");
		System.out.println(jsonArray);

		new JsonUtils(getApplicationContext()).JsonToModels(jsonArray);
		System.out.println(DAOFactory.getStationInstance(this)
				.getReadableDatabase().rawQuery("select * from station", null)
				.getCount());

		// StationHelper stationHelper;
		// SQLiteDatabase db;
		// Gson gson = new Gson();
		// JSONObject jo;
		// JSONArray ja;
		// try {
		// jo = new JSONObject(jsonArray);
		// Station station = new Station();
		// StationManager manager = new StationManager();
		// if (jo.has("stations")) {
		// stationHelper = DAOFactory.getStationInstance(this);
		// db = stationHelper.getWritableDatabase();
		// stationHelper.createTable(db);
		// ja = new JSONArray(jo.getString("stations"));
		// System.out.println(jo.getString("stations"));
		// for (int i = 0; i < ja.length(); i++) {
		// station = gson
		// .fromJson(ja.get(i).toString(), Station.class);
		// try {
		// stationHelper.insert(db, station);
		// } catch (Exception e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// }
		// System.out.println(stationHelper.getReadableDatabase()
		// .rawQuery("select * from station", null).getCount());
		// stationHelper.close();
		// }
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}
}
