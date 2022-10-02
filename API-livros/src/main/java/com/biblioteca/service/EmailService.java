package com.biblioteca.service;



import java.util.List;


public interface EmailService {

    public default void sendMails(List<String> mailsList, String mensagem) {
    }
}
