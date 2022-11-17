package com.miskiewicz.michal.foodorderingsystem.config;

import com.miskiewicz.michal.foodorderingsystem.inout.IOWriter;
import com.miskiewicz.michal.foodorderingsystem.inout.IOWriterConsoleImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Scanner;

@Configuration
public class Config {

    @Bean
    public Scanner createScanner() {
        return new Scanner(System.in);
    }

    @Bean
    @Primary
    IOWriter ioWriter() {
        return new IOWriterConsoleImpl(createScanner());
    }
}
