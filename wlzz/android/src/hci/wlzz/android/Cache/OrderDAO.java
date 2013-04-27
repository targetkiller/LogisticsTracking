package hci.wlzz.android.Cache;

import java.util.ArrayList;
import java.util.List;

import hci.wlzz.android.Model.Order;

import com.google.gson.Gson;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OrderDAO {
	private SQLiteDatabase db;
	private OrderHelper orderHelper;
	public OrderDAO(Context context)
	{
		orderHelper=new OrderHelper(context);
	}
	
	/*		添加订单		*/
	public void add(Order order)
	{
		db = orderHelper.getWritableDatabase();
		ContentValues value=new ContentValues();
		Gson gson =new Gson();
		String info=gson.toJson(order,Order.class);
		value.put("Id", order.getOrderFormId()+"");
		value.put("Info",info);
		db.insert("order", null, value);
		db.close();
	}
	
	/*		修改订单		*/
	public void update(Order order)
	{
		db = orderHelper.getWritableDatabase();
		ContentValues value=new ContentValues();
		Gson gson =new Gson();
		String info=gson.toJson(order,Order.class);
		value.put("Info",info);
		db.update("order",value,"Id=?",new String[]{Integer.valueOf(order.getOrderFormId()).toString()});
		db.close();
	}
	
	/*		查找订单		*/
	public Order find(int id)
	{
		db = orderHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select Info from order where Id=?",new String[]{String.valueOf(id)});
		if (cursor.moveToNext())
		{
			String info=cursor.getString(cursor.getColumnIndex("Info"));
			Gson gson =new Gson();
			Order order=gson.fromJson(info, Order.class);
			cursor.close();
			return order;
		}
		db.close();
		return null;
	}
	
	/*		删除订单		*/
	public void detele(Order order)
	{
		db = orderHelper.getWritableDatabase();
		db.delete("manager", "Id=?", new String[]{Integer.valueOf(order.getOrderFormId()).toString()});
		db.close();
	}
	
	/*		获取订单数量		*/
	public int getCount()
	{
		db = orderHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select count(Id) from order", null);
		if (cursor.moveToNext())
		{
			int count=Integer.parseInt(Long.valueOf(cursor.getLong(0)).toString());
			cursor.close();
			db.close();
			return count;	
		}
		return 0;
	}
	
	/*		获取订单列表		*/
	public List<Order> getScrollData(int start, int count) {
		List<Order> orderList = new ArrayList<Order>();
		db = orderHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from order limit ?,?",
				new String[] { String.valueOf(start), String.valueOf(count) });
		while (cursor.moveToNext()) {
			String info = cursor.getString(cursor.getColumnIndex("Info"));
			Gson gson = new Gson();
			Order order = gson.fromJson(info, Order.class);
			orderList.add(order);
		}
		cursor.close();
		db.close();
		return orderList;
	}
	
	public void closeDatabase(){
		db.close();
	}

}
