package com.scavengers.plane.system.dao;

import com.scavengers.plane.system.entity.TicketOrder;
import com.scavengers.plane.system.utils.SQLHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @Project: ticketSystem
 * @Date: 2023/10/20 18:49
 * @author: Scavengers
 * @Description: 订单Dao
 */
public class OrderSheetsDao {

    //通过id信息获得订单详情
    public ArrayList<TicketOrder> orderSheetsQueryById(String userid) {
        String mysql = "select * from Ticket, Flight , Passenger where Passenger.identityID='" + userid + "' and Ticket.flightID = Flight.flightID and Ticket.identityID='" + userid + "'";

        System.out.println(mysql);
        return getOrderSheets(mysql);
    }

    //增加机票订单
    public int addOrderSheet(String userid, String flightID, String grade, String seat, String departureTime, int line,
                             int gate) {
        String mysql = "insert into Ticket (flightDate,grade,seat,gate,identityID,flightID,line) values ('" +
                departureTime + "','" + grade + "','" + seat + "'," + gate + ",'" + userid + "','" + flightID + "','" + line + "')";
        System.out.println(mysql);
        return SQLHelper.executeUpdate(mysql);
    }

    //删除车票(退票),根据ordersID
    public int deleteOrderSheet(String ordersID) {
        String mysql = "delete from Ticket where ordersID=" + ordersID;
        System.out.println(mysql);
        return SQLHelper.executeUpdate(mysql);
    }

    //判断用户是否买过此车次此日期的票
    public boolean haveBought(String userid, String flightID, String starttime) {
        boolean havebought = false;
        String mysql = "select * from Ticket where identityID='" + userid + "' and flightID='" + flightID + "'";
        System.out.println(mysql);
        try {
            ResultSet rs = SQLHelper.executeQuery(mysql);
            if (rs.next()) {
                havebought = true;
            }
            SQLHelper.closeConnection();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return havebought;
    }

    //获取订单信息
    private ArrayList<TicketOrder> getOrderSheets(String mysql) {
        ArrayList<TicketOrder> orderSheets = new ArrayList<>();
        try {
            ResultSet rs = SQLHelper.executeQuery(mysql);
            while (rs.next()) {
                TicketOrder orderSheet = new TicketOrder();
                orderSheet.setUserid(rs.getString(7));
                orderSheet.setUsername(rs.getString(19));
                orderSheet.setOrdersID(rs.getString(1));
                orderSheet.setflightID(rs.getString(8));
                orderSheet.setStartplace(rs.getString(13));
                orderSheet.setEndplace(rs.getString(14));
                orderSheet.setStarttime(rs.getString(16) + ' ' + rs.getString(17));
                orderSheet.setSeatno(rs.getString(9) + '-' + rs.getString(4));
                orderSheet.setPrice(rs.getString(5));
                orderSheet.setGrade(rs.getString(3));
                orderSheet.setGate(rs.getString(6));
                orderSheets.add(orderSheet);
            }
            SQLHelper.closeConnection();
        } catch (SQLException e) {
            System.out.println("获取订单信息方法报错");
        }
        return orderSheets;
    }
}
