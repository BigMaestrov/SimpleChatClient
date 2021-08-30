package com.company;

public class Main {
    public static void main(String[] args) {
        SimpleChatClient client = new SimpleChatClient();
        client.go();
        new SimpleChatClientServer().go();
    }
}
