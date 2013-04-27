package controllers;

import play.mvc.Before; 
import play.mvc.Controller;
import annotation.Check;

import models.Role;
import models.User;


/**
 * 以拦截器(check)方式提供检测每个操作权限
 * @author haikang
 *
 */
public class RoleCheck extends Controller{
	@Before
	static void check(){
		Check c = Controller.getActionAnnotation(Check.class);
		if(c!=null){
			User user = User.findById(session.get("user_id"));
			Role r = Role.findById(user.roleId);
			if(r.name!=c.value())
				render("/users/fail.html","你没有权限访问该页面");
		}
	}
}
