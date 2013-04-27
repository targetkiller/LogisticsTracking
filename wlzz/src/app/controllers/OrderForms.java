package controllers;

import java.util.ArrayList; 
import java.util.Collections;
import java.util.Date;
import java.util.List;
import models.OrderDetail;
import models.OrderForm;
import models.Station;
import models.UserInfo;

import org.scauhci.enumvalue.IsFinishEnum;
import org.scauhci.enumvalue.LastStationEnum;
import org.scauhci.enumvalue.RoleNameEnum;

import play.cache.Cache;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.libs.* ;
import play.mvc.Before;
import play.mvc.Controller;

/**
 *
 * @author willing
 */
public class OrderForms extends Controller {



//    @play.mvc.Before(unless = {})
//    public static void text() {
//        Controller.session.clear();
//        Controller.session.put("station_id", "127");
//        Controller.session.put("user_id", "321");
//        Controller.session.put("role", "station_manager");
//        //Controller.session.put("role", "root");
//        //System.out.println(Controller.session.get("role"));
//
//    }
    /**
     *
     * @param noPage 需要返回到第几页
     * @param allOrderForm OrderForm列表
     * @param controllerPage 跳到哪个Controler下的哪个页面
     */
    private static void pageing(Integer noPage, List<OrderForm> allOrderForm, String controllerPage) {
        if (noPage == null) {
            noPage = 1;
        }
        List<Integer> pageList = new ArrayList<Integer>();
        int pageSize = 15;//每页有十条记录
        if (allOrderForm == null) {
            allOrderForm = new ArrayList<OrderForm>();
        }
        System.out.println(allOrderForm.size());
        int count = allOrderForm.size();
        int page = noPage;
        int allPage;
        if (count == 0) {
            allPage = 0;
            page = 0;
            render(controllerPage, allOrderForm, pageList, page, allPage);
        }
        int pageNum = count / pageSize;
        if (count % pageSize != 0) {
            pageNum++;
        }
        allPage = pageNum;
        for (int i = 1; i <= pageNum; i++) {
            pageList.add(i);
        }
        if (page == pageNum) {
            allOrderForm = allOrderForm.subList(pageSize * (page - 1), allOrderForm.size());
            for (OrderForm of : allOrderForm) {
                of.address += "   ";
                of.address = of.address.substring(0, 4);
                of.address += "...";
            }
        } else {
            allOrderForm = allOrderForm.subList(pageSize * (page - 1), pageSize * page);
            for (OrderForm of : allOrderForm) {
                of.address += "   ";
                of.address = of.address.substring(0, 4);
                of.address += "...";
            }
        }
        render(controllerPage, allOrderForm, pageList, page, allPage);
    }

    /**
     * 根据站管理员或总管理员列出现有的所有OrderForms
     */
    public static void index(Integer noPage) {

        List<OrderForm> allOrderForm = new ArrayList<OrderForm>();
        //超级管理员可以看到全部的订单
        if (RoleNameEnum.root.toString().equals(Controller.session.get("role"))) {
            List<OrderDetail> allOrderDetail = OrderDetail.findAll();
            for (OrderDetail od : allOrderDetail) {
                if (od.nextStationId != LastStationEnum.isLastStation.value) {
                    Station nextStation = Station.findById(od.nextStationId);
                    od.nextStation = nextStation;
                    OrderForm of = OrderForm.findById(od.orderFormId);
                    OrderForm orderForm = new OrderForm(of.id, of.userName, of.address, of.zipCode, of.content, of.amount, of.weight, of.code, of.barcode, of.createTime);
                    orderForm.orderDetail = od;
                    allOrderForm.add(orderForm);
                }
            }
            pageing(noPage, allOrderForm, "OrderForms/index.html");
        }
        //站管理员只能看到本站的订单
        if (RoleNameEnum.station_manager.toString().equals(Controller.session.get("role"))) {
            List<OrderDetail> allOrderDetail = OrderDetail.find("next_station_id", Integer.parseInt(Controller.session.get("station_id"))).fetch();//TODO
            for (OrderDetail od : allOrderDetail) {
                Station nextStation = Station.findById(od.nextStationId);
                od.nextStation = nextStation;
                OrderForm of = OrderForm.findById(od.orderFormId);
                OrderForm orderForm = new OrderForm(of.id, of.userName, of.address, of.zipCode, of.content, of.amount, of.weight, of.code, of.barcode, of.createTime);
                orderForm.orderDetail = od;
                allOrderForm.add(orderForm);
            }
            pageing(noPage, allOrderForm, "OrderForms/index.html");
        }
        render("OrderForms/checkOrder.html");
    }

    /**
     * 根据展馆里员或总管理员列出已处理的订单
     */
    public static void showFinishOrderForms(Integer noPage) {
        List<OrderForm> allOrderForm = new ArrayList<OrderForm>();
        if (RoleNameEnum.root.toString().equals(Controller.session.get("role"))) {
            List<OrderDetail> allOrderDetail = OrderDetail.find("is_finish", IsFinishEnum.isFinish.value).fetch();
            for (OrderDetail od : allOrderDetail) {
                if (od.nextStationId != LastStationEnum.isLastStation.value) {
                    Station nextStation = Station.findById(od.nextStationId);
                    od.nextStation = nextStation;
                    OrderForm of = OrderForm.findById(od.orderFormId);
                    OrderForm orderForm = new OrderForm(of.id, of.userName, of.address, of.zipCode, of.content, of.amount, of.weight, of.code, of.barcode, of.createTime);
                    orderForm.orderDetail = od;
                    allOrderForm.add(orderForm);
                }
            }
            pageing(noPage, allOrderForm, "OrderForms/showFinishOrderForms.html");
        }//超级管理员可以看到全部已处理的订单

        if (RoleNameEnum.station_manager.toString().equals(Controller.session.get("role"))) {
            List<OrderDetail> allOrderDetail = OrderDetail.find("next_station_id = ? AND is_finish = ?", Integer.parseInt(Controller.session.get("station_id")), IsFinishEnum.isFinish.value).fetch();//TODO
            for (OrderDetail od : allOrderDetail) {
                Station nextStation = Station.findById(od.nextStationId);
                od.nextStation = nextStation;
                OrderForm of = OrderForm.findById(od.orderFormId);
                OrderForm orderForm = new OrderForm(of.id, of.userName, of.address, of.zipCode, of.content, of.amount, of.weight, of.code, of.barcode, of.createTime);
                orderForm.orderDetail = od;
                allOrderForm.add(orderForm);
            }
            pageing(noPage, allOrderForm, "OrderForms/showFinishOrderForms.html");
        }//站管理员只能看到本站已处理的订单    
        render("OrderForms/checkOrder.html");
    }

    /**
     * 根据站管理员或总管理员列出未处理的订单
     */
    public static void showNoFinishOrderForms(Integer noPage) {
        List<OrderForm> allOrderForm = new ArrayList<OrderForm>();
        if (RoleNameEnum.root.toString().equals(Controller.session.get("role"))) {

            List<OrderDetail> allOrderDetail = OrderDetail.find("is_finish", IsFinishEnum.noFinish.value).fetch();
            for (OrderDetail od : allOrderDetail) {
                Station nextStation = Station.findById(od.nextStationId);
                od.nextStation = nextStation;
                OrderForm of = OrderForm.findById(od.orderFormId);
                OrderForm orderForm = new OrderForm(of.id, of.userName, of.address, of.zipCode, of.content, of.amount, of.weight, of.code, of.barcode, of.createTime);
                orderForm.orderDetail = od;
                allOrderForm.add(orderForm);
            }
            pageing(noPage, allOrderForm, "OrderForms/showNoFinishOrderForms.html");
        }//超级管理员可以看到全部已处理的订单

        if (RoleNameEnum.station_manager.toString().equals(Controller.session.get("role"))) {
            List<OrderDetail> allOrderDetail = OrderDetail.find("next_station_id = ? AND is_finish = ?", Integer.parseInt(Controller.session.get("station_id")), IsFinishEnum.noFinish.value).fetch();//TODO
            for (OrderDetail od : allOrderDetail) {
                Station nextStation = Station.findById(od.nextStationId);
                od.nextStation = nextStation;
                OrderForm of = OrderForm.findById(od.orderFormId);
                OrderForm orderForm = new OrderForm(of.id, of.userName, of.address, of.zipCode, of.content, of.amount, of.weight, of.code, of.barcode, of.createTime);
                orderForm.orderDetail = od;
                allOrderForm.add(orderForm);
            }
            pageing(noPage, allOrderForm, "OrderForms/showNoFinishOrderForms.html");
        }//站管理员只能看到本站已处理的订单      
        render("OrderForms/checkOrder.html");
    }

    /**
     * 显示本站创建的订单
     */
    public static void showCreateOrderForms(Integer noPage) {
        List<OrderForm> allOrderForms = new ArrayList<OrderForm>();
        if (RoleNameEnum.root.toString().equals(Controller.session.get("role"))) {
            allOrderForms = OrderForm.findAll();
            for (OrderForm od : allOrderForms) {
                od.station = Station.findById(od.stationId);
            }
            pageing(noPage, allOrderForms, "OrderForms/showCreateOrderForms.html");
        }
        if (RoleNameEnum.station_manager.toString().equals(Controller.session.get("role"))) {
            allOrderForms = OrderForm.find("station_id", Controller.session.get("station_id")).fetch();
            for (OrderForm od : allOrderForms) {
                od.station = Station.findById(od.stationId);
            }
            pageing(noPage, allOrderForms, "OrderForms/showCreateOrderForms.html");
        }
        render("OrderForms/checkOrder.html");
    }

    /**
     * 查看订单
     *
     * @param id
     * @param message
     */
    public static void show(int id, String message) {

        OrderForm orderForm = OrderForm.findById(id);
        if (orderForm == null) {
            render("OrderForms/add.html");
        }
        render(orderForm, message);
    }

    /**
     * 从菜单点击添加订单时先跳到这里
     */
    public static void addfirst() {
            List<Station> allStation = Station.findAll();
            render("OrderForms/add.html", allStation);
    }

    /**
     * 添加验证
     *
     * @param orderForm
     * @param orderDetail
     */
    public static void add(@Valid OrderForm orderForm, @Valid OrderDetail orderDetail, int stationId) {
        
        if (validation.hasErrors()) {
            List<Station> allStation = Station.findAll();
            render(orderForm, allStation);
        }
        List<OrderForm> tmpCode = OrderForm.find("code", orderForm.code).fetch();
        List<OrderForm> tmpBarcode = OrderForm.find("barcode", orderForm.barcode).fetch();
        if (tmpCode.size() != 0 && tmpBarcode.size() != 0) {    //订单号和条形码都重复     
            String codeError = "该订单号已经存在！";
            String barcodeError = "该条形码已经存在！";
            List<Station> allStation = Station.findAll();
            render(orderForm, codeError, barcodeError, allStation);
        }
        if (tmpCode.size() != 0) {                                  //订单号重复 
            String codeError = "该订单号已经存在！";
            List<Station> allStation = Station.findAll();
            render(orderForm, codeError, allStation);
        }
        if (tmpBarcode.size() != 0) {                                //条形码重复 
            String barcodeError = "该条形码已经存在！";
            List<Station> allStation = Station.findAll();
            render(orderForm, barcodeError, allStation);
        }
        orderForm.createTime = System.currentTimeMillis();
        if (RoleNameEnum.root.toString().equals(Controller.session.get("role"))) {
            orderForm.stationId = stationId;
        } else {
            orderForm.stationId = Integer.parseInt(Controller.session.get("station_id"));
        }

        orderDetail.description = orderForm.content;
        orderDetail.isFinish = IsFinishEnum.noFinish.value;
        if (RoleNameEnum.root.toString().equals(Controller.session.get("role"))) {
            orderDetail.stationId = stationId;
        } else {
            orderDetail.stationId = Integer.parseInt(Controller.session.get("station_id"));//TODO
        }

        orderDetail.userId = Integer.parseInt(Controller.session.get("user_id"));//TODO
        orderDetail.updateTime = System.currentTimeMillis();
        orderForm.save();
        orderDetail.orderFormId = orderForm.id;
        orderDetail.save();
        String message = "订单添加成功！";
        show(orderForm.id, message);
    }

    public static void updateById(int id) {
        OrderForm orderForm = OrderForm.findById(id);
        if (orderForm == null) {
            render("OrderForms/add.html");
        }
        render("OrderForms/update.html", orderForm);
    }

    /**
     * 删除订单
     *
     * @param id
     */
    public static void delete(int id) {
        OrderForm orderForm = OrderForm.findById(id);

        if (orderForm == null) {
            index(1);
        }
        List<OrderDetail> orderDetials = OrderDetail.find("order_form_id", orderForm.id).fetch();
        for (OrderDetail od : orderDetials) {
            od.delete();
        }
        orderForm.delete();
        showCreateOrderForms(1);
    }

    /**
     * 订单修改
     *
     * @param id
     * @param code
     * @param userName
     * @param address
     * @param zipCode
     * @param content
     * @param amount
     * @param weight
     * @param barcode
     */
    public static void update(@Required(message = "id must be required!") int id, @Required(message = "订单号不能为空!") String code, @Required(message = "用户名不能为空!") String userName, @Required(message = "地址不能为空!") String address, @Required(message = "邮编不能为空!") String zipCode, @Required(message = "内容不能为空!") String content, @Required(message = "数量不能为空!") int amount, @Required(message = "重量不能为空!") float weight, @Required(message = "条形码不能为空!") String barcode) {
       
        if (validation.hasErrors()) {
            OrderForm orderForm = new OrderForm(id, userName, address, zipCode, content, amount, weight, code, barcode, amount);
            render(orderForm);
        }

        OrderForm orderForm = OrderForm.findById(id);
        if (orderForm == null) {
            render("OrderForms/add.html");
        }
        orderForm.id = id;
        orderForm.code = code;
        orderForm.address = address;
        orderForm.amount = amount;
        orderForm.barcode = barcode;
        orderForm.content = content;
        orderForm.userName = userName;
        orderForm.zipCode = zipCode;

        orderForm.save();
        String message = "订单修改成功！";
        show(orderForm.id, message);
    }

    /**
     * 按订单号查找订单类表
     *
     * @param code
     */
    public static void searchByCode(String code) {
        System.out.println(code);
       
        List<OrderForm> allOrderForm = new ArrayList<OrderForm>();
        OrderForm orderForm = OrderForm.find("code", code).first();
        if (orderForm == null) {
            render("OrderForms/index.html", allOrderForm);
        }
        if (RoleNameEnum.root.toString().equals(Controller.session.get("role"))) {
            List<OrderDetail> orderDetails = OrderDetail.find("order_form_id", orderForm.id).fetch();
            for (OrderDetail od : orderDetails) {
                if (od.nextStationId != LastStationEnum.isLastStation.value) {
                    Station nextStation = Station.findById(od.nextStationId);
                    od.nextStation = nextStation;
                    OrderForm of = new OrderForm(orderForm.id, orderForm.userName, orderForm.address, orderForm.zipCode, orderForm.content, orderForm.amount, orderForm.weight, orderForm.code, orderForm.barcode, orderForm.createTime);
                    of.orderDetail = od;
                    allOrderForm.add(of);
                }
            }
            render("OrderForms/index.html", allOrderForm);
        }
        if (RoleNameEnum.station_manager.toString().equals(Controller.session.get("role"))) {
            List<OrderDetail> orderDetails = OrderDetail.find("order_form_id = ? AND next_station_id = ?", orderForm.id, Controller.session.get("station_id")).fetch();
            if (orderDetails == null || orderDetails.size() == 0) {
                render("OrderForms/index.html", allOrderForm);
            }
            for (OrderDetail od : orderDetails) {
                Station nextStation = Station.findById(od.nextStationId);
                od.nextStation = nextStation;
                OrderForm of = new OrderForm(orderForm.id, orderForm.userName, orderForm.address, orderForm.zipCode, orderForm.content, orderForm.amount, orderForm.weight, orderForm.code, orderForm.barcode, orderForm.createTime);
                of.orderDetail = od;
                allOrderForm.add(of);
            }
            render("OrderForms/index.html", allOrderForm);
        }
        render("OrderForms/checkOrder.html");
    }

    /**
     * 根据订单号查询订单的流程
     *
     * @param orderFormCode
     * @param randomId 验证码id
     * @param code 验证码文本
     */
    public static void checkOrder(@Required(message="请填上订单号...") String orderFormCode,String randomId,@Required(message="请填上验证码...") String code) {
    	String message;
    	if(validation.hasErrors()){
        	render(orderFormCode);
        }
        if(!code.equals(Cache.get(randomId))){
        	String codemessage="验证码不正确...";
        	render(orderFormCode,codemessage);
        }
    	OrderForm orderForm = OrderForm.find("code", orderFormCode).first();
        if (orderForm == null) {
            message = "该订单不存在！";
            render(message);
        }
        List<OrderDetail> orderDetails = OrderDetail.find("order_form_id", orderForm.id).fetch();
        Collections.sort(orderDetails);
        for (OrderDetail od : orderDetails) {
            od.userInfo = UserInfo.findById(od.userId);
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            od.time = sdf.format(new Date(od.updateTime));
            od.station = Station.findById(od.stationId);
        }
        message = "查找成功！";
        render(message, orderForm, orderDetails, orderFormCode);
    }
    
    /**
     * 拿到image
     */
    public static void captcha(String id) {
    	Images.Captcha captcha = Images.captcha();
        String code = captcha.getText();
        Cache.set(id, code, "30mn");
        renderBinary(captcha);
    }
    
    private static String randomID=null;
    
    /**
     * 拿到唯一id
     * @param id
     */
    public static String getCaptchaId(int num) {
    	if(num==1){
    		randomID = Codec.UUID();
    	}
    	return randomID;
    }
}
