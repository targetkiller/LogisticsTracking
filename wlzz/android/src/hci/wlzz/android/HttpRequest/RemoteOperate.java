package hci.wlzz.android.HttpRequest;

import hci.wlzz.android.Data.OperateInterface;
import hci.wlzz.android.Model.Manager;
import hci.wlzz.android.Model.Order;
import hci.wlzz.android.Model.Station;

import java.util.Date;
import java.util.List;
import android.content.Context;

/**
 * 
 * @author hezi
 * @version 001
 * 
 **/
public class RemoteOperate implements OperateInterface {

	Context context;
	ServerResponse serverResponse;

	public RemoteOperate(Context context) {
		this.context = context;
		this.serverResponse = new ServerResponse(context);
	}

	public int managerLogin(String account, String password) {
		// TODO Auto-generated method stub
		return serverResponse.managerLogin(account, password);
	}

	public int stationLogin(String account, String password) {
		// TODO Auto-generated method stub
		return serverResponse.stationLogin(account, password);
	}

	public List<String> getStationNametList() {
		// TODO Auto-generated method stub
		return serverResponse.getStationNameList();
	}

	public Station getStation(String stationName) {
		// TODO Auto-generated method stub
		return serverResponse.getStation(stationName);
	}

	public List<String> getManagerNameList(String stationName) {
		// TODO Auto-generated method stub
		return serverResponse.getManagerNameList(stationName);
	}

	public Manager getManager(String managerName) {
		// TODO Auto-generated method stub
		return serverResponse.getManager(managerName);
	}

	public List<String> getOrderCodeList(String stationName) {
		// TODO Auto-generated method stub
		return serverResponse.getOrderCodeList(stationName);
	}

	public Order getOrder(String orderCode) {
		// TODO Auto-generated method stub
		return serverResponse.getOrder(orderCode);
	}

	public int updateManager(Manager manager) {
		// TODO Auto-generated method stub
		return serverResponse.updateManager(manager);
	}

	public int updateStation(int stationId, String name, String account,
			String password, String address, String phone, int onlineNum) {
		// TODO Auto-generated method stub
		return serverResponse.updateStation(stationId, name, account, password,
				address, phone, onlineNum);
	}

	/**
	 * 修改訂單信息
	 **/
	public int updateOrder(Order order) {
		return serverResponse.updateOrder(order);
	}

	public Order addOrderToStation(String orderCode, String nextStationName,
			String desciption) {
		// TODO Auto-generated method stub
		return serverResponse.addOrderToStation(orderCode, nextStationName,
				desciption);
	}

	/**
	 * 添加新訂單
	 **/
	public Order addNewOrder(Order order) {
		return serverResponse.addNewOrder(order);
	}

	public Station addNewStation(String account, String password, String name,
			String address, String phone, int onlineNum) {
		// TODO Auto-generated method stub
		return serverResponse.addNewStation(account, password, name, address,
				phone, onlineNum);
	}

	public Manager addNewManager(Manager manager) {
		// TODO Auto-generated method stub
		return serverResponse.addNewManager(manager);
	}

	public boolean detectManager(String account) {
		// TODO Auto-generated method stub
		return serverResponse.detectManager(account);
	}

	/*
	 * about delete
	 */
	public boolean deleteStation(String stationName) {
		// TODO Auto-generated method stub
		return serverResponse.deleteStation(stationName);
	}

	public boolean deleteManager(String stationName, String managerName) {
		// TODO Auto-generated method stub
		return serverResponse.deleteManager(stationName, managerName);
	}

	public boolean deleteStationOrder(String stationName, String orderCode) {
		return serverResponse.deleteStationOrder(stationName, orderCode);
	}

	public boolean deleteOrder(String orderCode) {
		return serverResponse.deleteOrder(orderCode);
	}

	/*
	 * about statistic
	 */

	public int getStationDailyOrderStatistics(String stationName, Date date) {
		// TODO Auto-generated method stub
		return serverResponse.getStationDailyOrderStatistics(stationName, date);
	}

	public int getStationMonthlyOrderStatistics(String stationName, Date date) {
		// TODO Auto-generated method stub
		return serverResponse.getStationMonthlyOrderStatistics(stationName,
				date);
	}

	public int getDailyOrderStatistics(Date date) {
		// TODO Auto-generated method stub
		return serverResponse.getDailyOrderStatistics(date);
	}

	public int getMonthlyOrderStatistics(Date date) {
		// TODO Auto-generated method stub
		return serverResponse.getMonthOrderStatistics(date);
	}

}
