package hci.wlzz.android.UI;

import hci.wlzz.android.HttpRequest.HttpClient;
import hci.wlzz.android.Models.User;
import hci.wlzz.android.Utils.GlobalVariable;
import hci.wlzz.android.Utils.JsonUtils;
import hci.wlzz.android.sql.DAOFactory;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class Login extends Activity {

	EditText account, password;
	Button submit;
	String strAccount, strPassword;
	String response = null;

	List<NameValuePair> param = new ArrayList<NameValuePair>();
	HttpClient httpClient = new HttpClient();
	JsonUtils jsonToObject;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);

		account = (EditText) findViewById(R.id.Peoplelogin_edit_account);
		password = (EditText) findViewById(R.id.Peoplelogin_edit_pwd);
		submit = (Button) findViewById(R.id.Peoplelogin_btn_login);
		jsonToObject = new JsonUtils(getApplicationContext());

		submit.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				// TODO Auto-generated method stub

				strAccount = account.getText().toString();
				strPassword = password.getText().toString();
				if (validate()) {
					if (ifLoginSecceed()) {
						GlobalVariable.ifmanagerLoginSucceed = true;
						/*
						 * 用多线程下载登录用户的个人信息
						 */

						/*
						 * 现在不考虑异步和多线程了
						 */
						response = httpClient.sendHttpPost("/manager/infos",
								param);
						jsonToObject.JsonToModels(response);
						/*
						 * 请求工作站和管理员列表
						 */
						response = httpClient.sendHttpGet("/stations");
						jsonToObject.JsonToModels(response);
						response = httpClient.sendHttpGet("/managers");
						jsonToObject.JsonToModels(response);

						// System.out.println(DAOFactory
						// .getStationManagerInstance(
						// getApplicationContext())
						// .getReadableDatabase()
						// .rawQuery("select* from station_manager", null)
						// .getCount());
						/*
						 * activity跳转
						 */
						Intent intent = new Intent();
						intent.setClass(Login.this, StationList.class);
						startActivity(intent);
						finish();
					}
				}
			}

		});

	}

	private boolean validate() {
		if (strPassword.equals("") && strAccount.equals("")) {
			Toast.makeText(Login.this, "帐号和密码不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (strAccount.equals("")) {
			Toast.makeText(Login.this, "帐号不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (strPassword.equals("")) {
			Toast.makeText(Login.this, "密码不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private boolean ifLoginSecceed() {

		param.add(new BasicNameValuePair("account", strAccount));
		param.add(new BasicNameValuePair("password", strPassword));
		response = httpClient.sendHttpPost("/manager/login", param);
		JSONObject jo;
		try {
			jo = new JSONObject(response);
			if (jo.has("status")) {
				if (jo.getInt("status") == 1) {
					Toast.makeText(Login.this, "帐号不存在", Toast.LENGTH_SHORT)
							.show();
				}
				if (jo.getInt("status") == 2) {
					Toast.makeText(Login.this, "密码不正确", Toast.LENGTH_SHORT)
							.show();
				}
				return false;
			} else {
				User user = new Gson().fromJson(response, User.class);
				if (user.getRoleId() == 0) {
					GlobalVariable.ifStationAdmin = true;
				}
				if (user.getRoleId() == 1111) {
					GlobalVariable.ifStationAdmin = true;
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
