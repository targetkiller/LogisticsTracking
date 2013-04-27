package hci.wlzz.android.Data;

import hci.wlzz.android.Model.Manager;
import hci.wlzz.android.Model.Order;
import hci.wlzz.android.Model.Station;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author 2012暑假android小组
 * @version 001
 * 
 **/
public abstract interface OperateInterface {

	/*
	 * about station
	 */

	/**
	 * 管理员登录验证
	 **/
	int managerLogin(String account, String password);

	/**
	 * 工作站登录验证
	 **/
	int stationLogin(String account, String password);

	/**
	 * 得到所有的station的name链表
	 **/
	public List<String> getStationNametList();

	/**
	 * 根據station的name得到station對象
	 **/
	public Station getStation(String stationName);

	/*
	 * about manager
	 */

	/**
	 * 根據station的name得到manager的name链表
	 **/
	public List<String> getManagerNameList(String stationName);

	/**
	 * 根据manager的name得到一个Manager对象
	 **/
	public Manager getManager(String managerName);

	/*
	 * about order
	 */

	/**
	 * 根據station的name得到order的code链表
	 **/
	public List<String> getOrderCodeList(String stationName);

	/**
	 * 根據order的code得到一个order对象
	 **/
	public Order getOrder(String orderCode);

	/*
	 * about update
	 */

	/**
	 * 修改manager信息
	 **/
	public int updateManager(Manager manager);

	/**
	 * 修改工作站信息
	 **/
	public int updateStation(int stationId, String name, String account,
			String password, String address, String phone, int onlineNum);

	/**
	 * 修改訂單信息
	 **/
	public int updateOrder(Order order);

	/*
	 * about add
	 */

	/**
	 * 给工作站添加到站訂單
	 **/

	public Order addOrderToStation(String orderCode, String nextStationName,
			String desciption);

	/**
	 * 添加新訂單
	 **/
	public Order addNewOrder(Order order);

	/**
	 * 添加工作站
	 **/
	public Station addNewStation(String account, String password, String name,
			String address, String phone, int onlineNum);

	/**
	 * 添加工作站管理員
	 **/
	public Manager addNewManager(Manager manager);

	/**
	 * 添加工作站管理員帐号，如果帳號可用，返回managerId,如果不可用，返回-1
	 **/
	public boolean detectManager(String ccount);

	/*
	 * about delete
	 */
	/**
	 * 根据工作站名字删除工statio作站
	 **/
	public boolean deleteStation(String stationName);

	/**
	 * 根据管理员名字删除管理员
	 **/
	public boolean deleteManager(String stationName, String managerName);

	public boolean deleteStationOrder(String stationName, String orderCode);

	public boolean deleteOrder(String orderCode);

	/*
	 * about Statistics
	 */
	/**
	 * 根據時間（格式是"yyyy-mm-dd"）统计工作站订单數
	 **/
	public int getStationDailyOrderStatistics(String stationName, Date date);

	/**
	 * 根據時間（格式是"yyyy-mm"）统计工作站订单數
	 **/
	public int getStationMonthlyOrderStatistics(String stationName, Date date);

	/**
	 * 根據時間（格式是"yyyy-mm-dd"）统计工作站订单數
	 **/
	public int getDailyOrderStatistics(Date date);

	/**
	 * 根據時間（格式是"yyyy-mm"）统计所有订单數
	 **/
	public int getMonthlyOrderStatistics(Date date);
}