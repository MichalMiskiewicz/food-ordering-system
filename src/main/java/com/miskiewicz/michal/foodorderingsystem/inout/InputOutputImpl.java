package com.miskiewicz.michal.foodorderingsystem.inout;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class InputOutputImpl implements InputOutput {

    private final Scanner scanner;

    @Override
    public String read() {
        return scanner.nextLine();
    }

    @Override
    public void write(String value) {
        System.out.println(value);
    }

    @Override
    public boolean hasNext() {
        return scanner.hasNext();
    }
}
