package hci.wlzz.android.Cache;

import hci.wlzz.android.Data.OperateInterface;
import hci.wlzz.android.HttpRequest.ServerResponse;
import hci.wlzz.android.Model.Manager;
import hci.wlzz.android.Model.Order;
import hci.wlzz.android.Model.Station;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;

public class LocalOperate implements OperateInterface {

	Context context;
	ServerResponse serverResponse;

	public LocalOperate(Context context) {
		this.context = context;
		this.serverResponse = new ServerResponse(context);
	}

	public int managerLogin(String account, String password) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int stationLogin(String account, String password) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<String> getStationNametList() {
		// TODO Auto-generated method stub
		return new ListDAO(context).getStationNameList();
	}

	public Station getStation(String stationName) {
		// TODO Auto-generated method stub
		Station station;
		StationDAO stationDAO = new StationDAO(context);
		List<Station> stationList = stationDAO.getScrollData(0,
				stationDAO.getCount());
		for (int i = 0; i < stationDAO.getCount(); i++) {
			if (stationName.equals(stationList.get(i).getName())) {
				station = stationList.get(i);
				return station;
			}
		}
		return null;
	}

	public List<String> getManagerNameList(String stationName) {
		// TODO Auto-generated method stub
		List<String> managerNameList = new ArrayList<String>();
		ManagerDAO managerDAO = new ManagerDAO(context);
		List<Manager> managerList = managerDAO.getScrollData(0,
				managerDAO.getCount());
		for (int i = 0; i < managerDAO.getCount(); i++) {
			managerNameList.add(managerList.get(i).getAccount());
		}
		return managerNameList;
	}

	public Manager getManager(String managerName) {
		// TODO Auto-generated method stub
		Manager manager;
		ManagerDAO managerDAO = new ManagerDAO(context);
		List<Manager> managerList = managerDAO.getScrollData(0,
				managerDAO.getCount());
		for (int i = 0; i < managerDAO.getCount(); i++) {
			if (managerName.equals(managerList.get(i).getName())) {
				manager = managerList.get(i);
				return manager;
			}
		}
		return null;
	}

	public List<String> getOrderCodeList(String stationName) {
		// TODO Auto-generated method stub
		List<String> orderCodeList = new ArrayList<String>();
		StationDAO stationDAO = new StationDAO(context);
		List<Station> stationList = stationDAO.getScrollData(0,
				stationDAO.getCount());
		for (int i = 0; i < stationDAO.getCount(); i++) {
			if (stationName.equals(stationList.get(i).getName())) {
				Station station = stationList.get(i);
				List<Order> orderList = station.getOrders();
				for (int j = 0; j < orderList.size(); j++) {
					orderCodeList.add(orderList.get(j).getCode() + "");
				}
				return orderCodeList;
			}
		}
		return null;
	}

	public Order getOrder(String orderCode) {
		// TODO Auto-generated method stub
		Order order;
		OrderDAO orderDAO = new OrderDAO(context);
		List<Order> orderList = orderDAO.getScrollData(0, orderDAO.getCount());
		for (int i = 0; i < orderDAO.getCount(); i++) {
			if (orderCode.equals(orderList.get(i).getCode())) {
				order = orderList.get(i);
				return order;
			}
		}
		return null;
	}

	public int updateManager(Manager manager) {
		// TODO Auto-generated method stub
		ManagerDAO managerDAO = new ManagerDAO(context);
		managerDAO.update(manager);
		return 1;
	}

	public int updateStation(int stationId, String name, String account,
			String password, String address, String phone, int onlineNum) {
		// TODO Auto-generated method stub
		StationDAO stationDAO = new StationDAO(context);
		Station station = stationDAO.find(stationId);
		station.setName(name);
		station.setAccount(account);
		station.setPassword(password);
		station.setAddress(address);
		station.setPhone(phone);
		station.setOnlineNum(onlineNum);
		stationDAO.update(station);
		return 0;

	}

	/**
	 * 修改訂單信息
	 **/
	public int updateOrder(Order order) {
		return 0;
	}

	/**
	 * 给工作站添加到站訂單
	 **/

	public Order addOrderToStation(String orderCode, String nextStationName,
			String desciption) {
		return null;
	}

	/**
	 * 添加新訂單
	 **/
	public Order addNewOrder(Order order) {
		return null;
	}

	public Station addNewStation(String account, String password, String name,
			String address, String phone, int onlineNum) {
		// TODO Auto-generated method stub
		return null;
	}

	public Manager addNewManager(Manager manager) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean detectManager(String account) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteStation(String stationName) {
		// TODO Auto-generated method stub
		// StationDAO stationDAO = new StationDAO(context);
		// List<Station> stationList = stationDAO.getScrollData(0,
		// stationDAO.getCount());
		// for (int i = 0; i < stationDAO.getCount(); i++) {
		// if (stationName.equals(stationList.get(i).getName())) {
		// Station station = stationList.get(i);
		// stationDAO.detele(station);
		// return true;
		// }
		// }
		return false;
	}

	public boolean deleteManager(String stationName, String managerName) {
		// TODO Auto-generated method stub
		// ManagerDAO managerDAO = new ManagerDAO(context);
		// Manager manager;
		// List<Manager> managerList = managerDAO.getScrollData(0,
		// managerDAO.getCount());
		// for (int i = 0; i < managerDAO.getCount(); i++) {
		// if (managerName.equals(managerList.get(i).getName())) {
		// manager = managerList.get(i);
		// managerDAO.detele(manager);
		// return true;
		// }
		// }
		return false;
	}

	public boolean deleteStationOrder(String stationName, String orderCode) {
		return false;
	}

	public boolean deleteOrder(String orderCode) {
		return false;
	}

	/*
	 * about Statistics
	 */
	/**
	 * 根據時間（格式是"yyyy-mm-dd"）统计工作站订单數
	 **/
	public int getStationDailyOrderStatistics(String stationName, Date date) {
		return 0;
	}

	/**
	 * 根據時間（格式是"yyyy-mm"）统计工作站订单數
	 **/
	public int getStationMonthlyOrderStatistics(String stationName, Date date) {
		return 0;
	}

	/**
	 * 根據時間（格式是"yyyy-mm-dd"）统计工作站订单數
	 **/
	public int getDailyOrderStatistics(Date date) {
		return 0;
	}

	/**
	 * 根據時間（格式是"yyyy-mm"）统计所有订单數
	 **/
	public int getMonthlyOrderStatistics(Date date) {
		return 0;
	}
}