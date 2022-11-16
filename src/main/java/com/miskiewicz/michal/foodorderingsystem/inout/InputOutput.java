package com.miskiewicz.michal.foodorderingsystem.inout;

public interface InputOutput {
    String read();

    void write(String value);

    boolean hasNext();
}
