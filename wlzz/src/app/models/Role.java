package models;

import java.util.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import play.data.validation.Required;
import play.db.jpa.GenericModel;

@Entity(name = "role")
public class Role extends GenericModel{
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int id;
	
	@Required(message="必须提供用户名...")
	@Column(name="name")
	public String name;
	
	@Required(message="必须提供角色描述...")
	@Column(name="discription")
	public String discription;
	
	public Role(String name,String discription){
		this.name=name;
		this.discription=discription;
	}
	
}
