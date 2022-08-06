package com.letgo.book.infrastructure.configuration

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.jdbc.DatabaseDriver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.hibernate5.HibernateTransactionManager
import org.springframework.orm.hibernate5.LocalSessionFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.Properties
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties(DatabaseParameters::class)
open class HibernateConfiguration(
    private val dbParameters: DatabaseParameters,
) {
    @Bean
    open fun sessionFactory(): LocalSessionFactoryBean = LocalSessionFactoryBean().apply {
        setDataSource(dataSource())
        setMappingDirectoryLocations(
            FileSystemResource("./src/main/java/com/letgo/book/infrastructure/persistence/mapping")
        )
        hibernateProperties = hibernateProperties()
    }

    @Bean
    open fun dataSource(): DataSource = DriverManagerDataSource().apply {
        setDriverClassName(DatabaseDriver.MYSQL.driverClassName)
        url = "jdbc:mysql://${dbParameters.host}:${dbParameters.port}/${dbParameters.name}"
        username = dbParameters.user
        password = dbParameters.password
    }

    @Bean
    open fun hibernateTransactionManager(): PlatformTransactionManager = HibernateTransactionManager().apply {
        sessionFactory = sessionFactory().getObject()
    }

    private fun hibernateProperties(): Properties = Properties(2).apply {
        setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
        setProperty("hibernate.hbm2ddl.auto", "none")
    }
}
