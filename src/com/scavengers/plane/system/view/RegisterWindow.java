package com.scavengers.plane.system.view;

import com.scavengers.plane.system.dao.UsersDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Project: ticketSystem
 * @Date: 2023/10/20 18:49
 * @author: Scavengers
 * @Description: 业务界面
 */
public class RegisterWindow extends JFrame implements ActionListener {
    public boolean isRegister = false;
    private JTextField txt_tel, txt_name, txt_id, txt_birth;
    private JPasswordField txt_pwd1, txt_pwd2;
    private JComboBox<String> com_gender, com_idType;
    private JRadioButton rbtn_clause;
    private JLabel lbl_clause;
    private JButton btn_confirm, btn_cancel;
    private JPanel jp_show, jp_input, jp_bottom;

    public RegisterWindow(String s) {
        //设置界面题头和符号
        setTitle(s);
        ImageIcon icon = new ImageIcon("picture/logo1.png");
        setIconImage(icon.getImage());
        //初始化顶部显示信息模块
        initTopShowInfo();

        //初始化中部用户填写信息模块
        initMidUserInfo();

        //初始化底部阅读条约与界面功能按钮模块
        initBottomModule();

        //主窗体添加以上模块
        this.add(jp_show, BorderLayout.NORTH);
        this.add(jp_input, BorderLayout.CENTER);
        this.add(jp_bottom, BorderLayout.SOUTH);
        this.validate();
        this.setVisible(true);
        this.setSize(450, 400);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        new RegisterWindow("注册为乘客");
    }

    //实现监听事件
    public void actionPerformed(ActionEvent e) {
        //获取到输入信息
        String tel = txt_tel.getText().trim();
        String pwd1 = new String(txt_pwd1.getPassword());
        String pwd2 = new String(txt_pwd2.getPassword());
        String name = txt_name.getText().trim();
        String birth = txt_birth.getText().trim();
        String gender = com_gender.getSelectedItem().toString();
        String id = txt_id.getText().trim();


        //登录按钮事件
        if (e.getSource() == btn_confirm) {
            if (!inputInfoValid(tel, pwd1, pwd2, name, id)) {
                return;//如果输入无效，直接返回
            }
            UsersDao usersDao = new UsersDao();
            if (usersDao.userExisted(tel)) { //如果此账号已存在
                JOptionPane.showMessageDialog(this, "该账户已存在，登记成功！", "提醒", JOptionPane.PLAIN_MESSAGE);
                this.isRegister = true;
                dispose();
                return;
            }
            //如果用户不存在则开始注册
            boolean success = usersDao.register(tel, pwd1, name, gender, birth, id);
            if (success) { //如果注册成功
                JOptionPane.showMessageDialog(this, "该账户已成功注册登记！", "提醒", JOptionPane.PLAIN_MESSAGE);
                this.dispose();
                this.isRegister = true;
                dispose();
                return;
            } else {
                JOptionPane.showMessageDialog(this, "注册失败！", "提醒", JOptionPane.PLAIN_MESSAGE);
                return;
            }
        }
        //取消按钮事件，关闭当前窗口
        if (e.getSource() == btn_cancel) {
            this.dispose();
        }
    }

    //初始化顶部信息显示模块
    private void initTopShowInfo() {
        //界面上部显示信息
        JLabel lbl_show = new JLabel("请完善乘客资料");
        lbl_show.setFont(new Font("楷体", Font.PLAIN, 30));
        jp_show = new JPanel();
        jp_show.add(lbl_show);
    }

    //初始化中部用户填写信息模块
    private void initMidUserInfo() {
        //初始化所要填写信息的标签
        //1.手机号码标签
        JLabel lbl_tel = new JLabel("手机号码:");
        lbl_tel.setFont(new Font("楷体", Font.BOLD, 20));
        lbl_tel.setHorizontalAlignment(JLabel.CENTER);
        //2.设置密码标签
        JLabel lbl_setPassword = new JLabel("设置密码:");
        lbl_setPassword.setFont(new Font("楷体", Font.BOLD, 20));
        lbl_setPassword.setHorizontalAlignment(JLabel.CENTER);
        //3.确认密码标签
        JLabel lbl_confirmPassword = new JLabel("确认密码:");
        lbl_confirmPassword.setFont(new Font("楷体", Font.BOLD, 20));
        lbl_confirmPassword.setHorizontalAlignment(JLabel.CENTER);
        //4.姓名标签
        JLabel lbl_name = new JLabel("姓    名:");
        lbl_name.setFont(new Font("楷体", Font.BOLD, 20));
        lbl_name.setHorizontalAlignment(JLabel.CENTER);
        //出生日期
        JLabel lbl_birth = new JLabel("出生日期:");
        lbl_birth.setFont(new Font("楷体", Font.BOLD, 20));
        lbl_birth.setHorizontalAlignment(JLabel.CENTER);
        //5.性别标签
        JLabel lbl_gender = new JLabel("性    别:");
        lbl_gender.setFont(new Font("楷体", Font.BOLD, 20));
        lbl_gender.setHorizontalAlignment(JLabel.CENTER);
        //6.证件号码标签
        JLabel lbl_certificateNumber = new JLabel("证件号码:");
        lbl_certificateNumber.setFont(new Font("楷体", Font.BOLD, 20));
        lbl_certificateNumber.setHorizontalAlignment(JLabel.CENTER);
        //7.证件类型标签
        JLabel lbl_certificateType = new JLabel("证件类型:");
        lbl_certificateType.setFont(new Font("楷体", Font.BOLD, 20));
        lbl_certificateType.setHorizontalAlignment(JLabel.CENTER);

        //初始化所要填写信息的输入框
        txt_tel = new JTextField(11);
        txt_pwd1 = new JPasswordField(15);
        txt_pwd2 = new JPasswordField(15);
        txt_name = new JTextField(10);
        txt_birth = new JTextField(15);
        com_gender = new JComboBox<>(new String[]{"男", "女"});
        String[] idType = {"中华人民共和国身份证"};
        com_idType = new JComboBox<>(idType);
        txt_id = new JTextField(20);

        //初始化中部填写信息面板
        jp_input = new JPanel();
        jp_input.setLayout(new GridLayout(8, 2));
        jp_input.add(lbl_tel);
        jp_input.add(txt_tel);
        jp_input.add(lbl_setPassword);
        jp_input.add(txt_pwd1);
        jp_input.add(lbl_confirmPassword);
        jp_input.add(txt_pwd2);
        jp_input.add(lbl_name);
        jp_input.add(txt_name);
        jp_input.add(lbl_gender);
        jp_input.add(com_gender);
        jp_input.add(lbl_birth);
        jp_input.add(txt_birth);
        jp_input.add(lbl_certificateNumber);
        jp_input.add(txt_id);
        jp_input.add(lbl_certificateType);
        jp_input.add(com_idType);
    }

    //初始化底部阅读条约与界面功能按钮模块
    private void initBottomModule() {

        //功能按钮
        btn_confirm = new JButton("确认");
        btn_confirm.addActionListener(this);
        btn_cancel = new JButton("取消");
        JPanel jp_clause_confirm = new JPanel();
        jp_clause_confirm.add(btn_confirm);
        jp_clause_confirm.add(btn_cancel);

        //初始化底部模块模板
        jp_bottom = new JPanel();
        jp_bottom.setLayout(new GridLayout(2, 1));
        jp_bottom.add(jp_clause_confirm);
    }

    //检查输入信息的有效性
    private boolean inputInfoValid(String tel, String pwd1, String pwd2, String name, String id) {
        if (tel.equals("") || pwd1.equals("") || pwd1.equals("") ||
                name.equals("") || id.equals("")) {
            JOptionPane.showMessageDialog(this, "输入均不能为空", "注意", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!pwd1.equals(pwd2)) {
            JOptionPane.showMessageDialog(this, "两次密码不一致", "注意", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}

