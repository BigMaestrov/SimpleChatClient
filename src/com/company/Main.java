package com.company;

public class Main {

    static SimpleChatClientServer server = new SimpleChatClientServer();
    static SimpleChatClient client = new SimpleChatClient();

    public static void main(String[] args) {


        Thread t1 = new Thread(new MyThreadServer());
        t1.start();
        Thread t2 = new Thread(new MyThreadClient());
        t2.start();
    }

    public static class MyThreadServer implements Runnable {

        public void run() {
            try {
                server.go();
                Thread.sleep(500);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
        }
    }

    public static class MyThreadClient implements Runnable {

        public void run() {
            try {
                client.go();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
