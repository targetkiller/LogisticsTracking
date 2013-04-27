package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import models.OrderDetail;
import models.OrderForm;
import models.Role;
import models.Station;
import models.StationManager;
import models.User;
import models.UserInfo;
import play.mvc.Controller;

import com.google.gson.Gson;

public class Android extends Controller {

	static Gson gson = new Gson();

	public static void managerLogin(String account, String password) {
		User user = User.find("byAccount", account).first();
		Map<String, String> map = new HashMap<String, String>();
		if (user == null) {
			map.put("status", "200");
		}
		if (!user.password.equals(password)) {
			map.put("status", "300");
		} else {
			UserInfo userInfo = UserInfo.findById(user.id);
			Role role = Role.findById(user.roleId);
			map.put("managerName", userInfo.name);
			map.put("role", role.name);
		}
		renderJSON(map);
	}

	public static void stationLogin(String account, String password) {
		Station station = Station.find("byAccount", account).first();
		Map<String, String> map = new HashMap<String, String>();
		if (station == null) {
			map.put("status", "200");
		}
		if (!station.password.equals(password)) {
			map.put("status", "300");
		} else {
			map.put("status", "1");
		}
		renderJSON(map);

	}

	public static void getStationNameList() {
		List<Station> stations = Station.findAll();
		List<String> list = new ArrayList<String>();
		for (Station staion : stations) {
			list.add(staion.name);
		}
		renderJSON(list);
	}

	public static void getManagerNameList(String stationName) {
		renderJSON(getAndroidManagerNameList(stationName));
	}

	public static void getOrderCodeList(String stationName) {
		renderJSON(getAndroidOrderCodeList(stationName));
	}

	public static void getStation(String stationName) {
		Station station = Station.find("byName", stationName).first();
		android.Station aStation = new android.Station();

		// get List<android.Order>
		List<android.Order> orderList = new ArrayList<android.Order>();
		List<String> orcderCodeList = getAndroidOrderCodeList(stationName);
		android.Order aOrder = new android.Order();
		for (int i = 0; i < orcderCodeList.size(); i++) {
			aOrder = getAndroidOrderByCode(orcderCodeList.get(i));
			orderList.add(aOrder);
		}

		// get List<android.Manager>
		List<android.Manager> aManagerList = new ArrayList<android.Manager>();
		android.Manager aManager = null;
		List<String> managerNameList = getAndroidManagerNameList(stationName);
		for (int i = 0; i < managerNameList.size(); i++) {
			aManager = getAndroidManager(managerNameList.get(i));
			aManagerList.add(aManager);
		}
		// set android.Station
		aStation.setAccount(station.account);
		aStation.setAddress(station.address);
		aStation.setManagers(aManagerList);
		aStation.setName(station.name);
		aStation.setOnlineNum(station.onlineNum);
		aStation.setOrders(orderList);
		aStation.setPassword(station.password);
		aStation.setPhone(station.phone);
		aStation.setStationId(station.id);
		renderJSON(aStation);
	}

	public static void getManager(String managerName) {
		renderJSON(getAndroidManager(managerName));
	}

	public static void getOrder(String code) {
		renderJSON(getAndroidOrderByCode(code));
	}

	public static void updateManager(String managerJSON) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		android.Manager aManager = gson.fromJson(managerJSON,
				android.Manager.class);
		if (updateAndroidManager(aManager)) {
			map.put("status", aManager.getManagerId());
		} else {
			map.put("status", -1);
		}
		renderJSON(map);
	}

	public static void updateStation(String stationId, String name,
			String account, String password, String address, String phone,
			int onlineNum) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			Station station = Station.findById(Integer.parseInt(stationId));
			station.name = name;
			station.account = account;
			station.address = address;
			station.password = password;
			station.onlineNum = onlineNum;
			station.phone = phone;
			station.save();
			map.put("status", Integer.parseInt(stationId));
		} catch (Exception e) {
			// TODO: handle exception
			map.put("status", -1);
		}
		renderJSON(map);
	}

	public static void updateOrder(String orderJSON) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		android.Order aOrder = gson.fromJson(orderJSON, android.Order.class);
		if (updateAndroidOrder(aOrder)) {
			map.put("status", aOrder.getOrderFormId());
		} else {
			map.put("status", -1);
		}
		renderJSON(map);
	}

	public static void addOrderToStation(String orderCode,
			String localStationName, String nextStationName, String description) {
		try {
			OrderForm orderForm = OrderForm.find("byCode", orderCode).first();
			User user = User.find("byName", orderForm.userName).first();
			int userId = user.id;
			Station station = Station.find("byName", localStationName).first();
			int stationId = station.id;
			station = Station.find("byName", nextStationName).first();
			int nextStationId = station.id;
			OrderDetail orderDetail = new OrderDetail(orderForm.id, stationId,
					userId, nextStationId, new Date().getTime(), description);
			orderDetail.save();
			android.Order aOrder = getAndroidOrderByCode(orderCode);
			renderJSON(aOrder);
		} catch (Exception e) {
			// TODO: handle exception
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("status", -1);
			renderJSON(map);
		}
	}

	public static void addNewOrder(String orderJSON) {
		android.Order aOrder = gson.fromJson(orderJSON, android.Order.class);
		Map<String, Integer> map = new HashMap<String, Integer>();
		if (updateAndroidOrder(aOrder)) {
			map.put("status", aOrder.getOrderFormId());
		} else {
			map.put("status", -1);
		}
		renderJSON(map);
	}

	public static void addNewStation(String account, String password,
			String name, String address, String phone, String onlineNum) {
		Station station = new Station(account, password, name, address, Integer.parseInt(onlineNum));
		station.save();
	}

	public static void addNewManager(String managerJSON) {
		android.Manager aManager = gson.fromJson(managerJSON,
				android.Manager.class);
		Map<String, Integer> map = new HashMap<String, Integer>();
		if (updateAndroidManager(aManager)) {
			map.put("status", aManager.getManagerId());
		} else {
			map.put("status", -1);
		}
		renderJSON(map);
	}

	public static void detectManager(String account) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		User user = User.find("byAccount", account).first();
		if (user != null) {
			StationManager manager = StationManager.find("byUserId", user.id)
					.first();
			if (manager != null) {
				map.put("status", -1);
			} else {
				map.put("status", 1);
			}
		} else {
			map.put("status", 1);
		}
		renderJSON(map);
	}

	public static void deleteStation(String stationName) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			Station station = Station.find("byName", stationName).first();
			station.delete();
			map.put("status", 1);
		} catch (Exception e) {
			// TODO: handle exception
			map.put("status", -1);
		}
		renderJSON(map);
	}

	public static void deleteManager(String stationName,String managerName) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			UserInfo userInfo = UserInfo.find("byName", managerName).first();
//			manager的userId就是userInfo和user的id吧？测试出错？？？？
			StationManager stationManager = Station.find("byUserId",
					userInfo.id).first();
			Station station =Station.find("byName", stationName).first();
			if(station.id==stationManager.stationId){
				stationManager.delete();
				map.put("status", 1);
			}
			else{
				map.put("status", -1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			map.put("status", -1);
		}
		renderJSON(map);
	}
	
//	删除某个工作站的订单
	public static void deleteStationOrder(String stationName,String orderCode){
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			OrderForm orderForm =OrderForm.find("byCode", orderCode).first();
			List<OrderDetail> orderDetails =OrderDetail.find("byOderFormId", orderForm.id).fetch();
			Station station =Station.find("byName", stationName).first();
			for(OrderDetail orderDetail:orderDetails){
				if(orderDetail.stationId ==station.id) {
					orderDetail.delete();
					map.put("status", 1);
				}
				else{
					map.put("status", -1);
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			map.put("status", -1);
		}
		renderJSON(map);
	}
	
//	删除该订单
	public static void deleteOrder(String orderCode){
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			OrderForm orderForm =OrderForm.find("byCode", orderCode).first();
			List<OrderDetail> orderDetails =OrderDetail.find("byOderFormId", orderForm.id).fetch();
			for(OrderDetail orderDetail:orderDetails){
				orderDetail.delete();
			}
			orderForm.delete();
			map.put("status", 1);
		}catch (Exception e) {
			// TODO: handle exception
			map.put("status", -1);
		}
		renderJSON(map);
	}
	
	/*
	 * 传递过来的date是long數據的字符串,我不知道要用什麼時間才能統計到想要的數據？？？？？？
	*/
	public static void getStationDailyOrderStatistics(String stationName,String date){
		Map<String, Integer> map = new HashMap<String, Integer>();
		try{
		long time =Long.parseLong(date);
		Station station =Station.find("byName", stationName).first();
	
		/*
		 * 
		 * 统计没做？？
		 * 
		*/
		map.put("statistic", 0);
		}catch (Exception e) {
			// TODO: handle exception
			map.put("statistic", -1);
		}
		renderJSON(map);
	}
	
	public static void getStationMonthlyOrderStatistics(String stationName,String date){
		Map<String, Integer> map = new HashMap<String, Integer>();
		try{
		long time =Long.parseLong(date);
		Station station =Station.find("byName", stationName).first();

		/*
		 * 
		 * 统计没做？？
		 * 
		*/
		
		map.put("statistic", 0);
		}catch (Exception e) {
			// TODO: handle exception
			map.put("statistic", -1);
		}
		renderJSON(map);
	}
	
	
//	传递过来的date是long數據的字符串
	public static void getDailyOrderStatistics(String date){
		Map<String, Integer> map = new HashMap<String, Integer>();
		try{
		long time =Long.parseLong(date);
	
		/*
		 * 
		 * 统计没做？？
		 * 
		*/
		map.put("statistic", 0);
		}catch (Exception e) {
			// TODO: handle exception
			map.put("statistic", -1);
		}
		renderJSON(map);
	}
	
//	传递过来的date是long數據的字符串
	public static void getMonthlyOrderStatistics(String date){
		Map<String, Integer> map = new HashMap<String, Integer>();
		try{
		long time =Long.parseLong(date);

		/*
		 * 
		 * 统计没做？？
		 * 
		*/
		
		map.put("statistic", 0);
		}catch (Exception e) {
			// TODO: handle exception
			map.put("statistic", -1);
		}
		renderJSON(map);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 工具类方法
	 **/

	public static List<String> getAndroidManagerNameList(String stationName) {
		Station station = Station.find("byName", stationName).first();
		List<StationManager> managers = StationManager.find("byStationId",
				station.id).fetch();
		List<String> list = new ArrayList<String>();
		UserInfo userInfo = null;
		for (StationManager manager : managers) {
			userInfo = UserInfo.findById(manager.userId);
			list.add(userInfo.name);
		}
		return list;
	}

	public static List<String> getAndroidOrderCodeList(String stationName) {
		Station station = Station.find("byName", stationName).first();
		// 得到所有orderformId
		List<OrderDetail> orderDetails = OrderDetail.find("byStationId",
				station.id).fetch();
		Set<Integer> orderformIds = new HashSet<Integer>();
		for (OrderDetail orderDetail : orderDetails) {
			orderformIds.add(orderDetail.orderFormId);
		}
		// 得到所有orderForm的code
		List<String> codelist = new ArrayList<String>();
		OrderForm orderForm = null;
		for (Integer id : orderformIds) {
			orderForm = OrderForm.findById(id);
			codelist.add(orderForm.code);
		}
		return codelist;
	}

	public static android.Manager getAndroidManager(String managerName) {

		UserInfo userInfo = UserInfo.find("byName", managerName).first();
		User user = User.findById(userInfo.id);
		Role role = Role.findById(user.roleId);
		StationManager manager = StationManager.find("byUserId", userInfo.id)
				.first();
		Station station = Station.findById(manager.stationId);
		android.Manager aManager = new android.Manager();
		aManager.setAccount(user.account);
		aManager.setAddress(userInfo.address);
		aManager.setAge(userInfo.age);
		aManager.setEmail(userInfo.email);
		aManager.setGender(userInfo.gender ? "male" : "female");
		aManager.setLastLoginTime(manager.lastLoginTime);
		aManager.setManagerId(manager.id);
		aManager.setName(userInfo.name);
		aManager.setPassword(user.password);
		aManager.setPhone(userInfo.phone);
		aManager.setRole(role.name);
		aManager.setStationName(station.name);
		aManager.setZipCode(userInfo.zipCode);
		return aManager;
	}

	public static boolean updateAndroidManager(android.Manager aManager) {
		try {
			StationManager manager = StationManager.findById(aManager
					.getManagerId());
			// 获得相关的三個model對象
			Station station = Station.findById(manager.stationId);
			User user = User.findById(manager.userId);
			UserInfo userInfo = UserInfo.findById(manager.userId);
			Role role = Role.findById(user.roleId);
			// 分别保存
			user.account = aManager.getAccount();
			user.password = aManager.getPassword();
			user.save();
			userInfo.address = aManager.getAddress();
			userInfo.age = aManager.getAge();
			userInfo.email = aManager.getEmail();
			userInfo.gender = aManager.getGender().equals("male") ? true
					: false;
			userInfo.name = aManager.getName();
			userInfo.phone = aManager.getPhone();
			userInfo.zipCode = aManager.getZipCode();
			userInfo.save();
			role.name = aManager.getRole();
			role.save();
			manager.lastLoginTime = new Date().getTime();
			manager.save();
			station.name = aManager.getStationName();
			station.save();
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	public static android.Order getAndroidOrderByCode(String code) {
		// get list
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		OrderForm orderForm = OrderForm.find("byCode", code).first();
		List<OrderDetail> orderDetails = OrderDetail.find("byOrderFormId",
				orderForm.id).fetch();
		Station firstStation = Station.findById(orderDetails.get(0).stationId);
		String firstStationName = firstStation.name;
		Station station = null;
		for (OrderDetail orderDetail : orderDetails) {
			station = Station.findById(orderDetail.stationId);
			map.put("station", station.name);
			map.put("updateTime", orderDetail.updateTime + "");
			map.put("description", orderDetail.description);
		}
		list.add(map);
		// set android.Order
		android.Order aOrder = new android.Order();
		aOrder.setAddress(orderForm.address);
		aOrder.setAmount(orderForm.amount);
		aOrder.setBarcode(orderForm.barcode);
		aOrder.setCode(orderForm.code);
		aOrder.setContent(orderForm.content);
		aOrder.setCreateTime(orderForm.createTime);
		aOrder.setOrderFormId(orderForm.id);
		aOrder.setStations(list);
		aOrder.setUserName(orderForm.userName);
		aOrder.setWeight(orderForm.weight);
		aOrder.setZipCode(orderForm.zipCode);
		aOrder.setFirstStationName(firstStationName);
		return aOrder;
	}

	public static boolean updateAndroidOrder(android.Order aOrder) {
		try {
			OrderForm orderForm = OrderForm.findById(aOrder.getOrderFormId());
			// 獲得相關的model
			List<Map<String, String>> orderDetailList = aOrder.getStations();
			OrderDetail orderDetail = null;
			Station station = null;
			for (int i = 0; i < orderDetailList.size(); i++) {
				// 通過station字段找orderDetail不知道行不行？？？？？
				Map<String, String> map = orderDetailList.get(i);
				orderDetail = OrderDetail.find("byStation", map.get("station"))
						.first();
				orderDetail.description = map.get("description");
				station = Station.find("byName", map.get("station")).first();
				orderDetail.stationId = station.id;
				orderDetail.updateTime = Long.parseLong(map.get("updateTime"));
				orderDetail.save();
			}
			orderForm.address = aOrder.getAddress();
			orderForm.amount = aOrder.getAmount();
			orderForm.barcode = aOrder.getBarcode();
			orderForm.code = aOrder.getCode();
			orderForm.content = aOrder.getContent();
			orderForm.createTime = aOrder.getCreateTime();
			orderForm.userName = aOrder.getUserName();
			orderForm.weight = aOrder.getWeight();
			orderForm.zipCode = aOrder.getZipCode();
			orderForm.save();
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	public static List<android.Order> getAndroidOrderByStationName(
			String stationName) {
		List<android.Order> list = new ArrayList<android.Order>();
		// get
		Station station = Station.find("byName", stationName).first();
		List<OrderDetail> orderDetails = OrderDetail.find("byStationId",
				station.id).fetch();
		Set<Integer> orderformIds = new HashSet<Integer>();
		for (OrderDetail orderDetail : orderDetails) {
			orderformIds.add(orderDetail.orderFormId);
		}
		OrderForm orderForm;
		// 得到所有orderformId后，得到一个android.Order的操作
		List<Map<String, String>> stationlist = new ArrayList<Map<String, String>>();
		Map<String, String> map;
		for (Integer orderformId : orderformIds) {
			orderForm = OrderForm.findById(orderformId);
			orderDetails = OrderDetail.find("byOrderFormId", orderForm.id)
					.fetch();
			map = new HashMap<String, String>();
			Station firstStation = Station
					.findById(orderDetails.get(0).stationId);
			String firstStationName = firstStation.name;
			for (OrderDetail ord : orderDetails) {
				map.put("station", ord.station.name);
				map.put("updateTime", ord.updateTime + "");
				map.put("description", ord.description);
			}
			stationlist.add(map);
			// set android.Order
			android.Order aOrder = new android.Order();
			aOrder.setAddress(orderForm.address);
			aOrder.setAmount(orderForm.amount);
			aOrder.setBarcode(orderForm.barcode);
			aOrder.setCode(orderForm.code);
			aOrder.setContent(orderForm.content);
			aOrder.setCreateTime(orderForm.createTime);
			aOrder.setOrderFormId(orderForm.id);
			aOrder.setStations(stationlist);
			aOrder.setUserName(orderForm.userName);
			aOrder.setWeight(orderForm.weight);
			aOrder.setZipCode(orderForm.zipCode);
			aOrder.setFirstStationName(firstStationName);
			list.add(aOrder);
		}
		return list;
	}
}
