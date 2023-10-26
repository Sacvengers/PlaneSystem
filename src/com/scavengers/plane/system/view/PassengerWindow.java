package com.scavengers.plane.system.view;

import com.scavengers.plane.system.entity.Users;
import com.scavengers.plane.system.utils.Background;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Project: ticketSystem
 * @Date: 2023/10/20 18:49
 * @author: Scavengers
 * @Description: 乘客界面类
 */
public class PassengerWindow extends JFrame implements ActionListener {
    private JButton btn_ticketBusiness, btn_contact, btn_search;
    private JTextField txt_from, txt_to, txt_departure, txt_people;
    private JRadioButton rbtn_student, rbtn_Plane;
    private JButton btn_buy, btn_orderSheet, btn_exit;
    //联系人信息
    private JButton btn_self, btn_contact1, btn_contact2, btn_modifyContact;
    private JTextField txt_tel, txt_password, txt_name, txt_id;
    //面板
    private JPanel jp_right, jp_contact, jp_ticketBusiness;
    //联系人的编号
    private int contactno;

    //获得从登录界面进来的乘客实体
    private static Users user = LoginWindow.getUser();

    public PassengerWindow(String s) {
        //设置界面题头和符号
        setTitle(s);
        String iconSrc = "picture/logo1.png";
        ImageIcon icon = new ImageIcon(iconSrc);
        setIconImage(icon.getImage());

        //自定义设置界面背景
        String bgdSrc = "picture/bg.jpg";
        ImageIcon background = new ImageIcon(bgdSrc);
        Background.setBackgroundPicture(this, background);

        //初始化车票业务和联系人信息功能按钮
        initTicketAndContactButton();
        //初始化车票业务模块
        initTicketBusinessModule();

        //主界面左部分模块:分为车票业务按钮和常用联系人按钮
        JPanel jp_left = new JPanel();
        jp_left.setOpaque(false);
        jp_left.setLayout(new GridLayout(2, 1));


        //主界面右部分模块总面板：初始显示车票业务面板
        jp_right = new JPanel();
        jp_right.setOpaque(false);
        jp_right.setLayout(new BorderLayout());
        jp_right.add(jp_ticketBusiness, BorderLayout.CENTER);

        //把左右模块面板添加到主窗体
        this.add(jp_left, BorderLayout.WEST);
        this.add(jp_right, BorderLayout.CENTER);
        this.validate();
        this.setVisible(true);
        this.setSize(700, 400);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        //选择车票业务或联系人事件
        if (e.getSource() == btn_ticketBusiness) {
            jp_right.removeAll();
            jp_right.add(jp_ticketBusiness);
            jp_right.updateUI();
            return;
        }
        //订单事件
        if (e.getSource() == btn_orderSheet) {
            new OrderSheetWindow("我的机票订单", user.getUserid());
            return;
        }

        //退出系统
        if (e.getSource() == btn_exit) {
            btn_buy.setForeground(Color.white);
            btn_exit.setForeground(Color.blue);
            int k = JOptionPane.showConfirmDialog(this, "是否退出系统？", "提醒", JOptionPane.OK_CANCEL_OPTION);
            if (k == 0) {
                dispose();
            } else {
                btn_buy.setForeground(Color.blue);
                btn_exit.setForeground(Color.white);
            }
        }
        //查询购票事件
        if (e.getSource() == btn_search) {
            String from = txt_from.getText().trim();
            String to = txt_to.getText().trim();
            String departure = txt_departure.getText().toString().trim();
            int people = Integer.parseInt(txt_people.getText().toString().trim());
            String departureDate;
            departureDate = departure;
            if (from.equals("") || to.equals("") || departure.equals("") || String.valueOf(people).equals("")) {
                JOptionPane.showMessageDialog(this, "输入均不能为空", "注意", JOptionPane.WARNING_MESSAGE);
            } else {
                new SearchResultWindow("机票查询结果", from, to, departure, people, user.getUsertel(), departureDate);
            }
        }
    }

    //主界面左部分模块:分为车票业务按钮
    private void initTicketAndContactButton() {
        //1.车票业务模块按钮
        btn_ticketBusiness = new JButton("车票业务");
        btn_ticketBusiness.setOpaque(false);
        btn_ticketBusiness.addActionListener(this);
        btn_ticketBusiness.setForeground(Color.BLACK);
        btn_ticketBusiness.setBackground(Color.DARK_GRAY); // 不设置此项按钮不透明
        btn_ticketBusiness.setHorizontalAlignment(JButton.CENTER);
        btn_ticketBusiness.setFont(new Font("楷体", Font.PLAIN, 30));
    }

    //初始化车票业务模块
    // 车票业务模块又分为四大模块：购票、订单查询、切换用户和退出系统
    private void initTicketBusinessModule() {
        //1. 车票业务界面四大模块功能按钮
        //1.1 购票模块按钮
        btn_buy = new JButton("购票");
        btn_buy.addActionListener(this);
        btn_buy.setForeground(Color.black);
        btn_buy.setBackground(Color.ORANGE);
        btn_buy.setFont(new Font("楷体", Font.PLAIN, 30));
        //1.2 订单模块按钮
        btn_orderSheet = new JButton("订单");
        btn_orderSheet.addActionListener(this);
        btn_orderSheet.setForeground(Color.black);
        btn_orderSheet.setBackground(Color.ORANGE);
        btn_orderSheet.setFont(new Font("楷体", Font.PLAIN, 30));
        //1.4 退出系统按钮
        btn_exit = new JButton("退出");
        btn_exit.addActionListener(this);
        btn_exit.setForeground(Color.black);
        btn_exit.setBackground(Color.ORANGE);
        btn_exit.setFont(new Font("楷体", Font.PLAIN, 30));
        //1.5 车票业务界面上部功能按钮面板
        JPanel jp_ticketTop = new JPanel();
        jp_ticketTop.setOpaque(false);
        jp_ticketTop.add(btn_buy);
        jp_ticketTop.add(new JLabel("   "));
        jp_ticketTop.add(btn_orderSheet);
        jp_ticketTop.add(new JLabel("   "));
        jp_ticketTop.add(btn_exit);

        //2.车票业务界面中间面板
        //2.1 出发地
        JLabel lbl_from = new JLabel("出发地:");
        lbl_from.setForeground(Color.black);
        lbl_from.setHorizontalAlignment(JLabel.CENTER);
        lbl_from.setFont(new Font("楷体", Font.PLAIN, 25));
        //2.2 到达地
        JLabel lbl_to = new JLabel("到达地:");
        lbl_to.setForeground(Color.black);
        lbl_to.setHorizontalAlignment(JLabel.CENTER);
        lbl_to.setFont(new Font("楷体", Font.PLAIN, 25));
        //2.3 出发日期
        JLabel lbl_departure = new JLabel("出发日期:");
        lbl_departure.setForeground(Color.black);
        lbl_departure.setHorizontalAlignment(JLabel.CENTER);
        lbl_departure.setFont(new Font("楷体", Font.PLAIN, 25));
        //2.4 出行人数
        JLabel lbl_people = new JLabel("出行人数:");
        lbl_people.setForeground(Color.black);
        lbl_people.setHorizontalAlignment(JLabel.CENTER);
        lbl_people.setFont(new Font("楷体", Font.PLAIN, 25));
        //2.3 录入信息框
        txt_from = new JTextField(10);
        txt_to = new JTextField(10);
        txt_departure = new JTextField(10);
        txt_people = new JTextField(10);
        //2.4 车票业务界面中间面板
        JPanel jp_tickedMid = new JPanel();
        jp_tickedMid.setOpaque(false);
        jp_tickedMid.setLayout(new GridLayout(9, 5));
        jp_tickedMid.add(new JLabel());
        jp_tickedMid.add(new JLabel());
        jp_tickedMid.add(lbl_from);
        jp_tickedMid.add(txt_from);
        jp_tickedMid.add(new JLabel());
        jp_tickedMid.add(new JLabel());
        jp_tickedMid.add(lbl_to);
        jp_tickedMid.add(txt_to);
        jp_tickedMid.add(new JLabel());
        jp_tickedMid.add(new JLabel());
        jp_tickedMid.add(lbl_departure);
        jp_tickedMid.add(txt_departure);
        jp_tickedMid.add(new JLabel());
        jp_tickedMid.add(new JLabel());
        jp_tickedMid.add(lbl_people);
        jp_tickedMid.add(txt_people);
        jp_tickedMid.add(new JLabel());
        jp_tickedMid.add(new JLabel());

        //3.车票业务界面底部面板
        //3.3 查票功能按钮
        btn_search = new JButton("查询/购票");
        btn_search.addActionListener(this);
        btn_search.setBackground(Color.orange);
        btn_search.setFont(new Font("楷体", Font.PLAIN, 20));
        //3.4 车票业务界面底部面板
        JPanel jp_ticketBottom = new JPanel();
        jp_ticketBottom.setOpaque(false);
        jp_ticketBottom.setLayout(new BorderLayout());
        jp_ticketBottom.add(btn_search, BorderLayout.SOUTH);

        //把上中下面板添加到车票业务模块总面板
        jp_ticketBusiness = new JPanel();
        jp_ticketBusiness.setOpaque(false);
        jp_ticketBusiness.setBorder(new TitledBorder("航班查询"));
        jp_ticketBusiness.setLayout(new BorderLayout());
        jp_ticketBusiness.add(jp_ticketTop, BorderLayout.NORTH);
        jp_ticketBusiness.add(jp_tickedMid, BorderLayout.CENTER);
        jp_ticketBusiness.add(jp_ticketBottom, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        new PassengerWindow("test");
    }
}
