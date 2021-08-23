package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.*;

public class SimpleChatClient {
    JTextField outgoing;
    PrintWriter writer;
    Socket socket;

    public void go(){
        JFrame jFrame = new JFrame("Simple chat client");
        JPanel mainPanel = new JPanel();
        outgoing = new JTextField();
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener( new SendButtonListener());
        mainPanel.add(outgoing);
        mainPanel.add(sendButton);
        jFrame.getContentPane().add(BorderLayout.NORTH, mainPanel);
        setUpNetworking();
        jFrame.setSize(400,400);
        jFrame.setVisible(true);

    }

    private void setUpNetworking(){

        try{
            socket = new Socket("127.0.0.1", 500);
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("networking established");

        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public class SendButtonListener implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){

            try{
                writer.println(outgoing.getText());
                writer.flush();

            }catch (Exception exception){
                exception.printStackTrace();
            }
            outgoing.setText("");
            outgoing.requestFocus();
        }
    }
}
