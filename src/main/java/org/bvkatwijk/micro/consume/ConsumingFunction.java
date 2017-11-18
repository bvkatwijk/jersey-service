package org.bvkatwijk.micro.consume;

import java.util.function.Consumer;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConsumingFunction<T> {

	private final Function<T, T> operations;

	public static <T> ConsumingFunction<T> identity() {
		return new ConsumingFunction<>(Function.identity());
	}

	public ConsumingFunction<T> andThen(Consumer<T> consumer) {
		operations.andThen(t -> { consumer.accept(t); return t;});
		return this;
	}

	public T apply(T t) {
		return operations.apply(t);
	}


}
