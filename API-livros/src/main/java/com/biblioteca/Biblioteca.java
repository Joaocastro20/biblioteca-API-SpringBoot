package com.biblioteca;

import com.biblioteca.service.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Arrays;
import java.util.List;


@SpringBootApplication
@EnableScheduling
public class Biblioteca {

    @Autowired
    private EmailService emailService;

    /*@Bean
    public CommandLineRunner runner(){
        return args -> {
            List<String> emails = Arrays.asList("45bbbaac2b-7feb62@inbox.mailtrap.io");
            emailService.sendMails(emails,"Testando serviço");
            System.out.println("enviou!");
        };
    }*/

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

	public static void main(String[] args) {
		SpringApplication.run(Biblioteca.class, args);
	}

}
