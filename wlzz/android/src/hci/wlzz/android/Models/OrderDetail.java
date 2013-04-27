package hci.wlzz.android.Models;

public class OrderDetail {
	private int id;
	private int orderFormId;
	private int stationId;
	private int userId;
	private int nextStationId;
	private long updateTime;
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderFormId() {
		return orderFormId;
	}

	public void setOrderFormId(int orderFormId) {
		this.orderFormId = orderFormId;
	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getNextStationId() {
		return nextStationId;
	}

	public void setNextStationId(int nextStationId) {
		this.nextStationId = nextStationId;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
