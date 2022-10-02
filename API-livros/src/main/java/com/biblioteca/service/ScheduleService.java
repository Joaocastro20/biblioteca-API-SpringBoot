package com.biblioteca.service;

import com.biblioteca.api.domain.Emprestimo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final EmprestimoService emprestimoService;

    private final EmailService emailService;

    private static final String CRON_LATE_EMPRESTIMOS = "0 0 0 1/1 * ?";

    @Value("${application.mail.message}")
    private String mensagem;

    @Scheduled(cron = CRON_LATE_EMPRESTIMOS)
    public void SendMailToLateEmprestimos(){
        List<Emprestimo> allLateEmprestimo = emprestimoService.getAllLateEmprestimo();
        List<String> mailsList = allLateEmprestimo.stream()
                .map(emprestimo -> emprestimo.getCustomerEmail())
                .collect(Collectors.toList());

        emailService.sendMails(mailsList,mensagem);
    }
}
