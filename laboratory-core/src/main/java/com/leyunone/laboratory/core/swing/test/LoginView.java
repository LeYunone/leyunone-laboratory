package com.leyunone.laboratory.core.swing.test;

import javax.swing.*;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023/10/23
 */
public class LoginView extends JFrame {


    public LoginView(){

        this.setSize(350,200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel formPanel = new JPanel();
        this.add(formPanel);
        this.formPanelLoad(formPanel);

        this.setVisible(true);
    }

    private void formPanelLoad(JPanel formPanel){
        formPanel.setLayout(null);

        JLabel accountLabel = new JLabel("账号:");
        accountLabel.setBounds(10,20,80,25);
        formPanel.add(accountLabel);

        JTextField accountText = new JTextField(20);
        accountText.setBounds(100,20,165,25);
        formPanel.add(accountText);

        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(10,50,80,25);
        formPanel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,50,165,25);
        formPanel.add(passwordText);

        JButton loginButton = new JButton("登录");
        loginButton.setBounds(100, 90, 80, 25);
        loginButton.addActionListener(e -> {
            if(accountText.getText().equals("123") && passwordText.getText().equals("123")){
                JOptionPane.showMessageDialog(formPanel, "登录成功!");
                HomeView homeView = new HomeView();
                homeView.setVisible(true);

                this.setVisible(false);
            }else{
                JOptionPane.showMessageDialog(formPanel,"用户名或密码错误！");
            }
        });

        formPanel.add(loginButton);
    }

    public static void main(String[] args) {
        new LoginView();
    }
}
