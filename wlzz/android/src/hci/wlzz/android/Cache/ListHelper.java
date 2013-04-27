package hci.wlzz.android.Cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ListHelper extends SQLiteOpenHelper {
	static final String DBNAME = "data.db";
	private static final int VERSION = 1;
	static final String TABLENAME = "list";
	SQLiteDatabase db;

	public ListHelper(Context context) {
		super(context, DBNAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table if not exists " + TABLENAME
				+ "(flag varchar(10),stationName varchar(20),Info text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
