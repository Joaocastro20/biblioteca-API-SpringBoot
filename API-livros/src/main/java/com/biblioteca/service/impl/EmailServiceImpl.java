package com.biblioteca.service.impl;

import com.biblioteca.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${application.mail.default-remetent}")
    private String remetent;

    private final JavaMailSender javaMailSender;
    @Override
    public void sendMails(List<String> mailsList, String mensagem) {
        String[] emails = mailsList.toArray(new String[mailsList.size()]);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(remetent);
        mailMessage.setSubject("Livro com emprestimo atrasado!");
        mailMessage.setText(mensagem);
        mailMessage.setTo(emails);

        javaMailSender.send(mailMessage);
    }
}
