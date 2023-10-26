package com.scavengers.plane.system.dao;

import com.scavengers.plane.system.entity.Users;
import com.scavengers.plane.system.utils.SQLHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @Project: ticketSystem
 * @Date: 2023/10/20 18:49
 * @author: Scavengers
 * @Description: 用户Dao
 */
public class UsersDao {
    //判断用户是否存在以及密码是否输入正确
    public boolean userValidate(String tel, String pwd, int type) {
        boolean existed = false;
        String mysql = "select tel,pwd from Passenger ";     //查找用户是否存在
        mysql += "where tel='" + tel + "'" + " and pwd='" + pwd + "'";
        System.out.println(mysql);
        try {
            ResultSet rs = SQLHelper.executeQuery(mysql);
            //如果用户存在
            if (rs.next()) {
                existed = true;
            }
            SQLHelper.closeConnection();
        } catch (SQLException e) {
            System.out.println("验证用户方法中报错");
        }
        return existed;
    }

    //通过用户手机号获取用户信息
    public Users userQueryByTel(String usertel) {
        String mysql = "select * from Passenger where tel='" + usertel + "'";
        System.out.println(mysql);
        return getUsers(mysql).get(0);
    }

    //通过身份证号来获取用户信息
    public Users userQueryById(String userid) {
        String mysql = "select * from Passenger where identityID='" + userid + "'";
        System.out.println(mysql);
        return getUsers(mysql).get(0);
    }

    //获得全部用户信息
    public ArrayList<Users> userQueryAll() {
        String mysql = "select * from Passenger";
        return getUsers(mysql);
    }

    //更新用户信息(用于管理员)
    public int updateUser(String usertel, String password, String username,
                          String usergender, int usertypeno, String userid) {
        String mysql = "update users set tel='" + usertel + "',pwd='" + password + "',";
        String mysql1 = "name='" + username + "',gender='" + usergender + "',";
        String mysql2 = "type=" + usertypeno + ",type=0";
        String mysql3 = " where identityID='" + userid + "'";
        mysql = mysql + mysql1 + mysql2 + mysql3;
        System.out.println(mysql);
        return SQLHelper.executeUpdate(mysql);
    }

    //删除用户信息(用于管理员)
    public int deleteUser(String userid) {
        String mysql = "delete from users where userid='" + userid + "'";
        return SQLHelper.executeUpdate(mysql);
    }

    //修改自我信息
    public int modifySelfInfo(String usertel, String password,
                              String username, String userid) {
        String mysql = "update users set tel='" + usertel +
                "',pwd='" + password + "',name='" + username + "' " +
                "where identiyuID='" + userid + "'";
        System.out.println(mysql);
        return SQLHelper.executeUpdate(mysql);
    }

    //查询用户是否存在，不存在返回false
    public boolean userExisted(String tel) {
        String mysql = "select * from Passenger where tel='" + tel + "'";
        System.out.println(mysql);
        Object obj = SQLHelper.executeSingleQuery(mysql);
        return obj != null;
    }

    //注册用户信息，返回注册是否成功
    public boolean register(String tel, String password, String name, String gender, String bitrh, String id) {
        String mysql = "insert into Passenger values('" + id +
                "','" + name + "','" + gender + "','" + bitrh + "','" + tel + "','" + password + "');";
        System.out.println(mysql);
        int rs = 0;//0表示插入失败
        try {
            rs = SQLHelper.executeUpdate(mysql); //如果更新成功，则rs=1
        } catch (Exception e) {
            System.out.println("注册用户信息报错");
        }
        return rs != 0;
    }

    //获得用户信息数组列表
    private ArrayList<Users> getUsers(String mysql) {
        ArrayList<Users> users = new ArrayList<>();
        try {
            ResultSet rs = SQLHelper.executeQuery(mysql);
            while (rs.next()) {
                Users user = new Users();
                user.setUsertel(rs.getString(5));
                user.setPassword(rs.getString(6));
                user.setUsername(rs.getString(2));
                user.setUsergender(rs.getString(3));
                user.setUserid(rs.getString(1));
                users.add(user);
            }
            SQLHelper.closeConnection();
        } catch (SQLException e) {
            System.out.println("获取用户数组列表方法中报错");
        }
        return users;
    }
}
