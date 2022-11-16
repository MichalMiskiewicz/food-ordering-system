package com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command;

import com.miskiewicz.michal.foodorderingsystem.inout.InputOutput;
import io.vavr.Tuple2;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public abstract class AbstractCommand {
    protected final InputOutput io;

    protected <T> void write(List<T> foods) {
        foods.stream().map(Objects::toString).forEach(io::write);
    }

    @RequiredArgsConstructor
    protected static class IndexedDishes<T> {
        private final Tuple2<String, T> indexed;

        public static <T> IndexedDishes<T> of(Tuple2<String, T> indexed) {
            return new IndexedDishes<>(indexed);
        }

        public String getIndex() {
            return indexed._1();
        }

        public T getValue(){
            return indexed._2();
        }

        @Override
        public String toString() {
            return "(" + indexed._1 + ") " + indexed._2().toString();
        }
    }
}
