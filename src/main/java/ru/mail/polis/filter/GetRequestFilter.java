package ru.mail.polis.filter;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Created by iters on 9/29/17.
 */
public class GetRequestFilter extends Filter {

    @Override
    public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
        if (httpExchange.getRequestMethod().equalsIgnoreCase("GET")) {
            chain.doFilter(httpExchange);
        }

        chain.doFilter(httpExchange);
    }

    @Override
    public String description() {
        return null;
    }
}
