package org.bvkatwijk.micro.consume;

import java.util.function.Consumer;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;

/**
 * Lender pattern for  not exposing a mutable object in local scope.
 * Useful for defining a chain of operations and subsequently returning
 * the original object, i.e. pojo configuration.
 *
 * @author bvkatwijk
 *
 * @param <T> Type of object to be lended to consumers
 */
@RequiredArgsConstructor
public class Subject<T> {

	private final Function<T, T> operations;

	public Subject() {
		this(Function.identity());
	}

	public Subject<T> lendTo(Consumer<T> consumer) {
		return new Subject<T>(
				operations.andThen(t -> {
					consumer.accept(t);
					return t;
				}));
	}

	public T apply(T t) {
		return operations.apply(t);
	}


}
