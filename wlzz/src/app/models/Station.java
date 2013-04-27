package models;

import javax.persistence.Column;  
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.GenericModel;

@Entity(name="station")
public class Station extends GenericModel{
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int id;
	
	@Required(message="工作站名字是必须的...")
	@Column(name="name",nullable=false)
	public String name;
	
	@Required(message="账户是必须的...")
	@Column(name="account",nullable=false)
	public String account;
	
	@Required(message="密码是必须的...")
	@Column(name="password",nullable=false)
	public String password;
	
	@Required(message="地址是必须的...")
	@MaxSize(108)
	@Column(name="address",nullable=false)
	public String address;
	
	@Required(message="联系电话是必须的...")
	@MaxSize(32)
	@Column(name="phone",nullable=false)
	public String phone;
	
	@Required(message="同时登录人数是必须的...")
	@Column(name="online_num",nullable=false)
	public int onlineNum;
	
	public Station(String account,String password,String address,String phone,int onlineNum){
		this.account=account;
		this.password=password;
		this.address=address;
		this.phone=phone;
		this.onlineNum=onlineNum;
	}

}
