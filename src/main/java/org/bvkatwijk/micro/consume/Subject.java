package org.bvkatwijk.micro.consume;

import java.util.function.Consumer;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;

/**
 * Lender pattern for  not exposing a mutable object in local scope.
 * @author boris
 *
 * @param <T> Type of object to be lended to consumers
 */
@RequiredArgsConstructor
public class Subject<T> {

	private final Function<T, T> operations = Function.identity();

	public static <T> Subject<T> identity() {
		return new Subject<>();
	}

	public Subject<T> lendTo(Consumer<T> consumer) {
		operations.andThen(t -> { consumer.accept(t); return t;});
		return this;
	}

	public T apply(T t) {
		return operations.apply(t);
	}


}
