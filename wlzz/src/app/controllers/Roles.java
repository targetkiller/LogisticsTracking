package controllers;

import java.util.List;

import models.Role;
import org.scauhci.enumvalue.RoleNameEnum;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.mvc.Controller;

/**
 *
 * @author volador
 *
 */
public class Roles extends Controller {

    @play.mvc.Before(unless = {})
    public static void checkRole() {
        if (!RoleNameEnum.root.toString().equals(Controller.session.get("role"))) {
            OrderForms.index(1);
        }
    }

    /**
     * 增加一种角色
     *
     * @param name 角色名
     * @param discription 角色描述
     */
    public static void add(@Required(message = "必须提供角色名...") String name, @Required(message = "必须提供角色描述...") String discription) {
         
        if (Validation.hasErrors()) {
            render(name, discription);
        }

        String errorMessage = "";

        if (Role.find("byName", name).fetch().size() != 0) {
            errorMessage = "该角色已存在...";
            render(errorMessage);
        }

        Role role1 = new Role(name, discription);

        role1.save();

        index();
    }

    /**
     * 显示角色的详细信息
     *
     * @param name 角色名
     */
    public static void show(@Required(message = "必须提供角色名...") String name) {
        if (Validation.hasErrors() || Role.find("byName", name).fetch().size() == 0) {
            render("errors/404.html");
        }
        Role role1 = Role.find("byName", name).first();
        render(role1);
    }

    /**
     * 拿到全部list
     * @return
     */
    public static List<Role> Rolelist(){
    	List<Role> roleList = Role.findAll();
    	return roleList;
    }
    
    /**
     * 角色列表
     */
    public static void index() {
       
        List<Role> roleList = Role.findAll();
        render(roleList);
    }

    /**
     * 删除角色
     *
     * @param id 角色ID
     */
    public static void delete(@Required(message = "必须提供ID...") int id) {
        if (Validation.hasErrors()) {
            render("errors/404.html");
        }
        Role role = Role.findById(id);
        if (role == null) {
            render("errors/404.html");
        }
        role.delete();
        index();
    }

    /**
     * 更新role
     *
     * @param id 角色ID
     */
    public static void updateById(@Required(message = "必须提供ID...") int id) {
        
        if (Validation.hasErrors()) {
            render("errors/404.html");
        }
        Role role1 = Role.findById(id);
        if (role1 == null) {
            render("errors/404.html");
        }

        render("Roles/update.html", role1);
    }

    /**
     * 更新role信息
     *
     * @param role
     */
    public static void update(@Valid Role role1) {
        if (Validation.hasErrors()) {
            render(role1);
        }
        Role role_new = Role.findById(role1.id);
        role_new.name = role1.name;
        role_new.discription = role1.discription;
        role_new.save();
        show(role_new.name);
    }
}
