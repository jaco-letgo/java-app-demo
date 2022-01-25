package com.letgo.book;

import com.letgo.shared.application.bus.command.CommandHandler;
import com.letgo.shared.application.bus.query.QueryHandler;
import com.letgo.shared.domain.DomainService;
import com.letgo.shared.infrastructure.InfrastructureService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {
                                QueryHandler.class,
                                CommandHandler.class,
                        }
                ),
                @ComponentScan.Filter(
                        type = FilterType.ANNOTATION,
                        classes = {
                                DomainService.class,
                                InfrastructureService.class,
                        }
                ),
        },
        basePackages = {"com.letgo.book", "com.letgo.shared"}
)
public class App {
    public static void main(String... args) {
        SpringApplication.run(App.class, args);
    }
}
