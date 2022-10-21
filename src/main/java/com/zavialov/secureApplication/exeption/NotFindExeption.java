package com.zavialov.secureApplication.exeption;

public class NotFindExeption extends RuntimeException{
    public NotFindExeption(String msg) {
        super("<html>" +
              "<body>" +
              "<h1>Search user by city result:</h1>" +
              "<font color=\"red\" size=\"5\">" + msg + "</font>" +
              "</body>" +
              "</html>");
    }
}