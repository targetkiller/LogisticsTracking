package controllers;

import annotation.Check; 
import models.Role;
import models.StationManager;
import models.User;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.mvc.Controller;

public class StationManagers extends Controller{
	
	
	/**
	 * 给某个工作站增加管理员
	 * @param stationId 工作站id
	 */
	
	public static void add(int stationId,@Required(message="用户ID必须提供...") String account){
		flash.error("");
		if(Validation.hasErrors()){
			Validation.keep();
			Stations.show(stationId);
		}
		
		User user=User.find("byAccount", account).first();
		if(user==null){//用户不存在
			flash.error("用户不存在...");
			flash.keep();
			Stations.show(stationId);
		}
		
		//判断角色,除了普通用户之外都能当作管理员 ..
		Role role=Role.findById(user.roleId);
		if(role.name.equals("user")){
			flash.error("该用户无权成为管理员...");
			flash.keep();
			Stations.show(stationId);
		}
		
		if(StationManager.find("byStationIdAndUserId",stationId,user.id).fetch().size()!=0){		//已经存在
			flash.error("管理员已存在...");
			flash.keep();
			Stations.show(stationId);
		}
		
		
		//初始化ip
		String ip="0.0.0.0";			//request.remoteAddress;
		//初始化time
		long lastLoginTime=0;				//request.date.getTime();
		
		StationManager stationmanager=new StationManager(user.id, stationId, ip, lastLoginTime);
		stationmanager.save();
		Stations.show(stationId);
		
	}
	
	/**
	 * 删除stationId下面的manager
	 * @param stationId 工作站ID
	 * @param managerId	管理员ID
	 */
	@Check("root")
	public static void delete(int stationId,int managerId){
		StationManager manager=StationManager.findById(managerId);
		manager.delete();
		Stations.show(stationId);
	}
}
