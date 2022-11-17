package com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command;

import com.miskiewicz.michal.foodorderingsystem.inout.IOWriter;
import com.miskiewicz.michal.foodorderingsystem.requests.OrderingRequest;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public abstract class AbstractCommand implements Command{
    protected final IOWriter io;

    protected <T> void write(List<T> foods) {
        foods.stream().map(Objects::toString).forEach(io::write);
    }

    protected <T> List<AbstractCommand.IndexedDishes<T>> getIndexedDishes(List<T> elements) {
        return IntStream.range(0, elements.size())
                .mapToObj(index -> Tuple.of(String.valueOf(index), elements.get(index)))
                .map(AbstractCommand.IndexedDishes::of)
                .toList();
    }

    protected <T> T getChosen(List<AbstractCommand.IndexedDishes<T>> courses, String chosen) {
        return courses.stream().filter(
                        it -> it.getIndex().equals(chosen)
                ).map(AbstractCommand.IndexedDishes::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no element of that index!"));
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

        public T getValue() {
            return indexed._2();
        }

        @Override
        public String toString() {
            return "(" + indexed._1 + ") " + indexed._2().toString();
        }
    }
}
