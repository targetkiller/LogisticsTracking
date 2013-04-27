package controllers;

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
import play.data.validation.Required;
import play.mvc.Controller;
import play.mvc.Scope;

public class OrderDetails extends Controller {

    public static void index() {

        List<OrderDetail> allOrderDetail = OrderDetail.findAll();
        render(allOrderDetail);
    }

    public static void show(int orderFormId) {
        OrderForm orderForm = OrderForm.findById(orderFormId);
        String message = null;
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
        render(message, orderForm, orderDetails);
    }

    public static void addfirst(OrderForm orderForm) {

        List<Station> allStation = Station.findAll();
        List<OrderDetail> orderDetails = OrderDetail.find("order_form_id", orderForm.id).fetch();
        if (orderDetails == null || orderDetails.isEmpty()) {
            OrderForms.index(1);
        }
        Collections.sort(orderDetails);
        for (OrderDetail od : orderDetails) {
            od.userInfo = UserInfo.findById(od.userId);
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            od.time = sdf.format(new Date(od.updateTime));
            od.station = Station.findById(od.stationId);
        }
        if (orderDetails.get(orderDetails.size() - 1).isFinish == IsFinishEnum.isFinish.value) {
            OrderForms.index(1);
        }
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.orderFormId = orderForm.id;
        orderDetail.stationId = orderDetails.get(orderDetails.size() - 1).nextStationId;
        orderDetail.station = Station.findById(orderDetails.get(orderDetails.size() - 1).nextStationId);
        render("OrderDetails/add.html", orderForm, orderDetails, orderDetail, allStation);
    }

    public static void add(@Required(message = "内容不能为空") String content, int nextStationId, int orderFormId, int stationId) {
        if (validation.hasErrors()) {
            OrderForm orderForm = OrderForm.findById(orderFormId);
            List<Station> allStation = Station.findAll();
            List<OrderDetail> orderDetails = OrderDetail.find("order_form_id", orderForm.id).fetch();
            Collections.sort(orderDetails);
            for (OrderDetail od : orderDetails) {
                od.userInfo = UserInfo.findById(od.userId);
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                od.time = sdf.format(new Date(od.updateTime));
                od.station = Station.findById(od.stationId);
            }
            if (orderDetails.get(orderDetails.size() - 1).isFinish == IsFinishEnum.isFinish.value) {
                OrderForms.index(1);
            }
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.orderFormId = orderForm.id;
            orderDetail.stationId = orderDetails.get(orderDetails.size() - 1).nextStationId;
            orderDetail.station = Station.findById(orderDetails.get(orderDetails.size() - 1).nextStationId);
            render(orderForm, orderDetails, orderDetail, allStation);
        }
        List<OrderDetail> details = OrderDetail.find("order_form_id = ? AND next_station_id = ?", orderFormId, stationId).fetch();
        Collections.sort(details);

        OrderDetail od = details.get(details.size() - 1);
        od.isFinish = IsFinishEnum.isFinish.value;
        od.save();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.description = content;
        if (nextStationId == LastStationEnum.isLastStation.value) {
            orderDetail.isFinish = IsFinishEnum.isFinish.value;
        } else {
            orderDetail.isFinish = IsFinishEnum.noFinish.value;
        }
        orderDetail.nextStationId = nextStationId;
        orderDetail.stationId = stationId;
        orderDetail.orderFormId = orderFormId;
        orderDetail.updateTime = System.currentTimeMillis();
        orderDetail.userId = Integer.parseInt(Controller.session.get("user_id"));
        orderDetail.save();
        OrderForms.index(1);
    }

    public static void delete(int id) {
        OrderDetail orderDetail = OrderDetail.findById(id);
        orderDetail.delete();
        index();
    }

    public static void updatePrepare(OrderDetail orderDetail) {
        render("/OrderDetails/update.html", orderDetail);
    }

    public static void update(OrderDetail orderDetail) {
        Scope.Params p = Controller.params;

        orderDetail.orderFormId = Integer.parseInt(p.get("orderFormId"));
        orderDetail.stationId = Integer.parseInt(p.get("stationId"));
        orderDetail.userId = Integer.parseInt(p.get("userId"));
        orderDetail.nextStationId = Integer.parseInt(p.get("nextStationId"));
        orderDetail.updateTime = Integer.parseInt(p.get("updateTime"));
        orderDetail.description = p.get("description");
        orderDetail.save();
        show(orderDetail.id);
    }
}
