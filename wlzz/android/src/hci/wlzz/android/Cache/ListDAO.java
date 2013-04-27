package hci.wlzz.android.Cache;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ListDAO {
	private ContentValues values;
	private SQLiteDatabase db;
	private ListHelper listHelper;
	Cursor cursor;
	public static String stationNameList = "stationNameList";
	public static String managerNameList = "managerNameList";
	public static String orderCodeList = "orderCodeList";
	JSONArray ja;
	JSONObject jo;

	public ListDAO(Context context) {
		listHelper = new ListHelper(context);
	}

	/**
	 * flag有三种：<br>
	 * "stationNameList":you known what I mean<br>
	 * ""
	 **/
	public void add(String flag, String stationName, String listJson) {
		db = listHelper.getWritableDatabase();
		values = new ContentValues();
		values.put("flag", flag);
		values.put("stationName", stationName);
		values.put("Info", listJson);
		db.insert(ListHelper.TABLENAME, null, values);
		db.close();
	}

	public void update(String flag, String stationName, String listJson) {
		values = new ContentValues();
		values.put("Info", listJson);
		db.update(ListHelper.TABLENAME, values, "flag=?,stationName=?",
				new String[] { listJson });
		db.close();
	}

	public List<String> getStationNameList() {
		db = listHelper.getReadableDatabase();
		cursor = db.rawQuery("select * from " + ListHelper.TABLENAME
				+ " where flag=?", new String[] { stationNameList });
		cursor.moveToFirst();
		String info = cursor.getString(cursor.getColumnIndex("Info"));
		cursor.close();
		List<String> list = new ArrayList<String>();
		try {
			ja = new JSONArray(info);
			for (int i = 0; i < ja.length(); i++) {
				list.add(ja.get(i).toString());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<String> getManagerNameList(String stationName) {
		db = listHelper.getReadableDatabase();
		cursor = db.rawQuery("select Info from " + ListHelper.TABLENAME
				+ " where flag=?,stationName=?", new String[] {
				managerNameList, stationName });
		cursor.moveToFirst();
		String info = cursor.getString(cursor.getColumnIndex("Info"));
		cursor.close();
		List<String> list = new ArrayList<String>();
		try {
			ja = new JSONArray(info);
			for (int i = 0; i < ja.length(); i++) {
				list.add(ja.get(i).toString());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<String> getOrderCodeNameList(String stationName) {
		db = listHelper.getReadableDatabase();
		cursor = db.rawQuery("select Info from " + ListHelper.TABLENAME
				+ " where flag=?,stationName=?", new String[] { orderCodeList,
				stationName });
		cursor.moveToFirst();
		String info = cursor.getString(cursor.getColumnIndex("Info"));
		cursor.close();
		List<String> list = new ArrayList<String>();
		try {
			ja = new JSONArray(info);
			for (int i = 0; i < ja.length(); i++) {
				list.add(ja.get(i).toString());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public void detele(String flag, String stationName) {
		db = listHelper.getWritableDatabase();
		db.execSQL("delete * from " + ListHelper.TABLENAME
				+ " where flag=?,stationName=?", new String[] { flag,
				stationName });
		db.close();
	}

}
