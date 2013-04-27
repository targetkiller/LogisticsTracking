package models;

  

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import play.data.validation.MaxSize;
import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.GenericModel;

@Entity(name = "order_form")
public class OrderForm extends GenericModel implements Cloneable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    
    @Required(message="用户名不能为空！")
    @Column(name = "user_name", nullable = false)
    public String userName;
    
    @Required(message="地址不能为空！")
    @MaxSize(value=108,message="地址不能超过108个字符...")
    @Column(name = "address", nullable = false)
    public String address;
    
    @Required(message="邮编不能为空！")
    @MaxSize(value=6,message="邮编不能超过6个字符...")
    @Column(name = "zip_code", nullable = false)
    public String zipCode;
    
    @Required(message="内容不能为空！")
    @MaxSize(value=100,message="内容不能超过100个字符...")
    @Column(name = "content", nullable = false)
    public String content;
    
    @Required(message="数量不能为空！")
    @Column(name = "amount", nullable = false)
    public int amount;
    
    @Required(message="重量不能为空！")
    @Column(name = "weight", nullable = false)
    public float weight;
    
    @Required(message="订单号不能为空！")
    @MaxSize(value=32,message="订单号不能超过32个字符...")
    @Column(name = "code", nullable = false)
    public String code;
    
    @Required(message="条形码不能为空！")
    @MaxSize(value=32,message="条形码不能超过32个字符...")
    @Column(name = "barcode", nullable = false)
    public String barcode;
    
    @Required(message="订单创建时间不能为空！")
    @Column(name = "create_time", nullable = false)
    public long createTime;
    
    @Required
    @Column(name = "station_id", nullable = false)
    public int stationId;
    
    @Transient
    public String creationTime;
    @Transient
    public OrderDetail orderDetail;
    @Transient
    public Station station;
       
    

    public OrderForm(String userName, String address, String zipCode, String content, int amount, float weight, String code, String barcode, long createTime) {
        this.userName = userName;
        this.address = address;
        this.zipCode = zipCode;
        this.content = content;
        this.amount = amount;
        this.weight = weight;
        this.code = code;
        this.barcode = barcode;
        this.createTime = createTime;
    }

    public OrderForm(int id, String userName, String address, String zipCode, String content, int amount, float weight, String code, String barcode, long createTime) {
        this.id = id;
        this.userName = userName;
        this.address = address;
        this.zipCode = zipCode;
        this.content = content;
        this.amount = amount;
        this.weight = weight;
        this.code = code;
        this.barcode = barcode;
        this.createTime = createTime;
    }
    
    
    
}
