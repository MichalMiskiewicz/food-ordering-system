package com.miskiewicz.michal.foodorderingsystem.inout;

public interface IOWriter {
    String read();

    void write(String value);

    boolean hasNext();
}
