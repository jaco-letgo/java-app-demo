package com.letgo.book.infrastructure.configuration

import com.letgo.book.infrastructure.interceptor.CustomInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
open class WebConfiguration(
    private val customInterceptor: CustomInterceptor
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(customInterceptor).addPathPatterns("/books/**")
    }
}
