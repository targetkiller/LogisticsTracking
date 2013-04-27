package controllers;

import java.util.List;
import models.Station;
import models.StationManager;
import models.User;
import org.scauhci.enumvalue.RoleNameEnum;
import page.PageFactory;
import page.impl.Page;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.db.jpa.GenericModel;
import play.mvc.Before;
import play.mvc.Controller;

/**
 * 检测用户登录...
 *
 * @author haikang
 *
 */
public class Application extends Controller {

    /**
     * 拦截器，判断用户是否登录
     */
    @Before(unless = {"login", "loginProcesser", "signUp", "register","index"})
    static void isSignIn() {			//using session id to verify that you are logged.
        String message = "你尚未登录...";
        String logged = Controller.session.get("login");

        if (logged == null || logged.equals("false")) {
            login(message);
        }
    }

    /**
     * 根据用户帐号密码处理登录过程。
     *
     * @param account 用户帐号
     * @param password	用户密码
     */
    public static void loginProcesser(@Required(message = "帐号不能为空...") String account, @Required(message = "密码不能为空...") String password) {
    	String errorMessage;
    	if (Validation.hasErrors()) {
        	errorMessage="请填完整登录信息...";
            login(errorMessage);
        }

        
        User user = User.find("byAccount", account).first();

        if (user == null) {
            errorMessage = "该用户尚未注册,请先注册...";
            login(errorMessage);
        }

        if (!user.password.equals(password)) {
            errorMessage = "密码错误...";
            login(errorMessage);
        }
        //登录成功
        Controller.session.clear();
        Controller.session.put("login", "true");
        Controller.session.put("user_id", user.id);
        Controller.session.put("role", user.getRoleName());
        
        if (RoleNameEnum.station_manager.toString().equals(user.getRoleName())) {
            secondLogin();
        }
        OrderForms.index(1);
    }

    /**
     * 二次登录
     */
    public static void secondLogin(Integer... pages) {
       //权限检测
    	if (!RoleNameEnum.station_manager.toString().equals(Controller.session.get("role"))) {
            index();
        }
        
    	Page pageNow = PageFactory.getPage(Station.class, pages);
        List<Station> allStation = (List<Station>) pageNow.getAllObject();
        long allPage = pageNow.getAllPage();
        Integer pageSize = pageNow.getPageSize();
        List<Integer> pageList = pageNow.getPageList();
        Integer page = pageNow.getPage();
        render(allStation, allPage, page, pageSize, pageList);
    }

    public static void secondLoginProcesser(@Required(message = "帐号不能为空...") String account, @Required(message = "密码不能为空...") String password) {
        //权限检测
    	if (!RoleNameEnum.station_manager.toString().equals(Controller.session.get("role"))) {
            OrderForms.index(1);
        }
        
    	if (validation.hasErrors()) {
            secondLogin();
        }
        StationManager stationManager = StationManager.find("byUserId", Integer.parseInt(session.get("user_id"))).first();

        Station station = Station.findById(stationManager.stationId);
        if (station.account.equals(account) && station.password.equals(password)) {
            Controller.session.put("station_id", stationManager.stationId);
            OrderForms.index(1);
        }
        secondLogin();
    }

    /**
     * 注册...
     */
    public static void register() {
        render("Users/add.html");
    }

    /**
     * 退出...
     */
    public static void signOut() {
        session.put("login", "false");
        Controller.session.clear();
        login(null);
    }

    public static void index() {
        OrderForms.index(1);
    }

    public static void login(String errorMessage) {
        render(errorMessage);
    }

    public static void signUp() {
        render();
    }

    /**
     * 跳转到静态资源
     */
    public static void goTo(String controller, String page) {
        String stationId = params.get("stationId");
        String account = params.get("account");
        String fromPath=params.get("fromPath");
        render(controller + "/" + page, stationId, account,fromPath);
    }
}
