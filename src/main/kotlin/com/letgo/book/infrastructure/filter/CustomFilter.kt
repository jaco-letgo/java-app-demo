package com.letgo.book.infrastructure.filter

import com.letgo.shared.infrastructure.InfrastructureService
import java.io.IOException
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

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
