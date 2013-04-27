package hci.wlzz.android.UI;

import hci.wlzz.android.HttpRequest.HttpClient;
import hci.wlzz.android.Utils.GlobalVariable;
import hci.wlzz.android.Utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StationLogin extends Activity {
	public static String stationManagerString = null;
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
		setContentView(R.layout.station);
		jsonToObject = new JsonUtils(getApplicationContext());

		account = (EditText) findViewById(R.id.Stationlogin_edit_account);
		password = (EditText) findViewById(R.id.Stationlogin_edit_pwd);
		submit = (Button) findViewById(R.id.Stationlogin_btn_login);
		submit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				strAccount = account.getText().toString();
				strPassword = password.getText().toString();
				if (validate()) {
					if (ifLoginSecceed()) {
						GlobalVariable.ifStationLoginSucceed = true;
						// 解析并保存user數據
						new JsonUtils(getApplicationContext())
								.JsonToModels(response);

						/*
						 * 请求该工作站的订单列表
						 */
						param.add(new BasicNameValuePair("account", strAccount));
						response = httpClient.sendHttpPost("/orderDetails",
								param);
						jsonToObject.JsonToModels(response);

						Intent intent = new Intent();
						intent.setClass(StationLogin.this, OrderList.class);
						StationLogin.this.finish();
						startActivity(intent);
					}
				}

			}
		});

	}

	private boolean validate() {
		if (strPassword.equals("") && strAccount.equals("")) {
			Toast.makeText(StationLogin.this, "帐号和密码不能为空", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if (strAccount.equals("")) {
			Toast.makeText(StationLogin.this, "帐号不能为空", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if (strPassword.equals("")) {
			Toast.makeText(StationLogin.this, "密码不能为空", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		return true;
	}

	private boolean ifLoginSecceed() {
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("account", strAccount));
		param.add(new BasicNameValuePair("password", strPassword));
		System.out.println(param.toString());
		response = new HttpClient().sendHttpPost("/station/login", param);

		JSONObject jo;
		try {
			jo = new JSONObject(response);
			if (jo.has("status")) {
				if (jo.getInt("status") == 1) {
					Toast.makeText(StationLogin.this, "帐号不存在",
							Toast.LENGTH_SHORT).show();
				}
				if (jo.getInt("status") == 2) {
					Toast.makeText(StationLogin.this, "密码不正确",
							Toast.LENGTH_SHORT).show();
				}
				return false;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}
}
