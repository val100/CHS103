/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chs103;

/**
 * Title: MyApp <br>
 * Description: <br>
 * Copyright:   Copyright (c) 2008 <br>
 * Company:     Agro Logic LTD. <br>
 * @author      Valery Manakhimov <br>
 * @version     1.0 <br>
 */
import chs103.physical.BareBonesBrowserLaunch;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MyApp {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        final JTextField urlField = new JTextField("http://www.centerkey.com       ");
        JButton webButton = new JButton("Web Trip");
        webButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                BareBonesBrowserLaunch.openURL(urlField.getText().trim());
            }
        });
        frame.setTitle("Bare Bones Browser Launch");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.add(new JLabel("URL:"));
        panel.add(urlField);
        panel.add(webButton);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
