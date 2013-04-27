package models;



import javax.persistence.*; 

import play.db.jpa.GenericModel;
import play.data.validation.*;

/**
 * 
 * @author haikang
 *
 */
@Entity(name = "user_info")
public class UserInfo extends GenericModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	public int id;
		
	@Required(message="必须提供姓名...")
	@Column(name = "name")
	@MaxSize(16)
	public String name;
	
	@Email(message="无效的email地址...")
	@Required(message="必须提供有效email，方便您找回密码...")
	@MaxSize(32)
	@Column(name = "email")
	public String email;
	
	@Required(message="必须提供有效年龄...")
	@Column(name = "age")
	public Integer age;
	
	@Required(message="必须给出性别...")
	@Column(name = "gender")
	public boolean gender;			//true == "man",false == "woman"
	
	@Required(message="必须提供有效联系方式...")
	@MaxSize(32)
	@Column(name = "phone")
	public String phone;
	
	@Required(message="必须提供有效邮编...")
	@MaxSize(6)
	@Column(name = "zip_code")
	public String zipCode;
	
	@Required(message="必须提供有效地址...")
	@MaxSize(108)
	@Column(name = "address")
	public String address;
	
	public UserInfo(String email,String name,String phone,String zipCode,String address,boolean gender,int age ){
		this(email,name,phone,zipCode,address);
		this.gender = gender;
		this.age = age;
	}
	
	public UserInfo(String email,String name,String phone,String zipCode,String address){
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.zipCode = zipCode;
		this.address = address;
	}
/*
	public static void delete(@Required int id){
		UserInfo uif = UserInfo.findById(id);
		uif.delete();
	}
	
	public UserInfo edit(String email,String name,int age,boolean gender,
			String phone,String zipCode,String address){
			this.email = email;
			this.name = name;
			this.age = age;
			this.gender = gender;
			this.phone = phone;
			this.zipCode = zipCode;
			this.address = address;
			return save();
		}
	
	public static List<UserInfo> getAllUserInfo(){
		return UserInfo.findAll();
	}

*/
}
