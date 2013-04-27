package hci.wlzz.android.UI;

import hci.wlzz.android.HttpRequest.HttpClient;
import hci.wlzz.android.Models.OrderForm;
import hci.wlzz.android.sql.DAOFactory;
import hci.wlzz.android.sql.OrderFormHelper;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

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

public class OrderInfo extends Activity {
	Button edit = null;
	Button cancel = null;
	Button save = null;
	int orderFormId;
	TextView ordername, orderaddress, orderzipcode, ordercontent, orderamount,
			orderweight, ordercode, orderbarcode, ordercreatetime, orderuserid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.orderinfo);
		edit = (Button) findViewById(R.id.orderinfoedit);
		getTextView();
		
		// 从本地数据库获取订单信息并显示
		orderFormId = getIntent().getIntExtra("orderFormId", 1);
		showOrder();

		edit.setOnClickListener(new OnClickListener() {
			// TODO Auto-generated method stub
			public void onClick(View v) {
				setContentView(R.layout.orderinfo_edit);				
				save = (Button) findViewById(R.id.save);
				getTextView();
				showOrder();
				save.setOnClickListener(new EditOnClickListener());

			}
		});

		cancel.setOnClickListener(new OnClickListener() {
			// TODO Auto-generated method stub
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(OrderInfo.this, OrderList.class);
				OrderInfo.this.finish();
				startActivity(intent);

			}
		});
	}
	
	public void getTextView(){
		cancel = (Button) findViewById(R.id.orderinfocancel);
		ordername = (TextView) findViewById(R.id.ordername);
		orderaddress = (TextView) findViewById(R.id.orderaddress);
		orderzipcode = (TextView) findViewById(R.id.orderzipcode);
		ordercontent = (TextView) findViewById(R.id.ordercontent);
		orderamount = (TextView) findViewById(R.id.orderamount);
		orderweight = (TextView) findViewById(R.id.orderweight);
		ordercode = (TextView) findViewById(R.id.ordercode);
		orderbarcode = (TextView) findViewById(R.id.orderbarcode);
		ordercreatetime = (TextView) findViewById(R.id.ordercreatetime);
		orderuserid = (TextView) findViewById(R.id.orderuserid);
	}

	public void showOrder() {
		OrderFormHelper orderFormHelper = DAOFactory
				.getOrderFormInstance(OrderInfo.this);
		SQLiteDatabase db = orderFormHelper.getWritableDatabase();
		OrderForm orderform = null;
		try {
			orderform = orderFormHelper.queryById(db, orderFormId);
			ordername.setText(orderform.getId() + "");
			orderaddress.setText(orderform.getAddress());
			orderzipcode.setText(orderform.getZipCode());
			ordercontent.setText(orderform.getContent());
			orderamount.setText(orderform.getAmount() + "");
			orderweight.setText(orderform.getWeight() + "");
			ordercode.setText(orderform.getCode());
			orderbarcode.setText(orderform.getBarcode());
			ordercreatetime.setText(orderform.getCreateTime() + "");
			orderuserid.setText(orderform.getUserId() + "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class EditOnClickListener implements OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case 0:
				save();
				break;
			case 1:
				break;
			}
		}

		public void save() {
			OrderForm tmp = new OrderForm();
			tmp.setId(Integer.parseInt(ordername.getText().toString()));
			tmp.setAddress(orderaddress.getText().toString());
			tmp.setZipCode(orderzipcode.getText().toString());
			tmp.setContent(ordercontent.getText().toString());
			tmp.setAmount(Integer.parseInt(orderamount.getText().toString()));
			tmp.setWeight(Float.valueOf(orderweight.getText().toString()));
			tmp.setCode(ordercode.getText().toString());
			tmp.setBarcode(orderbarcode.getText().toString());
			tmp.setCreateTime(Integer.parseInt(ordercreatetime.getText()
					.toString()));
			tmp.setUserId(Integer.parseInt(orderuserid.getText().toString()));

//			未保存
			setContentView(R.layout.orderinfo);
		}
	}
}
