package hci.wlzz.android.Cache;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import hci.wlzz.android.Model.Manager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ManagerDAO {
	ManagerHelper managerHelper;
	SQLiteDatabase db;

	public ManagerDAO(Context context) {
		managerHelper = new ManagerHelper(context);
	}

	/* 添加管理员 */
	public void add(Manager manager) {
		SQLiteDatabase db = managerHelper.getWritableDatabase();
		ContentValues value = new ContentValues();
		Gson gson = new Gson();
		String info = gson.toJson(manager, Manager.class);
		value.put("Id", manager.getManagerId() + "");
		value.put("Info", info);
		db.insert("manager", null, value);
		db.close();
	}

	/* 修改管理员 */
	public void update(Manager manager) {
		db = managerHelper.getWritableDatabase();
		ContentValues value = new ContentValues();
		Gson gson = new Gson();
		String info = gson.toJson(manager, Manager.class);
		value.put("Info", info);
		db.update("manager", value, "Id=?",
				new String[] { Integer.valueOf(manager.getManagerId())
						.toString() });
		db.close();
	}

	/* 查找管理员 */
	public Manager find(int id) {
		db = managerHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select Info from manager where Id=?",
				new String[] { String.valueOf(id) });
		cursor.moveToFirst();
		String info = cursor.getString(cursor.getColumnIndex("Info"));
		Gson gson = new Gson();
		Manager manager = gson.fromJson(info, Manager.class);
		cursor.close();
		db.close();
		return manager;
	}

	/* 删除管理员 */
	public void detele(int managerId) {
		db = managerHelper.getWritableDatabase();
		db.delete("manager", "Id=?", new String[] { managerId + "" });
		db.close();
	}

	/* 获取管理员数量 */
	public int getCount() {
		db = managerHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select count(Id) from manager", null);
		if (cursor.moveToNext()) {
			int count = Integer.parseInt(Long.valueOf(cursor.getLong(0))
					.toString());
			cursor.close();
			db.close();
			return count;
		}
		return 0;
	}

	/* 获取管理员列表 */
	public List<Manager> getScrollData(int start, int count) {
		List<Manager> managerList = new ArrayList<Manager>();
		db = managerHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from manager limit ?,?",
				new String[] { String.valueOf(start), String.valueOf(count) });
		while (cursor.moveToNext()) {
			String info = cursor.getString(cursor.getColumnIndex("Info"));
			Gson gson = new Gson();
			Manager manager = gson.fromJson(info, Manager.class);
			managerList.add(manager);
		}
		cursor.close();
		db.close();
		return managerList;
	}

	public void closeDatabase() {
		db.close();
	}

}
