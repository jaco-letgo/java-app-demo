package com.letgo.book.infrastructure.configuration

import org.springframework.boot.jdbc.DatabaseDriver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.io.FileSystemResource
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.hibernate5.HibernateTransactionManager
import org.springframework.orm.hibernate5.LocalSessionFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
open class HibernateConfiguration(
    private val environment: Environment
) {
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
            url = databaseUrl()
            username = databaseUser()
            password = databasePassword()
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

    private fun databaseName() = environment.getProperty("database.name")
    private fun databaseHost() = environment.getProperty("database.host")
    private fun databasePort() = environment.getProperty("database.port")
    private fun databasePassword() = environment.getProperty("database.password")
    private fun databaseUser() = environment.getProperty("database.user")
    private fun databaseUrl() = "jdbc:mysql://${databaseHost()}:${databasePort()}/${databaseName()}"
}
