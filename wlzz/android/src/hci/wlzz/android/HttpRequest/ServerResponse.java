package hci.wlzz.android.HttpRequest;

import hci.wlzz.android.Cache.ListDAO;
import hci.wlzz.android.Cache.ManagerDAO;
import hci.wlzz.android.Cache.OrderDAO;
import hci.wlzz.android.Cache.StationDAO;
import hci.wlzz.android.Model.Manager;
import hci.wlzz.android.Model.Order;
import hci.wlzz.android.Model.Station;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.google.gson.Gson;

/**
 * @param 所有http請求携带的参数中有int类型的变成了String
 *            ，因爲post請求的參數鍵值對裏不能放int類型的变量 接受请求的这边需要转换
 **/
public class ServerResponse {

	String lastLoginTime = null;
	String localStationName = null;
	String managerName = null;
	String role = null;

	Context context;
	String response;
	JSONArray ja;
	JSONObject jo;
	Gson gson = new Gson();
	List<NameValuePair> param;
	HttpClient httpClient = new HttpClient();
	SimpleDateFormat dfm;
	ListDAO listDAO;
	OrderDAO orderDAO;
	ManagerDAO managerDAO;
	StationDAO stationDAO;

	public ServerResponse(Context context) {
		this.context = context;
		listDAO = new ListDAO(context);
		orderDAO = new OrderDAO(context);
		managerDAO = new ManagerDAO(context);
		stationDAO = new StationDAO(context);
	}

	/**
	 * 
	 * 参数说明：（String account, String password）<br>
	 * POST请求路径：“/manager/login”<br>
	 * 返回值說明： renderJSON(Map<String,Object>)<br>
	 * JSON示例：<br>
	 * {<br>
	 * "status:"00<br>
	 * ("200":帐号错误,"300":密码错误)s<br>
	 * }<br>
	 * 或者 JSON示例：<br>
	 * {<br>
	 * "managerName:""managerName"<br>
	 * "role:""role"<br>
	 * (登录成功返回這個) }<br>
	 **/
	public int managerLogin(String account, String password) {
		param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("account", account));
		param.add(new BasicNameValuePair("password", password));
		int status = 1;
		try {
			response = httpClient.sendHttpPost("/manager/login", param);
			jo = new JSONObject(response);
			if (jo.has("status")) {
				status = jo.getInt("status");
			}
			if (jo.has("managerName")) {
				this.managerName = jo.getString("managerName");
				dfm = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
				this.lastLoginTime = dfm.format(new Date());
			}
			if (jo.has("role")) {
				this.role = jo.getString("role");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * 
	 * 參數說明： (String account, String password)<br>
	 * POST请求路徑："station/login" <br>
	 * 返回值說明： renderJSON(Map<String,Integer>)<br>
	 * JSON示例：<br>
	 * [<br>
	 * "status:"200<br>
	 * ("200":帐号错误,"300":密码错误)s<br>
	 * ]<br>
	 * 或者 <br>
	 * [<br>
	 * "stationName:""hz"<br>
	 * (登录成功返回這個) ]<br>
	 **/
	public int stationLogin(String account, String password) {
		// TODO Auto-generated method stub
		param.add(new BasicNameValuePair("account", account));
		param.add(new BasicNameValuePair("password", password));
		int status = 1;
		try {
			response = new HttpClient().sendHttpPost("/station/login", param);
			jo = new JSONObject(response);
			if (jo.has("status")) {
				status = jo.getInt("status");
			}
			if (jo.has("stationName")) {
				this.localStationName = jo.getString("stationName");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	public String getCurrentManagerName() {
		return this.managerName;
	}

	public String getCurrentStationName() {
		return this.localStationName;
	}

	public String getCurrentRole() {
		return this.role;
	}

	/**
	 * 参数说明： 没有参数<br>
	 * Get请求路徑："/station/namelist" <br>
	 * 返回說明：renderJSON(List<String>)<br>
	 * JSON示例：<br>
	 * [<br>
	 * "firstStationName"<br>
	 * "secondStationName"<br>
	 * ]
	 **/
	public List<String> getStationNameList() {
		List<String> list = new ArrayList<String>();
		response = new HttpClient().sendHttpGet("/station/namelist");
		try {
			ja = new JSONArray(response);
			for (int i = 0; i < ja.length(); i++) {
				list.add((String) ja.get(i));
			}
			/*
			 * 添加到本地数据库
			 */
			listDAO.add(ListDAO.stationNameList, null, response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 参数说明：(String stationName)<br>
	 * Post请求路徑："/station/byname"<br>
	 * 返回值说明： renderJSON(一個station對象，该对象的所有变量见Model.Station)<br>
	 * JSON示例：<br>
	 * {<br>
	 * "stationId": 11, <br>
	 * "account": "guangzhou", <br>
	 * "password": "password", <br>
	 * "name": "广州站", <br>
	 * "address": "sacu.xinxixueyuan.840", <br>
	 * "phone": "020-3425465", <br>
	 * "onlineNum": 3, <br>
	 * "managers": [<br>
	 * {<br>
	 * "managerId": 1, <br>
	 * "role": "myrole", <br>
	 * "stationAccount": "belongtowhichstation'name", <br>
	 * "account": "myaccount", <br>
	 * "password": "mypasswd", <br>
	 * "name": "myname", <br>
	 * "gender": "male", <br>
	 * "age": 2008, <br>
	 * "email": "hz@gz.com", <br>
	 * "phone": "myphone", <br>
	 * "address": "myaddress", <br>
	 * "zipCode": "myZipCode", <br>
	 * "lastLoginTime": "Date默认格式"<br>
	 * }<br>
	 * ], <br>
	 * "Orders": [<br>
	 * {<br>
	 * "orderFormId": 11, <br>
	 * "userName": "userName", <br>
	 * "code": 2233344, <br>
	 * "content": "content", <br>
	 * "amount": 3, <br>
	 * "weight": 12, <br>
	 * "createTime": "long", <br>
	 * "barcode": "1234567", <br>
	 * "address": "destination.adress", <br>
	 * "zipCode": "510610", <br>
	 * "description": "description", <br>
	 * "localStationName": "localStationName", <br>
	 * "nextStationName": "nextStationName", <br>
	 * "stations": [<br>
	 * "stations": [<br>
	 * {<br>
	 * "station": "该时间到达了哪一站的stationName", <br>
	 * "updateTime": "long"<br>
	 * }<br>
	 * {<br>
	 * "station": "该时间到达了哪一站站的stationName", <br>
	 * "updateTime": "long"<br>
	 * }<br>
	 * ]<br>
	 * } ] }
	 **/
	public Station getStation(String stationName) {
		// TODO Auto-generated method stub

		param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("stationName", stationName));
		response = httpClient.sendHttpPost("/station/byname", param);
		Station station = gson.fromJson(response, Station.class);
		List<Manager> managers = new ArrayList<Manager>();
		List<Order> orders = new ArrayList<Order>();

		try {
			Manager manager;
			jo = new JSONObject(response);
			ja = new JSONArray(jo.getString("managers"));
			for (int i = 0; i < ja.length(); i++) {
				manager = gson.fromJson(ja.get(i).toString(), Manager.class);
				managers.add(manager);
			}
			Order order;
			ja = new JSONArray(jo.getString("orders"));
			for (int i = 0; i < ja.length(); i++) {
				order = gson.fromJson(ja.get(i).toString(), Order.class);
				orders.add(order);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		station.setManagers(managers);
		station.setOrders(orders);

		/*
		 * add to local SQLiteDatabase;
		 */
		stationDAO.add(station);
		return station;
	}

	/**
	 * 参数说明：(String stationName)<br>
	 * Post请求路徑："/station/managerlist"<br>
	 * 返回值说明： renderJSON( List<String>)<br>
	 * JSON示例：<br>
	 * [<br>
	 * "firstManagerName"<br>
	 * "firstManagerName"<br>
	 * ...<br>
	 * (name是指userName)<br>
	 * ...<br>
	 * ]
	 **/
	public List<String> getManagerNameList(String stationName) {
		// TODO Auto-generated method stub
		param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("stationName", stationName));
		response = httpClient.sendHttpPost("/station/managerlist", param);
		List<String> list = new ArrayList<String>();
		try {
			ja = new JSONArray(response);
			for (int i = 0; i < ja.length(); i++) {
				list.add((String) ja.get(i));
			}
			/*
			 * 添加到本地数据库
			 */
			listDAO.add(ListDAO.managerNameList, stationName, response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 参数说明：(String managerName)<br>
	 * Post请求路徑："/manager/byname"<br>
	 * 返回值说明： renderJSON( Manager對象,该对象的所有变量見Model.Manager)<br>
	 * JSON示例：<br>
	 * {<br>
	 * "managerId": 1, <br>
	 * "role": "myrole", <br>
	 * "stationName": "belongtowhichstation'name", <br>
	 * "account": "myaccount", <br>
	 * "password": "mypasswd", <br>
	 * "name": "myname", <br>
	 * "gender": "male", <br>
	 * "age": 2008, <br>
	 * "email": "hz@gz.com", <br>
	 * "phone": "myphone", <br>
	 * "address": "myaddress", <br>
	 * "zipCode": "myZipCode", <br>
	 * "lastLoginTime": "long"<br>
	 * }<br>
	 **/
	public Manager getManager(String managerName) {
		// TODO Auto-generated method stub
		param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("managerName", managerName));
		response = httpClient.sendHttpPost("/manager/byname", param);
		Manager manager = gson.fromJson(response, Manager.class);
		/*
		 * add to local SQLiteDatabse
		 */
		managerDAO.add(manager);
		return manager;
	}

	/**
	 * 参数说明：（String stationName）<br>
	 * Post请求路徑："/station/orderlist"<br>
	 * 返回值说明： renderJSON( List<String>)<br>
	 * JSON示例：<br>
	 * [<br>
	 * "1232435"<br>
	 * "5154565"<br>
	 * ...<br>
	 * ...<br>
	 * ]
	 **/
	public List<String> getOrderCodeList(String stationName) {
		// TODO Auto-generated method stub
		param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("stationName", stationName));
		List<String> list = new ArrayList<String>();
		response = new HttpClient().sendHttpPost("/station/orderlist", param);
		try {
			ja = new JSONArray(response);
			for (int i = 0; i < ja.length(); i++) {
				list.add((String) ja.get(i));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * add to local SQLiteDatabase
		 */
		listDAO.add(ListDAO.orderCodeList, stationName, response);
		return list;
	}

	/**
	 * 参数说明：（String orderCode）<br>
	 * Post请求路徑："/order/bycode"<br>
	 * 返回值说明： renderJSON(Order對象,该对象的所有变量見Model.Order)<br>
	 * JSON示例：<br>
	 * {<br>
	 * "orderFormId": 11, <br>
	 * "userName": "userName", <br>
	 * "code": 2233344, <br>
	 * "content": "content",<br>
	 * "amount": 3, <br>
	 * "weight": 12, <br>
	 * "createTime": "long", <br>
	 * "barcode": "1234567", <br>
	 * "address": "destination.adress", <br>
	 * "zipCode": "510610", <br>
	 * "description": "description", <br>
	 * "localStationName": "localStationName", <br>
	 * "nextStationName": "nextStationName", <br>
	 * "stations": [<br>
	 * {<br>
	 * "station": "该时间到达了哪一站的stationName", <br>
	 * "updateTime": "long"<br>
	 * "description": "description"<br>
	 * }<br>
	 * {<br>
	 * "station": "该时间到达了哪一站站的stationName", <br>
	 * "updateTime": "long"<br>
	 * "description": "description"<br>
	 * }<br>
	 * ]<br>
	 * }<br>
	 **/
	public Order getOrder(String orderCode) {
		// TODO Auto-generated method stub
		param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("orderCode", orderCode));
		response = httpClient.sendHttpPost("/order/bycode", param);
		Order order = gson.fromJson(response, Order.class);
		try {
			jo = new JSONObject(response);
			ja = new JSONArray(jo.getString("stations"));
			JSONObject _jo;
			List<Map<String, String>> stations = new ArrayList<Map<String, String>>();
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < ja.length(); i++) {
				_jo = ja.getJSONObject(i);
				map.put("station", _jo.get("station").toString());
				map.put("updateTime", _jo.get("updateTime").toString());
				map.put("description", _jo.get("description").toString());
			}
			stations.add(map);
			order.setStations(stations);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * 保存在本地
		 */
		orderDAO.add(order);
		return order;
	}

	/**
	 * 约定: int id:<br>
	 * "XXX":保存成功,返回manager的Id<br>
	 * "-1":保存失败<br>
	 * 保存的時候去掉我们的ManagerId<br>
	 * 参数说明：（String managerJSON）(manager是Manager对象的json字符串)<br>
	 * 根據managerId保存Manager對象<br>
	 * Post请求路徑："/update/manager"<br>
	 * 返回值說明： renderJSON(Map<String,Integer>)<br>
	 * JSON示例：<br>
	 * {<br>
	 * "status:"1111<br>
	 * }<br>
	 * 或者<br>
	 * {<br>
	 * "status:"(int)orderFormId<br>
	 * }<br>
	 **/
	public int updateManager(Manager manager) {
		// TODO Auto-generated method stub
		param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("manager", gson.toJson(manager,
				Manager.class)));
		response = httpClient.sendHttpPost("/update/manager", param);
		int _managerId = 0;
		try {
			jo = new JSONObject(response);
			_managerId = jo.getInt("status");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * update local SQLiteDatabase
		 */
		managerDAO.update(manager);
		return _managerId;
	}

	/**
	 * 约定: int id:<br>
	 * "XXX":保存成功,返回station的Id<br>
	 * "-1":保存失败<br>
	 * 参数说明：（String stationId, String name,String account, String password,
	 * String address, String phone, int onlineNum）<br>
	 * (根據stationId保存)<br>
	 * Post请求路徑："/update/station"<br>
	 * 返回值說明： renderJSON(Map<String,Integer>)<br>
	 * JSON示例：<br>
	 * {<br>
	 * "status:"-1<br>
	 * ("-1":修改失败)<br>
	 * }<br>
	 * 或者<br>
	 * {<br>
	 * "status:"(int)stationId<br>
	 * (返回stationId,修改成功) }<br>
	 **/
	public int updateStation(int stationId, String name, String account,
			String password, String address, String phone, int onlineNum) {
		// TODO Auto-generated method stub
		param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("stationId", stationId + ""));
		param.add(new BasicNameValuePair("name", name));
		param.add(new BasicNameValuePair("account", account));
		param.add(new BasicNameValuePair("password", password));
		param.add(new BasicNameValuePair("address", address));
		param.add(new BasicNameValuePair("phone", phone));
		param.add(new BasicNameValuePair("onlineNum", onlineNum + ""));
		response = httpClient.sendHttpPost("/update/station", param);
		int _stationId = 0;
		try {
			jo = new JSONObject(response);
			_stationId = jo.getInt("status");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * update to local SQLiteDatabase
		 */
		Station station = stationDAO.find(stationId);
		station.setName(name);
		station.setAccount(account);
		station.setAddress(address);
		station.setOnlineNum(onlineNum);
		station.setPassword(password);
		station.setPhone(phone);
		stationDAO.update(station);
		return _stationId;
	}

	/**
	 * 参数说明：（Order order）(PS:根據order是Order对象的json字符串)<br>
	 * Post请求路徑："/update/order"<br>
	 * 返回值說明： renderJSON()<br>
	 * JSON示例：<br>
	 * {<br>
	 * "status:"-1<br>
	 * ("-1":修改失败)<br>
	 * }<br>
	 * 或者<br>
	 * {<br>
	 * "status:"(int)orderFormId<br>
	 * (返回stationId,修改成功) }<br>
	 **/
	public int updateOrder(Order order) {
		param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("order", gson.toJson(order,
				Order.class)));
		response = httpClient.sendHttpPost("/update/order", param);
		int _orderFormId = 0;
		try {
			jo = new JSONObject(response);
			_orderFormId = jo.getInt("status");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * update to local SQLiteDatabase
		 */
		orderDAO.update(order);
		return _orderFormId;
	}

	/**
	 * 参数说明：(String orderCode, String localStationName, String nextStationName,
	 * String desciption)<br>
	 * Post请求路徑："/add/stationOrder"<br>
	 * 返回值說明： renderJSON(...)<br>
	 * JSON示例：<br>
	 * {<br>
	 * "orderFormId": 11, <br>
	 * "userName": "userName", <br>
	 * "code": 2233344, <br>
	 * "content": "content",<br>
	 * "amount": 3, <br>
	 * "weight": 12, <br>
	 * "createTime": "long", <br>
	 * "barcode": "1234567", <br>
	 * "address": "destination.adress", <br>
	 * "zipCode": "510610", <br>
	 * "description": "description", <br>
	 * "localStationName": "localStationName", <br>
	 * "nextStationName": "nextStationName", <br>
	 * "stations": [<br>
	 * {<br>
	 * "station": "该时间到达了哪一站的stationName", <br>
	 * "updateTime": "long"<br>
	 * }<br>
	 * {<br>
	 * "station": "该时间到达了哪一站站的stationName", <br>
	 * "updateTime": "long"<br>
	 * }<br>
	 * ]<br>
	 * }<br>
	 * 
	 * 或者失敗返回<br>
	 * {<br>
	 * "status:"-1<br>
	 * ("-1":修改失败)<br>
	 * }<br>
	 **/
	public Order addOrderToStation(String orderCode, String nextStationName,
			String desciption) {
		// TODO Auto-generated method stub
		param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("orderCode", orderCode));
		param.add(new BasicNameValuePair("localStationName",
				this.localStationName));
		param.add(new BasicNameValuePair("nextStationName", nextStationName));
		param.add(new BasicNameValuePair("desciption", desciption));
		response = httpClient.sendHttpPost("/add/stationOrder", param);
		Order order = gson.fromJson(response, Order.class);
		try {
			jo = new JSONObject(response);
			ja = new JSONArray(jo.getString("stations"));
			JSONObject _jo;
			List<Map<String, String>> stations = new ArrayList<Map<String, String>>();
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < ja.length(); i++) {
				_jo = ja.getJSONObject(i);
				map.put("station", _jo.get("station").toString());
				map.put("updateTime", _jo.get("updateTime").toString());
				map.put("description", _jo.get("description").toString());
			}
			stations.add(map);
			order.setStations(stations);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * 保存在本地
		 */
		orderDAO.add(order);
		return order;
	}

	/**
	 * 参数说明：（String orderJSON）(PS:根據order是Order对象的json字符串,保存的時候可以去掉我們的id)<br>
	 * Post請求路徑：/add/newOrder JSON示例：<br>
	 * {<br>
	 * "orderFormId": 11, <br>
	 * "userName": "userName", <br>
	 * "code": 2233344, <br>
	 * "content": "content", <br>
	 * "amount": 3, <br>
	 * "weight": 12, <br>
	 * "createTime": "long", <br>
	 * "barcode": "1234567", <br>
	 * "address": "destination.adress", <br>
	 * "zipCode": "510610", <br>
	 * "description": "description", <br>
	 * "localStationName": "localStationName", <br>
	 * "nextStationName": "nextStationName", <br>
	 * "stations": [<br>
	 * {<br>
	 * "station": "该时间到达了哪一站的stationName", <br>
	 * "updateTime": "long"<br>
	 * }<br>
	 * {<br>
	 * "station": "该时间到达了哪一站站的stationName", <br>
	 * "updateTime": "long"<br>
	 * }<br>
	 * ]<br>
	 * }<br>
	 * Post请求路徑："/update/order/form"<br>
	 * 返回值說明： renderJSON()<br>
	 * JSON示例：<br>
	 * {<br>
	 * "status:"-1<br>
	 * ("-1":添加失败)<br>
	 * }<br>
	 * 或者<br>
	 * {<br>
	 * "status:"(int)orderFormId<br>
	 * (返回orderFormId,添加成功) }<br>
	 **/
	public Order addNewOrder(Order order) {
		// TODO Auto-generated method stub
		param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("orderJSON", gson.toJson(order,
				Order.class)));
		response = httpClient.sendHttpPost("/add/newOrder", param);
		try {
			jo = new JSONObject(response);
			if (jo.getInt("status") == -1) {
				return null;
			} else {
				order.setOrderFormId(jo.getInt("status"));

				/*
				 * add to local SQLiteDatabase
				 */
				orderDAO.add(order);
				return order;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 参数说明：（String account, String password, String name,String address, String
	 * phone, int onlineNum）<br>
	 * Post请求路徑："/add/newStation"<br>
	 * 返回值說明： renderJSON(Map<String,Integer>)<br>
	 * JSON示例：<br>
	 * {<br>
	 * "status:"-1<br>
	 * ("-1":保存失败)<br>
	 * }<br>
	 * 或者<br>
	 * {<br>
	 * "status:"(int)orderFormId<br>
	 * (返回orderFormId,保存成功) }<br>
	 **/
	public Station addNewStation(String account, String password, String name,
			String address, String phone, int onlineNum) {
		param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("account", account));
		param.add(new BasicNameValuePair("password", password));
		param.add(new BasicNameValuePair("name", name));
		param.add(new BasicNameValuePair("address", address));
		param.add(new BasicNameValuePair("phone", phone));
		param.add(new BasicNameValuePair("onlineNum", onlineNum + ""));
		Station station = null;
		try {
			response = httpClient.sendHttpPost("/add/newStation", param);
			jo = new JSONObject(response);
			if (jo.getInt("status") == -1) {

			} else {
				station = new Station();
				station.setAccount(account);
				station.setAddress(address);
				station.setManagers(null);
				station.setName(name);
				station.setOnlineNum(onlineNum);
				station.setOrders(null);
				station.setPassword(password);
				station.setPhone(phone);
				station.setStationId(jo.getInt("status"));
				/*
				 * add to local SQLiteDatabase
				 */
				stationDAO.add(station);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return station;
	}

	/**
	 * 参数说明：（String orderJSON)传递的是JSON字符串字符串內容包括 {<br>
	 * "role": "myrole", <br>
	 * "stationName": "belongtowhichstation'name", <br>
	 * "account": "myaccount", <br>
	 * "password": "mypasswd", <br>
	 * "name": "myname", <br>
	 * "gender": "male", <br>
	 * "age": 2008, <br>
	 * "email": "hz@gz.com", <br>
	 * "phone": "myphone", <br>
	 * "address": "myaddress", <br>
	 * "zipCode": "myZipCode", <br>
	 * "lastLoginTime": "yyyy-mm-dd hh:mm:ss"<br>
	 * }<br>
	 * Post请求路徑："/add/newManager"<br>
	 * 返回值說明： renderJSON(Map<String,Integer>)<br>
	 * JSON示例：<br>
	 * {<br>
	 * "status:"-1<br>
	 * ("-1":保存失败)<br>
	 * }<br>
	 * 或者<br>
	 * {<br>
	 * "status:"(int)managerId<br>
	 * }
	 **/
	public Manager addNewManager(Manager manager) {
		// TODO Auto-generated method stub
		try {
			param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("managerJSON", gson.toJson(
					manager, Manager.class)));
			response = httpClient.sendHttpPost("/add/newManager", param);
			jo = new JSONObject(response);
			if (jo.getInt("status") == -1) {

			} else {
				manager.setManagerId(jo.getInt("status"));
				/*
				 * add to local SQLiteDatabase
				 */
				managerDAO.add(manager);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return manager;
	}

	/**
	 * 參數說明：(String account)<br>
	 * Post請求路徑："/delete/manager" 返回值說明： renderJSON(Map<String,Integer>)<br>
	 * JSON示例：<br>
	 * {<br>
	 * "status:"-1<br>
	 * ("-1":該帐号不可用)<br>
	 * }<br>
	 * 或者<br>
	 * {<br>
	 * "status:"1<br>
	 * ("1":该帐号可用)<br>
	 **/
	public boolean detectManager(String account) {
		param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("account", account));
		int status = -1;
		try {
			response = httpClient.sendHttpPost("/delete/manager", param);
			jo = new JSONObject(response);
			status = jo.getInt("status");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status == 1 ? true : false;
	}

	/**
	 * 參數說明：(String stationName)<br>
	 * Post請求路徑："/delete/station" 返回值說明： renderJSON(Map<String,Integer>)<br>
	 * JSON示例：<br>
	 * {<br>
	 * "status:"-1<br>
	 * ("-1":刪除失败)<br>
	 * }<br>
	 * 或者<br>
	 * {<br>
	 * "status:"1<br>
	 * ("1":刪除成功)<br>
	 **/
	public boolean deleteStation(String stationName) {
		// TODO Auto-generated method stub
		param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("stationName", stationName));
		response = httpClient.sendHttpPost("/delete/manager", param);
		int status = -1;
		try {
			response = httpClient.sendHttpPost("/delete/manager", param);
			jo = new JSONObject(response);
			status = jo.getInt("status");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status == 1 ? true : false;
	}

	/**
	 * 參數說明：(String stationName,String managerName)<br>
	 * Post請求路徑："/delete/manager" 返回值說明： renderJSON(Map<String,Integer>)<br>
	 * JSON示例：<br>
	 * {<br>
	 * "status:"-1<br>
	 * ("-1":刪除失败)<br>
	 * }<br>
	 * 或者<br>
	 * {<br>
	 * "status:"1<br>
	 * ("1":刪除成功)<br>
	 **/
	public boolean deleteManager(String stationName, String managerName) {
		// TODO Auto-generated method stub
		param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("managerName", managerName));
		param.add(new BasicNameValuePair("stationName", stationName));
		response = httpClient.sendHttpPost("/delete/manager", param);
		int status = -1;
		try {
			response = httpClient.sendHttpPost("/delete/manager", param);
			jo = new JSONObject(response);
			status = jo.getInt("status");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * delete local data
		 */
		if (status != -1) {
			managerDAO.detele(status);
		}
		return status == 1 ? true : false;
	}
	
	/**
	 * 參數說明：(String stationName,String orderCode)<br>
	 * Post請求路徑："/delete/stationOrder" 返回值說明： renderJSON(Map<String,Integer>)<br>
	 * JSON示例：<br>
	 * {<br>
	 * "status:"-1<br>
	 * ("-1":刪除失败)<br>
	 * }<br>
	 * 或者<br>
	 * {<br>
	 * "status:"1<br>
	 * ("1":刪除成功)<br>
	 **/
	public boolean deleteStationOrder(String stationName, String orderCode) {
		// TODO Auto-generated method stub
		param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("stationName", stationName));
		param.add(new BasicNameValuePair("orderCode", orderCode));
		int status = -1;
		try {
			response = httpClient.sendHttpPost("/delete/stationOrder", param);
			jo = new JSONObject(response);
			status = jo.getInt("status");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * delete local data
		 */
		if (status != -1) {
			managerDAO.detele(status);
		}
		return status == 1 ? true : false;
	}
	/**
	 * 參數說明：(String stationName,String orderCode)<br>
	 * Post請求路徑："/delete/order" 返回值說明： renderJSON(Map<String,Integer>)<br>
	 * JSON示例：<br>
	 * {<br>
	 * "status:"-1<br>
	 * ("-1":刪除失败)<br>
	 * }<br>
	 * 或者<br>
	 * {<br>
	 * "status:"1<br>
	 * ("1":刪除成功)<br>
	 **/
	public boolean deleteOrder(String orderCode) {
		// TODO Auto-generated method stub
		param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("orderCode", orderCode));
		int status = -1;
		try {
			response = httpClient.sendHttpPost("/delete/order", param);
			jo = new JSONObject(response);
			status = jo.getInt("status");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * delete local data
		 */
		if (status != -1) {
			managerDAO.detele(status);
		}
		return status == 1 ? true : false;
	}


	/*
	 * about Statistics
	 */
	/**
	 * 參數說明：(String stationNam,String date)（PS:date是long類型数据的字符串）
	 * Post請求路徑："/statistics/station/orderdaily" 返回值說明： JSON示例：<br>
	 * {<br>
	 * "statistics:"123<br>
	 * }<br>
	 * 統計失敗，返回
	  * {<br>
	 * "statistics:"-1<br>
	 * }<br>
	 **/
	public int getStationDailyOrderStatistics(String stationName,Date date) {
		param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("stationName", stationName));
		param.add(new BasicNameValuePair("date", date.getTime()+""));
		int statistics = -1;
		try {
			response = httpClient.sendHttpPost(
					"/statistics/station/orderdaily", param);
			jo = new JSONObject(response);
			statistics = jo.getInt("statistics");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return statistics;
	}

	/**
	 * 參數說明：(String stationName,String date)（PS:格式是"yyyy-mm"）
	 * Post請求路徑："/statistics/statation/ordermonthly" 返回值說明： JSON示例：<br>
	 * {<br>
	 * "statistics:"123<br>
	 * }<br>
	 **/
	public int getStationMonthlyOrderStatistics(String stationName,Date date) {
		param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("stationName", stationName));
		param.add(new BasicNameValuePair("date", date.getTime()+""));
		int statistics = -1;
		try {
			response = httpClient.sendHttpPost(
					"/statistics/station/ordermonthly", param);
			jo = new JSONObject(response);
			statistics = jo.getInt("statistics");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return statistics;
	}

	/**
	 * 參數說明：(String date)（PS:格式是"yyyy-mm-dd"）
	 * Post請求路徑："/statistics/ordermonthly" 返回值說明： JSON示例：<br>
	 * {<br>
	 * "statistics:"123<br>
	 * }<br>
	 **/
	public int getDailyOrderStatistics(Date date) {
		param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("date", date.getTime()+""));
		int statistics = -1;
		try {
			response = httpClient
					.sendHttpPost("/statistics/orderdailly", param);
			jo = new JSONObject(response);
			statistics = jo.getInt("statistics");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return statistics;
	}

	/**
	 * 參數說明：(Date date)(PS:格式是"yyyy-mm") Post請求路徑："/statistics/ordermonthly"
	 * 返回值說明： JSON示例：<br>
	 * {<br>
	 * "statistics:"123<br>
	 * }<br>
	 **/
	public int getMonthOrderStatistics(Date date) {
		param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("date", date.getTime()+""));
		int statistics = -1;
		try {
			response = httpClient.sendHttpPost("/statistics/ordermonthly",
					param);
			jo = new JSONObject(response);
			statistics = jo.getInt("statistics");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return statistics;
	}

}
