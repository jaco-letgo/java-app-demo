package com.letgo.book.infrastructure.configuration

import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.orm.hibernate5.LocalSessionFactoryBean
import org.springframework.core.io.FileSystemResource
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.boot.jdbc.DatabaseDriver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.orm.hibernate5.HibernateTransactionManager
import java.util.*
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
open class HibernateConfiguration {
    @Bean
    open fun sessionFactory(): LocalSessionFactoryBean {
        return LocalSessionFactoryBean().apply {
            setDataSource(dataSource())
            setMappingDirectoryLocations(
                FileSystemResource("./src/main/java/com/letgo/book/infrastructure/persistence/mapping")
            )
            hibernateProperties = hibernateProperties()
        }
    }

    @Bean
    open fun dataSource(): DataSource {
        return DriverManagerDataSource().apply {
            setDriverClassName(DatabaseDriver.MYSQL.driverClassName)
            url = "jdbc:mysql://java-app-demo-mysql:3306/demo"
            username = "root"
            password = ""
        }
    }

    @Bean
    open fun hibernateTransactionManager(): PlatformTransactionManager {
        return HibernateTransactionManager().apply {
            sessionFactory = sessionFactory().getObject()
        }
    }

    private fun hibernateProperties(): Properties {
        return Properties(2).apply {
            setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
            setProperty("hibernate.hbm2ddl.auto", "none")
        }
    }
}
