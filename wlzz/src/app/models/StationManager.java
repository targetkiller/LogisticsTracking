package models;

import java.text.SimpleDateFormat; 
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import play.data.validation.Required;
import play.db.jpa.GenericModel;

@Entity(name="station_manager")
public class StationManager extends GenericModel{
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int id;
	
	@Required(message="工作站管理员的ID是必须的...")
	@Column(name="user_id",nullable=false)
	public int userId;
	
	@Required(message="工作站ID是必须的...")
	@Column(name="station_id",nullable=false)
	public int stationId;
	
	@Required(message="管理员上次登录的IP是必须的...")
	@Column(name="ip",nullable=false)
	public String ip;
	
	@Required(message="管理员上次登录的时间是必须的...")
	@Column(name="last_login_time",nullable=false)
	public long lastLoginTime;
	
	
	public StationManager(int userId,int stationId,String ip,long lastLoginTime){
		this.userId=userId;
		this.stationId=stationId;
		this.ip=ip;
		this.lastLoginTime=lastLoginTime;
	}
	
	/**
	 * 将秒数转换成String的显示格式
	 * @return 返回String格式的时间形式
	 */
	public String getTime(){
		String time="";
		Date n = new Date(this.lastLoginTime);
		time=(new SimpleDateFormat("yyyy-MM-dd")).format(n);
		if(time.equals("1970-01-01"))
				time="0000-00-00";
		return time;
	}
	
	/**
	 * 返回userid对应的user_account
	 * @return userid对应的user_account
	 */
	public String getUserAccount(){
		return ((User)User.findById(this.userId)).account;
	}
	
}
