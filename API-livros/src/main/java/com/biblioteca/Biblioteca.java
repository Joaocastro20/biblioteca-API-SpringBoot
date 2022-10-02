package com.biblioteca;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@SpringBootApplication
@EnableScheduling
public class Biblioteca {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void agendamentoTarefas(){
        System.out.println("aqui");
    }

	public static void main(String[] args) {
		SpringApplication.run(Biblioteca.class, args);
	}

}
