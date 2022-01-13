package com.letgo.book.infrastructure.filter;

import com.letgo.shared.domain.Service;

import javax.servlet.*;
import java.io.IOException;

@Service
final public class CustomFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // change the request/response here
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // do nothing
    }
}
