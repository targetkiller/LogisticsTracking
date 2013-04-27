package hci.wlzz.android.Utils;

import hci.wlzz.android.Models.OrderDetail;
import hci.wlzz.android.Models.OrderForm;
import hci.wlzz.android.Models.Role;
import hci.wlzz.android.Models.Station;
import hci.wlzz.android.Models.StationManager;
import hci.wlzz.android.Models.User;
import hci.wlzz.android.Models.UserInfo;
import hci.wlzz.android.sql.DAOFactory;
import hci.wlzz.android.sql.OrderDetailHelper;
import hci.wlzz.android.sql.OrderFormHelper;
import hci.wlzz.android.sql.RoleHelper;
import hci.wlzz.android.sql.StationHelper;
import hci.wlzz.android.sql.StationManagerHelper;
import hci.wlzz.android.sql.UserHelper;
import hci.wlzz.android.sql.UserInfoHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

public class JsonUtils extends Activity {

	Gson gson = new Gson();
	UserHelper userHelper;
	UserInfoHelper userInfoHelper;
	RoleHelper roleHelper;
	StationManagerHelper stationManagerHelper;
	StationHelper stationHelper;
	OrderDetailHelper orderDetailHelper;
	OrderFormHelper orderFormHelper;
	SQLiteDatabase db;
	Context ctx;

	public JsonUtils(Context ctx) {
		super();
		this.ctx = ctx;
	}

	// 解析JSON字符串为model并緩存在本地数据库
	public void JsonToModel(String map) {

		try {
			JSONObject jo = new JSONObject(map);
			if (jo.get("role") != null) {
				GlobalVariable.role = gson.fromJson(jo.getString("role"),
						Role.class);
				saveRole();
			}
			if (jo.get("user") != null) {
				GlobalVariable.user = gson.fromJson(jo.getString("user"),
						User.class);
				saveUser();
			}
			if (jo.get("userInfo") != null) {
				GlobalVariable.userInfo = gson.fromJson(
						jo.getString("userInfo"), UserInfo.class);
				saveUserInfo();
			}
			if (jo.get("manager") != null) {
				GlobalVariable.manager = gson.fromJson(jo.getString("manager"),
						StationManager.class);
				saveStationManager();
			}
			if (jo.get("station") != null) {
				GlobalVariable.station = gson.fromJson(jo.getString("station"),
						Station.class);
				saveStation();
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void JsonToModels(String jsonArray) {
		JSONObject jo;
		JSONArray ja;
		try {
			jo = new JSONObject(jsonArray);
			Station station = new Station();
			StationManager manager = new StationManager();
			Role role = new Role();
			User user = new User();
			UserInfo userInfo = new UserInfo();
			OrderForm orderForm = new OrderForm();
			OrderDetail orderDetail = new OrderDetail();
			if (jo.has("stations")) {
				stationHelper = DAOFactory.getStationInstance(ctx);
				db = stationHelper.getWritableDatabase();
				stationHelper.createTable(db);
				ja = new JSONArray(jo.getString("stations"));
				for (int i = 0; i < ja.length(); i++) {
					station = gson
							.fromJson(ja.get(i).toString(), Station.class);
					try {
						stationHelper.insert(db, station);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				stationHelper.close();
			}
			if (jo.has("managers")) {
				stationManagerHelper = DAOFactory
						.getStationManagerInstance(ctx);
				db = stationManagerHelper.getWritableDatabase();
				stationManagerHelper.createTable(db);
				ja = new JSONArray(jo.getString("managers"));
				for (int i = 0; i < ja.length(); i++) {
					manager = gson.fromJson(ja.get(i).toString(),
							StationManager.class);
					try {
						stationManagerHelper.insert(db, manager);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				stationManagerHelper.close();
			}
			if (jo.has("users")) {
				userHelper = DAOFactory.getUserInstance(ctx);
				db = userHelper.getWritableDatabase();
				userHelper.createTable(db);
				ja = new JSONArray(jo.getString("users"));
				for (int i = 0; i < ja.length(); i++) {
					user = gson.fromJson(ja.get(i).toString(), User.class);
					try {
						userHelper.insert(db, user);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				userHelper.close();
			}

			if (jo.has("roles")) {
				roleHelper = DAOFactory.getRoleInstance(ctx);
				db = roleHelper.getWritableDatabase();
				roleHelper.createTable(db);
				ja = new JSONArray(jo.getString("roles"));
				for (int i = 0; i < ja.length(); i++) {
					role = gson.fromJson(ja.get(i).toString(), Role.class);
					try {
						roleHelper.insert(db, role);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				userHelper.close();
			}

			if (jo.has("userInfos")) {
				userInfoHelper = DAOFactory.getUserInfoInstance(ctx);
				db = userInfoHelper.getWritableDatabase();
				userInfoHelper.createTable(db);
				ja = new JSONArray(jo.getString("userInfos"));
				for (int i = 0; i < ja.length(); i++) {
					userInfo = gson.fromJson(ja.get(i).toString(),
							UserInfo.class);
					try {
						userInfoHelper.insert(db, userInfo);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				userInfoHelper.close();
			}
			if (jo.has("orderDetails")) {
				orderDetailHelper = DAOFactory.getOrderDetailInstance(ctx);
				db = orderDetailHelper.getWritableDatabase();
				orderDetailHelper.createTable(db);
				ja = new JSONArray(jo.getString("orderDetails"));
				for (int i = 0; i < ja.length(); i++) {
					orderDetail = gson.fromJson(ja.get(i).toString(),
							OrderDetail.class);
					try {
						orderDetailHelper.insert(db, orderDetail);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				orderDetailHelper.close();
			}
			if (jo.has("orderForms")) {
				orderFormHelper = DAOFactory.getOrderFormInstance(ctx);
				db = orderFormHelper.getWritableDatabase();
				orderFormHelper.createTable(db);
				ja = new JSONArray(jo.getString("orderForms"));
				for (int i = 0; i < ja.length(); i++) {
					orderForm = gson.fromJson(ja.get(i).toString(),
							OrderForm.class);
					try {
						orderFormHelper.insert(
								orderFormHelper.getWritableDatabase(),
								orderForm);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				orderFormHelper.close();
			}

			if (jo.has("managerInfos")) {
				jo = new JSONObject(jo.get("managerInfos").toString());
				GlobalVariable.manager = gson.fromJson(jo.get("manager")
						.toString(), StationManager.class);
				GlobalVariable.user = gson.fromJson(jo.getString("user"),
						User.class);
				GlobalVariable.userInfo = gson.fromJson(
						jo.getString("userInfo"), UserInfo.class);
				GlobalVariable.role = gson.fromJson(jo.getString("role"),
						Role.class);
				ja = new JSONArray(jo.getString("managerInfos"));

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void saveRole() {
		roleHelper = DAOFactory.getRoleInstance(ctx);
		db = roleHelper.getWritableDatabase();
		roleHelper.createTable(db);
		try {
			roleHelper.insert(db, GlobalVariable.role);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		roleHelper.close();
	}

	public void saveUser() {
		userHelper = DAOFactory.getUserInstance(ctx);
		db = userHelper.getWritableDatabase();
		userHelper.createTable(db);
		try {
			userHelper.insert(userHelper.getWritableDatabase(),
					GlobalVariable.user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userHelper.close();
	}

	public void saveUserInfo() {
		userInfoHelper = DAOFactory.getUserInfoInstance(ctx);
		db = userInfoHelper.getWritableDatabase();
		userInfoHelper.createTable(db);
		try {
			userInfoHelper.insert(userInfoHelper.getWritableDatabase(),
					GlobalVariable.userInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userInfoHelper.close();
	}

	public void saveStationManager() {
		stationManagerHelper = DAOFactory.getStationManagerInstance(ctx);
		db = stationManagerHelper.getWritableDatabase();
		stationManagerHelper.createTable(db);
		try {
			stationManagerHelper.insert(
					stationManagerHelper.getWritableDatabase(),
					GlobalVariable.manager);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stationManagerHelper.close();
	}

	public void saveStation() {
		stationHelper = DAOFactory.getStationInstance(ctx);
		db = stationHelper.getWritableDatabase();
		stationHelper.createTable(db);
		try {
			stationHelper.insert(stationHelper.getWritableDatabase(),
					GlobalVariable.station);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stationHelper.close();
	}

	public <T> T parseJson(Class<T> cls, String json) {
		Gson gson = new Gson();
		T obj = gson.fromJson(json, cls);
		return obj;
	}
}
