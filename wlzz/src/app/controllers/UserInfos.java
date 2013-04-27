package controllers;

import models.User;  
import models.UserInfo;
import org.scauhci.enumvalue.RoleNameEnum;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.mvc.Controller;

public class UserInfos extends Controller{
	
	/**
	 * 通过account拿到用户的详细信息
	 * @param account 用户帐号
	 */
	public static void showByAccount(@Required(message="要提供帐号...") String account){
           
		if(Validation.hasErrors())
			render("/errors/404.html");
		User user=User.find("byAccount", account).first();
		if(user == null)
			render("/errors/404.html");
		UserInfo userinfo=UserInfo.findById(user.id);
		render("UserInfos/show.html",userinfo,account);
	}
	
	/**
	 * 按id显示user资料
	 * @param user_id
	 */
	public static void showById(@Required(message="要提供帐号...") String user_id){
		
		if(!"true".equals(session.get("login"))){
			Application.login("您还没登录...");
		}
		
		if(validation.hasErrors()){
			render("errors/404.html");
		}
		User user=null;
		try{
		user=User.findById(Integer.parseInt(user_id));
		}catch(NumberFormatException ex){
			render("errors/404.html");
		}
		if(user == null)
			render("errors/404.html");
		UserInfo userinfo=UserInfo.findById(user.id);
		String account=user.account;
		render("UserInfos/show.html",userinfo,account);
	}
	
	
	/**
	 * 增加个人信息
	 * @param userinfo 用户具体资料
	 */
	public static void add(String ACCOUNT,@Valid UserInfo userinfo){
           
		if(Validation.hasErrors()){
			long ID=userinfo.id;
			render(userinfo,ID,ACCOUNT);
		}
		userinfo.save();
		showByAccount(ACCOUNT);
	}
	
	/**
	 * 通过过account更改用户信息1
	 * @param account 用户帐号
	 */
	public static void updateByAccount(String account){
           
		User user=User.find("byAccount", account).first();
		if(user==null)
			render("errors/404.html");
		UserInfo userinfo=UserInfo.findById(user.id);
		render("/UserInfos/update.html",userinfo,account);
	}
	
	
	
	/**
	 * 通过用户account更改信息2
	 * @param user 用户更新后的信息u
	 */
	public static void update(@Required(message="必须提供帐号...") String account,@Valid UserInfo userinfo){
            
		if(Validation.hasErrors()){
			render("UserInfos/update.html",userinfo,account);
		}
		
		User user=User.find("byAccount", account).first();
		if(user==null)
			render("errors/404.html");
		
		UserInfo userinfo_new=UserInfo.findById(user.id);
		if(userinfo_new == null){	//首次更新资料...
			userinfo.id=user.id;
			userinfo.save();
		}else{
			userinfo_new.name=userinfo.name;
			userinfo_new.address=userinfo.address;
			userinfo_new.age=userinfo.age;
			userinfo_new.email=userinfo.email;
			userinfo_new.gender=userinfo.gender;
			userinfo_new.phone=userinfo.phone;
			userinfo_new.zipCode=userinfo.zipCode;
			userinfo_new.save();
		}
		
		showByAccount(account);
		
	}
	
}
