package controllers;


import annotation.Check;
import java.util.List;
import models.Station;
import models.StationManager;
import org.scauhci.enumvalue.RoleNameEnum;
import page.PageFactory;
import page.impl.Page;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.mvc.Controller;

/**
 * 
 * @author volador
 *
 */	
public class Stations extends Controller{
	/**
	 * 列出现有的所有Station
	 * @param page 现在处于的页数 默认是1
	 * @param pageSize 每页显示多少条 默认是10
	 */
     @play.mvc.Before(unless = {})
    public static void checkRole() {
        if (!RoleNameEnum.root.toString().equals(Controller.session.get("role"))) {
            OrderForms.index(1);
        }
    }
	@Check("root")	
	public static void index(Integer... pages){
		
//		Integer page=null;
//		Integer pageSize=null;
//		
//		if(pages!=null&&pages.length==1){
//			page=pages[0];
//		}else if(pages!=null&&pages.length>=2){
//			page=pages[0];
//			pageSize=pages[1];
//		}
//		
//		page=(page == null ? 1 : page);
//		pageSize=(pageSize == null ? 12 : pageSize);
//		List<Integer> pageList=new ArrayList<Integer>();
//		int pageNum=10;	//数字分页栏页面数量
//		
//		long allPage=Station.count()/pageSize;
//		
//		if(Station.count()%pageSize!=0){
//			allPage++;
//		}
//		
//		long all=Station.count();
//		
//		List<Station> allStation=Station.all().fetch(page, pageSize);
//		
//
//		
//		//根据page，allPage，计算fromPage，toPage，并装载进pageList，供前台分页:当前页处于中间位置，页面数量的绝对值为pageNum
//		int shouldLeftStep=pageNum/2;					//应该向前检索几步
//		int realLeftStep;
//		
//		
//		if((page-1) <= shouldLeftStep){  				//实际应该向前检索的步数
//			realLeftStep=page-1;
//		}else{
//			realLeftStep=shouldLeftStep;
//		}
//		
//		
//		
//		int shouldRightStep=pageNum-realLeftStep-1;		//应该向后检索几步
//		int realRightStep;
//		
//		if(page+shouldRightStep <= allPage){			//实际应该向后检索的步数
//			realRightStep=shouldRightStep;
//		}else{
//			realRightStep=(int) (allPage-page);
//		}
//		
//		for(int step=page-realLeftStep;step<=page+realRightStep;step++){
//			pageList.add(step);
//		}
		
		Page pageNow=PageFactory.getPage(Station.class, pages);
		List<Station> allStation=(List<Station>) pageNow.getAllObject();
		long allPage=pageNow.getAllPage();
		Integer pageSize=pageNow.getPageSize();
		List<Integer> pageList=pageNow.getPageList();
		Integer page=pageNow.getPage();

		
		render(allStation,allPage,page,pageSize,pageList);
		
	}
	
	/**
	 * 添加工作站
	 * @param station
	 * @param repassword
	 */
	@Check("root")
	public static void add(@Valid Station station,String repassword){
		//has error
		if(Validation.hasErrors()){
			render(station);
		}
		
		//检验帐号唯一
		List<Station> tmp=Station.find("byAccount", station.account).fetch();
		if(tmp.size()!=0){
			String accountError="该帐号已经被人注册";
			station.account="";
			render(station,accountError);
		}
		
		//校验重复密码
		if(!station.password.equals(repassword)){
			flash.error("两次输入的密码必须一致");
			station.password="";
			render(station);
		}
		
		//ok
		station.save();
		
		show(station.id,1,10);
	}
	
	/**
	 * 删除工作站
	 * @param id  工作站ID
	 */
	@Check("root")
	public static void delete(int id){
		Station station=Station.findById(id);
		if(station==null){
			index(1,5);
		}
		station.delete();
		index(1,5);
	}
	
	/**
	 * 显示具体工作站详细信息
	 * @param id 工作站ID
	 * @param pages 分页信息
	 */
	@Check("station_manager")
	public static void show(int id,Integer... pages){
		Station station=Station.findById(id);
		
		Integer page=null;
		Integer pageSize=null;
		
		if(pages!=null&&pages.length==1){
			page=pages[0];
		}else if(pages!=null&&pages.length>=2){
			page=pages[0];
			pageSize=pages[1];
		}
		
		if(station==null){
			render("Stations/add.html");
		}
		
		page=(page == null ? 1 : page);
		pageSize=(pageSize == null ? 10 : pageSize);
		
		long allPage=StationManager.count("byStationId", id)/pageSize;
		if(Station.count()%pageSize!=0){
			allPage++;
		}
		
		
		List<StationManager> allStationManagers=StationManager.find("byStationId", id).fetch(page,pageSize);
		
		render(station,allStationManagers,allPage,page,pageSize);
	}
	
	/**
	 * 更新工作站信息入口
	 * @param id 工作站ID
	 */
	@Check("root")
	public static void updateById(int id){
		Station station=Station.findById(id);
		if(station==null){
			render("errors/404.html");
		}
		render("Stations/update.html",station);
	}
	
	/**
	 * 具体更新工作站信息的功能实现
	 * @param station 工作站的更新后的信息
	 */
	@Check("root")
	public static void update(@Valid Station station){
		if(Validation.hasErrors()){
			render(station);
		}
		
		Station station_new=Station.findById(station.id);
		
		if(station_new == null){
			render("errors/404.html");
		}
		
		station_new.account=station.account;
		station_new.password=station.password;
		station_new.address=station.address;
		station_new.phone=station.phone;
		station_new.onlineNum=station.onlineNum;
		station_new.save();
		show(station.id,1,10);
	}
	
	/**
	 * 通过id获取station的名字，若id为-1，返回“此站为最后一站”
	 * @param station_id
	 * @return
	 */
	public static String getStationName(@Required(message="一定要提供站点id...") Integer station_id){
		if(validation.hasErrors()){
			render("errors/404.html");
		}
		
		if(station_id==-1){
			return "此站为最后一站";
		}
		
		
		Station station=Station.findById(station_id);
		
		
		if(station==null){
			return "找不到该工作站，可能已经被管理员删除...";
		}
		
		return station.name;
	}
}
