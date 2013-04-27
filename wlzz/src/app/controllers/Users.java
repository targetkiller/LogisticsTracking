package controllers;

import java.util.List;

import page.PageFactory;
import page.impl.Page;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.mvc.Controller;
import models.Role;
import models.User;
import models.UserInfo;
import org.scauhci.enumvalue.RoleNameEnum;

/**
 *
 * @author volador
 *
 */
public class Users extends Controller {

    /**
     * 分页显示全部人员信息
     *
     * @param pages	分页信息，param1：第几页 param2：每页显示几条
     */
    @SuppressWarnings("unchecked")
    public static void index(Integer... pages) {
   
        Page pageNow = PageFactory.getPage(User.class, pages);
        List<User> allUser = (List<User>) pageNow.getAllObject();
        long allPage = pageNow.getAllPage();
        long page = pageNow.getPage();
        long pageSize = pageNow.getPageSize();
        List<Integer> pageList = pageNow.getPageList();
        render(allUser, allPage, page, pageSize, pageList);
    }

    /**
     * 获取user名字
     * @param userId
     * @return 
     */
    public static String getUserName(String userId){
        User user=null;
        if(userId!=null){
        	try{
        		user=User.findById(Integer.parseInt(userId));
        	}catch(NumberFormatException ex){
        		render("errors/404.html");
        	}
        }
        String userName="";
        if(user!=null){
           userName=user.getRealName();
        }
        return userName;
    }
    
    /**
     * 根据用户id获取用户account
     * @param user_id
     * @return
     */
    public static String getUserAccount(String userId){
    	User user=null;
        if(userId!=null){
        	try{
            user=User.findById(Integer.parseInt(userId));
        	}catch(NumberFormatException ex){
        		render("errors/404.html");
        	}
        }
        String userAccount="";
        if(user!=null){
           userAccount=user.account;
        }
        return userAccount;
    }
    /**
     * 增加用户,接着进入完善用户资料的action
     *
     * @param user 用户账户资料
     */
    public static void add(@Valid User user, @Required(message = "必须填入重复密码...") String rePassword, @Required(message = "必须填入角色名...") String roleName) {
       
        String errorMessage = null;
        //有空字段...
        if (Validation.hasErrors()) {
            render(user);
        }

        //重复密码错误...
        if (!user.password.equals(rePassword)) {
            errorMessage = "重复密码错误...";
            render(user, errorMessage);
        }

        //账户已经存在...
        if (User.find("byAccount", user.account).first() != null) {
            errorMessage = "该账户已经存在...";
            render(errorMessage);
        }

        //不存在该角色
        Role role1 = Role.find("byName", roleName).first();
        if (role1 == null) {
            errorMessage = "不存在该角色...";
            render(user, errorMessage);
        }

        user.roleId = role1.id;

        //成功
        user.save();
        int ID = user.id;
        String ACCOUNT = user.account;
        render("UserInfos/add.html", ACCOUNT, ID); //增加用户资料
    }

    /**
     * 更改密码
     *
     * @param account	用户帐号
     * @param oldPassowrd	用户旧密码
     * @param newPassword	用户新密码
     * @param reNewPassword 重复新密码
     */
    public static void changePassword(@Required(message = "必须提供帐号...") String account, @Required(message = "必须提供旧密码...") String oldPassword, @Required(message = "必须提供新密码...") String newPassword, @Required(message = "必须提供重复密码...") String reNewPassword) {
        
    	//判断是哪个地方请求过来的，要用什么页面返回回去,
    	String fromPath=params.get("fromPath");
    	if(fromPath==null||fromPath.length()==0){
    		render("errors/404.html");
    	}
    	
    	
    	String message;
    	
        if (Validation.hasErrors()) {
            render(account, oldPassword, newPassword, reNewPassword);
        }

        String errorMessage;
        User user = User.find("byAccount", account).first();
        //用户不存在...
        if(user==null){
        	render("errors/404.html");
        }

        //检查旧密码是否正确
        if (!user.password.equals(oldPassword)) {
            errorMessage = "旧密码错误...";
            render(account, errorMessage);
        }

        //检查新密码与重复密码是否一致
        if (!newPassword.equals(reNewPassword)) {
            errorMessage = "新密码与重复密码必须一致...";
            render(account, errorMessage);
        }

        //ok
        user.password = newPassword;
        user.save();
        if(fromPath.equals("个人资料管理")){
        	message="更改成功...";
        	render(account,message);
        }
        if(fromPath.equals("用户管理")){
        	index();
        }
    }

    /**
     * 更改角色
     *
     * @param account	用户帐号
     * @param oldRole	用户旧角色
     * @param newRole	用户新角色
     */
    public static void changeRole(@Required(message = "必须输入用户帐号...") String account, @Required(message = "必须输入旧角色...") String oldRole, @Required(message = "必须输入旧角色...") String newRole) {
        //信息不完整
       
        String errorMessage = "";
        if (Validation.hasErrors()) {
            render(account, oldRole, newRole);
        }
        //用户不存在
        User user = User.find("byAccount", account).first();
        if (user == null) {
            render("errors/404.html");
        }
        //旧角色名错误
        if (!oldRole.equals(user.getRoleName())) {
            errorMessage = "旧角色名错误...";
            render(account, errorMessage);
        }

        //新角色名不存在
        Role role1 = Role.find("byName", newRole).first();
        if (role1 == null) {
            errorMessage = "新角色名不存在...";
            render(errorMessage, account, oldRole);
        }

        //ok
        user.roleId = role1.id;
        user.save();
        index();
    }

    /**
     * 彻底删除某用户...
     *
     * @param account 用户帐号
     */
    public static void delete(String account) {
        User user = User.find("byAccount", account).first();
        if (user == null) {
            render("errors/404.html");
        }
        UserInfo userinfo = UserInfo.findById(user.id);
        if (userinfo != null) {
            userinfo.delete();
        }
        user.delete();
        index();
    }
}