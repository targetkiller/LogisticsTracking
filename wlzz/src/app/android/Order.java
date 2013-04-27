package android;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Order {

	private int orderFormId;
	private String userName;
	private String code;
	private String content;
	private int amount;
	private float weight;
	private long createTime;
	private String barcode;
	private String address;
	private String zipCode;
	private String firstStationName;
	private List<Map<String, String>> stations;

	public long getCreateTime() {
		return createTime;
	}

	public String getFirstStationName() {
		return firstStationName;
	}

	public void setFirstStationName(String firstStationName) {
		this.firstStationName = firstStationName;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public int getOrderFormId() {
		return orderFormId;
	}

	public void setOrderFormId(int orderFormId) {
		this.orderFormId = orderFormId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public List<Map<String, String>> getStations() {
		return stations;
	}

	public void setStations(List<Map<String, String>> stations) {
		this.stations = stations;
	}

}
