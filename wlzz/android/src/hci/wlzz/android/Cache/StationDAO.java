package hci.wlzz.android.Cache;

import hci.wlzz.android.Model.Station;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

public class StationDAO {
	private SQLiteDatabase db;
	private StationHelper stationHelper;
	public StationDAO(Context context)
	{
		stationHelper=new StationHelper(context);
	}
	
	/*		添加工作站		*/
	public void add(Station station)
	{
		db = stationHelper.getWritableDatabase();
		ContentValues value=new ContentValues();
		Gson gson =new Gson();
		String info=gson.toJson(station,Station.class);
		value.put("Id", station.getStationId()+"");
		value.put("Info",info);
		db.insert("station", null, value);
		db.close();
	}
	
	/*		修改工作站		*/
	public void update(Station station)
	{
		db = stationHelper.getWritableDatabase();
		ContentValues value=new ContentValues();
		Gson gson =new Gson();
		String info=gson.toJson(station,Station.class);
		value.put("Info",info);
		db.update("station",value,"Id=?",new String[]{Integer.valueOf(station.getStationId()).toString()});
		db.close();
	}
	
	/*		查找工作站		*/
	public Station find(int id)
	{
		db = stationHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select Info from station where Id=?",new String[]{String.valueOf(id)});
		if (cursor.moveToNext())
		{
			String info=cursor.getString(cursor.getColumnIndex("Info"));
			Gson gson =new Gson();
			Station station=gson.fromJson(info, Station.class);
			cursor.close();
			return station;
		}
		db.close();
		return null;
	}
	
	/*		删除工作站		*/
	public void detele(Station station)
	{
		db = stationHelper.getWritableDatabase();
		db.delete("station", "Id=?", new String[]{Integer.valueOf(station.getStationId()).toString()});
		db.close();
	}
	
	/*		获取工作站数量		*/
	public int getCount()
	{
		db = stationHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select count(Id) from station", null);
		if (cursor.moveToNext())
		{
			int count=Integer.parseInt(Long.valueOf(cursor.getLong(0)).toString());
			cursor.close();
			db.close();
			return count;	
		}
		return 0;
	}
	
	/*		获取工作站列表		*/
	public List<Station> getScrollData(int start, int count) {
		List<Station> stationList = new ArrayList<Station>();
		db = stationHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from station limit ?,?",
				new String[] { String.valueOf(start), String.valueOf(count) });
		while (cursor.moveToNext()) {
			String info = cursor.getString(cursor.getColumnIndex("Info"));
			Gson gson = new Gson();
			Station station = gson.fromJson(info, Station.class);
			stationList.add(station);
		}
		cursor.close();
		db.close();
		return stationList;
	}
	
	public void closeDatabase(){
		db.close();
	}
}
