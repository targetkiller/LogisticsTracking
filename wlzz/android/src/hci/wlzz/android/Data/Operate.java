package hci.wlzz.android.Data;

import hci.wlzz.android.Cache.LocalOperate;
import hci.wlzz.android.HttpRequest.RemoteOperate;
import hci.wlzz.android.HttpRequest.ServerResponse;
import hci.wlzz.android.Model.Manager;
import hci.wlzz.android.Model.Order;
import hci.wlzz.android.Model.Station;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Operate {

	Context context;
	RemoteOperate remoteOperate;
	LocalOperate localOperate;
	Object ROUTE;

	public Operate(Context context) {
		this.context = context;
		remoteOperate = new RemoteOperate(context);
	}

	public boolean checkNetwork(Context context) {

		ConnectivityManager cManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nInfo = cManager.getActiveNetworkInfo();
		if (nInfo == null || nInfo.isAvailable()) {
			return false;
		}
		return true;
	}

	/**
	 * 管理员登录验证 返回"200":帐号错误，"300"密码错误,"1"登录成功,"2"網絡不行，是否跳到本地登錄，瀏覽本地緩存過的東西
	 **/
	public int managerLogin(String account, String password) {
		return remoteOperate.managerLogin(account, password);
	}

	/**
	 * 工作站登录验证 返回"200":帐号错误，"300"密码错误,"1"登录成功
	 **/
	public int stationLogin(String account, String password) {
		return remoteOperate.stationLogin(account, password);
	}

	/**
	 * 登录成功返回管理员名字，登录不成功返回空
	 **/
	public String getCurrentManagerName() {
		return new ServerResponse(context).getCurrentManagerName();
	}

	/**
	 * 登录成功返回管理員所在工作站名字，登录不成功返回空
	 **/
	public String getCurrentStationName() {
		return new ServerResponse(context).getCurrentStationName();
	}

	/**
	 * 登录成功返回管理員角色，登录不成功返回空
	 **/
	public String getCurrentRole() {
		return new ServerResponse(context).getCurrentRole();
	}

	/**
	 * 得到所有的station的account链表
	 **/
	public List<String> getStationNametList() {
		return remoteOperate.getStationNametList();
	}

	/**
	 * 根據station的account得到station對象
	 **/
	public Station getStation(String stationName) {
		return remoteOperate.getStation(stationName);
	}

	/*
	 * about manager
	 */

	/**
	 * 根據station的name得到manager的name链表
	 **/
	public List<String> getManagerNameList(String stationName) {
		return remoteOperate.getManagerNameList(stationName);
	}

	/**
	 * 根据manager的name得到一个Manager对象
	 **/
	public Manager getManager(String managerName) {
		return remoteOperate.getManager(managerName);
	}

	/*
	 * about order
	 */

	/**
	 * 根據station的name得到order的code链表
	 **/
	public List<String> getOrderCodeList(String stationName) {
		return remoteOperate.getOrderCodeList(stationName);
	}

	/**
	 * 根據order的code得到一个order对象
	 **/
	public Order getOrder(String orderCode) {
		return remoteOperate.getOrder(orderCode);
	}

	/*
	 * about update
	 */

	/**
	 * 修改manager信息<br>
	 * 返回"-1":修改失败<br>
	 * 返回"XX":managerId修改成功<br>
	 **/
	public int updateManager(Manager manager) {
		return remoteOperate.updateManager(manager);
	}

	/**
	 * 修改工作站信息<br>
	 * 返回"-1":修改失败<br>
	 * 返回"XX":stationId修改成功<br>
	 **/
	public int updateStation(int stationId, String name, String account,
			String password, String address, String phone, int onlineNum) {
		//
		// StationDAO stationDAO = new StationDAO(context);
		// Station station = stationDAO.find(stationId);
		// station.setAccount(account);
		// station.setAddress(address);
		// station.setPassword(password);
		// station.setOnlineNum(onlineNum);
		// station.setPhone(phone);
		// station.setName(name);
		// stationDAO.update(station);
		return remoteOperate.updateStation(stationId, name, account, password,
				address, phone, onlineNum);
	}

	/**
	 * 修改訂單信息<br>
	 * 返回"-1":修改失败<br>
	 * 返回"XX":orderFormId修改成功<br>
	 **/
	public int updateOrder(Order order) {
		return remoteOperate.updateOrder(order);
	}

	/*
	 * about add
	 */
	/**
	 * 给工作站添加到站訂單
	 **/
	public Order addOrderToStation(String orderCode, String nextStationName,
			String desciption) {
		// Order order = remoteOperate.addOrder(context, orderCode,
		// nextStationName, desciption);
		// /*
		// * 将order保存在本地
		// */
		// OrderDAO orderDAO = new OrderDAO(context);
		// orderDAO.add(order);
		return remoteOperate.addOrderToStation(orderCode, nextStationName,
				desciption);
	}

	/**
	 * 添加新訂單
	 **/
	public Order addNewOrder(Order order) {
		return remoteOperate.addNewOrder(order);
	}

	/**
	 * 添加工作站
	 **/
	public Station addNewStation(String account, String password, String name,
			String address, String phone, int onlineNum) {
		// Station station = remoteOperate.addStation(context, account,
		// password, name, address, phone, onlineNum);
		// /*
		// * 将station 保存在本地
		// */
		// StationDAO stationDAO = new StationDAO(context);
		// stationDAO.add(station);
		return remoteOperate.addNewStation(account, password, name, address,
				phone, onlineNum);
	}

	/**
	 * 添加工作站管理員
	 **/
	public Manager addNewManager(Manager manager) {

		// Manager _manager = remoteOperate.addManager(context, manager);
		// /*
		// */
		// ManagerDAO managerDAO = new ManagerDAO(context);
		// managerDAO.add(_manager);
		return remoteOperate.addNewManager(manager);
	}

	/**
	 * 添加工作站管理員帐号，如果帳號可用，返回managerId,如果不可用，返回-1
	 **/
	public boolean detectManager(String account) {
		return remoteOperate.detectManager(account);
	}

	/*
	 * about delete
	 */
	/**
	 * 根据工作站名字删除工作站
	 **/
	public boolean deleteStation(String stationName) {
		return remoteOperate.deleteStation(stationName);
	}

	/**
	 * 根据管理员名字删除管理员
	 **/
	public boolean deleteManager(String stationName, String managerName) {
		return remoteOperate.deleteManager(stationName, managerName);
	}

	public boolean deleteStationOrder(String stationName, String orderCode) {
		return remoteOperate.deleteStationOrder(stationName, orderCode);
	}

	public boolean deleteOrder(String orderCode) {
		return remoteOperate.deleteOrder(orderCode);
	}

	/*
	 * about Statistics
	 */
	/**
	 * 根據時間（格式是"yyyy-mm-dd"）统计工作站订单數
	 **/
	public int getStationDailyOrderStatistics(String stationName, Date date) {
		return remoteOperate.getStationDailyOrderStatistics(stationName, date);
	}

	/**
	 * 根據時間（格式是"yyyy-mm"）统计工作站订单數
	 **/
	public int getStationMonthlyOrderStatistics(String stationName, Date date) {
		return remoteOperate
				.getStationMonthlyOrderStatistics(stationName, date);
	}

	/**
	 * 根據時間（格式是"yyyy-mm-dd"）统计工作站订单數
	 **/
	public int getDailyOrderStatistics(Date date) {
		return remoteOperate.getDailyOrderStatistics(date);
	}

	/**
	 * 根據時間（格式是"yyyy-mm"）统计所有订单數
	 **/
	public int getMonthlyOrderStatistics(Date date) {
		return remoteOperate.getMonthlyOrderStatistics(date);
	}
}
