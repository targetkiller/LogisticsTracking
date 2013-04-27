package hci.wlzz.android.UI;

import hci.wlzz.android.HttpRequest.HttpClient;
import hci.wlzz.android.Models.UserInfo;
import hci.wlzz.android.Utils.GlobalVariable;
import hci.wlzz.android.sql.DAOFactory;
import hci.wlzz.android.sql.UserInfoHelper;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class PersonInfo extends Activity {

	UserInfo userInfo = new UserInfo();
	String name;
	TextView username, useremail, userage, usergender, userphone, userzipcode,
			useraddress;

	Button edit = null;
	Button save = null;
	Button cancel = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfo);
		edit = (Button) findViewById(R.id.userinfoedit);
		cancel = (Button) findViewById(R.id.userinfocancel);

		username = (TextView) findViewById(R.id.username);
		useremail = (TextView) findViewById(R.id.useremail);
		userage = (TextView) findViewById(R.id.userage);
		usergender = (TextView) findViewById(R.id.usergender);
		userphone = (TextView) findViewById(R.id.userphone);
		userzipcode = (TextView) findViewById(R.id.userzipcode);
		useraddress = (TextView) findViewById(R.id.useraddress);

		// 从本地数据库获取管理员信息并显示
		name = getIntent().getStringExtra("name");
		if (name != null) {
			showUserInfo();
		} else {
			showCurrentUserInfo();
		}

		edit.setOnClickListener(new OnClickListener() {

			// TODO Auto-generated method stub
			public void onClick(View v) {
				setContentView(R.layout.userinfo_edit);

				username = (EditText) findViewById(R.id.username);
				useremail = (EditText) findViewById(R.id.useremail);
				userage = (EditText) findViewById(R.id.userage);
				usergender = (TextView) findViewById(R.id.usergender);
				userphone = (EditText) findViewById(R.id.userphone);
				userzipcode = (EditText) findViewById(R.id.userzipcode);
				useraddress = (EditText) findViewById(R.id.useraddress);
				save = (Button) findViewById(R.id.save);
				cancel = (Button) findViewById(R.id.userinfocancel);
				if (name != null) {
					showUserInfo();
				} else {
					showCurrentUserInfo();
				}

				save.setOnClickListener(new SaveOnClilckListener());

				cancel.setOnClickListener(new OnClickListener() {

					// TODO Auto-generated method stub
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(PersonInfo.this, StationList.class);
						PersonInfo.this.finish();
						startActivity(intent);
					}
				});

			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			// TODO Auto-generated method stub
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(PersonInfo.this, StationList.class);
				PersonInfo.this.finish();
				startActivity(intent);

			}
		});
	}

	private void showUserInfo() {
		try {
			UserInfoHelper userInfoHelper = DAOFactory
					.getUserInfoInstance(PersonInfo.this);
			SQLiteDatabase db = userInfoHelper.getWritableDatabase();
			Cursor cs = db.rawQuery("select * from user_info where name = ?",
					new String[] { name });
			cs.moveToFirst();

			/*
			 * 保存全局变量usernfo的id，便于保存操作
			 */
			userInfo.setId(cs.getInt(cs.getColumnIndex("id")));

			/*
			 * 设置显示的内容
			 */
			username.setText(name);
			useremail.setText(cs.getString(cs.getColumnIndex("email")));
			userage.setText(cs.getInt(cs.getColumnIndex("age")) + "");
			usergender
					.setText(cs.getInt(cs.getColumnIndex("gender")) == 1 ? "male"
							: "female");
			userphone.setText(cs.getString(cs.getColumnIndex("phone")));
			userzipcode.setText(cs.getString(cs.getColumnIndex("zipCode")));
			useraddress.setText(cs.getString(cs.getColumnIndex("address")));
			cs.close();
			db.close();
			userInfoHelper.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showCurrentUserInfo() {
		try {
			userInfo = GlobalVariable.userInfo;
			username.setText(userInfo.getName());
			useremail.setText(userInfo.getEmail());
			userage.setText(userInfo.getAge() + "");
			usergender.setText(userInfo.getGender() ? "male" : "female");
			userphone.setText(userInfo.getPhone());
			userzipcode.setText(userInfo.getZipCode());
			useraddress.setText(userInfo.getAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class SaveOnClilckListener implements OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				userInfo.setName(username.getText().toString());
				userInfo.setEmail(useremail.getText().toString());
				userInfo.setAge(Integer.parseInt(userage.getText().toString()));
				userInfo.setGender(usergender.getText().toString()
						.equals("male") ? true : false);
				userInfo.setPhone(userphone.getText().toString());
				userInfo.setZipCode(userzipcode.getText().toString());
				userInfo.setAddress(useraddress.getText().toString());
				/*
				 * 更新本地数据库
				 */
				UserInfoHelper userInfoHelper = DAOFactory
						.getUserInfoInstance(PersonInfo.this);
				SQLiteDatabase db = userInfoHelper.getWritableDatabase();
				userInfoHelper.update(db, userInfo);
				db.close();
				userInfoHelper.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			 /*
			  * 更新远程服务器的数据
			 */
			saveRequest();
		}

		 /*
		  * 更新远程服务器的数据的方法
		 */
		private void saveRequest() {
			Gson gson = new Gson();
			String json = gson.toJson(userInfo, UserInfo.class);
			try {
				JSONObject jo = new JSONObject(json);
				System.out.println(jo.toString());
				String response = new HttpClient().sendJSON("/save/userInfo",
						jo);
				jo =new JSONObject(response);
				if(jo.get("status").equals("0")){
					Toast.makeText(getApplicationContext(), "成功保存", Toast.LENGTH_SHORT).show();
				}
				if(jo.get("status").equals("1")){
					Toast.makeText(getApplicationContext(), "网络不好，再保存一次", Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
