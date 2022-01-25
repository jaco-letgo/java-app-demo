package com.letgo.book.infrastructure.filter

import com.letgo.shared.infrastructure.InfrastructureService
import java.io.IOException
import javax.servlet.*

@InfrastructureService
class CustomFilter : Filter {
    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {
        // do nothing
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        // change the request/response here
        chain.doFilter(request, response)
    }

    override fun destroy() {
        // do nothing
    }
}
