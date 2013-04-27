package models;

import java.util.List;

import javax.persistence.*;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.GenericModel;

@Entity(name = "user")
public class User extends GenericModel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "id")
    public int id;
    @Column(name = "role_id")
    public int roleId;
    @Required(message = "用户帐号必须提供...")
    @MaxSize(32)
    @Column(name = "account")
    public String account;
    @Required(message = "用户密码必须提供...")
    @MaxSize(32)
    @Column(name = "password")
    public String password;
    
	/**
	 * 拿到该用户的角色
	 * @return 角色名
	 */
	public String getRoleName(){
		Role role=Role.findById(this.roleId);
		return role.name;
	}

    public User(String account, String password,int roleId) {
        this.account = account;
        this.password = password;
        //Role r = Role.find("byName", "default_role").first();
        this.roleId = roleId;
    }

    /**
     * 返回用户名字
     *
     * @return 用户名字
     */
    public String getRealName() {
        UserInfo userinfo = UserInfo.findById(this.id);
        if(userinfo==null)
        	return "暂无";
        else
        	return userinfo.name;
    }

    /**
     * 返回用户性别
     *
     * @return 用户性别
     */
    public boolean getGender() {
        UserInfo userinfo = UserInfo.findById(this.id);
        return userinfo.gender;
    }
}
