package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class SimpleChatClient {
    JTextArea incoming;
    JTextField outgoing;
    BufferedReader reader;
    PrintWriter writer;
    Socket socket;

    public static void main(String[] args) {
        SimpleChatClient client = new SimpleChatClient();
        client.go();
    }

    public void go() {

        JFrame jFrame = new JFrame("Simple chat client");
        JPanel mainPanel = new JPanel();
        incoming = new JTextArea(15, 50);
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(false);
        JScrollPane scroller = new JScrollPane(incoming);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outgoing = new JTextField(20);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());
        mainPanel.add(scroller);
        mainPanel.add(outgoing);
        mainPanel.add(sendButton);

        setUpNetworking();

        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();

        jFrame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        jFrame.setSize(400, 400);
        jFrame.setVisible(true);

    }

    private void setUpNetworking() {

        try {
            socket = new Socket("127.0.0.1", 5000);
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(inputStreamReader);
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("networking established");

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public class SendButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {

            try {
                writer.println(outgoing.getText());
                writer.flush();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            outgoing.setText("");
            outgoing.requestFocus();
        }
    }

    public class IncomingReader implements Runnable {
        public void run() {
            String message;
            try{
                while((message = reader.readLine())!= null){
                    System.out.println("read"+ message);
                    incoming.append(message+ "\n");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
