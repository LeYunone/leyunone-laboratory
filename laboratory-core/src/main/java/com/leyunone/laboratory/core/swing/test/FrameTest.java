package com.leyunone.laboratory.core.swing.test;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023/10/22
 */
public class FrameTest extends JFrame  {

    private JPanel contentPane;

    private JPanel panel;

    public FrameTest(){
//        this.myuserinfoview=myuserinfoview;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 440, 414);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        panel = new JPanel();
        scrollPane.setViewportView(panel);
        panel.setLayout(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new FrameTest();
    }
}
