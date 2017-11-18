package org.bvkatwijk.micro.servlet.context;

import java.util.function.Supplier;

import org.bvkatwijk.micro.consume.Subject;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ServletContextHandlerFactory implements Supplier<ServletContextHandler> {

	private final @NonNull ServletHolder servletHolder;
	private final @NonNull String servletsUrlPath;

	@Override
	public ServletContextHandler get() {
		return new Subject<ServletContextHandler>()
				.lendTo(it -> it.addServlet(servletHolder, servletsUrlPath))
				.apply(new ServletContextHandler(ServletContextHandler.SESSIONS));
	}

}
